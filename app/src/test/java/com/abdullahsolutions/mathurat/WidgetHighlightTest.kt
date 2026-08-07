package com.abdullahsolutions.mathurat

import com.abdullahsolutions.mathurat.data.PrayerTimeRepository
import com.abdullahsolutions.mathurat.model.PrayerDay
import com.abdullahsolutions.mathurat.widget.PrayerTimesWidget
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar

/**
 * Which cell the widget pills out. Uses the real captured August 2026 SGR01 table:
 * Subuh 06:01, Zohor 13:22, Asar 16:43, Maghrib 19:30, Isyak 20:42 on 1 August.
 */
class WidgetHighlightTest {

    private val day: PrayerDay by lazy {
        val json = javaClass.classLoader!!
            .getResourceAsStream("esolat_month_august.json")!!
            .bufferedReader().use { it.readText() }
        PrayerTimeRepository.parseDays(json).first { it.day == 1 }
    }

    private fun at(hour: Int, minute: Int): Long =
        Calendar.getInstance(PrayerTimeRepository.malaysiaTz).apply {
            set(2026, Calendar.AUGUST, 1, hour, minute, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

    private fun keyAt(hour: Int, minute: Int) =
        PrayerTimesWidget.currentPrayerKey(day, at(hour, minute))

    @Test
    fun `highlights the prayer currently in progress`() {
        assertEquals("fajr", keyAt(6, 30))     // after Subuh 06:01
        assertEquals("fajr", keyAt(10, 12))
        assertEquals("dhuhr", keyAt(13, 30))   // after Zohor 13:22
        assertEquals("asr", keyAt(17, 0))      // after Asar 16:43
        assertEquals("maghrib", keyAt(19, 45)) // after Maghrib 19:30
        assertEquals("isha", keyAt(21, 0))     // after Isyak 20:42
        assertEquals("isha", keyAt(23, 59))
    }

    @Test
    fun `before subuh it is still last night's isyak`() {
        assertEquals("isha", keyAt(0, 5))
        assertEquals("isha", keyAt(5, 0))
        // One minute before Subuh.
        assertEquals("isha", keyAt(6, 0))
    }

    @Test
    fun `a prayer exactly now is already the current one`() {
        assertEquals("fajr", keyAt(6, 1))
        assertEquals("dhuhr", keyAt(13, 22))
        assertEquals("isha", keyAt(20, 42))
    }

    @Test
    fun `every minute of the day resolves to a prayer`() {
        val valid = setOf("fajr", "dhuhr", "asr", "maghrib", "isha")
        for (hour in 0..23) {
            for (minute in 0..59 step 5) {
                val key = keyAt(hour, minute)
                assert(key in valid) { "unexpected key $key at $hour:$minute" }
            }
        }
    }
}
