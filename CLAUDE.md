# Al-Mathurat Android App — Project Guide

## Project Overview

Al-Mathurat is an Android app (Kotlin) for reciting the daily Islamic wirid/zikr from the Al-Mathurat booklet compiled by Imam Hassan Al-Banna. It supports two versions (Sughra/Kubra), two sessions (Morning/Evening), tap-to-count recitation tracking, and a Reference section for prayer guides.

**Package:** `com.abdullahsolutions.mathurat`
**Language:** Kotlin
**UI:** Android Material Design with ViewBinding

---

## Physical Book Reference

The physical Al-Mathurat booklet photos are in `/home/abdza/data/kakikoding/mathurat_sample/`.

- `IMG20260321001801.jpg` through `IMG20260321002239.jpg` — pages of the actual physical booklet in order
- `Screenshot_*.jpg` — screenshots of the running app and a reference prayer app
- `doa-selepas-tarawih*.jpg`, `zikir-solat-tarawih*.jpg`, `takbir_raya.webp` — reference images for special occasions

**Always consult these photos when verifying Arabic text, page order, translations, or which items belong in Sughra vs Kubra.** The comments in `ZikrData.kt` reference page numbers (e.g. `// ms 3`) which correspond to pages in this physical booklet.

---

## Architecture

All content is **hardcoded in Kotlin** — no database, no JSON files, no external assets for content.

### Key Files

| File | Purpose |
|------|---------|
| `data/ZikrData.kt` | All 44 zikr/wirid items + `getZikr()` filter function |
| `data/ReferenceData.kt` | 6 reference categories (solat jenazah, doa qunut, etc.) |
| `model/ZikrItem.kt` | Data model + `VerseEntry` + `Session` and `Version` enums |
| `model/ReferenceEntry.kt` | Data model for reference entries |
| `MainActivity.kt` | Main screen: tabs, version toggle, recycler, count persistence |
| `SettingsActivity.kt` | Font size, language (EN/MS), transliteration toggle |
| `ReferenceListActivity.kt` | List of reference categories |
| `ReferenceDetailActivity.kt` | Detail view for a reference category |
| `adapter/ZikrAdapter.kt` | RecyclerView adapter for zikr items with tap-to-count |
| `adapter/ReferenceCategoryAdapter.kt` | Adapter for reference list |
| `adapter/ReferenceAdapter.kt` | Adapter for reference detail entries |

---

## Data Model

```kotlin
data class VerseEntry(
    val arabic: String,
    val transliteration: String = "",
    val translationMs: String = "",
    val translationEn: String = ""
)

data class ZikrItem(
    val id: Int,
    val sortOrder: Int,         // Controls display order (10, 20, 30...)
    val title: String,          // Malay title (primary)
    val titleMs: String,        // Malay subtitle/description
    val titleEn: String,        // English title
    val subtitleEn: String,     // English subtitle
    val arabic: String,         // Arabic text (full, used in single-block mode)
    val transliteration: String,
    val translation: String,    // Malay translation
    val translationEn: String,  // English translation
    val targetCount: Int,       // How many times to recite
    val sessions: Set<Session>, // MORNING, EVENING, or both
    val versions: Set<Version>, // SUGHRA, KUBRA, or both
    val pairedVerses: List<VerseEntry>? = null, // verse-by-verse display (see below)
    var currentCount: Int = 0
)

enum class Session { MORNING, EVENING }
enum class Version { SUGHRA, KUBRA }
```

---

## Verse-by-Verse Display

Multi-verse Quran items (ids 2–11, **excluding** Al-Fatihah and the 3 Quls) use `pairedVerses` to display each Arabic verse immediately followed by its translation.

- When `pairedVerses != null`: the adapter shows each `VerseEntry` in sequence (arabic → transliteration if enabled → translation), with a divider between entries. The single-block `tvArabic`/`tvTranslation` views are hidden.
- When `pairedVerses == null`: normal single-block display (all Arabic then translation).
- Items that start with Bismillah include it as the first `VerseEntry` with empty translations.
- Layout: `item_zikr.xml` contains `llContent` LinearLayout with both single-block views and `llVerses` container. `item_verse_pair.xml` is the per-verse layout.

---

## Auto-Scroll on Completion

When a user taps the counter and it reaches `targetCount`, the RecyclerView smoothly scrolls to the **top** of the next item using `LinearSmoothScroller` with `SNAP_TO_START`.

```kotlin
// In MainActivity.kt setupRecyclerView()
adapter.onItemCompleted = { position ->
    val nextPosition = position + 1
    if (nextPosition < adapter.itemCount) {
        val scroller = object : LinearSmoothScroller(this) {
            override fun getVerticalSnapPreference() = SNAP_TO_START
        }
        scroller.targetPosition = nextPosition
        binding.recyclerView.layoutManager?.startSmoothScroll(scroller)
    }
}
```

**Why `LinearSmoothScroller` with `SNAP_TO_START`:** `smoothScrollToPosition()` only scrolls enough to make the item barely visible (ends up at the bottom, hidden under tabs). `scrollToPositionWithOffset(pos, 0)` snaps instantly with no animation. `LinearSmoothScroller(SNAP_TO_START)` gives smooth animation AND positions at the top.

---

## Sughra vs Kubra

- **Sughra** = shorter/condensed version of Al-Mathurat
- **Kubra** = full/complete version

Each `ZikrItem` declares which versions it belongs to via `versions = setOf(...)`.

- `setOf(Version.SUGHRA, Version.KUBRA)` — appears in both
- `setOf(Version.KUBRA)` — Kubra only (longer/extra items)

The `getZikr(session, version)` function in `ZikrData` filters by both session and version.

**Asterisk rule:** Items marked with `*` in the physical booklet are Sughra items. Items without asterisk are Kubra only.

---

## Content List (ZikrData — 44 items)

All items appear in MORNING and EVENING unless noted. Page references (`ms X`) refer to the physical booklet in `/home/abdza/data/kakikoding/mathurat_sample/`.

### Bahagian Quran (ms 1–22)

| ID | sortOrder | Title | Book Page | Versions | pairedVerses |
|----|-----------|-------|-----------|---------|--------------|
| 1 | 10 | Al-Fatihah | ms 1 | SUGHRA + KUBRA | No |
| 2 | 20 | Al-Baqarah: 1–5 | ms 3 | SUGHRA + KUBRA | Yes (5 verses) |
| 3 | 30 | Al-Baqarah: 255–257 (Ayatul Kursi) | ms 5–8 | SUGHRA + KUBRA | Yes (3 verses) |
| 4 | 40 | Al-Baqarah: 284–286 | ms 9 | SUGHRA + KUBRA | Yes (3 verses) |
| 5 | 50 | Ali Imran: 1–2 | ms 9 (bawah) | KUBRA only | Yes (2 verses) |
| 6 | 60 | At-Tawbah: 129 | ms 11 | KUBRA only | No (single verse) |
| 7 | 70 | Al-Hashr: 22–24 | ms 11–14 | KUBRA only | Yes (3 verses) |
| 8 | 80 | Ar-Rum: 20–27 | ms 15–16 | KUBRA only | Yes (8 verses) |
| 9 | 90 | Al-Mu'minun: 115–118 | ms 17–18 | KUBRA only | Yes (4 verses) |
| 10 | 100 | Al-Kafirun | ms 19 | KUBRA only | Yes (6 verses) |
| 11 | 110 | An-Nasr | ms 21 | KUBRA only | Yes (3 verses) |
| 12 | 120 | Al-Ikhlas (x3) | ms 21 | SUGHRA + KUBRA | No |
| 13 | 130 | Al-Falaq (x3) | ms 21 | SUGHRA + KUBRA | No |
| 14 | 140 | An-Nas (x3) | ms 23 | SUGHRA + KUBRA | No |

### Zikir & Doa (ms 23–47)

| ID | sortOrder | Title | Book Page | Versions | Session |
|----|-----------|-------|-----------|---------|---------|
| 15 | 150 | Asbahna — Kerajaan Milik Allah (x3) | ms 23 | SUGHRA + KUBRA | Morning |
| 16 | 150 | Amsayna — Kerajaan Milik Allah (x3) | ms 23 | SUGHRA + KUBRA | Evening |
| 17 | 160 | Fitrah Islam — Pagi (x3) | ms 25 | SUGHRA + KUBRA | Morning |
| 18 | 160 | Fitrah Islam — Petang (x3) | ms 25 | SUGHRA + KUBRA | Evening |
| 19 | 170 | Nikmat dari Allah — Pagi (x3) | ms 25 | SUGHRA + KUBRA | Morning |
| 20 | 170 | Nikmat dari Allah — Petang (x3) | ms 25 | SUGHRA + KUBRA | Evening |
| 21 | 180 | Setiap Nikmat Dari-Mu — Pagi (x3) | ms 27 | SUGHRA + KUBRA | Morning |
| 22 | 180 | Setiap Nikmat Dari-Mu — Petang (x3) | ms 27 | SUGHRA + KUBRA | Evening |
| 23 | 190 | Ya Rabbi Lakal Hamd (x3) | ms 27 | SUGHRA + KUBRA | Both |
| 24 | 200 | Redha dengan Allah (x3) | ms 27 | SUGHRA + KUBRA | Both |
| 25 | 210 | Tasbih Adada Khalqih (x3) | ms 27 | SUGHRA + KUBRA | Both |
| 26 | 220 | Perlindungan Nama Allah (x3) | ms 29 | SUGHRA + KUBRA | Both |
| 27 | 230 | Berlindung dari Syirik (x3) | ms 29 | KUBRA only | Both |
| 28 | 240 | A'udhu Bikalimatillah (x3) | ms 29 | KUBRA only | Both |
| 29 | 250 | Berlindung dari Kesusahan (x3) | ms 31 | KUBRA only | Both |
| 30 | 260 | Sihatkan Badan dan Pancaindera (x3) | ms 31 | KUBRA only | Both |
| 31 | 270 | Berlindung dari Kufur (x3) | ms 31 | SUGHRA + KUBRA | Both |
| 32 | 280 | Sayyidul Istighfar (x3) | ms 33 | SUGHRA + KUBRA | Both |
| 33 | 290 | Istighfar (x3) | ms 33 | SUGHRA + KUBRA | Both |
| 34 | 300 | Selawat Ibrahimiyyah (x10) | ms 35 | KUBRA only | Both |
| 35 | 310 | Tasbih + Tahmid + Tahlil + Takbir (x100) | ms 35 | KUBRA only | Both |
| 36 | 320 | Tahlil (x10) | ms 37 | KUBRA only | Both |
| 37 | 330 | Kaffarat Majlis (x3) | ms 37 | SUGHRA + KUBRA | Both |
| 38 | 340 | Selawat Nabi Ummiy (x3) | ms 37 | KUBRA only | Both |
| 39 | 350 | Doa Para Sahabat (x1) | ms 39 | KUBRA only | Both |
| 40 | 360 | Doa Khusyu (x1) | ms 39–42 | KUBRA only | Both |
| 41 | 370 | Doa Awal Malam (x1) | ms 45 | SUGHRA + KUBRA | Evening only |
| 42 | 380 | Doa Rabithah (x1) | ms 47 | SUGHRA + KUBRA | Both |
| 43 | 365 | Ali Imran: 26–27 | ms 47 | SUGHRA + KUBRA | Both |
| 44 | 390 | Doa Penutup (x1) | ms 47 | SUGHRA + KUBRA | Both |

---

## Reference Section (ReferenceData — 6 categories)

1. Solat Jenazah (Funeral Prayer)
2. Doa Selepas Azan (Dua After Azan)
3. Doa Qunut (Qunut Supplication)
4. Doa Selepas Solat (Dua After Prayer)
5. Takbir Raya (Eid Takbir)
6. Zikir & Doa Tarawikh (Tarawikh Zikir & Dua)

---

## Session Detection

```kotlin
// MainActivity.kt
val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
return if (hour in 4..11) Session.MORNING else Session.EVENING
```

App auto-selects Morning tab if launched between 4am–11am, Evening otherwise.

---

## Persistence

- **`mathurat_counts`** SharedPreferences — stores recitation counts keyed by `"${SESSION}_${VERSION}_${id}"` (e.g. `"MORNING_SUGHRA_1"`)
- **`mathurat_settings`** SharedPreferences — stores:
  - `show_english` (Boolean, default false)
  - `show_transliteration` (Boolean, default false)
  - `arabic_font_size` (Float, default 28f, range 16–~60sp)

---

## Settings

User-configurable in `SettingsActivity`:
- **Language** — toggle between Malay (default) and English for UI labels, titles, translations
- **Transliteration** — show/hide romanized Arabic text
- **Arabic Font Size** — SeekBar from 16sp upward, live preview

---

## Adding or Editing Zikr Items

1. Edit `ZikrData.kt` — add a new `ZikrItem(...)` to `allZikr`
2. Assign a unique `id` (next integer after 44)
3. Set `sortOrder` (multiples of 10; items with same sortOrder appear together)
4. Set `sessions` — which session(s) it appears in
5. Set `versions` — which version(s) it appears in
6. Cross-reference the physical booklet in `/home/abdza/data/kakikoding/mathurat_sample/` for correct Arabic text, translations, and page position
7. Add `// ms X` comment to indicate the booklet page number
8. For multi-verse Quran items, add `pairedVerses = listOf(...)` with one `VerseEntry` per verse (include Bismillah as first entry with empty translations where applicable)

---

## ADB Wireless Debugging

Device connected at `192.168.1.7:39039`.

```bash
./gradlew assembleDebug
adb -s 192.168.1.7:39039 install -r app/build/outputs/apk/debug/app-debug.apk
```
