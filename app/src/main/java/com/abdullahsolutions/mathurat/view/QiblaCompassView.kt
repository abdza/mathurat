package com.abdullahsolutions.mathurat.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.abdullahsolutions.mathurat.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Compass dial that rotates with the device heading and points a needle towards the Kaaba.
 *
 * [azimuth] is the device heading in degrees from TRUE north (0 = device top faces north).
 * [qiblaBearing] is the direction of the Kaaba in degrees from TRUE north at the user's location.
 */
class QiblaCompassView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var azimuth: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var qiblaBearing: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    /** When false the needle is drawn dimmed — location not known yet. */
    var hasQibla: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    /** Cardinal labels: Malay (U/T/S/B) or English (N/E/S/W). */
    var useEnglishCardinals: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    /** Device is pointing at the Kaaba within [ALIGN_TOLERANCE] degrees. */
    val isAligned: Boolean
        get() {
            if (!hasQibla) return false
            val diff = Math.abs(normalize(qiblaBearing - azimuth))
            return diff <= ALIGN_TOLERANCE || diff >= 360f - ALIGN_TOLERANCE
        }

    private val colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary)
    private val colorSecondary = ContextCompat.getColor(context, R.color.colorSecondary)
    private val colorDivider = ContextCompat.getColor(context, R.color.progress_track)
    private val colorTextPrimary = ContextCompat.getColor(context, R.color.text_primary)
    private val colorTextSecondary = ContextCompat.getColor(context, R.color.text_secondary)
    private val colorSurface = ContextCompat.getColor(context, R.color.surface)

    private val dialPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = colorSurface
    }

    private val ringPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = colorDivider
    }

    private val alignArcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = colorSecondary
        strokeCap = Paint.Cap.ROUND
    }

    private val tickPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    private val needlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val kaabaPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val kaabaBandPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#D4AF37")
    }

    private val indexPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = colorPrimary
    }

    private val needlePath = Path()
    private val indexPath = Path()
    private val arcRect = RectF()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = min(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        val cx = width / 2f
        val cy = height / 2f
        val outer = min(cx, cy)
        // Leave room for the fixed index triangle above the dial.
        val radius = outer - outer * 0.10f

        ringPaint.strokeWidth = radius * 0.035f
        alignArcPaint.strokeWidth = radius * 0.035f
        labelPaint.textSize = radius * 0.16f

        canvas.drawCircle(cx, cy, radius, dialPaint)
        canvas.drawCircle(cx, cy, radius, ringPaint)

        // The whole dial (ticks, cardinals, needle) turns opposite to the device heading.
        canvas.save()
        canvas.rotate(-azimuth, cx, cy)

        drawTicks(canvas, cx, cy, radius)
        drawCardinals(canvas, cx, cy, radius)

        if (hasQibla) {
            drawAlignArc(canvas, cx, cy, radius)
            drawNeedle(canvas, cx, cy, radius)
        }

        canvas.restore()

        drawHub(canvas, cx, cy, radius)
        drawIndex(canvas, cx, cy, radius)
    }

    private fun drawTicks(canvas: Canvas, cx: Float, cy: Float, radius: Float) {
        for (deg in 0 until 360 step 5) {
            val major = deg % 45 == 0
            tickPaint.color = if (major) colorPrimary else colorDivider
            tickPaint.strokeWidth = if (major) radius * 0.025f else radius * 0.012f
            val inner = radius - (if (major) radius * 0.16f else radius * 0.09f)
            val rad = Math.toRadians(deg.toDouble() - 90.0)
            val c = cos(rad).toFloat()
            val s = sin(rad).toFloat()
            canvas.drawLine(
                cx + c * inner, cy + s * inner,
                cx + c * (radius - radius * 0.04f), cy + s * (radius - radius * 0.04f),
                tickPaint
            )
        }
    }

    private fun drawCardinals(canvas: Canvas, cx: Float, cy: Float, radius: Float) {
        val labels = if (useEnglishCardinals) {
            listOf("N" to 0, "E" to 90, "S" to 180, "W" to 270)
        } else {
            listOf("U" to 0, "T" to 90, "S" to 180, "B" to 270)
        }
        val labelRadius = radius - radius * 0.30f
        val offset = (labelPaint.descent() + labelPaint.ascent()) / 2f
        for ((text, deg) in labels) {
            labelPaint.color = if (deg == 0) colorPrimary else colorTextSecondary
            val rad = Math.toRadians(deg.toDouble() - 90.0)
            val x = cx + cos(rad).toFloat() * labelRadius
            val y = cy + sin(rad).toFloat() * labelRadius
            // Keep the letters upright while the dial spins.
            canvas.save()
            canvas.rotate(azimuth, x, y)
            canvas.drawText(text, x, y - offset, labelPaint)
            canvas.restore()
        }
    }

    /** Green arc marking the tolerance window around the qibla direction. */
    private fun drawAlignArc(canvas: Canvas, cx: Float, cy: Float, radius: Float) {
        val r = radius - radius * 0.02f
        arcRect.set(cx - r, cy - r, cx + r, cy + r)
        canvas.drawArc(
            arcRect,
            qiblaBearing - 90f - ALIGN_TOLERANCE,
            ALIGN_TOLERANCE * 2f,
            false,
            alignArcPaint
        )
    }

    private fun drawNeedle(canvas: Canvas, cx: Float, cy: Float, radius: Float) {
        val aligned = isAligned
        needlePaint.color = if (aligned) colorSecondary else colorPrimary

        canvas.save()
        canvas.rotate(qiblaBearing, cx, cy)

        val tip = cy - (radius - radius * 0.34f)
        val halfWidth = radius * 0.075f
        needlePath.reset()
        needlePath.moveTo(cx, tip)
        needlePath.lineTo(cx - halfWidth, cy + radius * 0.10f)
        needlePath.lineTo(cx, cy + radius * 0.02f)
        needlePath.lineTo(cx + halfWidth, cy + radius * 0.10f)
        needlePath.close()
        canvas.drawPath(needlePath, needlePaint)

        drawKaaba(canvas, cx, tip - radius * 0.12f, radius * 0.11f, aligned)

        canvas.restore()
    }

    /** Small stylised Kaaba at the needle tip, kept upright as the dial turns. */
    private fun drawKaaba(canvas: Canvas, cx: Float, cy: Float, half: Float, aligned: Boolean) {
        canvas.save()
        canvas.rotate(azimuth - qiblaBearing, cx, cy)
        kaabaPaint.color = if (aligned) colorPrimary else colorTextPrimary
        canvas.drawRect(cx - half, cy - half, cx + half, cy + half, kaabaPaint)
        canvas.drawRect(
            cx - half, cy - half * 0.35f,
            cx + half, cy + half * 0.1f,
            kaabaBandPaint
        )
        canvas.restore()
    }

    private fun drawHub(canvas: Canvas, cx: Float, cy: Float, radius: Float) {
        needlePaint.color = colorPrimary
        canvas.drawCircle(cx, cy, radius * 0.045f, needlePaint)
    }

    /** Fixed marker at the top of the view showing where the device is pointing. */
    private fun drawIndex(canvas: Canvas, cx: Float, cy: Float, radius: Float) {
        val top = cy - radius - radius * 0.02f
        val w = radius * 0.06f
        indexPath.reset()
        indexPath.moveTo(cx, top + radius * 0.09f)
        indexPath.lineTo(cx - w, top - radius * 0.05f)
        indexPath.lineTo(cx + w, top - radius * 0.05f)
        indexPath.close()
        canvas.drawPath(indexPath, indexPaint)
    }

    private fun normalize(deg: Float): Float {
        var d = deg % 360f
        if (d < 0) d += 360f
        return d
    }

    companion object {
        const val ALIGN_TOLERANCE = 5f
    }
}
