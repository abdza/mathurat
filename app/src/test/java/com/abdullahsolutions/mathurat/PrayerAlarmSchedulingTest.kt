package com.abdullahsolutions.mathurat

import com.abdullahsolutions.mathurat.data.PrayerTimeRepository
import com.abdullahsolutions.mathurat.notification.PrayerAlarmScheduler
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar

/**
 * Covers which reminders get queued and when, using a real captured August 2026 table for
 * SGR01. Prayer times that day: Subuh 06:01, Zohor 13:22, Asar 16:41, Maghrib 19:29,
 * Isyak 20:41.
 */
class PrayerAlarmSchedulingTest {

    private val allPrayers = listOf("fajr", "dhuhr", "asr", "maghrib", "isha")

    private val data: PrayerTimeRepository.YearData by lazy {
        val json = javaClass.classLoader!!
            .getResourceAsStream("esolat_month_august.json")!!
            .bufferedReader().use { it.readText() }
        val days = PrayerTimeRepository.parseDays(json)
        assertEquals(31, days.size)
        PrayerTimeRepository.YearData("SGR01", 2026, days, 0L)
    }

    /** A moment in Malaysia local time. */
    private fun at(day: Int, hour: Int, minute: Int): Long =
        Calendar.getInstance(PrayerTimeRepository.malaysiaTz).apply {
            set(2026, Calendar.AUGUST, day, hour, minute, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

    @Test
    fun `queues the rest of today then tomorrow`() {
        val slots = PrayerAlarmScheduler.computeUpcoming(data, allPrayers, at(7, 9, 0))

        // Subuh has passed; the next five are today's, then tomorrow's Subuh.
        assertEquals(
            listOf("dhuhr", "asr", "maghrib", "isha", "fajr", "dhuhr"),
            slots.map { it.key }
        )
        assertEquals("13:22", slots.first().timeText)
        assertEquals(6, slots.size)
    }

    @Test
    fun `after isha the next reminder is tomorrow subuh`() {
        val slots = PrayerAlarmScheduler.computeUpcoming(data, allPrayers, at(7, 21, 0))

        assertEquals("fajr", slots.first().key)
        assertEquals("06:01", slots.first().timeText)

        val fired = Calendar.getInstance(PrayerTimeRepository.malaysiaTz)
            .apply { timeInMillis = slots.first().triggerAt }
        assertEquals(8, fired.get(Calendar.DAY_OF_MONTH))
        assertEquals(6, fired.get(Calendar.HOUR_OF_DAY))
        assertEquals(1, fired.get(Calendar.MINUTE))
    }

    @Test
    fun `a prayer exactly now is not re-queued`() {
        // Standing precisely at Zohor, the next slot must be Asar — not Zohor again,
        // otherwise the alarm that just fired would immediately reschedule itself.
        val slots = PrayerAlarmScheduler.computeUpcoming(data, allPrayers, at(7, 13, 22))
        assertEquals("asr", slots.first().key)
    }

    @Test
    fun `only selected prayers are queued`() {
        val slots = PrayerAlarmScheduler.computeUpcoming(
            data, listOf("fajr", "maghrib"), at(7, 9, 0)
        )
        assertTrue(slots.all { it.key == "fajr" || it.key == "maghrib" })
        assertEquals(listOf("maghrib", "fajr", "maghrib"), slots.take(3).map { it.key })
    }

    @Test
    fun `no selection means no alarms`() {
        assertTrue(PrayerAlarmScheduler.computeUpcoming(data, emptyList(), at(7, 9, 0)).isEmpty())
    }

    @Test
    fun `slots are always future and in order`() {
        listOf(at(1, 0, 1), at(7, 9, 0), at(7, 23, 59), at(15, 16, 45)).forEach { now ->
            val slots = PrayerAlarmScheduler.computeUpcoming(data, allPrayers, now)
            assertTrue("expected alarms at $now", slots.isNotEmpty())
            assertTrue(slots.all { it.triggerAt > now })
            assertEquals(slots.sortedBy { it.triggerAt }, slots)
        }
    }

    @Test
    fun `running past the end of cached data yields no alarms`() {
        // 31 August is the last cached day, so nothing can be queued after its Isha.
        val slots = PrayerAlarmScheduler.computeUpcoming(data, allPrayers, at(31, 22, 0))
        assertTrue(slots.isEmpty())
    }
}
