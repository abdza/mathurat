package com.abdullahsolutions.mathurat

import android.content.Context
import com.abdullahsolutions.mathurat.model.Achievement
import com.abdullahsolutions.mathurat.model.Session
import com.abdullahsolutions.mathurat.model.Version
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object AchievementManager {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    // ── Achievement catalogue ─────────────────────────────────────────────────

    val all: List<Achievement> = listOf(

        // First completions
        Achievement("FIRST_SUGHRA_MORNING", "🌅",
            "Permulaan Pagi Sughra", "First Sughra Morning",
            "Pertama kali khatam Al-Mathurat Sughra waktu pagi",
            "Completed Al-Mathurat Sughra in the morning for the first time"),
        Achievement("FIRST_SUGHRA_EVENING", "🌇",
            "Permulaan Petang Sughra", "First Sughra Evening",
            "Pertama kali khatam Al-Mathurat Sughra waktu petang",
            "Completed Al-Mathurat Sughra in the evening for the first time"),
        Achievement("FIRST_KUBRA_MORNING", "🌅",
            "Permulaan Pagi Kubra", "First Kubra Morning",
            "Pertama kali khatam Al-Mathurat Kubra waktu pagi",
            "Completed Al-Mathurat Kubra in the morning for the first time"),
        Achievement("FIRST_KUBRA_EVENING", "🌇",
            "Permulaan Petang Kubra", "First Kubra Evening",
            "Pertama kali khatam Al-Mathurat Kubra waktu petang",
            "Completed Al-Mathurat Kubra in the evening for the first time"),

        // First full day (both sessions)
        Achievement("FIRST_FULL_DAY_SUGHRA", "☀️",
            "Hari Penuh Pertama — Sughra", "First Full Day — Sughra",
            "Khatam Sughra pagi dan petang dalam satu hari",
            "Completed Sughra both morning and evening in one day"),
        Achievement("FIRST_FULL_DAY_KUBRA", "☀️",
            "Hari Penuh Pertama — Kubra", "First Full Day — Kubra",
            "Khatam Kubra pagi dan petang dalam satu hari",
            "Completed Kubra both morning and evening in one day"),

        // ── Sughra Morning streaks ────────────────────────────────────────────
        Achievement("STREAK_SUGHRA_MORNING_3", "🔥",
            "3 Hari Pagi Sughra", "3-Day Sughra Morning Streak",
            "Khatam Sughra pagi 3 hari berturut-turut",
            "3 consecutive Sughra mornings"),
        Achievement("STREAK_SUGHRA_MORNING_7", "🔥",
            "Seminggu Pagi Sughra", "Week of Sughra Mornings",
            "Khatam Sughra pagi 7 hari berturut-turut",
            "7 consecutive Sughra mornings"),
        Achievement("STREAK_SUGHRA_MORNING_30", "⭐",
            "Sebulan Pagi Sughra", "Month of Sughra Mornings",
            "Khatam Sughra pagi 30 hari berturut-turut",
            "30 consecutive Sughra mornings"),
        Achievement("STREAK_SUGHRA_MORNING_90", "🌟",
            "3 Bulan Pagi Sughra", "3-Month Sughra Morning Streak",
            "Khatam Sughra pagi 90 hari berturut-turut",
            "90 consecutive Sughra mornings"),
        Achievement("STREAK_SUGHRA_MORNING_365", "💎",
            "Setahun Pagi Sughra", "Year of Sughra Mornings",
            "Khatam Sughra pagi 365 hari berturut-turut",
            "365 consecutive Sughra mornings"),

        // ── Sughra Evening streaks ────────────────────────────────────────────
        Achievement("STREAK_SUGHRA_EVENING_3", "🔥",
            "3 Hari Petang Sughra", "3-Day Sughra Evening Streak",
            "Khatam Sughra petang 3 hari berturut-turut",
            "3 consecutive Sughra evenings"),
        Achievement("STREAK_SUGHRA_EVENING_7", "🔥",
            "Seminggu Petang Sughra", "Week of Sughra Evenings",
            "Khatam Sughra petang 7 hari berturut-turut",
            "7 consecutive Sughra evenings"),
        Achievement("STREAK_SUGHRA_EVENING_30", "⭐",
            "Sebulan Petang Sughra", "Month of Sughra Evenings",
            "Khatam Sughra petang 30 hari berturut-turut",
            "30 consecutive Sughra evenings"),
        Achievement("STREAK_SUGHRA_EVENING_90", "🌟",
            "3 Bulan Petang Sughra", "3-Month Sughra Evening Streak",
            "Khatam Sughra petang 90 hari berturut-turut",
            "90 consecutive Sughra evenings"),
        Achievement("STREAK_SUGHRA_EVENING_365", "💎",
            "Setahun Petang Sughra", "Year of Sughra Evenings",
            "Khatam Sughra petang 365 hari berturut-turut",
            "365 consecutive Sughra evenings"),

        // ── Sughra Full Day streaks ───────────────────────────────────────────
        Achievement("STREAK_SUGHRA_FULL_3", "🔥",
            "3 Hari Penuh Sughra", "3-Day Sughra Full Streak",
            "Khatam Sughra pagi dan petang 3 hari berturut-turut",
            "3 consecutive full days of Sughra"),
        Achievement("STREAK_SUGHRA_FULL_7", "🔥",
            "Seminggu Penuh Sughra", "Week of Full Sughra Days",
            "Khatam Sughra pagi dan petang 7 hari berturut-turut",
            "7 consecutive full days of Sughra"),
        Achievement("STREAK_SUGHRA_FULL_30", "⭐",
            "Sebulan Penuh Sughra", "Month of Full Sughra Days",
            "Khatam Sughra pagi dan petang 30 hari berturut-turut",
            "30 consecutive full days of Sughra"),
        Achievement("STREAK_SUGHRA_FULL_90", "🌟",
            "3 Bulan Penuh Sughra", "3-Month Full Sughra Streak",
            "Khatam Sughra pagi dan petang 90 hari berturut-turut",
            "90 consecutive full days of Sughra"),
        Achievement("STREAK_SUGHRA_FULL_365", "💎",
            "Setahun Penuh Sughra", "Year of Full Sughra Days",
            "Khatam Sughra pagi dan petang 365 hari berturut-turut",
            "365 consecutive full days of Sughra"),

        // ── Kubra Morning streaks ─────────────────────────────────────────────
        Achievement("STREAK_KUBRA_MORNING_3", "🔥",
            "3 Hari Pagi Kubra", "3-Day Kubra Morning Streak",
            "Khatam Kubra pagi 3 hari berturut-turut",
            "3 consecutive Kubra mornings"),
        Achievement("STREAK_KUBRA_MORNING_7", "🔥",
            "Seminggu Pagi Kubra", "Week of Kubra Mornings",
            "Khatam Kubra pagi 7 hari berturut-turut",
            "7 consecutive Kubra mornings"),
        Achievement("STREAK_KUBRA_MORNING_30", "⭐",
            "Sebulan Pagi Kubra", "Month of Kubra Mornings",
            "Khatam Kubra pagi 30 hari berturut-turut",
            "30 consecutive Kubra mornings"),
        Achievement("STREAK_KUBRA_MORNING_90", "🌟",
            "3 Bulan Pagi Kubra", "3-Month Kubra Morning Streak",
            "Khatam Kubra pagi 90 hari berturut-turut",
            "90 consecutive Kubra mornings"),
        Achievement("STREAK_KUBRA_MORNING_365", "💎",
            "Setahun Pagi Kubra", "Year of Kubra Mornings",
            "Khatam Kubra pagi 365 hari berturut-turut",
            "365 consecutive Kubra mornings"),

        // ── Kubra Evening streaks ─────────────────────────────────────────────
        Achievement("STREAK_KUBRA_EVENING_3", "🔥",
            "3 Hari Petang Kubra", "3-Day Kubra Evening Streak",
            "Khatam Kubra petang 3 hari berturut-turut",
            "3 consecutive Kubra evenings"),
        Achievement("STREAK_KUBRA_EVENING_7", "🔥",
            "Seminggu Petang Kubra", "Week of Kubra Evenings",
            "Khatam Kubra petang 7 hari berturut-turut",
            "7 consecutive Kubra evenings"),
        Achievement("STREAK_KUBRA_EVENING_30", "⭐",
            "Sebulan Petang Kubra", "Month of Kubra Evenings",
            "Khatam Kubra petang 30 hari berturut-turut",
            "30 consecutive Kubra evenings"),
        Achievement("STREAK_KUBRA_EVENING_90", "🌟",
            "3 Bulan Petang Kubra", "3-Month Kubra Evening Streak",
            "Khatam Kubra petang 90 hari berturut-turut",
            "90 consecutive Kubra evenings"),
        Achievement("STREAK_KUBRA_EVENING_365", "💎",
            "Setahun Petang Kubra", "Year of Kubra Evenings",
            "Khatam Kubra petang 365 hari berturut-turut",
            "365 consecutive Kubra evenings"),

        // ── Kubra Full Day streaks ────────────────────────────────────────────
        Achievement("STREAK_KUBRA_FULL_3", "🔥",
            "3 Hari Penuh Kubra", "3-Day Kubra Full Streak",
            "Khatam Kubra pagi dan petang 3 hari berturut-turut",
            "3 consecutive full days of Kubra"),
        Achievement("STREAK_KUBRA_FULL_7", "🔥",
            "Seminggu Penuh Kubra", "Week of Full Kubra Days",
            "Khatam Kubra pagi dan petang 7 hari berturut-turut",
            "7 consecutive full days of Kubra"),
        Achievement("STREAK_KUBRA_FULL_30", "⭐",
            "Sebulan Penuh Kubra", "Month of Full Kubra Days",
            "Khatam Kubra pagi dan petang 30 hari berturut-turut",
            "30 consecutive full days of Kubra"),
        Achievement("STREAK_KUBRA_FULL_90", "🌟",
            "3 Bulan Penuh Kubra", "3-Month Full Kubra Streak",
            "Khatam Kubra pagi dan petang 90 hari berturut-turut",
            "90 consecutive full days of Kubra"),
        Achievement("STREAK_KUBRA_FULL_365", "💎",
            "Setahun Penuh Kubra", "Year of Full Kubra Days",
            "Khatam Kubra pagi dan petang 365 hari berturut-turut",
            "365 consecutive full days of Kubra")
    )

    private val byId = all.associateBy { it.id }

    // ── Storage helpers ───────────────────────────────────────────────────────

    private fun compPrefs(context: Context) =
        context.getSharedPreferences("mathurat_completions", Context.MODE_PRIVATE)

    private fun achPrefs(context: Context) =
        context.getSharedPreferences("mathurat_achievements", Context.MODE_PRIVATE)

    private fun dateString(cal: Calendar) = dateFormat.format(cal.time)

    fun isEarned(id: String, context: Context): Boolean =
        achPrefs(context).getLong("ach_$id", -1L) != -1L

    fun getEarnedDate(id: String, context: Context): Long? {
        val v = achPrefs(context).getLong("ach_$id", -1L)
        return if (v == -1L) null else v
    }

    // ── Streak calculation ────────────────────────────────────────────────────

    /** sessionKey: "MORNING", "EVENING", or null for full-day (both required) */
    private fun calculateStreak(version: Version, sessionKey: String?, context: Context): Int {
        val prefs = compPrefs(context)
        val cal = Calendar.getInstance()
        var streak = 0
        repeat(400) {
            val ds = dateString(cal)
            val done = if (sessionKey != null) {
                prefs.getBoolean("comp_${version.name}_${sessionKey}_$ds", false)
            } else {
                prefs.getBoolean("comp_${version.name}_MORNING_$ds", false) &&
                prefs.getBoolean("comp_${version.name}_EVENING_$ds", false)
            }
            if (!done) return streak
            streak++
            cal.add(Calendar.DAY_OF_YEAR, -1)
        }
        return streak
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Call this when the user finishes all items for [version]/[session].
     * Records the completion for today and returns any newly earned achievements.
     * Completing Kubra also counts as completing Sughra (Sughra is a subset of Kubra).
     */
    fun recordAndCheck(version: Version, session: Session, context: Context): List<Achievement> {
        val newly = mutableListOf<Achievement>()
        // Kubra completion implicitly includes Sughra
        if (version == Version.KUBRA) {
            newly.addAll(recordAndCheckSingle(Version.SUGHRA, session, context))
        }
        newly.addAll(recordAndCheckSingle(version, session, context))
        return newly
    }

    private fun recordAndCheckSingle(version: Version, session: Session, context: Context): List<Achievement> {
        val today = dateString(Calendar.getInstance())
        val compPrefs = compPrefs(context)
        val achPrefs = achPrefs(context)

        // Record today's completion
        compPrefs.edit()
            .putBoolean("comp_${version.name}_${session.name}_$today", true)
            .apply()

        val newly = mutableListOf<Achievement>()
        val now = System.currentTimeMillis()

        fun award(id: String) {
            if (!isEarned(id, context)) {
                achPrefs.edit().putLong("ach_$id", now).apply()
                byId[id]?.let { newly.add(it) }
            }
        }

        // First completion achievements
        award("FIRST_${version.name}_${session.name}")

        // Full day check
        val otherSession = if (session == Session.MORNING) Session.EVENING else Session.MORNING
        val otherDone = compPrefs.getBoolean("comp_${version.name}_${otherSession.name}_$today", false)
        if (otherDone) award("FIRST_FULL_DAY_${version.name}")

        // Streak achievements
        val morningStreak = calculateStreak(version, "MORNING", context)
        val eveningStreak = calculateStreak(version, "EVENING", context)
        val fullStreak = calculateStreak(version, null, context)

        listOf(3, 7, 30, 90, 365).forEach { days ->
            if (morningStreak >= days) award("STREAK_${version.name}_MORNING_$days")
            if (eveningStreak >= days) award("STREAK_${version.name}_EVENING_$days")
            if (fullStreak >= days) award("STREAK_${version.name}_FULL_$days")
        }

        return newly
    }

    /** Returns current streak for display purposes */
    fun getStreaks(version: Version, context: Context): Triple<Int, Int, Int> {
        val morning = calculateStreak(version, "MORNING", context)
        val evening = calculateStreak(version, "EVENING", context)
        val full = calculateStreak(version, null, context)
        return Triple(morning, evening, full)
    }
}
