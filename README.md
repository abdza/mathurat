# Al-Mathurat

An Android app for reciting the daily Islamic wirid/zikr from the Al-Mathurat booklet compiled by Imam Hassan Al-Banna.

## Features

### Core Recitation
- **Two versions**: Sughra (short) and Kubra (complete)
- **Two sessions**: Morning and Evening, with auto-detection based on time of day (4am–11am = Morning)
- **Tap-to-count**: Tap each item to track recitation count toward its target
- **Auto-scroll on completion**: When a zikr item's target count is reached, the list smoothly scrolls to the top of the next item

### Content
- 44 zikr/wirid items covering Quranic verses and supplications
- Verse-by-verse display for multi-verse Quranic items — each Arabic verse is immediately followed by its translation
- Reference section with 6 categories: Solat Jenazah, Doa Selepas Azan, Doa Qunut, Doa Selepas Solat, Takbir Raya, and Zikir & Doa Tarawikh

### Zikir Counter
- Standalone full-screen tap counter (accessible from the menu)
- Visual color flash feedback on each tap
- Vibration feedback with debounce
- Click sound on each tap
- Count persists across app restarts
- Reset button

### Achievement System
- Tracks daily completion of Morning and Evening sessions
- Kubra completion also counts as Sughra completion
- Calendar view showing daily completion history

### Settings
- **Language**: Malay (default) or English for UI labels and translations
- **Transliteration**: Show/hide romanized Arabic text
- **Arabic font size**: Adjustable via SeekBar with live preview

### Persistence
- Recitation counts saved per session/version combination
- Settings and Zikir Counter value persist across restarts

## Tech Stack

- **Language**: Kotlin
- **UI**: Android Material Design with ViewBinding
- **Min SDK**: Android (see `build.gradle`)
- **Content**: All zikr text hardcoded in Kotlin — no database or external content files

## Package

`com.abdullahsolutions.mathurat`

## Building & Installing

```bash
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```
