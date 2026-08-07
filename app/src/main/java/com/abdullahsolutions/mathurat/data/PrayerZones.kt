package com.abdullahsolutions.mathurat.data

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * A JAKIM e-solat zone.
 *
 * [displayName] is the best-known town in the zone, shown wherever a human should read the
 * location instead of the code — "Kota Bharu" rather than "KTN01". [areas] stays as JAKIM
 * publishes it, so a user can still confirm their district is covered.
 *
 * [lat]/[lon] is a representative point (usually that same town) used only to suggest the
 * nearest zone from the user's location — the user can always override it. [autoMatch] is
 * false for special-case zones (mountain peaks, islands, restricted areas) that should never
 * be auto-suggested even if their reference point happens to be closest.
 */
data class PrayerZone(
    val code: String,
    val displayName: String,
    val state: String,
    val areas: String,
    val lat: Double,
    val lon: Double,
    val autoMatch: Boolean = true
)

object PrayerZones {

    const val DEFAULT_ZONE = "WLY01"

    // Zone codes and area descriptions as published at
    // https://www.e-solat.gov.my/index.php?siteId=24&pageId=24
    val all = listOf(
        PrayerZone("JHR01", "Pulau Aur", "Johor", "Pulau Aur dan Pulau Pemanggil", 2.4500, 104.5200, autoMatch = false),
        PrayerZone("JHR02", "Johor Bahru", "Johor", "Johor Bahru, Kota Tinggi, Mersing, Kulai", 1.4927, 103.7414),
        PrayerZone("JHR03", "Kluang", "Johor", "Kluang, Pontian", 2.0250, 103.3167),
        PrayerZone("JHR04", "Batu Pahat", "Johor", "Batu Pahat, Muar, Segamat, Gemas Johor, Tangkak", 1.8548, 102.9325),

        PrayerZone("KDH01", "Alor Setar", "Kedah", "Kota Setar, Kubang Pasu, Pokok Sena (Daerah Kecil)", 6.1210, 100.3679),
        PrayerZone("KDH02", "Sungai Petani", "Kedah", "Kuala Muda, Yan, Pendang", 5.6470, 100.4877),
        PrayerZone("KDH03", "Padang Terap", "Kedah", "Padang Terap, Sik", 6.2500, 100.6100),
        PrayerZone("KDH04", "Baling", "Kedah", "Baling", 5.6750, 100.9180),
        PrayerZone("KDH05", "Kulim", "Kedah", "Bandar Baharu, Kulim", 5.3650, 100.5610),
        PrayerZone("KDH06", "Langkawi", "Kedah", "Langkawi", 6.3500, 99.8000),
        PrayerZone("KDH07", "Gunung Jerai", "Kedah", "Puncak Gunung Jerai", 5.7900, 100.4400, autoMatch = false),

        PrayerZone("KTN01", "Kota Bharu", "Kelantan", "Bachok, Kota Bharu, Machang, Pasir Mas, Pasir Puteh, Tanah Merah, Tumpat, Kuala Krai, Mukim Chiku", 6.1333, 102.2386),
        PrayerZone("KTN02", "Gua Musang", "Kelantan", "Gua Musang (Daerah Galas Dan Bertam), Jeli, Jajahan Kecil Lojing", 4.8823, 101.9670),

        PrayerZone("MLK01", "Melaka", "Melaka", "Seluruh Negeri Melaka", 2.1896, 102.2501),

        PrayerZone("NGS01", "Tampin", "Negeri Sembilan", "Tampin, Jempol", 2.4700, 102.2300),
        PrayerZone("NGS02", "Kuala Pilah", "Negeri Sembilan", "Jelebu, Kuala Pilah, Rembau", 2.7380, 102.2490),
        PrayerZone("NGS03", "Seremban", "Negeri Sembilan", "Port Dickson, Seremban", 2.7297, 101.9381),

        PrayerZone("PHG01", "Pulau Tioman", "Pahang", "Pulau Tioman", 2.7900, 104.1700, autoMatch = false),
        PrayerZone("PHG02", "Kuantan", "Pahang", "Kuantan, Pekan, Muadzam Shah", 3.8077, 103.3260),
        PrayerZone("PHG03", "Temerloh", "Pahang", "Jerantut, Temerloh, Maran, Bera, Chenor, Jengka", 3.4500, 102.4170),
        PrayerZone("PHG04", "Raub", "Pahang", "Bentong, Lipis, Raub", 3.7900, 101.8570),
        PrayerZone("PHG05", "Janda Baik", "Pahang", "Genting Sempah, Janda Baik, Bukit Tinggi", 3.3500, 101.8000, autoMatch = false),
        PrayerZone("PHG06", "Cameron Highlands", "Pahang", "Cameron Highlands, Genting Highlands, Bukit Fraser", 4.4710, 101.3770, autoMatch = false),
        PrayerZone("PHG07", "Rompin", "Pahang", "Zon Khas Daerah Rompin (Mukim Rompin, Mukim Endau, Mukim Pontian)", 2.8100, 103.4400),

        PrayerZone("PLS01", "Kangar", "Perlis", "Kangar, Padang Besar, Arau", 6.4414, 100.1986),

        PrayerZone("PNG01", "Pulau Pinang", "Pulau Pinang", "Seluruh Negeri Pulau Pinang", 5.4141, 100.3288),

        PrayerZone("PRK01", "Tapah", "Perak", "Tapah, Slim River, Tanjung Malim", 4.1970, 101.2600),
        PrayerZone("PRK02", "Ipoh", "Perak", "Kuala Kangsar, Sg. Siput, Ipoh, Batu Gajah, Kampar", 4.5975, 101.0901),
        PrayerZone("PRK03", "Gerik", "Perak", "Lenggong, Pengkalan Hulu, Grik", 5.4300, 101.1280),
        PrayerZone("PRK04", "Temengor", "Perak", "Temengor, Belum", 5.5500, 101.4000, autoMatch = false),
        PrayerZone("PRK05", "Teluk Intan", "Perak", "Kg Gajah, Teluk Intan, Bagan Datuk, Seri Iskandar, Beruas, Parit, Lumut, Sitiawan, Pulau Pangkor", 4.0230, 101.0210),
        PrayerZone("PRK06", "Taiping", "Perak", "Selama, Taiping, Bagan Serai, Parit Buntar", 4.8500, 100.7400),
        PrayerZone("PRK07", "Bukit Larut", "Perak", "Bukit Larut", 4.8600, 100.7900, autoMatch = false),

        PrayerZone("SBH01", "Sandakan", "Sabah", "Bahagian Sandakan (Timur), Bukit Garam, Semawang, Temanggong, Tambisan, Bandar Sandakan, Sukau", 5.8400, 118.1170),
        PrayerZone("SBH02", "Beluran", "Sabah", "Beluran, Telupid, Pinangah, Terusan, Kuamut, Bahagian Sandakan (Barat)", 5.7900, 117.4400),
        PrayerZone("SBH03", "Lahad Datu", "Sabah", "Lahad Datu, Silabukan, Kunak, Sahabat, Semporna, Tungku, Bahagian Tawau (Timur)", 5.0270, 118.3270),
        PrayerZone("SBH04", "Tawau", "Sabah", "Bandar Tawau, Balong, Merotai, Kalabakan, Bahagian Tawau (Barat)", 4.2440, 117.8910),
        PrayerZone("SBH05", "Kudat", "Sabah", "Kudat, Kota Marudu, Pitas, Pulau Banggi, Bahagian Kudat", 6.8830, 116.8480),
        PrayerZone("SBH06", "Gunung Kinabalu", "Sabah", "Gunung Kinabalu", 6.0750, 116.5580, autoMatch = false),
        PrayerZone("SBH07", "Kota Kinabalu", "Sabah", "Kota Kinabalu, Ranau, Kota Belud, Tuaran, Penampang, Papar, Putatan, Bahagian Pantai Barat", 5.9804, 116.0735),
        PrayerZone("SBH08", "Keningau", "Sabah", "Pensiangan, Keningau, Tambunan, Nabawan, Bahagian Pendalaman (Atas)", 5.3390, 116.1600),
        PrayerZone("SBH09", "Beaufort", "Sabah", "Beaufort, Kuala Penyu, Sipitang, Tenom, Long Pasia, Membakut, Weston, Bahagian Pendalaman (Bawah)", 5.3470, 115.7450),

        PrayerZone("SGR01", "Shah Alam", "Selangor", "Gombak, Petaling, Sepang, Hulu Langat, Hulu Selangor, S.Alam", 3.0733, 101.5185),
        PrayerZone("SGR02", "Kuala Selangor", "Selangor", "Kuala Selangor, Sabak Bernam", 3.3400, 101.2500),
        PrayerZone("SGR03", "Klang", "Selangor", "Klang, Kuala Langat", 3.0449, 101.4455),

        PrayerZone("SWK01", "Limbang", "Sarawak", "Limbang, Lawas, Sundar, Trusan", 4.7500, 115.0000),
        PrayerZone("SWK02", "Miri", "Sarawak", "Miri, Niah, Bekenu, Sibuti, Marudi", 4.3995, 113.9914),
        PrayerZone("SWK03", "Bintulu", "Sarawak", "Pandan, Belaga, Suai, Tatau, Sebauh, Bintulu", 3.1700, 113.0410),
        PrayerZone("SWK04", "Sibu", "Sarawak", "Sibu, Mukah, Dalat, Song, Igan, Oya, Balingian, Kanowit, Kapit", 2.2870, 111.8300),
        PrayerZone("SWK05", "Sarikei", "Sarawak", "Sarikei, Matu, Julau, Rajang, Daro, Bintangor, Belawai", 2.1280, 111.5190),
        PrayerZone("SWK06", "Sri Aman", "Sarawak", "Lubok Antu, Sri Aman, Roban, Debak, Kabong, Lingga, Engkelili, Betong, Spaoh, Pusa, Saratok", 1.2370, 111.4630),
        PrayerZone("SWK07", "Serian", "Sarawak", "Serian, Simunjan, Samarahan, Sebuyau, Meludam", 1.1700, 110.5700),
        PrayerZone("SWK08", "Kuching", "Sarawak", "Kuching, Bau, Lundu, Sematan", 1.5533, 110.3592),
        PrayerZone("SWK09", "Kampung Patarikan", "Sarawak", "Zon Khas (Kampung Patarikan)", 1.6000, 111.0000, autoMatch = false),

        PrayerZone("TRG01", "Kuala Terengganu", "Terengganu", "Kuala Terengganu, Marang, Kuala Nerus", 5.3302, 103.1408),
        PrayerZone("TRG02", "Besut", "Terengganu", "Besut, Setiu", 5.7430, 102.5030),
        PrayerZone("TRG03", "Hulu Terengganu", "Terengganu", "Hulu Terengganu", 5.0630, 102.8600),
        PrayerZone("TRG04", "Dungun", "Terengganu", "Dungun, Kemaman", 4.7570, 103.4200),

        PrayerZone("WLY01", "Kuala Lumpur", "Wilayah Persekutuan", "Kuala Lumpur, Putrajaya", 3.1390, 101.6869),
        PrayerZone("WLY02", "Labuan", "Wilayah Persekutuan", "Labuan", 5.2800, 115.2400)
    )

    fun byCode(code: String): PrayerZone? = all.firstOrNull { it.code == code }

    /** Human-readable place name for a zone code, falling back to the code itself. */
    fun displayNameFor(code: String): String = byCode(code)?.displayName ?: code

    /** Nearest auto-matchable zone to the given point, used to pre-select a zone. */
    fun nearest(lat: Double, lon: Double): PrayerZone =
        all.filter { it.autoMatch }.minByOrNull { haversineKm(lat, lon, it.lat, it.lon) }
            ?: byCode(DEFAULT_ZONE)!!

    private fun haversineKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2) * sin(dLon / 2)
        return 2 * atan2(sqrt(a), sqrt(1 - a)) * 6371.0
    }
}
