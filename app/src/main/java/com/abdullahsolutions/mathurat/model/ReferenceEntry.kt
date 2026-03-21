package com.abdullahsolutions.mathurat.model

enum class EntryType {
    SECTION_HEADING,   // Large bold heading (e.g. "Takbir Pertama")
    INSTRUCTION,       // Explanatory paragraph text
    ARABIC,            // Arabic text (right-aligned, Amiri font)
    TRANSLITERATION,   // Italic romanization
    TRANSLATION,       // Regular translation text
    NOTE               // Small italic note
}

data class ReferenceEntry(
    val type: EntryType,
    val content: String,
    val contentEn: String = ""
)

data class ReferenceCategory(
    val id: Int,
    val title: String,
    val titleEn: String,
    val subtitle: String,
    val subtitleEn: String,
    val entries: List<ReferenceEntry>
)
