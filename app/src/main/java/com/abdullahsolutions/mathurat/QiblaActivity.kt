package com.abdullahsolutions.mathurat

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.GeomagneticField
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import android.view.MenuItem
import android.view.Surface
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.abdullahsolutions.mathurat.databinding.ActivityQiblaBinding
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

/**
 * Qibla (kiblat) finder. Combines the device compass with the user's location to point
 * towards the Kaaba. The last known location is cached so the screen still works offline.
 */
class QiblaActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityQiblaBinding

    private val settingsPrefs by lazy { getSharedPreferences("mathurat_settings", Context.MODE_PRIVATE) }
    private val qiblaPrefs by lazy { getSharedPreferences("mathurat_qibla", Context.MODE_PRIVATE) }

    private val sensorManager by lazy { getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    private val locationManager by lazy { getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    private val rotationSensor: Sensor? by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) }
    private val accelSensor: Sensor? by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    private val magneticSensor: Sensor? by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) }

    private val vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    private val rotationMatrix = FloatArray(9)
    private val remappedMatrix = FloatArray(9)
    private val orientation = FloatArray(3)
    private var accelValues: FloatArray? = null
    private var magneticValues: FloatArray? = null

    // Smoothed heading, tracked as a unit vector so it wraps correctly across 0/360.
    private var smoothSin = 0f
    private var smoothCos = 0f
    private var hasHeading = false

    private var declination = 0f
    private var qiblaBearing = 0f
    private var location: Location? = null
    private var wasAligned = false
    private var lowAccuracy = false
    private var awaitingFix = false

    private val handler = Handler(Looper.getMainLooper())
    private val en get() = settingsPrefs.getBoolean("show_english", false)

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(loc: Location) {
            stopLocationUpdates()
            applyLocation(loc, cache = true)
        }

        @Deprecated("Required for API < 29")
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private val fixTimeout = Runnable {
        stopLocationUpdates()
        if (location == null) updateStatus()
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { granted ->
        if (granted.values.any { it }) {
            loadLocation()
        } else {
            updateStatus()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQiblaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.title = if (en) "Qibla Direction" else "Arah Kiblat"
        binding.tvBearingLabel.text =
            if (en) "Qibla bearing from true north" else "Arah kiblat dari utara sebenar"
        binding.compassView.useEnglishCardinals = en

        // Edge to edge from Android 15: keep the action button clear of the nav bar.
        ViewCompat.setOnApplyWindowInsetsListener(binding.content) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = bars.bottom)
            insets
        }

        binding.btnAction.setOnClickListener { onActionClicked() }

        restoreCachedLocation()
        updateStatus()
    }

    override fun onResume() {
        super.onResume()
        registerSensors()
        loadLocation()
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        stopLocationUpdates()
    }

    // ---------------------------------------------------------------- sensors

    private fun registerSensors() {
        val rate = SensorManager.SENSOR_DELAY_GAME
        when {
            rotationSensor != null ->
                sensorManager.registerListener(this, rotationSensor, rate)
            accelSensor != null && magneticSensor != null -> {
                sensorManager.registerListener(this, accelSensor, rate)
                sensorManager.registerListener(this, magneticSensor, rate)
            }
        }
    }

    private fun hasCompass(): Boolean =
        rotationSensor != null || (accelSensor != null && magneticSensor != null)

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ROTATION_VECTOR -> {
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                updateHeadingFromMatrix(rotationMatrix)
            }
            Sensor.TYPE_ACCELEROMETER -> {
                accelValues = event.values.clone()
                updateHeadingFromRawSensors()
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                magneticValues = event.values.clone()
                updateHeadingFromRawSensors()
            }
        }
    }

    private fun updateHeadingFromRawSensors() {
        val accel = accelValues ?: return
        val magnetic = magneticValues ?: return
        if (!SensorManager.getRotationMatrix(rotationMatrix, null, accel, magnetic)) return
        updateHeadingFromMatrix(rotationMatrix)
    }

    private fun updateHeadingFromMatrix(matrix: FloatArray) {
        // Compensate for the screen orientation so the dial stays correct in landscape.
        val (axisX, axisY) = when (displayRotation()) {
            Surface.ROTATION_90 -> SensorManager.AXIS_Y to SensorManager.AXIS_MINUS_X
            Surface.ROTATION_180 -> SensorManager.AXIS_MINUS_X to SensorManager.AXIS_MINUS_Y
            Surface.ROTATION_270 -> SensorManager.AXIS_MINUS_Y to SensorManager.AXIS_X
            else -> SensorManager.AXIS_X to SensorManager.AXIS_Y
        }
        SensorManager.remapCoordinateSystem(matrix, axisX, axisY, remappedMatrix)
        SensorManager.getOrientation(remappedMatrix, orientation)

        // Magnetic heading plus declination gives true north, which the qibla bearing uses.
        val magneticHeading = Math.toDegrees(orientation[0].toDouble()).toFloat()
        val trueHeading = Math.toRadians((magneticHeading + declination).toDouble()).toFloat()

        val s = sin(trueHeading)
        val c = cos(trueHeading)
        if (hasHeading) {
            smoothSin += (s - smoothSin) * SMOOTHING
            smoothCos += (c - smoothCos) * SMOOTHING
        } else {
            smoothSin = s
            smoothCos = c
            hasHeading = true
        }

        val azimuth = normalize(Math.toDegrees(atan2(smoothSin, smoothCos).toDouble()).toFloat())
        binding.compassView.azimuth = azimuth
        updateAlignment()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        if (sensor?.type == Sensor.TYPE_MAGNETIC_FIELD ||
            sensor?.type == Sensor.TYPE_ROTATION_VECTOR
        ) {
            lowAccuracy = accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE ||
                accuracy == SensorManager.SENSOR_STATUS_ACCURACY_LOW
            updateStatus()
        }
    }

    @Suppress("DEPRECATION")
    private fun displayRotation(): Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display?.rotation ?: Surface.ROTATION_0
        } else {
            windowManager.defaultDisplay.rotation
        }

    // --------------------------------------------------------------- location

    private fun hasLocationPermission(): Boolean =
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED

    private fun restoreCachedLocation() {
        if (!qiblaPrefs.contains("lat")) return
        val cached = Location("cache").apply {
            latitude = qiblaPrefs.getFloat("lat", 0f).toDouble()
            longitude = qiblaPrefs.getFloat("lon", 0f).toDouble()
            time = qiblaPrefs.getLong("time", 0L)
        }
        applyLocation(cached, cache = false)
    }

    private fun loadLocation() {
        if (!hasLocationPermission()) {
            updateStatus()
            return
        }
        bestLastKnownLocation()?.let { applyLocation(it, cache = true) }
        requestFreshFix()
    }

    private fun bestLastKnownLocation(): Location? {
        if (!hasLocationPermission()) return null
        return try {
            locationManager.getProviders(true)
                .mapNotNull { locationManager.getLastKnownLocation(it) }
                .maxByOrNull { it.time }
        } catch (e: SecurityException) {
            null
        }
    }

    private fun requestFreshFix() {
        if (awaitingFix || !hasLocationPermission()) return
        val providers = listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER)
            .filter { locationManager.isProviderEnabled(it) }
        if (providers.isEmpty()) {
            updateStatus()
            return
        }
        try {
            providers.forEach {
                locationManager.requestLocationUpdates(it, 0L, 0f, locationListener, Looper.getMainLooper())
            }
            awaitingFix = true
            handler.postDelayed(fixTimeout, FIX_TIMEOUT_MS)
            updateStatus()
        } catch (e: SecurityException) {
            awaitingFix = false
        }
    }

    private fun stopLocationUpdates() {
        if (!awaitingFix) return
        awaitingFix = false
        handler.removeCallbacks(fixTimeout)
        try {
            locationManager.removeUpdates(locationListener)
        } catch (e: SecurityException) {
            // nothing to remove
        }
    }

    private fun applyLocation(loc: Location, cache: Boolean) {
        location = loc
        qiblaBearing = calculateQiblaBearing(loc.latitude, loc.longitude)
        declination = GeomagneticField(
            loc.latitude.toFloat(),
            loc.longitude.toFloat(),
            loc.altitude.toFloat(),
            if (loc.time > 0) loc.time else System.currentTimeMillis()
        ).declination

        binding.compassView.qiblaBearing = qiblaBearing
        binding.compassView.hasQibla = true
        binding.tvBearing.text = String.format(Locale.US, "%.1f°", qiblaBearing)

        if (cache) {
            qiblaPrefs.edit()
                .putFloat("lat", loc.latitude.toFloat())
                .putFloat("lon", loc.longitude.toFloat())
                .putLong("time", System.currentTimeMillis())
                .apply()
        }
        updateStatus()
        updateAlignment()
    }

    // ------------------------------------------------------------------- math

    /** Great-circle initial bearing from the given point to the Kaaba, in degrees from true north. */
    private fun calculateQiblaBearing(lat: Double, lon: Double): Float {
        val phi = Math.toRadians(lat)
        val deltaLon = Math.toRadians(KAABA_LON - lon)
        val phiK = Math.toRadians(KAABA_LAT)
        val y = sin(deltaLon)
        val x = cos(phi) * tan(phiK) - sin(phi) * cos(deltaLon)
        return normalize(Math.toDegrees(atan2(y, x)).toFloat())
    }

    /** Haversine distance to the Kaaba in kilometres. */
    private fun distanceToKaaba(lat: Double, lon: Double): Double {
        val dLat = Math.toRadians(KAABA_LAT - lat)
        val dLon = Math.toRadians(KAABA_LON - lon)
        val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat)) * cos(Math.toRadians(KAABA_LAT)) *
            sin(dLon / 2) * sin(dLon / 2)
        return 2 * atan2(sqrt(a), sqrt(1 - a)) * EARTH_RADIUS_KM
    }

    private fun normalize(deg: Float): Float {
        var d = deg % 360f
        if (d < 0) d += 360f
        return d
    }

    // --------------------------------------------------------------------- UI

    private fun updateAlignment() {
        if (location == null) {
            binding.tvAlignment.text = if (en) "Waiting for location…" else "Menunggu lokasi…"
            binding.tvAlignment.setBackgroundResource(R.drawable.bg_qibla_pill)
            binding.tvAlignment.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            return
        }

        val aligned = binding.compassView.isAligned
        if (aligned) {
            binding.tvAlignment.text = if (en) "Facing the Qibla" else "Menghadap Kiblat"
            binding.tvAlignment.setBackgroundResource(R.drawable.bg_qibla_pill_aligned)
            binding.tvAlignment.setTextColor(ContextCompat.getColor(this, R.color.white))
            if (!wasAligned) vibrateAligned()
        } else {
            val diff = normalize(qiblaBearing - binding.compassView.azimuth)
            val turnRight = diff <= 180f
            val amount = if (turnRight) diff else 360f - diff
            val direction = if (turnRight) {
                if (en) "right" else "ke kanan"
            } else {
                if (en) "left" else "ke kiri"
            }
            binding.tvAlignment.text = if (en) {
                String.format(Locale.US, "Turn %s %.0f°", direction, amount)
            } else {
                String.format(Locale.US, "Pusing %s %.0f°", direction, amount)
            }
            binding.tvAlignment.setBackgroundResource(R.drawable.bg_qibla_pill)
            binding.tvAlignment.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        }
        wasAligned = aligned
    }

    private fun updateStatus() {
        val loc = location
        when {
            !hasCompass() -> {
                binding.tvStatus.text = if (en) {
                    "This device has no compass sensor."
                } else {
                    "Peranti ini tiada sensor kompas."
                }
                binding.btnAction.visibility = View.GONE
            }
            !hasLocationPermission() -> {
                binding.tvStatus.text = if (en) {
                    "Location permission is needed to work out the qibla direction."
                } else {
                    "Kebenaran lokasi diperlukan untuk mengira arah kiblat."
                }
                binding.btnAction.text = if (en) "Allow Location" else "Benarkan Lokasi"
                binding.btnAction.visibility = View.VISIBLE
            }
            loc == null -> {
                binding.tvStatus.text = if (awaitingFix) {
                    if (en) "Getting your location…" else "Mendapatkan lokasi anda…"
                } else {
                    if (en) {
                        "Location unavailable. Turn on location services and try again."
                    } else {
                        "Lokasi tidak tersedia. Hidupkan perkhidmatan lokasi dan cuba lagi."
                    }
                }
                binding.btnAction.text = if (en) "Update Location" else "Kemas Kini Lokasi"
                binding.btnAction.visibility = View.VISIBLE
            }
            else -> {
                val distance = distanceToKaaba(loc.latitude, loc.longitude)
                val coords = String.format(
                    Locale.US, "%.4f°, %.4f°", loc.latitude, loc.longitude
                )
                val distanceText = String.format(Locale.US, "%,.0f km", distance)
                binding.tvStatus.text = if (en) {
                    "$coords · $distanceText to the Kaaba"
                } else {
                    "$coords · $distanceText ke Kaabah"
                }
                binding.btnAction.text = if (en) "Update Location" else "Kemas Kini Lokasi"
                binding.btnAction.visibility = View.VISIBLE
            }
        }

        if (lowAccuracy && hasCompass()) {
            binding.tvWarning.visibility = View.VISIBLE
            binding.tvWarning.text = if (en) {
                "Compass accuracy is low — move your phone in a figure-8 to calibrate, and keep away from metal or magnets."
            } else {
                "Ketepatan kompas rendah — gerakkan telefon dalam bentuk angka 8 untuk menentukur, dan jauhkan dari logam atau magnet."
            }
        } else {
            binding.tvWarning.visibility = View.GONE
        }
    }

    private fun onActionClicked() {
        if (!hasLocationPermission()) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            return
        }
        val anyProviderOn = LocationManager.GPS_PROVIDER.let { locationManager.isProviderEnabled(it) } ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!anyProviderOn) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            return
        }
        loadLocation()
    }

    /** Called when the phone first lines up with the qibla. */
    private fun vibrateAligned() {
        if (!vibrator.hasVibrator()) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(60, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(60)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val KAABA_LAT = 21.4224779
        private const val KAABA_LON = 39.8251832
        private const val EARTH_RADIUS_KM = 6371.0
        private const val SMOOTHING = 0.12f
        private const val FIX_TIMEOUT_MS = 20_000L
    }
}
