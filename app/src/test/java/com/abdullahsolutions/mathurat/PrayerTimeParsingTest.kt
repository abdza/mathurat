package com.abdullahsolutions.mathurat

import com.abdullahsolutions.mathurat.data.PrayerTimeRepository
import com.abdullahsolutions.mathurat.data.PrayerZones
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Parsing is checked against real captured e-solat responses
 * (`app/src/test/resources/esolat_*.json`), since the Malay month names in the `date`
 * field are the easiest thing to get wrong.
 */
class PrayerTimeParsingTest {

    private fun fixture(name: String): String =
        javaClass.classLoader!!.getResourceAsStream(name)!!.bufferedReader().use { it.readText() }

    @Test
    fun `parses every malay month name`() {
        val days = PrayerTimeRepository.parseDays(fixture("esolat_year_sample.json"))

        // One row per month plus 31 December.
        assertEquals(13, days.size)
        assertEquals((1..12).toList(), days.take(12).map { it.month })
        assertTrue(days.all { it.year == 2026 })
    }

    @Test
    fun `parses fields of a known day`() {
        val days = PrayerTimeRepository.parseDays(fixture("esolat_year_sample.json"))
        val august = days.first { it.month == 8 }

        assertEquals(1, august.day)
        assertEquals(2026, august.year)
        assertEquals("1448-02-17", august.hijri)
        // Times are trimmed from "HH:mm:ss" to "HH:mm".
        assertEquals("06:01", august.fajr)
        assertEquals("13:22", august.dhuhr)
        assertEquals("19:30", august.maghrib)
        assertEquals("20:42", august.isha)
    }

    @Test
    fun `handles the last day of the year`() {
        val days = PrayerTimeRepository.parseDays(fixture("esolat_year_sample.json"))
        val last = days.last()

        assertEquals(31, last.day)
        assertEquals(12, last.month)
        assertEquals(2026, last.year)
    }

    @Test
    fun `unknown zone response yields no days instead of crashing`() {
        // For an unknown zone the API returns prayerTime as an OBJECT, not an array.
        assertTrue(PrayerTimeRepository.parseDays(fixture("esolat_no_record.json")).isEmpty())
    }

    @Test
    fun `malformed payloads yield no days`() {
        assertTrue(PrayerTimeRepository.parseDays("").isEmpty())
        assertTrue(PrayerTimeRepository.parseDays("not json").isEmpty())
        assertTrue(PrayerTimeRepository.parseDays("""{"status":"OK!"}""").isEmpty())
        assertTrue(
            PrayerTimeRepository.parseDays(
                """{"status":"OK!","prayerTime":[{"date":"01-Zzz-2026","fajr":"06:01:00"}]}"""
            ).isEmpty()
        )
    }

    @Test
    fun `five obligatory prayers are marked, markers are not`() {
        val day = PrayerTimeRepository.parseDays(fixture("esolat_year_sample.json")).first()
        val prayers = day.entries().filter { it.isPrayer }.map { it.key }

        assertEquals(listOf("fajr", "dhuhr", "asr", "maghrib", "isha"), prayers)
        assertFalse(day.entries().first { it.key == "syuruk" }.isPrayer)
        assertFalse(day.entries().first { it.key == "imsak" }.isPrayer)
    }

    @Test
    fun `nearest zone matches well known cities`() {
        assertEquals("WLY01", PrayerZones.nearest(3.1390, 101.6869).code)   // Kuala Lumpur
        assertEquals("SWK08", PrayerZones.nearest(1.5533, 110.3592).code)   // Kuching
        assertEquals("SBH07", PrayerZones.nearest(5.9804, 116.0735).code)   // Kota Kinabalu
        assertEquals("PNG01", PrayerZones.nearest(5.4141, 100.3288).code)   // George Town
        assertEquals("JHR02", PrayerZones.nearest(1.4927, 103.7414).code)   // Johor Bahru
        assertEquals("KTN01", PrayerZones.nearest(6.1333, 102.2386).code)   // Kota Bharu
    }

    @Test
    fun `special zones are never auto-suggested`() {
        val special = listOf("KDH07", "PRK04", "PRK07", "SBH06", "SWK09", "PHG01", "JHR01")
        // Standing right on Gunung Kinabalu still resolves to a normal zone.
        assertEquals("SBH07", PrayerZones.nearest(6.0750, 116.5580).code)
        special.forEach { code ->
            val zone = PrayerZones.byCode(code)
            assertNotNull("missing zone $code", zone)
            assertFalse("$code should not auto-match", zone!!.autoMatch)
        }
    }

    @Test
    fun `all 60 published zones are present and unique`() {
        assertEquals(60, PrayerZones.all.size)
        assertEquals(60, PrayerZones.all.map { it.code }.toSet().size)
        assertNotNull(PrayerZones.byCode(PrayerZones.DEFAULT_ZONE))
    }

    @Test
    fun `every zone has a human place name that is not the code`() {
        PrayerZones.all.forEach { zone ->
            assertTrue("${zone.code} has a blank name", zone.displayName.isNotBlank())
            assertFalse(
                "${zone.code} display name is just the code",
                zone.displayName.equals(zone.code, ignoreCase = true)
            )
        }
        assertEquals("Kota Bharu", PrayerZones.displayNameFor("KTN01"))
        assertEquals("Kuala Lumpur", PrayerZones.displayNameFor("WLY01"))
        assertEquals("Shah Alam", PrayerZones.displayNameFor("SGR01"))
        assertEquals("Kuching", PrayerZones.displayNameFor("SWK08"))
    }

    @Test
    fun `unknown zone code falls back to the code itself`() {
        assertEquals("XXX99", PrayerZones.displayNameFor("XXX99"))
    }
}
