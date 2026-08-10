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
| `ZikrCounterActivity.kt` | Standalone tap counter with flash + sound |
| `QiblaActivity.kt` | Qibla finder: compass sensors + location → bearing to the Kaaba |
| `view/QiblaCompassView.kt` | Custom-drawn compass dial with Kaaba needle |
| `PrayerTimesActivity.kt` | Prayer times screen: next-prayer countdown, zone picker, reminders |
| `data/PrayerTimeRepository.kt` | JAKIM e-solat fetch + year-long disk cache |
| `data/PrayerZones.kt` | All 60 JAKIM zones + nearest-zone lookup |
| `model/PrayerDay.kt` | One day of prayer times |
| `notification/PrayerAlarmScheduler.kt` | Queues the next few prayer alarms |
| `notification/PrayerAlarmReceiver.kt` | Fires at a prayer time → notify + re-queue |
| `notification/PrayerBootReceiver.kt` | Re-queues after reboot / clock change / update |
| `notification/PrayerNotifier.kt` | Notification channel + posting |
| `notification/PrayerNotificationSettings.kt` | Reminder on/off + per-prayer toggles |
| `widget/PrayerTimesWidget.kt` | Home screen prayer times widget |
| `widget/ZikrCounterWidget.kt` | Home screen tap-to-count zikir widget |

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

Multi-verse Quran items use `pairedVerses` to display each Arabic verse immediately followed by its translation. This includes Quran surahs (ids 2, 5, 7–11, 49–52) but **excludes** Al-Fatihah, single verses (Ayatul Kursi, Tawbah 129), and the 3 Quls.

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

## Content List (ZikrData — 48 items)

All items appear in MORNING and EVENING unless noted. Page references (`ms X`) refer to the physical booklet in `/home/abdza/data/kakikoding/mathurat_sample/`.

### Bahagian Quran (ms 1–22)

| ID | sortOrder | Title | Book Page | Versions | pairedVerses |
|----|-----------|-------|-----------|---------|--------------|
| 1 | 10 | Al-Fatihah | ms 1 | SUGHRA + KUBRA | No |
| 2 | 20 | Al-Baqarah: 1–5 | ms 3 | SUGHRA + KUBRA | Yes (5 verses) |
| 3 | 30 | Al-Baqarah: 255 (Ayatul Kursi) | ms 5 | SUGHRA + KUBRA | No |
| 45 | 31 | Al-Baqarah: 256 | ms 7 | SUGHRA + KUBRA | No |
| 46 | 32 | Al-Baqarah: 257 | ms 8 | SUGHRA + KUBRA | No |
| 4 | 40 | Al-Baqarah: 284 | ms 9 | SUGHRA + KUBRA | No |
| 47 | 41 | Al-Baqarah: 285 | ms 9 | SUGHRA + KUBRA | No |
| 48 | 42 | Al-Baqarah: 286 | ms 9 | SUGHRA + KUBRA | No |
| 5 | 50 | Ali Imran: 1–2 | ms 9 (bawah) | KUBRA only | Yes (2 verses) |
| 49 | 55 | Taha: 111–112 | ms 10 | KUBRA only | Yes (2 verses) |
| 6 | 60 | At-Tawbah: 129 (x7) | ms 11 | KUBRA only | No (single verse) |
| 50 | 65 | Al-Isra': 110–111 | ms 11–12 | KUBRA only | Yes (2 verses) |
| 9 | 70 | Al-Mu'minun: 115–118 | ms 13–14 | KUBRA only | Yes (4 verses) |
| 8 | 80 | Ar-Rum: 17–26 | ms 13–16 | KUBRA only | Yes (10 verses) |
| 51 | 85 | Al-Mukmin (Ghafir): 1–3 | ms 17 | KUBRA only | Yes (3 verses) |
| 7 | 87 | Al-Hashr: 22–24 | ms 17–18 | KUBRA only | Yes (3 verses) |
| 52 | 89 | Az-Zalzalah: 1–8 | ms 19 | KUBRA only | Yes (8 verses) |
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
- **`mathurat_zikr_counter`** SharedPreferences — stores:
  - `count` (Int, default 0) — standalone Zikir Counter value, persists across app restarts
- **`mathurat_qibla`** SharedPreferences — stores the last known location for the Qibla finder:
  - `lat` / `lon` (Float) and `time` (Long) — lets the compass work without a fresh GPS fix
- **`mathurat_prayer`** SharedPreferences — prayer times + reminders:
  - `zone` (String) — selected JAKIM zone code
  - `notify_enabled` (Boolean, default false) — master reminder toggle
  - `notify_<prayer>` (Boolean, default true) — per-prayer toggle (`fajr`, `dhuhr`, `asr`,
    `maghrib`, `isha`)
- **`filesDir/prayer_<ZONE>_<YEAR>.json`** — cached year of prayer times (not SharedPreferences,
  the payload is ~77 KB)

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

## Zikir Counter (ZikrCounterActivity)

Standalone tap counter accessible from the main menu (⋮ → Kaunter Zikir / Zikir Counter).

- Full-screen tap zone — tap anywhere to increment
- Big number (120sp) centred on screen
- **Color flash** — `ValueAnimator` ArgbEvaluator fades background from green to normal (350ms) on each tap
- **Click sound** — `ToneGenerator(STREAM_MUSIC, 60)` with `TONE_PROP_BEEP` at 40ms
- **Reset FAB** at bottom — resets count to 0
- Count persists in `mathurat_zikr_counter` SharedPreferences
- Title/labels respect the `show_english` setting

---

## Prayer Times (PrayerTimesActivity)

Waktu solat from JAKIM, in the main menu (⋮ → Waktu Solat / Prayer Times).

### The API

JAKIM's e-solat site has an undocumented JSON endpoint behind the public page:

```
https://www.e-solat.gov.my/index.php?r=esolatApi/takwimsolat&period=year&zone=SGR01
```

`period` accepts `today`, `week`, `month`, `year`. **`year` returns all 365 days in a single
~77 KB response**, and is what the app uses. (`period=duration` with `datestart`/`dateend`
returns HTTP 500 — don't bother.) The API always serves the *current* calendar year; the year
cannot be requested explicitly, so next year's table cannot be pre-fetched.

Response shape (times are Malaysia time, `HH:mm:ss`):

```json
{"prayerTime":[{"hijri":"1448-02-23","date":"07-Ogos-2026","day":"Friday",
  "imsak":"05:51:00","fajr":"06:01:00","syuruk":"07:11:00","dhuha":"07:36:00",
  "dhuhr":"13:22:00","asr":"16:41:00","maghrib":"19:29:00","isha":"20:41:00"}],
 "status":"OK!","zone":"SGR01","bearing":"291&#176; 7&#8242; 23&#8243;"}
```

Two traps, both covered by tests:
- `date` uses **Malay** month names: Jan, Feb, **Mac**, Apr, **Mei**, Jun, Jul, **Ogos**, Sep,
  **Okt**, Nov, **Dis**.
- For an unknown zone, `status` is `NO_RECORD!` and **`prayerTime` is an object, not an array** —
  parsing must not assume an array.

### Keeping load off their server

`PrayerTimeRepository` is built to be a good citizen — normally **one request per zone per year**:

- fetches the whole year, never polls per day
- caches raw JSON in `filesDir/prayer_<ZONE>_<YEAR>.json` (too big for SharedPreferences)
- serves cache instantly, revalidating in the background only after 90 days (catches the rare
  mid-year correction) — so ~1–5 requests a year, plus manual refreshes
- a failed request backs off 5 minutes instead of retrying on every screen open
- concurrent loads for the same zone/year collapse into one
- prunes cache files for other zones and past years

### Screen

- Next-prayer card with a live `HH:MM:SS` countdown, rolling over to tomorrow's Subuh after Isyak
- All 8 rows (Imsak, Subuh, Syuruk, Dhuha, Zohor, Asar, Maghrib, Isyak); only the five obligatory
  prayers are eligible as "next" — `PrayerDay.Entry.isPrayer` marks them
- Zone card opens a picker of all 60 zones, plus "Guna lokasi saya" which picks the nearest zone
  using the location cached by the Qibla screen (`mathurat_qibla` prefs)
- Times are resolved in `Asia/Kuala_Lumpur` regardless of device timezone, since that is the
  calendar JAKIM publishes against
- Selected zone persists in `mathurat_prayer` SharedPreferences (`zone`)

### Zone data

`PrayerZones.all` holds all 60 codes with a representative lat/lon per zone, used **only** to
suggest a default. `autoMatch = false` on special zones (Gunung Kinabalu, Bukit Larut, Puncak
Gunung Jerai, Temengor/Belum, Pulau Tioman, Pulau Aur, Cameron/Genting, Kg Patarikan) so they are
never auto-suggested — a user near Gunung Kinabalu should get SBH07, not SBH06.

### Prayer time reminders (azan notifications)

Toggled from a card on the prayer times screen, with a per-prayer picker (all five on by default).

**Scheduling** — `PrayerAlarmScheduler` queues only the next `MAX_ALARMS` (6) prayers rather
than a standing daily alarm. Each alarm re-queues the rest when it fires. Because alarms do not
survive reboots, `PrayerBootReceiver` also re-queues on `BOOT_COMPLETED`, `MY_PACKAGE_REPLACED`,
`TIME_SET`, `TIMEZONE_CHANGED`, and the exact-alarm permission change broadcast. Everything is
read from the **on-disk cache**, so reminders keep working with no network.

**Exact alarms** — `SCHEDULE_EXACT_ALARM` is not granted by default from Android 13, so the
scheduler checks `canScheduleExactAlarms()` and falls back to `setAndAllowWhileIdle` (a few
minutes of drift) instead of failing. The screen explains this and links to the settings page.
Deliberately **not** using `USE_EXACT_ALARM` — Play policy restricts it to alarm/calendar apps.

**Notification sound** — the channel is created with the default notification sound; the app
ships **no azan audio**. To hear a real azan the user points the channel at their own file via
Android's per-channel notification settings. Note that a channel's settings are fixed once
created, so changing the in-app default later requires a new channel ID.

**Permissions** — `POST_NOTIFICATIONS` is requested when the toggle is switched on (Android 13+);
if denied, the toggle flips back rather than pretending to be armed. The screen re-checks
system-level notification blocking and exact-alarm permission in `onResume`.

**Small icon** — `ic_notification_prayer.xml`, a flat white crescent. Status bar icons are alpha
masks, so a launcher icon there would render as a white blob.

### Tests

`app/src/test/` holds JVM unit tests (`./gradlew testDebugUnitTest`, 22 tests) run against **real
captured API responses** in `app/src/test/resources/`:

- `PrayerTimeParsingTest` — Malay month names, the `NO_RECORD!` object-not-array shape,
  malformed payloads, nearest-zone matching, all 60 zones present
- `PrayerAlarmSchedulingTest` — `PrayerAlarmScheduler.computeUpcoming()` (the pure, clock-free
  core): rollover to tomorrow's Subuh after Isyak, a prayer exactly "now" not re-queueing itself
  (which would loop), per-prayer filtering, running past the end of cached data
- `WidgetHighlightTest` — `PrayerTimesWidget.currentPrayerKey()`: which cell is pilled through
  the day, the pre-Subuh fallback to Isyak, and that every minute of the day resolves to a prayer

Both scheduler and widget helpers take the clock as a parameter rather than reading
`System.currentTimeMillis()` internally — that is what makes the day boundaries testable.

`org.json:json` is a test dependency because the android.jar stub throws "not mocked".

---

## Home Screen Widgets

Two `AppWidgetProvider`s. Both read the same SharedPreferences the in-app screens use, so
widget and app never disagree.

### Zikir Counter (`widget/ZikrCounterWidget.kt`, 3x2)

Large tap target that counts, small reset beside it.

- Tap and reset are `PendingIntent.getBroadcast` back into the provider itself
  (`WIDGET_ZIKR_INCREMENT` / `WIDGET_ZIKR_RESET`). **Request codes must be unique per
  action AND per widget id** or the PendingIntents collide across placed widgets.
- Shares `mathurat_zikr_counter`'s `count` with `ZikrCounterActivity`. The activity pushes
  `ZikrCounterWidget.updateAll()` after every tap/reset, and re-reads the count in `onResume`
  because the widget may have changed it while the activity was backgrounded.
- Mirrors the in-app tap feedback: the same `ToneGenerator` beep on every tap, and vibration
  per the `mathurat_settings` vibrate toggles (every tap if `vibrate_on_click`, milestones at
  33/100). **Widget vibration must be sent with `USAGE_ALARM` vibration attributes** —
  Android 12+ silently drops background-process vibrations (a widget tap runs in a broadcast
  receiver) unless they carry an alerting usage. Plain `vibrate()` never reaches the motor.
- `widget_count_on_right` (`mathurat_settings`, default false) flips the layout for
  right-handed use: reset moves to the left, the big count target to the right. Toggled from
  SettingsActivity, which calls `updateAll()` so placed widgets redraw immediately. The layout
  has a reset block on each side and `render()` shows exactly one.
- `updatePeriodMillis="0"` — it only ever redraws in response to a tap.
- **Reset is immediate, with no confirmation.** That is what was asked for; if accidental
  taps become a problem, the cheap fix is a two-tap arm ("Ketuk lagi") rather than a dialog,
  since widgets cannot show dialogs.

### Prayer Times (`widget/PrayerTimesWidget.kt`, 4x1)

Compact 4x1. Mosque mark on the left, zone on top, and the **whole day as a row of five times**
below. Tap opens `PrayerTimesActivity`. **No countdown** — the widget is a glanceable table.

- **No prayer names in the table.** At 4x1 there is no room, and the order (Subuh, Zohor, Asar,
  Maghrib, Isyak) is fixed and familiar, so the times alone carry it.
- The pill marks the prayer **currently in progress** — `currentPrayerKey()`, the last prayer
  that has already started. Note this is *not* the next prayer: at 10:12 the Subuh cell is lit,
  not Zohor. Before Subuh it falls back to `isha` (still the previous night's Isyak), so the
  row is never blank.
- Highlighting uses `setInt(id, "setBackgroundResource", …)` plus `setTextColor` —
  RemoteViews has no typeface setter, so a pill reads better than trying to bold text.
  **Every cell is set explicitly on each render** (`0` clears the background), otherwise the
  highlight would smear across cells as the day advances.
- Nothing on the widget ticks, so the refresh alarm is now the *only* thing that moves the
  pill — it is scheduled at the next prayer time (`nextPrayer()` is still used for exactly
  this, even though the next prayer is no longer displayed).

- Reads only the on-disk cache — **the widget never hits the network**. With no cache it says
  "Buka aplikasi sekali untuk memuat turun waktu solat."
- The countdown is a **`Chronometer` with `setChronometerCountDown(true)`**, so it ticks
  locally. That is the whole reason the widget does not need frequent remote updates: it only
  needs one when the *next prayer* changes, so it sets a single inexact alarm at that moment
  (`WIDGET_PRAYER_REFRESH`). `updatePeriodMillis` of 30 min is only a safety net.
- Refreshed also from `PrayerAlarmReceiver` (prayer boundary), `PrayerBootReceiver` (reboot
  loses the alarm), and `PrayerTimesActivity` after a load or zone change.
- Next prayer ignores the notification per-prayer toggles — the widget shows the schedule,
  not the reminder settings.

### Gotchas

- Widget layouts may only use RemoteViews-supported views. `Chronometer` is supported;
  `styles` and custom shape drawables are fine.
- Previews use `android:previewLayout` (the real layout) rather than a preview PNG asset.
- **Changing `targetCellWidth/Height` does not resize widgets already on the home screen.**
  An existing placement keeps its old cell size and just re-renders the new layout inside it;
  the user has to resize it by hand or remove and re-add.

---

## Mosque Motif (`ic_mosque_dome.xml`)

Flat mosque silhouette — dome, spire, two minarets — drawn as straight lines plus one arc so
it stays crisp at any size and tints cleanly. Used in two places:

- The prayer times widget, at 28dp in `colorPrimary`.
- A watermark on the next-prayer card in `PrayerTimesActivity`, at 120dp in white with
  `alpha="0.13"`, anchored bottom-end with negative margins so it bleeds off the card corner.

Keep it a single flat shape (no strokes, no gradients) — it has to survive being tinted and
scaled down to a 24dp status-bar-sized mark.

---

## Qibla Finder (QiblaActivity)

Compass pointing to the Kaaba, accessible from the main menu (⋮ → Arah Kiblat / Qibla Direction).

- **Heading** — `TYPE_ROTATION_VECTOR` when available, otherwise `ACCELEROMETER` + `MAGNETIC_FIELD`.
  Axes are remapped with `SensorManager.remapCoordinateSystem()` for the current display rotation.
- **True north** — the sensor reports magnetic heading, so `GeomagneticField.declination` is added.
  This matters because the qibla bearing is computed against true north.
- **Smoothing** — the heading is low-pass filtered as a sin/cos pair so it wraps correctly across 0°/360°.
- **Qibla bearing** — great-circle initial bearing to the Kaaba (21.4224779, 39.8251832):
  `atan2(sin Δλ, cos φ · tan φ_k − sin φ · cos Δλ)`. Distance uses haversine.
- **Location** — `LocationManager` last-known fix across enabled providers, plus a fresh fix
  request with a 20s timeout. Coarse/fine permission requested on demand from the screen itself.
- **Offline** — the last used coordinates are cached in `mathurat_qibla` SharedPreferences,
  so re-opening the screen works without a new fix.
- **Alignment** — within 5° (`QiblaCompassView.ALIGN_TOLERANCE`) the needle and pill turn green
  and the phone vibrates once.
- **Degraded states** — no compass sensor, permission denied, or location services off each show
  their own message with an appropriate action button.
- All labels respect the `show_english` setting; cardinal letters switch between U/T/S/B and N/E/S/W.

---

## ADB Wireless Debugging

The phone pairs over wireless debugging. The IP:port changes every session, so don't hardcode
it — `adb devices` and use whatever id is listed (currently it attaches by mDNS as
`adb-R5CY41BPGGT-AgHgYB._adb-tls-connect._tcp`).

```bash
./gradlew assembleDebug
adb -s "$(adb devices | awk 'NR==2{print $1}')" install -r app/build/outputs/apk/debug/app-debug.apk
```
