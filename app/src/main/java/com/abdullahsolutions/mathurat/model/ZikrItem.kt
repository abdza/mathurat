package com.abdullahsolutions.mathurat.model

data class VerseEntry(
    val arabic: String,
    val transliteration: String = "",
    val translationMs: String = "",
    val translationEn: String = ""
)

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
    val pairedVerses: List<VerseEntry>? = null,
    var currentCount: Int = 0
) {
    val isCompleted: Boolean get() = currentCount >= targetCount

    fun reset() { currentCount = 0 }
}

enum class Session { MORNING, EVENING }

enum class Version { SUGHRA, KUBRA }
