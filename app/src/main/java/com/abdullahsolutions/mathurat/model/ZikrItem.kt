package com.abdullahsolutions.mathurat.model

data class ZikrItem(
    val id: Int,
    val sortOrder: Int,
    val title: String,
    val titleMs: String,
    val titleEn: String = "",
    val subtitleEn: String = "",
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val translationEn: String = "",
    val targetCount: Int,
    val sessions: Set<Session>,
    val versions: Set<Version>,
    var currentCount: Int = 0
) {
    val isCompleted: Boolean get() = currentCount >= targetCount

    fun reset() { currentCount = 0 }
}

enum class Session { MORNING, EVENING }

enum class Version { SUGHRA, KUBRA }
