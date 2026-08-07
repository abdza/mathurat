package com.abdullahsolutions.mathurat.model

/** One day of prayer times as published by JAKIM e-solat. Times are "HH:mm" in Malaysia time. */
data class PrayerDay(
    val year: Int,
    val month: Int,
    val day: Int,
    val hijri: String,
    val date: String,
    val imsak: String,
    val fajr: String,
    val syuruk: String,
    val dhuha: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String
) {
    /**
     * Everything shown on screen. `isPrayer` marks the five obligatory prayers — the others
     * are timing markers (imsak, sunrise, dhuha) and are never announced as "next prayer".
     */
    fun entries(): List<Entry> = listOf(
        Entry("imsak", imsak, isPrayer = false),
        Entry("fajr", fajr, isPrayer = true),
        Entry("syuruk", syuruk, isPrayer = false),
        Entry("dhuha", dhuha, isPrayer = false),
        Entry("dhuhr", dhuhr, isPrayer = true),
        Entry("asr", asr, isPrayer = true),
        Entry("maghrib", maghrib, isPrayer = true),
        Entry("isha", isha, isPrayer = true)
    )

    data class Entry(val key: String, val time: String, val isPrayer: Boolean)
}
