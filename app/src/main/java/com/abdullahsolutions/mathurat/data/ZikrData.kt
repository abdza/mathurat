package com.abdullahsolutions.mathurat.data

import com.abdullahsolutions.mathurat.model.Session
import com.abdullahsolutions.mathurat.model.Version
import com.abdullahsolutions.mathurat.model.VerseEntry
import com.abdullahsolutions.mathurat.model.ZikrItem

object ZikrData {

    val allZikr: List<ZikrItem> = listOf(

        // ══════════════════════════════════════════
        // BAHAGIAN QURAN (ms 1–22)
        // ══════════════════════════════════════════

        // ms 1 — A'udhu + Al-Fatihah
        ZikrItem(
            id = 1, sortOrder = 10,
            title = "Al-Fatihah",
            titleMs = "Surah Al-Fatihah",
            titleEn = "Al-Fatihah",
            subtitleEn = "Surah Al-Fatihah",
            arabic = "أَعُوذُ بِاللَّهِ السَّمِيعِ الْعَلِيمِ مِنَ الشَّيْطَانِ الرَّجِيمِ\n\nبِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ۝ الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ ۝ الرَّحْمَٰنِ الرَّحِيمِ ۝ مَالِكِ يَوْمِ الدِّينِ ۝ إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ ۝ اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ ۝ صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
            transliteration = "A'udhu billahis-sami'il-'alimi minash-shayṭanir-rajim. Bismillahir-rahmanir-rahim. Alhamdulillahi rabbil-'alamin. Ar-rahmanir-rahim. Maliki yawmid-din. Iyyaka na'budu wa iyyaka nasta'in. Ihdinaṣ-ṣirathal-mustaqim. Ṣiratha alladhina an'amta 'alayhim ghayril-maghdubi 'alayhim wa laḍ-ḍallin.",
            translation = "Aku berlindung dengan Allah dari syaitan yang direjam. Dengan nama Allah, Yang Maha Pemurah, lagi Maha Mengasihani. Segala puji tertentu bagi Allah, Tuhan yang memelihara sekalian alam. Yang Maha Pemurah, lagi Maha Mengasihani. Yang Menguasai hari Pembalasan. Engkaulah sahaja yang kami sembah, dan kepada Engkaulah sahaja kami memohon pertolongan. Tunjukilah kami jalan yang lurus. Jalan orang-orang yang Engkau kurniakan nikmat kepada mereka, bukan jalan mereka yang Engkau murkai, dan bukan pula jalan mereka yang sesat.",
            translationEn = "I seek refuge in Allah from the accursed Satan. In the name of Allah, the Most Gracious, the Most Merciful. All praise is due to Allah, Lord of all the worlds. The Most Gracious, the Most Merciful. Master of the Day of Judgment. It is You we worship and You we ask for help. Guide us to the straight path — the path of those upon whom You have bestowed favour, not of those who have evoked anger or of those who are astray.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 3 — Al-Baqarah 1-5
        ZikrItem(
            id = 2, sortOrder = 20,
            title = "Al-Baqarah: 1–5",
            titleMs = "Surah Al-Baqarah (ayat 1–5)",
            titleEn = "Al-Baqarah: 1–5",
            subtitleEn = "Surah Al-Baqarah (verses 1–5)",
            arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n\nالٓمٓ ۝ ذَٰلِكَ الْكِتَابُ لَا رَيْبَ فِيهِ هُدًى لِّلْمُتَّقِينَ ۝ الَّذِينَ يُؤْمِنُونَ بِالْغَيْبِ وَيُقِيمُونَ الصَّلَاةَ وَمِمَّا رَزَقْنَاهُمْ يُنفِقُونَ ۝ وَالَّذِينَ يُؤْمِنُونَ بِمَا أُنزِلَ إِلَيْكَ وَمَا أُنزِلَ مِن قَبْلِكَ وَبِالْآخِرَةِ هُمْ يُوقِنُونَ ۝ أُولَٰئِكَ عَلَىٰ هُدًى مِّن رَّبِّهِمْ وَأُولَٰئِكَ هُمُ الْمُفْلِحُونَ",
            transliteration = "Alif Lam Mim. Dhalikal-kitabu la rayba fih, hudal-lil-muttaqin. Alladhina yu'minuna bil-ghaybi wa yuqimunas-salata wa mimma razaqnahum yunfiqun. Walladhina yu'minuna bima unzila ilayka wa ma unzila min qablik, wa bil-akhirati hum yuqinun. Ula'ika 'ala hudam min rabbihim wa ula'ika humul-muflihun.",
            translation = "Alif Lam Mim. Kitab Al-Quran ini tidak ada keraguan padanya; petunjuk bagi mereka yang bertakwa. Iaitu mereka yang beriman kepada yang ghaib, mendirikan solat, dan menafkahkan sebahagian rezeki yang Kami kurniakan. Dan mereka yang beriman kepada Al-Quran yang diturunkan kepadamu dan Kitab-kitab yang diturunkan sebelummu, serta yakin akan adanya akhirat. Mereka itulah yang mendapat petunjuk dari Tuhan mereka, dan merekalah orang-orang yang beruntung.",
            translationEn = "Alif Lam Mim. This is the Book about which there is no doubt, a guidance for those conscious of Allah — who believe in the unseen, establish prayer, and spend from what We have provided for them, and who believe in what has been revealed to you and what was revealed before you, and of the Hereafter they are certain. Those are upon guidance from their Lord, and it is those who are the successful.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA),
            pairedVerses = listOf(
                VerseEntry(arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ"),
                VerseEntry(
                    arabic = "الٓمٓ",
                    transliteration = "Alif Lam Mim.",
                    translationMs = "Alif Lam Mim.",
                    translationEn = "Alif Lam Mim."
                ),
                VerseEntry(
                    arabic = "ذَٰلِكَ الْكِتَابُ لَا رَيْبَ فِيهِ هُدًى لِّلْمُتَّقِينَ",
                    transliteration = "Dhalikal-kitabu la rayba fih, hudal-lil-muttaqin.",
                    translationMs = "Kitab Al-Quran ini tidak ada keraguan padanya; petunjuk bagi mereka yang bertakwa.",
                    translationEn = "This is the Book about which there is no doubt, a guidance for those conscious of Allah."
                ),
                VerseEntry(
                    arabic = "الَّذِينَ يُؤْمِنُونَ بِالْغَيْبِ وَيُقِيمُونَ الصَّلَاةَ وَمِمَّا رَزَقْنَاهُمْ يُنفِقُونَ",
                    transliteration = "Alladhina yu'minuna bil-ghaybi wa yuqimunas-salata wa mimma razaqnahum yunfiqun.",
                    translationMs = "Iaitu mereka yang beriman kepada yang ghaib, mendirikan solat, dan menafkahkan sebahagian rezeki yang Kami kurniakan.",
                    translationEn = "Who believe in the unseen, establish prayer, and spend from what We have provided for them."
                ),
                VerseEntry(
                    arabic = "وَالَّذِينَ يُؤْمِنُونَ بِمَا أُنزِلَ إِلَيْكَ وَمَا أُنزِلَ مِن قَبْلِكَ وَبِالْآخِرَةِ هُمْ يُوقِنُونَ",
                    transliteration = "Walladhina yu'minuna bima unzila ilayka wa ma unzila min qablik, wa bil-akhirati hum yuqinun.",
                    translationMs = "Dan mereka yang beriman kepada apa yang diturunkan kepadamu dan apa yang diturunkan sebelummu, serta yakin akan adanya akhirat.",
                    translationEn = "And who believe in what has been revealed to you and what was revealed before you, and of the Hereafter they are certain."
                ),
                VerseEntry(
                    arabic = "أُولَٰئِكَ عَلَىٰ هُدًى مِّن رَّبِّهِمْ وَأُولَٰئِكَ هُمُ الْمُفْلِحُونَ",
                    transliteration = "Ula'ika 'ala hudam min rabbihim wa ula'ika humul-muflihun.",
                    translationMs = "Mereka itulah yang mendapat petunjuk dari Tuhan mereka, dan merekalah orang-orang yang beruntung.",
                    translationEn = "Those are upon guidance from their Lord, and it is those who are the successful."
                )
            )
        ),

        // ms 5–8 — Al-Baqarah 255–257
        ZikrItem(
            id = 3, sortOrder = 30,
            title = "Al-Baqarah: 255–257",
            titleMs = "Ayatul Kursi + Ayat Lanjutan",
            titleEn = "Al-Baqarah: 255–257 (Ayatul Kursi)",
            subtitleEn = "Ayatul Kursi + Following Verses",
            arabic = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ لَهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ مَنْ ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلَّا بِإِذْنِهِ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ وَلَا يُحِيطُونَ بِشَيْءٍ مِنْ عِلْمِهِ إِلَّا بِمَا شَاءَ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ وَلَا يَئُودُهُ حِفْظُهُمَا وَهُوَ الْعَلِيُّ الْعَظِيمُ ۝ لَا إِكْرَاهَ فِي الدِّينِ قَد تَّبَيَّنَ الرُّشْدُ مِنَ الْغَيِّ فَمَن يَكْفُرْ بِالطَّاغُوتِ وَيُؤْمِن بِاللَّهِ فَقَدِ اسْتَمْسَكَ بِالْعُرْوَةِ الْوُثْقَىٰ لَا انفِصَامَ لَهَا وَاللَّهُ سَمِيعٌ عَلِيمٌ ۝ اللَّهُ وَلِيُّ الَّذِينَ آمَنُوا يُخْرِجُهُم مِّنَ الظُّلُمَاتِ إِلَى النُّورِ وَالَّذِينَ كَفَرُوا أَوْلِيَاؤُهُمُ الطَّاغُوتُ يُخْرِجُونَهُم مِّنَ النُّورِ إِلَى الظُّلُمَاتِ أُولَٰئِكَ أَصْحَابُ النَّارِ هُمْ فِيهَا خَالِدُونَ",
            transliteration = "Allahu la ilaha illa huwal-hayyul-qayyum, la ta'khudhahu sinatuw wa la nawm, lahu ma fis-samawati wa ma fil-ardh...",
            translation = "Allah, tidak ada Tuhan melainkan Dia Yang Hidup kekal lagi terus-menerus mengurus makhluk-Nya; tidak mengantuk dan tidak tidur. Kepunyaan-Nya apa yang di langit dan di bumi. Tiada yang dapat memberi syafaat di sisi-Nya tanpa izin-Nya... Tidak ada paksaan untuk memasuki agama Islam; sesungguhnya telah jelas jalan yang benar daripada jalan yang sesat. Allah adalah Pelindung orang-orang yang beriman; Dia mengeluarkan mereka dari kegelapan kepada cahaya.",
            translationEn = "Allah — there is no deity except Him, the Ever-Living, the Sustainer of existence. Neither drowsiness overtakes Him nor sleep. To Him belongs whatever is in the heavens and whatever is on the earth. Who is it that can intercede with Him except by His permission? He knows what is before them and what will be after them, and they encompass not a thing of His knowledge except for what He wills. His Kursi extends over the heavens and the earth, and their preservation tires Him not. And He is the Most High, the Most Great. There shall be no compulsion in religion. Allah is the ally of those who believe: He brings them out from darknesses into the light.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA),
            pairedVerses = listOf(
                VerseEntry(
                    arabic = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ لَهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ مَنْ ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلَّا بِإِذْنِهِ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ وَلَا يُحِيطُونَ بِشَيْءٍ مِنْ عِلْمِهِ إِلَّا بِمَا شَاءَ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ وَلَا يَئُودُهُ حِفْظُهُمَا وَهُوَ الْعَلِيُّ الْعَظِيمُ",
                    transliteration = "Allahu la ilaha illa huwal-hayyul-qayyum, la ta'khudhahu sinatuw wa la nawm, lahu ma fis-samawati wa ma fil-ardh, man dhal-ladhi yashfa'u 'indahu illa bi-idhnih, ya'lamu ma bayna aydihim wa ma khalfahum, wa la yuhituna bi-shay'im min 'ilmihi illa bima sha', wasi'a kursiyyuhus-samawati wal-ardha wa la ya'uduhu hifzuhuma wa huwal-'aliyyul-'azim.",
                    translationMs = "Allah, tidak ada Tuhan melainkan Dia Yang Hidup kekal lagi terus-menerus mengurus makhluk-Nya; tidak mengantuk dan tidak tidur. Kepunyaan-Nya apa yang di langit dan di bumi. Tiada yang dapat memberi syafaat di sisi-Nya tanpa izin-Nya. Dia mengetahui apa yang ada di hadapan mereka dan di belakang mereka, dan mereka tidak mengetahui apa-apa dari ilmu Allah melainkan yang dikehendaki-Nya. Kursi-Nya meliputi langit dan bumi. Dan Allah tidak merasa berat memelihara keduanya, dan Allah Maha Tinggi lagi Maha Besar.",
                    translationEn = "Allah — there is no deity except Him, the Ever-Living, the Sustainer of existence. Neither drowsiness overtakes Him nor sleep. To Him belongs whatever is in the heavens and whatever is on the earth. Who is it that can intercede with Him except by His permission? He knows what is before them and what will be after them, and they encompass not a thing of His knowledge except for what He wills. His Kursi extends over the heavens and the earth, and their preservation tires Him not. And He is the Most High, the Most Great."
                ),
                VerseEntry(
                    arabic = "لَا إِكْرَاهَ فِي الدِّينِ قَد تَّبَيَّنَ الرُّشْدُ مِنَ الْغَيِّ فَمَن يَكْفُرْ بِالطَّاغُوتِ وَيُؤْمِن بِاللَّهِ فَقَدِ اسْتَمْسَكَ بِالْعُرْوَةِ الْوُثْقَىٰ لَا انفِصَامَ لَهَا وَاللَّهُ سَمِيعٌ عَلِيمٌ",
                    transliteration = "La ikraha fid-din, qad tabayyanar-rushdu minal-ghayy, faman yakfur bit-taghuti wa yu'min billahi faqadis-tamsaka bil-'urwatil-wuthqa lan-fisama laha wallahu sami'un 'alim.",
                    translationMs = "Tidak ada paksaan untuk memasuki agama Islam; sesungguhnya telah jelas jalan yang benar daripada jalan yang sesat. Kerana itu barangsiapa yang ingkar kepada Thaghut dan beriman kepada Allah, maka sesungguhnya ia telah berpegang kepada buhul tali yang amat kuat yang tidak akan putus. Dan Allah Maha Mendengar lagi Maha Mengetahui.",
                    translationEn = "There shall be no compulsion in religion. The right course has become clear from the wrong. So whoever disbelieves in Taghut and believes in Allah has grasped the most trustworthy handhold with no break in it. And Allah is Hearing and Knowing."
                ),
                VerseEntry(
                    arabic = "اللَّهُ وَلِيُّ الَّذِينَ آمَنُوا يُخْرِجُهُم مِّنَ الظُّلُمَاتِ إِلَى النُّورِ وَالَّذِينَ كَفَرُوا أَوْلِيَاؤُهُمُ الطَّاغُوتُ يُخْرِجُونَهُم مِّنَ النُّورِ إِلَى الظُّلُمَاتِ أُولَٰئِكَ أَصْحَابُ النَّارِ هُمْ فِيهَا خَالِدُونَ",
                    transliteration = "Allahu waliyyul-ladhina amanu yukhrijuhum minaẓ-ẓulumati ilan-nur, walladhina kafaru awliya'uhumut-taghut yukhrijunahum minan-nuri ilaẓ-ẓulumat, ula'ika ash-habun-nar, hum fiha khalidun.",
                    translationMs = "Allah Pelindung orang-orang yang beriman; Dia mengeluarkan mereka dari kegelapan kepada cahaya. Dan orang-orang yang kafir, pelindung-pelindungnya ialah syaitan, yang mengeluarkan mereka daripada cahaya kepada kegelapan. Mereka itu adalah penghuni neraka; mereka kekal di dalamnya.",
                    translationEn = "Allah is the ally of those who believe: He brings them out from darknesses into the light. And those who disbelieve — their allies are Taghut. They take them out of the light into darknesses. Those are the companions of the Fire; they will abide eternally therein."
                )
            )
        ),

        // ms 9 — Al-Baqarah 284–286
        ZikrItem(
            id = 4, sortOrder = 40,
            title = "Al-Baqarah: 284–286",
            titleMs = "Al-Baqarah ayat 284–286",
            titleEn = "Al-Baqarah: 284–286",
            subtitleEn = "Al-Baqarah verses 284–286",
            arabic = "لِلَّهِ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ وَإِن تُبْدُوا مَا فِي أَنفُسِكُمْ أَوْ تُخْفُوهُ يُحَاسِبْكُم بِهِ اللَّهُ فَيَغْفِرُ لِمَن يَشَاءُ وَيُعَذِّبُ مَن يَشَاءُ وَاللَّهُ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ ۝ آمَنَ الرَّسُولُ بِمَا أُنزِلَ إِلَيْهِ مِن رَّبِّهِ وَالْمُؤْمِنُونَ كُلٌّ آمَنَ بِاللَّهِ وَمَلَائِكَتِهِ وَكُتُبِهِ وَرُسُلِهِ لَا نُفَرِّقُ بَيْنَ أَحَدٍ مِّن رُّسُلِهِ وَقَالُوا سَمِعْنَا وَأَطَعْنَا غُفْرَانَكَ رَبَّنَا وَإِلَيْكَ الْمَصِيرُ ۝ لَا يُكَلِّفُ اللَّهُ نَفْسًا إِلَّا وُسْعَهَا لَهَا مَا كَسَبَتْ وَعَلَيْهَا مَا اكْتَسَبَتْ رَبَّنَا لَا تُؤَاخِذْنَا إِن نَّسِينَا أَوْ أَخْطَأْنَا رَبَّنَا وَلَا تَحْمِلْ عَلَيْنَا إِصْرًا كَمَا حَمَلْتَهُ عَلَى الَّذِينَ مِن قَبْلِنَا رَبَّنَا وَلَا تُحَمِّلْنَا مَا لَا طَاقَةَ لَنَا بِهِ وَاعْفُ عَنَّا وَاغْفِرْ لَنَا وَارْحَمْنَا أَنتَ مَوْلَانَا فَانصُرْنَا عَلَى الْقَوْمِ الْكَافِرِينَ",
            transliteration = "Lillahi ma fis-samawati wa ma fil-ardh...",
            translation = "Kepunyaan Allah-lah segala apa yang ada di langit dan di bumi. Rasul beriman kepada Al-Quran yang diturunkan kepadanya dari Tuhannya, demikian pula orang-orang yang beriman... Allah tidak membebani seseorang melainkan sesuai dengan kesanggupannya. Ya Tuhan kami, janganlah Engkau hukum kami jika kami lupa atau kami tersalah. Ya Tuhan kami, ampunilah kami, kasihilah kami. Engkaulah pelindung kami, tolonglah kami menghadapi kaum yang kafir.",
            translationEn = "To Allah belongs whatever is in the heavens and whatever is in the earth. The Messenger has believed in what was revealed to him from his Lord, and so have the believers. All of them have believed in Allah and His angels and His books and His messengers. Allah does not burden a soul beyond that it can bear. Our Lord, do not impose blame upon us if we have forgotten or erred. Our Lord, grant us forgiveness. You are our protector, so give us victory over the disbelieving people.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA),
            pairedVerses = listOf(
                VerseEntry(
                    arabic = "لِلَّهِ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ وَإِن تُبْدُوا مَا فِي أَنفُسِكُمْ أَوْ تُخْفُوهُ يُحَاسِبْكُم بِهِ اللَّهُ فَيَغْفِرُ لِمَن يَشَاءُ وَيُعَذِّبُ مَن يَشَاءُ وَاللَّهُ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ",
                    translationMs = "Kepunyaan Allah-lah segala apa yang ada di langit dan di bumi. Dan jika kamu melahirkan apa yang ada di dalam hatimu atau kamu menyembunyikannya, nescaya Allah akan membuat perhitungan dengan kamu tentang perbuatanmu itu. Maka Allah mengampuni siapa yang dikehendaki-Nya dan menyeksa siapa yang dikehendaki-Nya; dan Allah Maha Kuasa atas segala sesuatu.",
                    translationEn = "To Allah belongs whatever is in the heavens and whatever is in the earth. Whether you show what is within yourselves or conceal it, Allah will bring you to account for it. Then He will forgive whom He wills and punish whom He wills, and Allah is over all things competent."
                ),
                VerseEntry(
                    arabic = "آمَنَ الرَّسُولُ بِمَا أُنزِلَ إِلَيْهِ مِن رَّبِّهِ وَالْمُؤْمِنُونَ كُلٌّ آمَنَ بِاللَّهِ وَمَلَائِكَتِهِ وَكُتُبِهِ وَرُسُلِهِ لَا نُفَرِّقُ بَيْنَ أَحَدٍ مِّن رُّسُلِهِ وَقَالُوا سَمِعْنَا وَأَطَعْنَا غُفْرَانَكَ رَبَّنَا وَإِلَيْكَ الْمَصِيرُ",
                    translationMs = "Rasul telah beriman kepada Al-Quran yang diturunkan kepadanya dari Tuhannya, demikian pula orang-orang yang beriman. Semuanya beriman kepada Allah, malaikat-malaikat-Nya, kitab-kitab-Nya dan rasul-rasul-Nya. Mereka mengatakan: 'Kami tidak membeza-bezakan antara seseorangpun dari rasul-rasul-Nya'; dan mereka mengatakan: 'Kami dengar dan kami taat.' Ampunilah kami ya Tuhan kami dan kepada Engkaulah tempat kembali.",
                    translationEn = "The Messenger has believed in what was revealed to him from his Lord, and so have the believers. All of them have believed in Allah and His angels and His books and His messengers, saying: We make no distinction between any of His messengers. And they say: We hear and we obey. Grant us Your forgiveness, our Lord, and to You is the final destination."
                ),
                VerseEntry(
                    arabic = "لَا يُكَلِّفُ اللَّهُ نَفْسًا إِلَّا وُسْعَهَا لَهَا مَا كَسَبَتْ وَعَلَيْهَا مَا اكْتَسَبَتْ رَبَّنَا لَا تُؤَاخِذْنَا إِن نَّسِينَا أَوْ أَخْطَأْنَا رَبَّنَا وَلَا تَحْمِلْ عَلَيْنَا إِصْرًا كَمَا حَمَلْتَهُ عَلَى الَّذِينَ مِن قَبْلِنَا رَبَّنَا وَلَا تُحَمِّلْنَا مَا لَا طَاقَةَ لَنَا بِهِ وَاعْفُ عَنَّا وَاغْفِرْ لَنَا وَارْحَمْنَا أَنتَ مَوْلَانَا فَانصُرْنَا عَلَى الْقَوْمِ الْكَافِرِينَ",
                    translationMs = "Allah tidak membebani seseorang melainkan sesuai dengan kesanggupannya. Ia mendapat pahala dari kebajikan yang diusahakannya dan ia mendapat seksa dari kejahatan yang dikerjakannya. Doa mereka: Ya Tuhan kami, janganlah Engkau hukum kami jika kami lupa atau kami tersalah. Ya Tuhan kami, janganlah Engkau bebankan kepada kami beban yang berat sebagaimana Engkau bebankan kepada orang-orang yang sebelum kami. Ya Tuhan kami, janganlah Engkau pikulkan kepada kami apa yang tidak sanggup kami memikulnya. Beri maaflah kami; ampunilah kami; dan rahmatilah kami. Engkaulah Penolong kami, maka tolonglah kami terhadap kaum yang kafir.",
                    translationEn = "Allah does not burden a soul beyond that it can bear. It will have the consequence of what good it has gained, and it will bear the consequence of what evil it has earned. Our Lord, do not impose blame upon us if we have forgotten or erred. Our Lord, lay not upon us a burden like that which You laid upon those before us. Our Lord, burden us not with that which we have no ability to bear. And pardon us; and forgive us; and have mercy upon us. You are our protector, so give us victory over the disbelieving people."
                )
            )
        ),

        // ms 9 (bawah) — Ali Imran 1-2
        ZikrItem(
            id = 5, sortOrder = 50,
            title = "Ali Imran: 1–2",
            titleMs = "Ali Imran ayat 1–2",
            titleEn = "Ali Imran: 1–2",
            subtitleEn = "Ali Imran verses 1–2",
            arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n\nالٓمٓ ۝ اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ",
            transliteration = "Alif Lam Mim. Allahu la ilaha illa huwal-hayyul-qayyum.",
            translation = "Alif Lam Mim. Allah, tidak ada Tuhan melainkan Dia, Yang Hidup kekal lagi terus-menerus mengurus makhluk-Nya.",
            translationEn = "Alif Lam Mim. Allah — there is no deity except Him, the Ever-Living, the Sustainer of existence.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.KUBRA),
            pairedVerses = listOf(
                VerseEntry(arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ"),
                VerseEntry(
                    arabic = "الٓمٓ",
                    transliteration = "Alif Lam Mim.",
                    translationMs = "Alif Lam Mim.",
                    translationEn = "Alif Lam Mim."
                ),
                VerseEntry(
                    arabic = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ",
                    transliteration = "Allahu la ilaha illa huwal-hayyul-qayyum.",
                    translationMs = "Allah, tidak ada Tuhan melainkan Dia, Yang Hidup kekal lagi terus-menerus mengurus makhluk-Nya.",
                    translationEn = "Allah — there is no deity except Him, the Ever-Living, the Sustainer of existence."
                )
            )
        ),

        // ms 11 — At-Tawbah 129
        ZikrItem(
            id = 6, sortOrder = 60,
            title = "At-Tawbah: 129",
            titleMs = "At-Tawbah ayat 129",
            titleEn = "At-Tawbah: 129",
            subtitleEn = "At-Tawbah verse 129",
            arabic = "فَإِن تَوَلَّوْا فَقُلْ حَسْبِيَ اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ عَلَيْهِ تَوَكَّلْتُ وَهُوَ رَبُّ الْعَرْشِ الْعَظِيمِ",
            transliteration = "Fa-in tawallaw faqul hasbiyallahu la ilaha illa huw, 'alayhi tawakkaltu wa huwa rabbul-'arshil-'azim.",
            translation = "Jika mereka berpaling, maka katakanlah: 'Cukuplah Allah bagiku; tidak ada Tuhan selain Dia. Hanya kepada-Nyalah aku bertawakkal; dan Dia adalah Tuhan yang memiliki Arsy yang agung.'",
            translationEn = "But if they turn away, say: Sufficient for me is Allah; there is no deity except Him. On Him I have relied, and He is the Lord of the Great Throne.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.KUBRA)
        ),

        // ms 11–14 — Al-Hashr 22–24
        ZikrItem(
            id = 7, sortOrder = 70,
            title = "Al-Hashr: 22–24",
            titleMs = "Al-Hashr ayat 22–24",
            titleEn = "Al-Hashr: 22–24",
            subtitleEn = "Al-Hashr verses 22–24",
            arabic = "هُوَ اللَّهُ الَّذِي لَا إِلَٰهَ إِلَّا هُوَ عَالِمُ الْغَيْبِ وَالشَّهَادَةِ هُوَ الرَّحْمَٰنُ الرَّحِيمُ ۝ هُوَ اللَّهُ الَّذِي لَا إِلَٰهَ إِلَّا هُوَ الْمَلِكُ الْقُدُّوسُ السَّلَامُ الْمُؤْمِنُ الْمُهَيْمِنُ الْعَزِيزُ الْجَبَّارُ الْمُتَكَبِّرُ سُبْحَانَ اللَّهِ عَمَّا يُشْرِكُونَ ۝ هُوَ اللَّهُ الْخَالِقُ الْبَارِئُ الْمُصَوِّرُ لَهُ الْأَسْمَاءُ الْحُسْنَىٰ يُسَبِّحُ لَهُ مَا فِي السَّمَاوَاتِ وَالْأَرْضِ وَهُوَ الْعَزِيزُ الْحَكِيمُ",
            transliteration = "Huwall-lahul-ladhi la ilaha illa huw, 'alimul-ghaybi wash-shahadah, huwar-rahmanur-rahim. Huwall-lahul-ladhi la ilaha illa huwal-malikul-quddusis-salamul-mu'minul-muhayminul-'azizul-jabbarul-mutakabbir, subhanallahi 'amma yushrikun. Huwall-lahul-khaliqul-bari'ul-musawwir, lahul-asma'ul-husna, yusabbihu lahu ma fis-samawati wal-ardh, wa huwal-'azizul-hakim.",
            translation = "Dialah Allah yang tiada Tuhan selain Dia, Yang Mengetahui yang ghaib dan yang nyata. Dialah Yang Maha Pemurah lagi Maha Penyayang. Dialah Allah yang tiada Tuhan selain Dia, Raja Yang Maha Suci, Yang Maha Sejahtera, Yang Mengaruniakan keamanan, Yang Maha Memelihara, Yang Maha Perkasa, Yang Maha Kuasa, Yang Memiliki Keagungan. Maha Suci Allah dari apa yang mereka persekutukan. Dialah Allah Yang Menciptakan, Yang Mengadakan, Yang Membentuk Rupa. Bagi-Nya nama-nama yang paling baik. Bertasbih kepada-Nya apa yang ada di langit dan bumi. Dan Dialah Yang Maha Perkasa lagi Maha Bijaksana.",
            translationEn = "He is Allah, other than whom there is no deity, Knower of the unseen and the witnessed. He is the Entirely Merciful, the Especially Merciful. He is Allah, other than whom there is no deity, the Sovereign, the Pure, the Perfection, the Bestower of Faith, the Overseer, the Exalted in Might, the Compeller, the Superior. Exalted is Allah above whatever they associate with Him. He is Allah, the Creator, the Inventor, the Fashioner; to Him belong the best names. Whatever is in the heavens and earth is exalting Him. And He is the Exalted in Might, the Wise.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.KUBRA),
            pairedVerses = listOf(
                VerseEntry(
                    arabic = "هُوَ اللَّهُ الَّذِي لَا إِلَٰهَ إِلَّا هُوَ عَالِمُ الْغَيْبِ وَالشَّهَادَةِ هُوَ الرَّحْمَٰنُ الرَّحِيمُ",
                    transliteration = "Huwall-lahul-ladhi la ilaha illa huw, 'alimul-ghaybi wash-shahadah, huwar-rahmanur-rahim.",
                    translationMs = "Dialah Allah yang tiada Tuhan selain Dia, Yang Mengetahui yang ghaib dan yang nyata. Dialah Yang Maha Pemurah lagi Maha Penyayang.",
                    translationEn = "He is Allah, other than whom there is no deity, Knower of the unseen and the witnessed. He is the Entirely Merciful, the Especially Merciful."
                ),
                VerseEntry(
                    arabic = "هُوَ اللَّهُ الَّذِي لَا إِلَٰهَ إِلَّا هُوَ الْمَلِكُ الْقُدُّوسُ السَّلَامُ الْمُؤْمِنُ الْمُهَيْمِنُ الْعَزِيزُ الْجَبَّارُ الْمُتَكَبِّرُ سُبْحَانَ اللَّهِ عَمَّا يُشْرِكُونَ",
                    transliteration = "Huwall-lahul-ladhi la ilaha illa huwal-malikul-quddusis-salamul-mu'minul-muhayminul-'azizul-jabbarul-mutakabbir, subhanallahi 'amma yushrikun.",
                    translationMs = "Dialah Allah yang tiada Tuhan selain Dia, Raja Yang Maha Suci, Yang Maha Sejahtera, Yang Mengaruniakan keamanan, Yang Maha Memelihara, Yang Maha Perkasa, Yang Maha Kuasa, Yang Memiliki Keagungan. Maha Suci Allah dari apa yang mereka persekutukan.",
                    translationEn = "He is Allah, other than whom there is no deity, the Sovereign, the Pure, the Perfection, the Bestower of Faith, the Overseer, the Exalted in Might, the Compeller, the Superior. Exalted is Allah above whatever they associate with Him."
                ),
                VerseEntry(
                    arabic = "هُوَ اللَّهُ الْخَالِقُ الْبَارِئُ الْمُصَوِّرُ لَهُ الْأَسْمَاءُ الْحُسْنَىٰ يُسَبِّحُ لَهُ مَا فِي السَّمَاوَاتِ وَالْأَرْضِ وَهُوَ الْعَزِيزُ الْحَكِيمُ",
                    transliteration = "Huwall-lahul-khaliqul-bari'ul-musawwir, lahul-asma'ul-husna, yusabbihu lahu ma fis-samawati wal-ardh, wa huwal-'azizul-hakim.",
                    translationMs = "Dialah Allah Yang Menciptakan, Yang Mengadakan, Yang Membentuk Rupa. Bagi-Nya nama-nama yang paling baik. Bertasbih kepada-Nya apa yang ada di langit dan bumi. Dan Dialah Yang Maha Perkasa lagi Maha Bijaksana.",
                    translationEn = "He is Allah, the Creator, the Inventor, the Fashioner; to Him belong the best names. Whatever is in the heavens and earth is exalting Him. And He is the Exalted in Might, the Wise."
                )
            )
        ),

        // ms 15–16 — Ar-Rum 20–27
        ZikrItem(
            id = 8, sortOrder = 80,
            title = "Ar-Rum: 20–27",
            titleMs = "Ar-Rum ayat 20–27",
            titleEn = "Ar-Rum: 20–27",
            subtitleEn = "Ar-Rum verses 20–27",
            arabic = "وَمِنْ آيَاتِهِ أَنْ خَلَقَكُم مِّن تُرَابٍ ثُمَّ إِذَا أَنتُم بَشَرٌ تَنتَشِرُونَ ۝ وَمِنْ آيَاتِهِ أَنْ خَلَقَ لَكُم مِّنْ أَنفُسِكُمْ أَزْوَاجًا لِّتَسْكُنُوا إِلَيْهَا وَجَعَلَ بَيْنَكُم مَّوَدَّةً وَرَحْمَةً إِنَّ فِي ذَٰلِكَ لَآيَاتٍ لِّقَوْمٍ يَتَفَكَّرُونَ ۝ وَمِنْ آيَاتِهِ خَلْقُ السَّمَاوَاتِ وَالْأَرْضِ وَاخْتِلَافُ أَلْسِنَتِكُمْ وَأَلْوَانِكُمْ إِنَّ فِي ذَٰلِكَ لَآيَاتٍ لِّلْعَالِمِينَ ۝ وَمِنْ آيَاتِهِ مَنَامُكُم بِاللَّيْلِ وَالنَّهَارِ وَابْتِغَاؤُكُم مِّن فَضْلِهِ إِنَّ فِي ذَٰلِكَ لَآيَاتٍ لِّقَوْمٍ يَسْمَعُونَ ۝ وَمِنْ آيَاتِهِ يُرِيكُمُ الْبَرْقَ خَوْفًا وَطَمَعًا وَيُنَزِّلُ مِنَ السَّمَاءِ مَاءً فَيُحْيِي بِهِ الْأَرْضَ بَعْدَ مَوْتِهَا إِنَّ فِي ذَٰلِكَ لَآيَاتٍ لِّقَوْمٍ يَعْقِلُونَ ۝ وَمِنْ آيَاتِهِ أَن تَقُومَ السَّمَاءُ وَالْأَرْضُ بِأَمْرِهِ ثُمَّ إِذَا دَعَاكُمْ دَعْوَةً مِّنَ الْأَرْضِ إِذَا أَنتُمْ تَخْرُجُونَ ۝ وَلَهُ مَن فِي السَّمَاوَاتِ وَالْأَرْضِ كُلٌّ لَّهُ قَانِتُونَ ۝ وَهُوَ الَّذِي يَبْدَأُ الْخَلْقَ ثُمَّ يُعِيدُهُ وَهُوَ أَهْوَنُ عَلَيْهِ وَلَهُ الْمَثَلُ الْأَعْلَىٰ فِي السَّمَاوَاتِ وَالْأَرْضِ وَهُوَ الْعَزِيزُ الْحَكِيمُ",
            transliteration = "Wa min ayatihi an khalaqa kum min turab...",
            translation = "Dan di antara tanda-tanda kekuasaan-Nya ialah Dia menciptakan kamu dari tanah. Dan di antara tanda-tanda kekuasaan-Nya ialah Dia menciptakan untukmu isteri-isteri dari jenismu sendiri, supaya kamu cenderung dan merasa tenteram kepadanya. Dan di antara tanda-tanda kekuasaan-Nya ialah penciptaan langit dan bumi dan berlain-lainan bahasamu dan warna kulitmu. Dan di antara tanda-tanda kekuasaan-Nya ialah tidurmu di waktu malam dan siang hari. Dan di antara tanda-tanda kekuasaan-Nya, Dia memperlihatkan kilat untuk menimbulkan ketakutan dan harapan.",
            translationEn = "And of His signs is that He created you from dust; then, suddenly you were human beings dispersing. And of His signs is that He created for you from yourselves mates that you may find tranquillity in them; and He placed between you affection and mercy. And of His signs is the creation of the heavens and the earth and the diversity of your languages and your colours. And of His signs is your sleep by night and day and your seeking of His bounty. And of His signs is that He shows you the lightning. And of His signs is that the heaven and earth stand by His command. To Him belongs whoever is in the heavens and earth. All are devoutly obedient to Him.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.KUBRA),
            pairedVerses = listOf(
                VerseEntry(
                    arabic = "وَمِنْ آيَاتِهِ أَنْ خَلَقَكُم مِّن تُرَابٍ ثُمَّ إِذَا أَنتُم بَشَرٌ تَنتَشِرُونَ",
                    translationMs = "Dan di antara tanda-tanda kekuasaan-Nya ialah Dia menciptakan kamu dari tanah. Kemudian tiba-tiba kamu menjadi manusia yang bertebaran.",
                    translationEn = "And of His signs is that He created you from dust; then, suddenly you were human beings dispersing."
                ),
                VerseEntry(
                    arabic = "وَمِنْ آيَاتِهِ أَنْ خَلَقَ لَكُم مِّنْ أَنفُسِكُمْ أَزْوَاجًا لِّتَسْكُنُوا إِلَيْهَا وَجَعَلَ بَيْنَكُم مَّوَدَّةً وَرَحْمَةً إِنَّ فِي ذَٰلِكَ لَآيَاتٍ لِّقَوْمٍ يَتَفَكَّرُونَ",
                    translationMs = "Dan di antara tanda-tanda kekuasaan-Nya ialah Dia menciptakan untukmu isteri-isteri dari jenismu sendiri, supaya kamu cenderung dan merasa tenteram kepadanya, dan dijadikan-Nya di antaramu rasa kasih dan sayang. Sesungguhnya pada yang demikian itu benar-benar terdapat tanda-tanda bagi kaum yang berfikir.",
                    translationEn = "And of His signs is that He created for you from yourselves mates that you may find tranquillity in them; and He placed between you affection and mercy. Indeed in that are signs for a people who give thought."
                ),
                VerseEntry(
                    arabic = "وَمِنْ آيَاتِهِ خَلْقُ السَّمَاوَاتِ وَالْأَرْضِ وَاخْتِلَافُ أَلْسِنَتِكُمْ وَأَلْوَانِكُمْ إِنَّ فِي ذَٰلِكَ لَآيَاتٍ لِّلْعَالِمِينَ",
                    translationMs = "Dan di antara tanda-tanda kekuasaan-Nya ialah penciptaan langit dan bumi dan berlain-lainan bahasamu dan warna kulitmu. Sesungguhnya pada yang demikian itu benar-benar terdapat tanda-tanda bagi orang-orang yang mengetahui.",
                    translationEn = "And of His signs is the creation of the heavens and the earth and the diversity of your languages and your colours. Indeed in that are signs for those of knowledge."
                ),
                VerseEntry(
                    arabic = "وَمِنْ آيَاتِهِ مَنَامُكُم بِاللَّيْلِ وَالنَّهَارِ وَابْتِغَاؤُكُم مِّن فَضْلِهِ إِنَّ فِي ذَٰلِكَ لَآيَاتٍ لِّقَوْمٍ يَسْمَعُونَ",
                    translationMs = "Dan di antara tanda-tanda kekuasaan-Nya ialah tidurmu di waktu malam dan siang hari dan usahamu mencari sebahagian dari kurnia-Nya. Sesungguhnya pada yang demikian itu benar-benar terdapat tanda-tanda bagi kaum yang mendengarkan.",
                    translationEn = "And of His signs is your sleep by night and day and your seeking of His bounty. Indeed in that are signs for a people who listen."
                ),
                VerseEntry(
                    arabic = "وَمِنْ آيَاتِهِ يُرِيكُمُ الْبَرْقَ خَوْفًا وَطَمَعًا وَيُنَزِّلُ مِنَ السَّمَاءِ مَاءً فَيُحْيِي بِهِ الْأَرْضَ بَعْدَ مَوْتِهَا إِنَّ فِي ذَٰلِكَ لَآيَاتٍ لِّقَوْمٍ يَعْقِلُونَ",
                    translationMs = "Dan di antara tanda-tanda kekuasaan-Nya, Dia memperlihatkan kepadamu kilat untuk menimbulkan ketakutan dan harapan, dan Dia menurunkan hujan dari langit, lalu menghidupkan bumi dengan air itu sesudah matinya. Sesungguhnya pada yang demikian itu benar-benar terdapat tanda-tanda bagi kaum yang mempergunakan akalnya.",
                    translationEn = "And of His signs is that He shows you the lightning, causing fear and aspiration, and He sends down rain from the sky by which He brings to life the earth after its lifelessness. Indeed in that are signs for a people who use reason."
                ),
                VerseEntry(
                    arabic = "وَمِنْ آيَاتِهِ أَن تَقُومَ السَّمَاءُ وَالْأَرْضُ بِأَمْرِهِ ثُمَّ إِذَا دَعَاكُمْ دَعْوَةً مِّنَ الْأَرْضِ إِذَا أَنتُمْ تَخْرُجُونَ",
                    translationMs = "Dan di antara tanda-tanda kekuasaan-Nya ialah berdirinya langit dan bumi dengan iradat-Nya. Kemudian apabila Dia memanggil kamu sekali panggil dari bumi, seketika itu kamu keluar.",
                    translationEn = "And of His signs is that the heaven and earth stand by His command. Then when He calls you with a single call from the earth, immediately you will come forth."
                ),
                VerseEntry(
                    arabic = "وَلَهُ مَن فِي السَّمَاوَاتِ وَالْأَرْضِ كُلٌّ لَّهُ قَانِتُونَ",
                    translationMs = "Dan kepunyaan-Nyalah siapa saja yang ada di langit dan di bumi. Semuanya hanya kepada-Nya tunduk.",
                    translationEn = "And to Him belongs whoever is in the heavens and earth. All are devoutly obedient to Him."
                ),
                VerseEntry(
                    arabic = "وَهُوَ الَّذِي يَبْدَأُ الْخَلْقَ ثُمَّ يُعِيدُهُ وَهُوَ أَهْوَنُ عَلَيْهِ وَلَهُ الْمَثَلُ الْأَعْلَىٰ فِي السَّمَاوَاتِ وَالْأَرْضِ وَهُوَ الْعَزِيزُ الْحَكِيمُ",
                    translationMs = "Dan Dialah yang menciptakan makhluk dari permulaannya, kemudian mengembalikannya; dan menghidupkan kembali itu adalah lebih mudah bagi-Nya. Dan bagi-Nyalah sifat yang Maha Tinggi di langit dan di bumi; dan Dialah Yang Maha Perkasa lagi Maha Bijaksana.",
                    translationEn = "And it is He who begins creation; then He repeats it, and that is even easier for Him. To Him belongs the highest attribute in the heavens and earth. And He is the Exalted in Might, the Wise."
                )
            )
        ),

        // ms 17–18 — Al-Mu'minun 115–118
        ZikrItem(
            id = 9, sortOrder = 90,
            title = "Al-Mu'minun: 115–118",
            titleMs = "Al-Mu'minun ayat 115–118",
            titleEn = "Al-Mu'minun: 115–118",
            subtitleEn = "Al-Mu'minun verses 115–118",
            arabic = "أَفَحَسِبْتُمْ أَنَّمَا خَلَقْنَاكُمْ عَبَثًا وَأَنَّكُمْ إِلَيْنَا لَا تُرْجَعُونَ ۝ فَتَعَالَى اللَّهُ الْمَلِكُ الْحَقُّ لَا إِلَٰهَ إِلَّا هُوَ رَبُّ الْعَرْشِ الْكَرِيمِ ۝ وَمَن يَدْعُ مَعَ اللَّهِ إِلَٰهًا آخَرَ لَا بُرْهَانَ لَهُ بِهِ فَإِنَّمَا حِسَابُهُ عِندَ رَبِّهِ إِنَّهُ لَا يُفْلِحُ الْكَافِرُونَ ۝ وَقُل رَّبِّ اغْفِرْ وَارْحَمْ وَأَنتَ خَيْرُ الرَّاحِمِينَ",
            transliteration = "Afahasibtum annama khalaqnakum 'abathan wa annakum ilayna la turja'un. Fata'alallahul-malikul-haqq, la ilaha illa huwa rabbul-'arshil-karim...",
            translation = "Maka apakah kamu mengira bahawa Kami menciptakan kamu main-main saja, dan kamu tidak akan dikembalikan kepada Kami? Maka Maha Tinggi Allah, Raja Yang sebenarnya; tidak ada Tuhan selain Dia, Tuhan yang memiliki Arsy yang mulia. Dan katakanlah: Ya Tuhanku, ampunilah dan berilah rahmat, dan Engkau adalah Pemberi rahmat yang paling baik.",
            translationEn = "Then did you think that We created you uselessly and that to Us you would not be returned? So exalted is Allah, the Sovereign, the Truth; there is no deity except Him, Lord of the Noble Throne. And whoever invokes besides Allah another deity for which he has no proof — his account is only with his Lord. Indeed, the disbelievers will not succeed. And say: My Lord, forgive and have mercy, and You are the best of the merciful.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.KUBRA),
            pairedVerses = listOf(
                VerseEntry(
                    arabic = "أَفَحَسِبْتُمْ أَنَّمَا خَلَقْنَاكُمْ عَبَثًا وَأَنَّكُمْ إِلَيْنَا لَا تُرْجَعُونَ",
                    transliteration = "Afahasibtum annama khalaqnakum 'abathan wa annakum ilayna la turja'un.",
                    translationMs = "Maka apakah kamu mengira bahawa Kami menciptakan kamu main-main saja, dan kamu tidak akan dikembalikan kepada Kami?",
                    translationEn = "Then did you think that We created you uselessly and that to Us you would not be returned?"
                ),
                VerseEntry(
                    arabic = "فَتَعَالَى اللَّهُ الْمَلِكُ الْحَقُّ لَا إِلَٰهَ إِلَّا هُوَ رَبُّ الْعَرْشِ الْكَرِيمِ",
                    transliteration = "Fata'alallahul-malikul-haqq, la ilaha illa huwa rabbul-'arshil-karim.",
                    translationMs = "Maka Maha Tinggi Allah, Raja Yang sebenarnya; tidak ada Tuhan selain Dia, Tuhan yang memiliki Arsy yang mulia.",
                    translationEn = "So exalted is Allah, the Sovereign, the Truth; there is no deity except Him, Lord of the Noble Throne."
                ),
                VerseEntry(
                    arabic = "وَمَن يَدْعُ مَعَ اللَّهِ إِلَٰهًا آخَرَ لَا بُرْهَانَ لَهُ بِهِ فَإِنَّمَا حِسَابُهُ عِندَ رَبِّهِ إِنَّهُ لَا يُفْلِحُ الْكَافِرُونَ",
                    transliteration = "Wa man yad'u ma'allahi ilahan akhara la burhanala hu bihi fa-innama hisabuhu 'inda rabbih, innahu la yuflichul-kafirun.",
                    translationMs = "Dan barangsiapa menyembah tuhan yang lain di samping Allah, padahal tidak ada suatu dalilpun baginya tentang itu, maka sesungguhnya perhitungannya di sisi Tuhannya. Sesungguhnya orang-orang yang kafir tiada beruntung.",
                    translationEn = "And whoever invokes besides Allah another deity for which he has no proof — his account is only with his Lord. Indeed, the disbelievers will not succeed."
                ),
                VerseEntry(
                    arabic = "وَقُل رَّبِّ اغْفِرْ وَارْحَمْ وَأَنتَ خَيْرُ الرَّاحِمِينَ",
                    transliteration = "Wa qur-rabbi ghfir warham wa anta khayrul-rahimin.",
                    translationMs = "Dan katakanlah: Ya Tuhanku, berilah ampun dan berilah rahmat, dan Engkau adalah Pemberi rahmat yang paling baik.",
                    translationEn = "And say: My Lord, forgive and have mercy, and You are the best of the merciful."
                )
            )
        ),

        // ms 19 — Al-Kafirun
        ZikrItem(
            id = 10, sortOrder = 100,
            title = "Al-Kafirun",
            titleMs = "Surah Al-Kafirun",
            titleEn = "Al-Kafirun",
            subtitleEn = "Surah Al-Kafirun",
            arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n\nقُلْ يَا أَيُّهَا الْكَافِرُونَ ۝ لَا أَعْبُدُ مَا تَعْبُدُونَ ۝ وَلَا أَنتُمْ عَابِدُونَ مَا أَعْبُدُ ۝ وَلَا أَنَا عَابِدٌ مَّا عَبَدتُّمْ ۝ وَلَا أَنتُمْ عَابِدُونَ مَا أَعْبُدُ ۝ لَكُمْ دِينُكُمْ وَلِيَ دِينِ",
            transliteration = "Qul ya ayyuhal-kafirun. La a'budu ma ta'budun. Wa la antum 'abiduna ma a'bud. Wa la ana 'abidun ma 'abadtum. Wa la antum 'abiduna ma a'bud. Lakum dinukum wa liya din.",
            translation = "Katakanlah: Hai orang-orang kafir, aku tidak akan menyembah apa yang kamu sembah. Dan kamu bukan penyembah Tuhan yang aku sembah. Dan aku tidak pernah menjadi penyembah apa yang kamu sembah. Dan kamu tidak pernah pula menjadi penyembah Tuhan yang aku sembah. Untukmu agamamu, dan untukku agamaku.",
            translationEn = "Say: O disbelievers, I do not worship what you worship. Nor are you worshippers of what I worship. Nor will I be a worshipper of what you worship. Nor will you be worshippers of what I worship. For you is your religion, and for me is my religion.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.KUBRA),
            pairedVerses = listOf(
                VerseEntry(arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ"),
                VerseEntry(
                    arabic = "قُلْ يَا أَيُّهَا الْكَافِرُونَ",
                    transliteration = "Qul ya ayyuhal-kafirun.",
                    translationMs = "Katakanlah: Hai orang-orang kafir,",
                    translationEn = "Say: O disbelievers,"
                ),
                VerseEntry(
                    arabic = "لَا أَعْبُدُ مَا تَعْبُدُونَ",
                    transliteration = "La a'budu ma ta'budun.",
                    translationMs = "Aku tidak akan menyembah apa yang kamu sembah.",
                    translationEn = "I do not worship what you worship."
                ),
                VerseEntry(
                    arabic = "وَلَا أَنتُمْ عَابِدُونَ مَا أَعْبُدُ",
                    transliteration = "Wa la antum 'abiduna ma a'bud.",
                    translationMs = "Dan kamu bukan penyembah Tuhan yang aku sembah.",
                    translationEn = "Nor are you worshippers of what I worship."
                ),
                VerseEntry(
                    arabic = "وَلَا أَنَا عَابِدٌ مَّا عَبَدتُّمْ",
                    transliteration = "Wa la ana 'abidun ma 'abadtum.",
                    translationMs = "Dan aku tidak pernah menjadi penyembah apa yang kamu sembah.",
                    translationEn = "Nor will I be a worshipper of what you worship."
                ),
                VerseEntry(
                    arabic = "وَلَا أَنتُمْ عَابِدُونَ مَا أَعْبُدُ",
                    transliteration = "Wa la antum 'abiduna ma a'bud.",
                    translationMs = "Dan kamu tidak pernah pula menjadi penyembah Tuhan yang aku sembah.",
                    translationEn = "Nor will you be worshippers of what I worship."
                ),
                VerseEntry(
                    arabic = "لَكُمْ دِينُكُمْ وَلِيَ دِينِ",
                    transliteration = "Lakum dinukum wa liya din.",
                    translationMs = "Untukmu agamamu, dan untukku agamaku.",
                    translationEn = "For you is your religion, and for me is my religion."
                )
            )
        ),

        // ms 21 — An-Nasr
        ZikrItem(
            id = 11, sortOrder = 110,
            title = "An-Nasr",
            titleMs = "Surah An-Nasr",
            titleEn = "An-Nasr",
            subtitleEn = "Surah An-Nasr",
            arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n\nإِذَا جَاءَ نَصْرُ اللَّهِ وَالْفَتْحُ ۝ وَرَأَيْتَ النَّاسَ يَدْخُلُونَ فِي دِينِ اللَّهِ أَفْوَاجًا ۝ فَسَبِّحْ بِحَمْدِ رَبِّكَ وَاسْتَغْفِرْهُ إِنَّهُ كَانَ تَوَّابًا",
            transliteration = "Idha ja'a nasrullahi wal-fath. Wa ra'aytan-nasa yadkhuluna fi dinillahi afwaja. Fasabbih bihamdi rabbika wastaghfirh, innahu kana tawwaba.",
            translation = "Apabila telah datang pertolongan Allah dan kemenangan, dan kamu lihat manusia masuk agama Allah dengan berbondong-bondong, maka bertasbihlah dengan memuji Tuhanmu dan mohonlah ampun kepada-Nya. Sesungguhnya Dia adalah Maha Penerima taubat.",
            translationEn = "When the victory of Allah has come and the conquest, and you see the people entering into the religion of Allah in multitudes, then exalt Him with praise of your Lord and ask forgiveness of Him. Indeed, He is ever Accepting of repentance.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.KUBRA),
            pairedVerses = listOf(
                VerseEntry(arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ"),
                VerseEntry(
                    arabic = "إِذَا جَاءَ نَصْرُ اللَّهِ وَالْفَتْحُ",
                    transliteration = "Idha ja'a nasrullahi wal-fath.",
                    translationMs = "Apabila telah datang pertolongan Allah dan kemenangan,",
                    translationEn = "When the victory of Allah has come and the conquest,"
                ),
                VerseEntry(
                    arabic = "وَرَأَيْتَ النَّاسَ يَدْخُلُونَ فِي دِينِ اللَّهِ أَفْوَاجًا",
                    transliteration = "Wa ra'aytan-nasa yadkhuluna fi dinillahi afwaja.",
                    translationMs = "Dan kamu lihat manusia masuk agama Allah dengan berbondong-bondong,",
                    translationEn = "And you see the people entering into the religion of Allah in multitudes,"
                ),
                VerseEntry(
                    arabic = "فَسَبِّحْ بِحَمْدِ رَبِّكَ وَاسْتَغْفِرْهُ إِنَّهُ كَانَ تَوَّابًا",
                    transliteration = "Fasabbih bihamdi rabbika wastaghfirh, innahu kana tawwaba.",
                    translationMs = "Maka bertasbihlah dengan memuji Tuhanmu dan mohonlah ampun kepada-Nya. Sesungguhnya Dia adalah Maha Penerima taubat.",
                    translationEn = "Then exalt Him with praise of your Lord and ask forgiveness of Him. Indeed, He is ever Accepting of repentance."
                )
            )
        ),

        // ms 21 — Al-Ikhlas x3
        ZikrItem(
            id = 12, sortOrder = 120,
            title = "Al-Ikhlas",
            titleMs = "Surah Al-Ikhlas",
            titleEn = "Al-Ikhlas",
            subtitleEn = "Surah Al-Ikhlas",
            arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n\nقُلْ هُوَ اللَّهُ أَحَدٌ ۝ اللَّهُ الصَّمَدُ ۝ لَمْ يَلِدْ وَلَمْ يُولَدْ ۝ وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ",
            transliteration = "Qul huwallahu ahad. Allahus-samad. Lam yalid wa lam yulad. Wa lam yakul lahu kufuwan ahad.",
            translation = "Katakanlah: Dialah Allah, Yang Maha Esa. Allah adalah Tuhan yang bergantung kepada-Nya segala sesuatu. Dia tiada beranak dan tidak pula diperanakkan. Dan tidak ada seorang pun yang setara dengan Dia.",
            translationEn = "Say: He is Allah, the One. Allah, the Eternal Refuge. He neither begets nor is born. Nor is there to Him any equivalent.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 21 — Al-Falaq x3
        ZikrItem(
            id = 13, sortOrder = 130,
            title = "Al-Falaq",
            titleMs = "Surah Al-Falaq",
            titleEn = "Al-Falaq",
            subtitleEn = "Surah Al-Falaq",
            arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n\nقُلْ أَعُوذُ بِرَبِّ الْفَلَقِ ۝ مِن شَرِّ مَا خَلَقَ ۝ وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ ۝ وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ ۝ وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ",
            transliteration = "Qul a'udhu birabbil-falaq. Min sharri ma khalaq. Wa min sharri ghasiqin idha waqab. Wa min sharrin-naffathati fil-'uqad. Wa min sharri hasidin idha hasad.",
            translation = "Katakanlah: Aku berlindung dengan Tuhan yang membelah cahaya subuh, dari kejahatan makhluk-Nya, dari kejahatan malam apabila telah gelap gulita, dari kejahatan wanita-wanita tukang sihir yang menghembus pada buhul-buhul, dan dari kejahatan pendengki bila ia mendengki.",
            translationEn = "Say: I seek refuge in the Lord of daybreak, from the evil of that which He created, and from the evil of darkness when it settles, and from the evil of the blowers in knots, and from the evil of an envier when he envies.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 23 — An-Nas x3
        ZikrItem(
            id = 14, sortOrder = 140,
            title = "An-Nas",
            titleMs = "Surah An-Nas",
            titleEn = "An-Nas",
            subtitleEn = "Surah An-Nas",
            arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n\nقُلْ أَعُوذُ بِرَبِّ النَّاسِ ۝ مَلِكِ النَّاسِ ۝ إِلَٰهِ النَّاسِ ۝ مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ ۝ الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ ۝ مِنَ الْجِنَّةِ وَالنَّاسِ",
            transliteration = "Qul a'udhu birabbin-nas. Malikin-nas. Ilahin-nas. Min sharril-waswasil-khannas. Alladhi yuwaswisu fi sudurin-nas. Minal-jinnati wan-nas.",
            translation = "Katakanlah: Aku berlindung dengan Tuhan (yang memelihara dan menguasai) manusia. Raja manusia. Sembahan manusia. Dari kejahatan (bisikan) syaitan yang biasa bersembunyi, yang membisikkan kejahatan ke dalam dada manusia, dari golongan jin dan manusia.",
            translationEn = "Say: I seek refuge in the Lord of mankind, the Sovereign of mankind, the God of mankind, from the evil of the retreating whisperer — who whispers in the breasts of mankind — from among the jinn and mankind.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ══════════════════════════════════════════
        // BAHAGIAN ZIKIR (ms 23–48)
        // ══════════════════════════════════════════

        // ms 23 — Asbahna Mulku Lillah (Pagi) x3
        ZikrItem(
            id = 15, sortOrder = 150,
            title = "Asbahna — Kerajaan Milik Allah",
            titleMs = "Pagi: Kerajaan milik Allah",
            titleEn = "Morning — Kingdom Belongs to Allah",
            subtitleEn = "Morning: Kingdom belongs to Allah",
            arabic = "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ لَا شَرِيكَ لَهُ لَا إِلَٰهَ إِلَّا هُوَ وَإِلَيْهِ النُّشُورُ",
            transliteration = "Asbahna wa asbahal-mulku lillahi wal-hamdu lillah, la sharika lah, la ilaha illa huwa wa ilayhin-nushur.",
            translation = "Sesungguhnya kami terjaga di pagi hari dengan kerajaan bumi dan segala isinya adalah milik Allah, segala puji bagi Allah, tiada sekutu bagi-Nya, tiada Tuhan selain Dia dan kepada-Nya kami dibangkitkan.",
            translationEn = "We have reached the morning, and the dominion of the heavens and earth belongs to Allah. All praise is for Allah. There is no partner with Him. There is no god but Him, and to Him is the resurrection.",
            targetCount = 3,
            sessions = setOf(Session.MORNING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 23 — Amsayna Mulku Lillah (Petang) x3
        ZikrItem(
            id = 16, sortOrder = 150,
            title = "Amsayna — Kerajaan Milik Allah",
            titleMs = "Petang: Kerajaan milik Allah",
            titleEn = "Evening — Kingdom Belongs to Allah",
            subtitleEn = "Evening: Kingdom belongs to Allah",
            arabic = "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ لَا شَرِيكَ لَهُ لَا إِلَٰهَ إِلَّا هُوَ وَإِلَيْهِ الْمَصِيرُ",
            transliteration = "Amsayna wa amsal-mulku lillahi wal-hamdu lillah, la sharika lah, la ilaha illa huwa wa ilayhal-masir.",
            translation = "Sesungguhnya kami terjaga di petang hari dengan kerajaan bumi dan segala isinya adalah milik Allah, segala puji bagi Allah, tiada sekutu bagi-Nya, tiada Tuhan selain Dia dan kepada-Nya tempat kembali.",
            translationEn = "We have reached the evening, and the dominion of the heavens and earth belongs to Allah. All praise is for Allah. There is no partner with Him. There is no god but Him, and to Him is the final return.",
            targetCount = 3,
            sessions = setOf(Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 25 — Fitrah Islam Pagi x3
        ZikrItem(
            id = 17, sortOrder = 160,
            title = "Fitrah Islam (Pagi)",
            titleMs = "Pagi: Kami dalam fitrah Islam",
            titleEn = "Morning — Declaration of Faith",
            subtitleEn = "Morning: We are upon the fitrah of Islam",
            arabic = "أَصْبَحْنَا عَلَى فِطْرَةِ الْإِسْلَامِ وَكَلِمَةِ الْإِخْلَاصِ وَعَلَى دِينِ نَبِيِّنَا مُحَمَّدٍ صَلَّى اللَّهُ عَلَيْهِ وَسَلَّمَ وَعَلَى مِلَّةِ أَبِينَا إِبْرَاهِيمَ حَنِيفًا وَمَا كَانَ مِنَ الْمُشْرِكِينَ",
            transliteration = "Asbahna 'ala fitratal-islami wa kalimatal-ikhlasi wa 'ala dini nabiyyina muhammadin sallallahu 'alayhi wa sallama wa 'ala millati abina ibrahima hanifan, wa ma kana minal-mushrikin.",
            translation = "Kami terjaga di pagi hari dalam keadaan berada di atas fitrah Islam, kalimat ikhlas, agama Nabi kami Muhammad ﷺ, dan agama bapa kami Ibrahim yang lurus, dan dia tidaklah termasuk orang-orang yang musyrik.",
            translationEn = "We have reached the morning upon the natural disposition of Islam, upon the word of sincerity, upon the religion of our Prophet Muhammad ﷺ, and upon the way of our father Ibrahim, who was a true Muslim, and he was not of the polytheists.",
            targetCount = 3,
            sessions = setOf(Session.MORNING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 25 — Fitrah Islam Petang x3
        ZikrItem(
            id = 18, sortOrder = 160,
            title = "Fitrah Islam (Petang)",
            titleMs = "Petang: Kami dalam fitrah Islam",
            titleEn = "Evening — Declaration of Faith",
            subtitleEn = "Evening: We are upon the fitrah of Islam",
            arabic = "أَمْسَيْنَا عَلَى فِطْرَةِ الْإِسْلَامِ وَكَلِمَةِ الْإِخْلَاصِ وَعَلَى دِينِ نَبِيِّنَا مُحَمَّدٍ صَلَّى اللَّهُ عَلَيْهِ وَسَلَّمَ وَعَلَى مِلَّةِ أَبِينَا إِبْرَاهِيمَ حَنِيفًا وَمَا كَانَ مِنَ الْمُشْرِكِينَ",
            transliteration = "Amsayna 'ala fitratal-islami wa kalimatal-ikhlasi wa 'ala dini nabiyyina muhammadin sallallahu 'alayhi wa sallama wa 'ala millati abina ibrahima hanifan, wa ma kana minal-mushrikin.",
            translation = "Kami terjaga di petang hari dalam keadaan berada di atas fitrah Islam, kalimat ikhlas, agama Nabi kami Muhammad ﷺ, dan agama bapa kami Ibrahim yang lurus, dan dia tidaklah termasuk orang-orang yang musyrik.",
            translationEn = "We have reached the evening upon the natural disposition of Islam, upon the word of sincerity, upon the religion of our Prophet Muhammad ﷺ, and upon the way of our father Ibrahim, who was a true Muslim, and he was not of the polytheists.",
            targetCount = 3,
            sessions = setOf(Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 25 — Allahumma asbaht minka ni'mah (Pagi) x3
        ZikrItem(
            id = 19, sortOrder = 170,
            title = "Nikmat dari Allah (Pagi)",
            titleMs = "Pagi: Ya Allah aku dalam nikmat-Mu",
            titleEn = "Morning — Blessings from Allah",
            subtitleEn = "Morning: O Allah, I am in Your blessings",
            arabic = "اللَّهُمَّ إِنِّي أَصْبَحْتُ مِنْكَ فِي نِعْمَةٍ وَعَافِيَةٍ وَسِتْرٍ فَأَتِمَّ عَلَيَّ نِعْمَتَكَ وَعَافِيَتَكَ وَسِتْرَكَ فِي الدُّنْيَا وَالْآخِرَةِ",
            transliteration = "Allahumma inni asbahtu minka fi ni'matin wa 'afiyatin wa sitr, fa-atimma 'alayya ni'mataka wa 'afiyataka wa sitrak fid-dunya wal-akhirah.",
            translation = "Ya Allah, sesungguhnya aku berada di pagi hari dalam kenikmatan, kesihatan, dan perlindungan dari-Mu. Maka sempurnakanlah nikmat-Mu, kesihatan-Mu, dan perlindungan-Mu ke atasku di dunia dan akhirat.",
            translationEn = "O Allah, I have entered the morning with blessings, good health and protection from You, so complete for me Your blessings, good health and protection in this world and the Hereafter.",
            targetCount = 3,
            sessions = setOf(Session.MORNING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 25 — Allahumma amsayt minka ni'mah (Petang) x3
        ZikrItem(
            id = 20, sortOrder = 170,
            title = "Nikmat dari Allah (Petang)",
            titleMs = "Petang: Ya Allah aku dalam nikmat-Mu",
            titleEn = "Evening — Blessings from Allah",
            subtitleEn = "Evening: O Allah, I am in Your blessings",
            arabic = "اللَّهُمَّ إِنِّي أَمْسَيْتُ مِنْكَ فِي نِعْمَةٍ وَعَافِيَةٍ وَسِتْرٍ فَأَتِمَّ عَلَيَّ نِعْمَتَكَ وَعَافِيَتَكَ وَسِتْرَكَ فِي الدُّنْيَا وَالْآخِرَةِ",
            transliteration = "Allahumma inni amsaytu minka fi ni'matin wa 'afiyatin wa sitr, fa-atimma 'alayya ni'mataka wa 'afiyataka wa sitrak fid-dunya wal-akhirah.",
            translation = "Ya Allah, sesungguhnya aku berada di petang hari dalam kenikmatan, kesihatan, dan perlindungan dari-Mu. Maka sempurnakanlah nikmat-Mu, kesihatan-Mu, dan perlindungan-Mu ke atasku di dunia dan akhirat.",
            translationEn = "O Allah, I have entered the evening with blessings, good health and protection from You, so complete for me Your blessings, good health and protection in this world and the Hereafter.",
            targetCount = 3,
            sessions = setOf(Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 27 — Allahumma ma asbaha bi (Pagi) x3
        ZikrItem(
            id = 21, sortOrder = 180,
            title = "Setiap Nikmat Dari-Mu (Pagi)",
            titleMs = "Pagi: Setiap nikmat dari Allah",
            titleEn = "Morning — Every Blessing is from You",
            subtitleEn = "Morning: Every blessing is from Allah",
            arabic = "اللَّهُمَّ مَا أَصْبَحَ بِي مِنْ نِعْمَةٍ أَوْ بِأَحَدٍ مِنْ خَلْقِكَ فَمِنْكَ وَحْدَكَ لَا شَرِيكَ لَكَ فَلَكَ الْحَمْدُ وَلَكَ الشُّكْرُ",
            transliteration = "Allahumma ma asbaha bi min ni'matin aw bi-ahadin min khalqika faminka wahdaka la sharika lak, falakal-hamdu wa lakash-shukr.",
            translation = "Ya Allah, nikmat apa saja yang aku rasakan di pagi ini atau yang dirasakan oleh seseorang dari makhluk-Mu, maka semuanya adalah dari-Mu semata, tiada sekutu bagi-Mu. Maka bagi-Mu segala puji dan syukur.",
            translationEn = "O Allah, whatever blessing I or any of Your creation have received this morning is from You alone. You have no partner. All praise and thanks are due to You.",
            targetCount = 3,
            sessions = setOf(Session.MORNING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 27 — Allahumma ma amsa bi (Petang) x3
        ZikrItem(
            id = 22, sortOrder = 180,
            title = "Setiap Nikmat Dari-Mu (Petang)",
            titleMs = "Petang: Setiap nikmat dari Allah",
            titleEn = "Evening — Every Blessing is from You",
            subtitleEn = "Evening: Every blessing is from Allah",
            arabic = "اللَّهُمَّ مَا أَمْسَى بِي مِنْ نِعْمَةٍ أَوْ بِأَحَدٍ مِنْ خَلْقِكَ فَمِنْكَ وَحْدَكَ لَا شَرِيكَ لَكَ فَلَكَ الْحَمْدُ وَلَكَ الشُّكْرُ",
            transliteration = "Allahumma ma amsa bi min ni'matin aw bi-ahadin min khalqika faminka wahdaka la sharika lak, falakal-hamdu wa lakash-shukr.",
            translation = "Ya Allah, nikmat apa saja yang aku rasakan di petang ini atau yang dirasakan oleh seseorang dari makhluk-Mu, maka semuanya adalah dari-Mu semata, tiada sekutu bagi-Mu. Maka bagi-Mu segala puji dan syukur.",
            translationEn = "O Allah, whatever blessing I or any of Your creation have received this evening is from You alone. You have no partner. All praise and thanks are due to You.",
            targetCount = 3,
            sessions = setOf(Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 27 — Ya Rabbi lakal hamd x3
        ZikrItem(
            id = 23, sortOrder = 190,
            title = "Ya Rabbi Lakal Hamd",
            titleMs = "Ya Tuhanku, segala puji bagi-Mu",
            titleEn = "All Praise Belongs to My Lord",
            subtitleEn = "O Lord, all praise belongs to You",
            arabic = "يَا رَبِّ لَكَ الْحَمْدُ كَمَا يَنْبَغِي لِجَلَالِ وَجْهِكَ وَعَظِيمِ سُلْطَانِكَ",
            transliteration = "Ya rabbi lakal-hamdu kama yanbaghi lijalali wajhika wa 'azimi sultanik.",
            translation = "Wahai Tuhanku, bagi-Mu segala puji sebagaimana yang layak bagi kemuliaan wajah-Mu dan keagungan kekuasaan-Mu.",
            translationEn = "O my Lord, to You belongs all praise as befits the glory of Your countenance and the greatness of Your authority.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 27 — Radhitu billahi x3
        ZikrItem(
            id = 24, sortOrder = 200,
            title = "Redha dengan Allah",
            titleMs = "Redha dengan Allah, Islam dan Nabi Muhammad",
            titleEn = "Contentment with Allah",
            subtitleEn = "Contentment with Allah, Islam and Prophet Muhammad",
            arabic = "رَضِيتُ بِاللَّهِ رَبًّا وَبِالْإِسْلَامِ دِينًا وَبِمُحَمَّدٍ نَبِيًّا وَرَسُولًا",
            transliteration = "Radhitu billahi rabban wa bil-islami dinan wa bi-muhammadin nabiyyan wa rasulan.",
            translation = "Aku redha dengan Allah sebagai Tuhan, Islam sebagai agama, dan Muhammad ﷺ sebagai Nabi dan Rasul.",
            translationEn = "I am pleased with Allah as my Lord, with Islam as my religion, and with Muhammad ﷺ as my Prophet and Messenger.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 27 — Subhanallah adada khalqih x3
        ZikrItem(
            id = 25, sortOrder = 210,
            title = "Tasbih Adada Khalqih",
            titleMs = "Maha Suci Allah sebanyak bilangan makhluk-Nya",
            titleEn = "Glorification — Equal to His Creation",
            subtitleEn = "Glory be to Allah equal to His creation",
            arabic = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ عَدَدَ خَلْقِهِ وَرِضَا نَفْسِهِ وَزِنَةَ عَرْشِهِ وَمِدَادَ كَلِمَاتِهِ",
            transliteration = "Subhanallahi wa bihamdih, 'adada khalqih, wa ridha nafsih, wa zinata 'arshih, wa midada kalimatih.",
            translation = "Maha Suci Allah dan segala puji bagi-Nya, sebanyak bilangan makhluk-Nya, keredhaan zat-Nya, timbangan Arsy-Nya, dan sebanyak tinta untuk menulis kalimat-kalimat-Nya.",
            translationEn = "Glory be to Allah and praise be to Him, as many times as the number of His creation, to the extent of His pleasure, equal to the weight of His Throne, and as much as the ink of His words.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 29 — Bismillahilladhi la yadhurru x3
        ZikrItem(
            id = 26, sortOrder = 220,
            title = "Perlindungan Nama Allah",
            titleMs = "Dengan nama Allah yang melindungi",
            titleEn = "Protection in Allah's Name",
            subtitleEn = "With Allah's name, nothing can cause harm",
            arabic = "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الْأَرْضِ وَلَا فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ",
            transliteration = "Bismillahil-ladhi la yadhurru ma'asmihi shay'un fil-ardhi wa la fis-sama'i wa huwas-sami'ul-'alim.",
            translation = "Dengan nama Allah yang apabila disebut nama-Nya, tidak ada sesuatu pun di bumi dan di langit yang dapat memberi bahaya, dan Dia Maha Mendengar lagi Maha Mengetahui.",
            translationEn = "In the name of Allah, with whose name nothing in the earth or the heaven can cause harm, and He is the All-Hearing, the All-Knowing.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 29 — Allahumma inna na'udhu bika x3
        ZikrItem(
            id = 27, sortOrder = 230,
            title = "Berlindung dari Syirik",
            titleMs = "Ya Allah, kami berlindung dari syirik",
            titleEn = "Protection from Associating Partners",
            subtitleEn = "O Allah, we seek refuge from associating partners",
            arabic = "اللَّهُمَّ إِنَّا نَعُوذُبِكَ أَنْ نُشْرِكَ بِكَ شَيْئًا نَعْلَمُهُ وَنَسْتَغْفِرُكَ لِمَا لَا نَعْلَمُهُ",
            transliteration = "Allahumma inna na'udhubika an nushrika bika shay'an na'lamuh, wa nastaghfiruka lima la na'lamuh.",
            translation = "Ya Allah, kami berlindung kepada-Mu dari menyekutukan-Mu dengan sesuatu yang kami ketahui, dan kami memohon ampun dari syirik yang tidak kami ketahui.",
            translationEn = "O Allah, we seek refuge in You from associating anything with You knowingly, and we seek Your forgiveness for what we do not know.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 29 — A'udhu bikalimatillah x3
        ZikrItem(
            id = 28, sortOrder = 240,
            title = "A'udhu Bikalimatillah",
            titleMs = "Berlindung dengan kalimah Allah yang sempurna",
            titleEn = "Seeking Refuge in Allah's Words",
            subtitleEn = "Seeking refuge in Allah's perfect words",
            arabic = "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ",
            transliteration = "A'udhu bikalimatillahit-tammati min sharri ma khalaq.",
            translation = "Aku berlindung dengan kalimah-kalimah Allah yang sempurna dari kejahatan apa-apa yang diciptakan-Nya.",
            translationEn = "I seek refuge in the perfect words of Allah from the evil of what He has created.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 31 — Allahumma hamm wal huzn x3
        ZikrItem(
            id = 29, sortOrder = 250,
            title = "Berlindung dari Kesusahan",
            titleMs = "Ya Allah, aku berlindung dari rasa gelisah",
            titleEn = "Protection from Distress and Grief",
            subtitleEn = "O Allah, I seek refuge from worry and grief",
            arabic = "اللَّهُمَّ إِنِّي أَعُوذُبِكَ مِنَ الْهَمِّ وَالْحَزَنِ وَأَعُوذُبِكَ مِنَ الْعَجْزِ وَالْكَسَلِ وَأَعُوذُبِكَ مِنَ الْجُبْنِ وَالْبُخْلِ وَأَعُوذُبِكَ مِنْ غَلَبَةِ الدَّيْنِ وَقَهْرِ الرِّجَالِ",
            transliteration = "Allahumma inni a'udhubika minal-hammi wal-hazan, wa a'udhubika minal-'ajzi wal-kasal, wa a'udhubika minal-jubni wal-bukhl, wa a'udhubika min ghalabatid-dayni wa qahrir-rijal.",
            translation = "Ya Allah, aku berlindung kepada-Mu dari rasa gelisah dan sedih, dari sifat lemah dan malas, dari sifat pengecut dan bakhil, dan dari tekanan hutang dan paksaan orang.",
            translationEn = "O Allah, I seek refuge in You from worry and grief, from helplessness and laziness, from cowardice and miserliness, and from the burden of debt and the tyranny of people.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 31 — Allahumma afini x3
        ZikrItem(
            id = 30, sortOrder = 260,
            title = "Sihatkan Badan dan Pancaindera",
            titleMs = "Ya Allah, sihatkan badan, pendengaran dan penglihatan",
            titleEn = "Health of Body and Senses",
            subtitleEn = "O Allah, grant health to my body and senses",
            arabic = "اللَّهُمَّ عَافِنِي فِي بَدَنِي اللَّهُمَّ عَافِنِي فِي سَمْعِي اللَّهُمَّ عَافِنِي فِي بَصَرِي",
            transliteration = "Allahumma 'afini fi badani, Allahumma 'afini fi sam'i, Allahumma 'afini fi basari.",
            translation = "Ya Allah, sihatkanlah badanku. Ya Allah, sihatkanlah pendengaranku. Ya Allah, sihatkanlah penglihatanku.",
            translationEn = "O Allah, grant my body good health. O Allah, grant my hearing good health. O Allah, grant my sight good health.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 31 — Allahumma kufr wal faqr x3
        ZikrItem(
            id = 31, sortOrder = 270,
            title = "Berlindung dari Kufur",
            titleMs = "Ya Allah, aku berlindung dari kufur dan kemiskinan",
            titleEn = "Protection from Disbelief and Poverty",
            subtitleEn = "O Allah, I seek refuge from disbelief and poverty",
            arabic = "اللَّهُمَّ إِنِّي أَعُوذُبِكَ مِنَ الْكُفْرِ وَالْفَقْرِ وَأَعُوذُبِكَ مِنْ عَذَابِ الْقَبْرِ لَا إِلَٰهَ إِلَّا أَنْتَ",
            transliteration = "Allahumma inni a'udhubika minal-kufri wal-faqr, wa a'udhubika min 'adhabii-qabr, la ilaha illa ant.",
            translation = "Ya Allah, aku berlindung kepada-Mu dari kekufuran dan kemiskinan, dan aku berlindung kepada-Mu dari azab kubur. Tiada Tuhan selain Engkau.",
            translationEn = "O Allah, I seek refuge in You from disbelief and poverty, and I seek refuge in You from the punishment of the grave. There is no deity worthy of worship but You.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 33 — Sayyidul Istighfar x3
        ZikrItem(
            id = 32, sortOrder = 280,
            title = "Sayyidul Istighfar",
            titleMs = "Penghulu Istighfar",
            titleEn = "Sayyidul Istighfar",
            subtitleEn = "The Master Supplication for Forgiveness",
            arabic = "اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَٰهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ أَعُوذُبِكَ مِنْ شَرِّ مَا صَنَعْتُ أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ وَأَبُوءُ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ",
            transliteration = "Allahumma anta rabbi la ilaha illa anta khalaqtani wa ana 'abduk, wa ana 'ala 'ahdika wa wa'dika mastata'tu, a'udhubika min sharri ma sana't, abu'u laka bini'matika 'alayya wa abu'u bidhanbiy, faghfir li fa-innahu la yaghfirudh-dhunuba illa ant.",
            translation = "Ya Allah, Engkau adalah Tuhanku, tiada Tuhan selain Engkau. Engkau yang menciptakanku dan aku adalah hamba-Mu. Aku berpegang pada perjanjian dan janji-Mu semampuku. Aku berlindung kepada-Mu dari kejahatan yang aku lakukan. Aku mengakui nikmat-Mu kepadaku dan aku mengakui dosaku. Maka ampunilah aku, sesungguhnya tiada yang dapat mengampuni dosa selain Engkau.",
            translationEn = "O Allah, You are my Lord, there is no deity except You. You created me and I am Your servant. I am upon Your covenant and Your promise as much as I am able. I seek refuge in You from the evil I have done. I acknowledge Your blessings upon me, and I acknowledge my sins, so forgive me, for indeed none forgives sins except You.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 33 — Istighfar x3
        ZikrItem(
            id = 33, sortOrder = 290,
            title = "Istighfar",
            titleMs = "Memohon ampun kepada Allah",
            titleEn = "Istighfar (Seeking Forgiveness)",
            subtitleEn = "Seeking forgiveness from Allah",
            arabic = "أَسْتَغْفِرُ اللَّهَ الَّذِي لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ وَأَتُوبُ إِلَيْهِ",
            transliteration = "Astaghfirullahl-ladhi la ilaha illa huwal-hayyul-qayyumu wa atubu ilayh.",
            translation = "Aku memohon ampun kepada Allah, yang tiada Tuhan melainkan Dia, Yang Maha Hidup lagi Maha Berdiri Sendiri, dan aku bertaubat kepada-Nya.",
            translationEn = "I seek forgiveness from Allah, other than whom there is no deity, the Ever-Living, the Sustainer, and I repent to Him.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 35 — Selawat Ibrahimiyyah x10
        ZikrItem(
            id = 34, sortOrder = 300,
            title = "Selawat Ibrahimiyyah",
            titleMs = "Selawat ke atas Nabi Muhammad ﷺ",
            titleEn = "Salawat Ibrahimiyyah",
            subtitleEn = "Salawat upon Prophet Muhammad ﷺ",
            arabic = "اللَّهُمَّ صَلِّ عَلَى سَيِّدِنَا مُحَمَّدٍ وَعَلَى آلِ سَيِّدِنَا مُحَمَّدٍ كَمَا صَلَّيْتَ عَلَى سَيِّدِنَا إِبْرَاهِيمَ وَعَلَى آلِ سَيِّدِنَا إِبْرَاهِيمَ وَبَارِكْ عَلَى سَيِّدِنَا مُحَمَّدٍ وَعَلَى آلِ سَيِّدِنَا مُحَمَّدٍ كَمَا بَارَكْتَ عَلَى سَيِّدِنَا إِبْرَاهِيمَ وَعَلَى آلِ سَيِّدِنَا إِبْرَاهِيمَ فِي الْعَالَمِينَ إِنَّكَ حَمِيدٌ مَجِيدٌ",
            transliteration = "Allahumma salli 'ala sayyidina muhammadin wa 'ala ali sayyidina muhammadin kama sallayta 'ala sayyidina ibrahima wa 'ala ali sayyidina ibrahim, wa barik 'ala sayyidina muhammadin wa 'ala ali sayyidina muhammadin kama barakta 'ala sayyidina ibrahima wa 'ala ali sayyidina ibrahima fil-'alamin, innaka hamidun-majid.",
            translation = "Ya Allah, limpahkanlah selawat kepada junjungan kami Muhammad dan keluarganya, sebagaimana Engkau melimpahkan selawat kepada junjungan kami Ibrahim dan keluarganya. Dan berkatilah junjungan kami Muhammad dan keluarganya, sebagaimana Engkau memberkati junjungan kami Ibrahim dan keluarganya di seluruh alam. Sesungguhnya Engkau Maha Terpuji lagi Maha Mulia.",
            translationEn = "O Allah, bestow Your mercy upon Muhammad and upon the family of Muhammad, as You bestowed Your mercy upon Ibrahim and upon the family of Ibrahim. And bestow Your blessings upon Muhammad and upon the family of Muhammad, as You bestowed Your blessings upon Ibrahim and upon the family of Ibrahim in all the worlds. Indeed You are Praiseworthy, Most Glorious.",
            targetCount = 10,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.KUBRA)
        ),

        // ms 35 — Tasbih + Tahmid + Tahlil + Takbir x100
        ZikrItem(
            id = 35, sortOrder = 310,
            title = "Tasbih, Tahmid, Tahlil & Takbir",
            titleMs = "Maha Suci Allah, Segala Puji, Tiada Tuhan, Allah Maha Besar",
            titleEn = "Tasbih, Tahmid, Tahlil & Takbir",
            subtitleEn = "Glory, Praise, Oneness and Greatness of Allah",
            arabic = "سُبْحَانَ اللَّهِ وَالْحَمْدُ لِلَّهِ وَلَا إِلَٰهَ إِلَّا اللَّهُ وَاللَّهُ أَكْبَرُ",
            transliteration = "Subhanallahi wal-hamdu lillahi wa la ilaha illallahu wallahu akbar.",
            translation = "Maha Suci Allah, segala puji bagi Allah, tiada Tuhan selain Allah, dan Allah Maha Besar.",
            translationEn = "Glory be to Allah, all praise is for Allah, there is no deity but Allah, and Allah is the Greatest.",
            targetCount = 100,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.KUBRA)
        ),

        // ms 37 — La ilaha illallah wahdahu x10
        ZikrItem(
            id = 36, sortOrder = 320,
            title = "Tahlil",
            titleMs = "Tiada Tuhan selain Allah Yang Maha Esa",
            titleEn = "Tahlil",
            subtitleEn = "There is no god but Allah, the One",
            arabic = "لَا إِلَٰهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
            transliteration = "La ilaha illallahu wahdahu la sharika lah, lahul-mulku wa lahul-hamdu wa huwa 'ala kulli shay'in qadir.",
            translation = "Tiada Tuhan selain Allah Yang Maha Esa, tiada sekutu bagi-Nya. Bagi-Nya kerajaan dan bagi-Nya segala puji, dan Dia Maha Kuasa atas segala sesuatu.",
            translationEn = "There is no deity but Allah, alone, with no partner. To Him belongs the dominion and to Him belongs all praise, and He has power over all things.",
            targetCount = 10,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.KUBRA)
        ),

        // ms 37 — Subhanaka Allahumma (Kaffarat) x3
        ZikrItem(
            id = 37, sortOrder = 330,
            title = "Kaffarat Majlis",
            titleMs = "Penutup dan penebus majlis",
            titleEn = "Expiation of the Gathering",
            subtitleEn = "Expiation and closing of the gathering",
            arabic = "سُبْحَانَكَ اللَّهُمَّ وَبِحَمْدِكَ أَشْهَدُ أَنْ لَا إِلَٰهَ إِلَّا أَنْتَ أَسْتَغْفِرُكَ وَأَتُوبُ إِلَيْكَ",
            transliteration = "Subhanaka allahumma wa bihamdika ashhadu an la ilaha illa anta, astaghfiruka wa atubu ilayk.",
            translation = "Maha Suci Engkau ya Allah, dan segala puji bagi-Mu. Aku bersaksi bahawa tiada Tuhan selain Engkau. Aku memohon ampun-Mu dan bertaubat kepada-Mu.",
            translationEn = "Glory be to You, O Allah, and praise. I bear witness that there is no deity but You. I seek Your forgiveness and repent to You.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 37 — Selawat Nabi Ummiy x3
        ZikrItem(
            id = 38, sortOrder = 340,
            title = "Selawat Nabi Ummiy",
            titleMs = "Selawat ke atas Nabi yang Ummi",
            titleEn = "Salawat upon the Unlettered Prophet",
            subtitleEn = "Salawat upon the Unlettered Prophet",
            arabic = "اللَّهُمَّ صَلِّ عَلَى سَيِّدِنَا مُحَمَّدٍ عَبْدِكَ وَنَبِيِّكَ وَرَسُولِكَ النَّبِيِّ الْأُمِّيِّ وَعَلَى آلِهِ وَصَحْبِهِ وَسَلِّمْ تَسْلِيمًا عَدَدَ مَا أَحَاطَ بِهِ عِلْمُكَ وَخَطَّ بِهِ قَلَمُكَ وَأَحْصَاهُ كِتَابُكَ",
            transliteration = "Allahumma salli 'ala sayyidina muhammadin 'abdika wa nabiyyika wa rasulikan-nabiyyil-ummiyyi wa 'ala alihi wa sahbihi wa sallim tasliman 'adada ma ahata bihi 'ilmuka wa khatta bihi qalamuka wa ahsahu kitabuk.",
            translation = "Ya Allah, limpahkanlah selawat ke atas junjungan kami Muhammad, hamba-Mu, Nabi-Mu dan Rasul-Mu yang Ummi, dan ke atas keluarga serta para sahabatnya, dan sejahterakanlah sepenuh-penuh kesejahteraan sebanyak bilangan yang meliputi ilmu-Mu, yang ditulis oleh pena-Mu dan yang dihitung oleh kitab-Mu.",
            translationEn = "O Allah, bestow mercy upon our master Muhammad, Your servant, Your Prophet and Your Messenger, the unlettered Prophet, and upon his family and companions, and grant them complete peace as many times as what Your knowledge encompasses, what Your pen has written, and what Your Book has counted.",
            targetCount = 3,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 39 — Doa para Sahabat x1
        ZikrItem(
            id = 39, sortOrder = 350,
            title = "Doa Para Sahabat",
            titleMs = "Doa untuk para sahabat Nabi",
            titleEn = "Prayer for the Companions",
            subtitleEn = "Prayer for the Companions of the Prophet",
            arabic = "وَارْضَ اللَّهُمَّ عَنْ سَادَاتِنَا أَبِي بَكْرٍ وَعُمَرَ وَعُثْمَانَ وَعَلِيٍّ وَعَنِ الصَّحَابَةِ أَجْمَعِينَ وَعَنِ التَّابِعِينَ وَتَابِعِيهِمْ بِإِحْسَانٍ إِلَى يَوْمِ الدِّينِ سُبْحَانَ رَبِّكَ رَبِّ الْعِزَّةِ عَمَّا يَصِفُونَ وَسَلَامٌ عَلَى الْمُرْسَلِينَ وَالْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
            transliteration = "War-dhallahummah 'an sadatina abi bakrin wa 'umara wa 'uthmana wa 'aliyyin wa 'anis-sahabati ajma'in wa 'anit-tabi'ina wa tabi'ihim bi-ihsanin ila yawmid-din. Subhana rabbika rabbil-'izzati 'amma yasifun, wa salamun 'alal-mursalin, wal-hamdu lillahi rabbil-'alamin.",
            translation = "Ya Allah, redhailah pemimpin-pemimpin kami Abu Bakar, Umar, Uthman dan Ali, serta semua para sahabat, para tabi'in dan pengikut mereka dengan baik hingga hari Kiamat. Maha Suci Tuhanmu, Tuhan Yang Maha Mulia dari sifat yang mereka nisbahkan. Semoga keselamatan dilimpahkan ke atas para Rasul. Segala puji bagi Allah, Tuhan sekalian alam.",
            translationEn = "O Allah, be pleased with our leaders Abu Bakr, Umar, Uthman and Ali, and with all the Companions, the Successors and those who follow them in righteousness until the Day of Judgement. Glory be to your Lord, the Lord of Honour, above what they attribute to Him. Peace be upon the Messengers, and all praise is for Allah, Lord of all the worlds.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 39–42 — Doa Khusyu x1
        ZikrItem(
            id = 40, sortOrder = 360,
            title = "Doa Khusyu",
            titleMs = "Doa memohon hati yang khusyu",
            titleEn = "Prayer for Humility and Goodness",
            subtitleEn = "Prayer for humility, sincerity and goodness",
            arabic = "اللَّهُمَّ إِنَّا نَسْأَلُكَ لِسَانًا رَطِبًا بِذِكْرِكَ وَقَلْبًا مَفْعُومًا بِشُكْرِكَ وَبَدَنًا هَيِّنًا لَيِّنًا بِطَاعَتِكَ اللَّهُمَّ إِنَّا نَسْأَلُكَ إِيمَانًا كَامِلًا وَنَسْأَلُكَ قَلْبًا خَاشِعًا وَنَسْأَلُكَ عِلْمًا نَافِعًا وَنَسْأَلُكَ يَقِينًا صَادِقًا وَنَسْأَلُكَ دِينًا قَيِّمًا وَنَسْأَلُكَ الْعَافِيَةَ مِنْ كُلِّ بَلِيَّةٍ وَنَسْأَلُكَ تَمَامَ الْغِنَى عَنِ النَّاسِ وَهَبْ لَنَا حَقِيقَةَ الْإِيمَانِ بِكَ حَتَّى لَا نَخَافَ وَلَا نَرْجُو غَيْرَكَ وَلَا نَعْبُدَ شَيْئًا سِوَاكَ وَاجْعَلْ يَدَكَ مَبْسُوطَةً عَلَيْنَا وَعَلَى أَهْلِينَا وَأَوْلَادِنَا وَمَنْ مَعَنَا بِرَحْمَتِكَ وَلَا تَكِلْنَا إِلَى أَنْفُسِنَا طَرْفَةَ عَيْنٍ وَلَا أَقَلَّ مِنْ ذَلِكَ يَا نَعِمَ الْمُجِيبُ وَصَلَّى اللَّهُ عَلَى سَيِّدِنَا مُحَمَّدٍ النَّبِيِّ الْكَرِيمِ وَعَلَى آلِهِ وَصَحْبِهِ أَجْمَعِينَ",
            transliteration = "Allahumma inna nas'aluka lisanan ratiban bidhikrik, wa qalban maf'uman bishukrik, wa badanan hayinan layyinan bita'atik. Allahumma inna nas'aluka imanan kamilan, wa nas'aluka qalban kashi'an, wa nas'aluka 'ilman nafi'an, wa nas'aluka yaqinan sadiqa, wa nas'aluka dinan qayyima, wa nas'aluka al-'afiyata min kulli baliyyah, wa nas'aluka tamam al-ghina 'anin-nas. Wa hab lana haqiqatal-imani bika hatta la nakhafa wa la narju ghayrak, wa la na'buda shay'an siwak. Waj'al yadaka mabsutatan 'alayna wa 'ala ahlina wa awladina wa man ma'ana birahmatik, wa la takilna ila anfusina tarfata 'aynin wa la aqalla min dhalik, ya na'imal-mujib.",
            translation = "Ya Allah, kami memohon kepada-Mu lisan yang sentiasa basah dengan zikir-Mu, hati yang penuh dengan rasa syukur kepada-Mu, dan badan yang mudah lagi lembut dalam mentaati-Mu. Ya Allah, kami memohon iman yang sempurna, hati yang khusyu, ilmu yang bermanfaat, keyakinan yang benar, agama yang lurus, dan kesejahteraan dari setiap bencana. Kami memohon kecukupan yang tidak memerlukan manusia. Kurniakanlah kepada kami hakikat keimanan kepada-Mu sehingga kami tidak takut dan tidak mengharap selain-Mu, dan tidak menyembah sesuatu pun selain-Mu. Jadikanlah tangan-Mu terbentang ke atas kami, ahli keluarga kami, anak-anak kami dan orang-orang yang bersama kami dengan rahmat-Mu. Janganlah Engkau serahkan kami kepada diri kami sendiri walaupun sekelip mata atau kurang dari itu, wahai sebaik-baik Pemberi jawapan.",
            translationEn = "O Allah, we ask You for a tongue moist with Your remembrance, a heart filled with gratitude to You, and a body that is gentle and supple in obedience to You. O Allah, we ask You for complete faith, a humble heart, beneficial knowledge, sincere certainty, an upright religion, and well-being from every affliction. We ask You for contentment that frees us from depending on people. Grant us the reality of faith in You such that we fear and hope for none but You, and worship nothing besides You. Lay Your hand of mercy over us, our families, our children, and those with us. Do not leave us to ourselves even for the blink of an eye or less than that, O Best of Responders.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 43 — Ali Imran 26-27 x1
        ZikrItem(
            id = 43, sortOrder = 365,
            title = "Ali Imran: 26–27",
            titleMs = "Ali Imran ayat 26–27",
            titleEn = "Ali Imran: 26–27",
            subtitleEn = "Ali Imran verses 26–27",
            arabic = "قُلِ اللَّهُمَّ مَالِكَ الْمُلْكِ تُؤْتِي الْمُلْكَ مَنْ تَشَاءُ وَتَنْزِعُ الْمُلْكَ مِمَّنْ تَشَاءُ وَتُعِزُّ مَنْ تَشَاءُ وَتُذِلُّ مَنْ تَشَاءُ بِيَدِكَ الْخَيْرُ إِنَّكَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ تُولِجُ اللَّيْلَ فِي النَّهَارِ وَتُولِجُ النَّهَارَ فِي اللَّيْلِ وَتُخْرِجُ الْحَيَّ مِنَ الْمَيِّتِ وَتُخْرِجُ الْمَيِّتَ مِنَ الْحَيِّ وَتَرْزُقُ مَنْ تَشَاءُ بِغَيْرِ حِسَابٍ",
            transliteration = "Qulillahumma malikal-mulki tu'til-mulka man tasha' wa tanzi'ul-mulka mimman tasha', wa tu'izzu man tasha' wa tudhillu man tasha', biyadikal-khayr, innaka 'ala kulli shay'in qadir. Tulijul-layla fin-nahari wa tulijun-nahara fil-layl, wa tukhrujul-hayya minal-mayyiti wa tukhrujul-mayyita minal-hayy, wa tarzuqu man tasha' bighayri hisab.",
            translation = "Katakanlah: Ya Allah, Tuhan yang memiliki kerajaan, Engkau berikan kerajaan kepada siapa yang Engkau kehendaki dan Engkau cabut kerajaan dari siapa yang Engkau kehendaki. Engkau muliakan siapa yang Engkau kehendaki dan Engkau hinakan siapa yang Engkau kehendaki. Di tangan-Mulah segala kebajikan. Sesungguhnya Engkau Maha Kuasa atas segala sesuatu. Engkau masukkan malam ke dalam siang dan Engkau masukkan siang ke dalam malam. Engkau keluarkan yang hidup dari yang mati, dan Engkau keluarkan yang mati dari yang hidup. Engkau beri rezeki siapa yang Engkau kehendaki tanpa hisab.",
            translationEn = "Say: O Allah, Owner of Sovereignty, You give sovereignty to whom You will and You take sovereignty away from whom You will. You honour whom You will and You humble whom You will. In Your hand is all good. Indeed, You are over all things competent. You cause the night to enter the day, and You cause the day to enter the night; and You bring the living out of the dead, and You bring the dead out of the living. And You give provision to whom You will without account.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 45 — Doa Awal Malam (Petang sahaja) x1
        ZikrItem(
            id = 41, sortOrder = 370,
            title = "Doa Awal Malam",
            titleMs = "Doa menjelang malam (khusus petang)",
            titleEn = "Evening Prayer",
            subtitleEn = "Evening prayer as the night approaches",
            arabic = "اللَّهُمَّ إِنَّ هَذَا إِقْبَالُ لَيْلِكَ وَإِدْبَارُ نَهَارِكَ وَأَصْوَاتُ دُعَاتِكَ فَاغْفِرْ لِي",
            transliteration = "Allahumma inna hadha iqbalu laylika wa idbaru naharika wa aswatu du'atika faghfir li.",
            translation = "Ya Allah, sesungguhnya ini adalah menjelangnya malam-Mu dan berlalunya siang-Mu, dan suara-suara para penyeru-Mu, maka ampunilah aku.",
            translationEn = "O Allah, this is the approach of Your night and the retreat of Your day, and the voices of Your callers, so forgive me.",
            targetCount = 1,
            sessions = setOf(Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 45–47 — Doa Rabithah (Petang sahaja) x1
        ZikrItem(
            id = 42, sortOrder = 380,
            title = "Doa Rabithah",
            titleMs = "Doa perpaduan hati",
            titleEn = "Prayer of the Heart Bond",
            subtitleEn = "Prayer for hearts united in love and obedience to Allah",
            arabic = "اللَّهُمَّ إِنَّكَ تَعْلَمُ أَنَّ هَذِهِ الْقُلُوبَ قَدِ اجْتَمَعَتْ عَلَى مَحَبَّتِكَ وَالْتَفَّتْ عَلَى طَاعَتِكَ وَتَوَحَّدَتْ عَلَى دَعْوَتِكَ وَتَعَاهَدَتْ عَلَى نُصْرَةِ شَرِيعَتِكَ فَوَثِّقِ اللَّهُمَّ رَابِطَتَهَا وَأَدِمْ وُدَّهَا وَاهْدِهَا سُبُلَهَا وَامْلَأْهَا بِنُورِكَ الَّذِي لَا يَخْبُو وَاشْرَحْ صُدُورَهَا بِفَيْضِ الْإِيمَانِ بِكَ وَجَمِيلِ التَّوَكُّلِ عَلَيْكَ وَأَحْيِهَا بِمَعْرِفَتِكَ وَأَمِتْهَا عَلَى الشَّهَادَةِ فِي سَبِيلِكَ إِنَّكَ نِعْمَ الْمَوْلَى وَنِعْمَ النَّصِيرُ آمِينَ اللَّهُمَّ صَلِّ عَلَى سَيِّدِنَا مُحَمَّدٍ وَعَلَى آلِهِ وَصَحْبِهِ وَسَلِّمَ",
            transliteration = "Allahumma innaka ta'lamu anna hadhihil-quluba qadij-tama'at 'ala mahabbatik, wal-taffat 'ala ta'atik, wa tawahhdat 'ala da'watik, wa ta'ahadat 'ala nusrati shari'atik. Fa-waththiqil-lahumma rabitataها wa adim wuddaha wah-diha subulaha wam-la'ha binurikal-ladhi la yakhbu, wash-rah suduraha bifaydil-imani bika wa jamilit-tawakkuli 'alayk, wa ahyiha bima'rifatik wa amitها 'alash-shahadata fi sabilik. Innaka ni'mal-mawla wa ni'man-nasir. Amin. Allahumma salli 'ala sayyidina muhammadin wa 'ala alihi wa sahbihi wa sallim.",
            translation = "Ya Allah, sesungguhnya Engkau mengetahui bahawa hati-hati ini telah berhimpun atas kecintaan kepada-Mu, bertaut atas ketaatan kepada-Mu, bersatu atas seruan kepada-Mu, dan berjanji untuk menolong syariat-Mu. Maka kukuhkanlah ikatan mereka, kekalkanlah kasih sayang mereka, tunjukkanlah mereka jalan-jalan-Mu, penuhilah mereka dengan cahaya-Mu yang tidak padam, lapangkanlah dada mereka dengan limpahan iman kepada-Mu dan keindahan tawakkal kepada-Mu. Hidupkanlah mereka dengan makrifat-Mu dan matikanlah mereka sebagai syahid di jalan-Mu. Sesungguhnya Engkaulah sebaik-baik pelindung dan sebaik-baik penolong. Amin. Ya Allah, limpahkanlah selawat ke atas junjungan kami Muhammad dan ke atas keluarga serta para sahabatnya, dan sejahterakanlah.",
            translationEn = "O Allah, You know that these hearts have gathered in love for You, clung together in obedience to You, united in Your cause, and pledged to support Your law. So strengthen their bond, make their love lasting, guide them on their paths, fill them with Your light that never fades, expand their chests with the overflow of faith in You and beautiful reliance upon You. Give them life through Your knowledge and let them die as martyrs in Your path. Indeed You are the best Protector and the best Helper. Amin. O Allah, bestow mercy upon our master Muhammad and upon his family and companions, and grant them peace.",
            targetCount = 1,
            sessions = setOf(Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        ),

        // ms 47 — Doa Penutup x1
        ZikrItem(
            id = 44, sortOrder = 390,
            title = "Doa Penutup",
            titleMs = "Doa penutup Al-Mathurat",
            titleEn = "Closing Prayer",
            subtitleEn = "Closing prayer of Al-Mathurat",
            arabic = "وَصَلَّى اللَّهُ عَلَى سَيِّدِنَا مُحَمَّدٍ وَعَلَى آلِهِ وَصَحْبِهِ وَسَلَّمَ تَسْلِيمًا كَثِيرًا",
            transliteration = "Wa sallallahu 'ala sayyidina muhammadin wa 'ala alihi wa sahbihi wa sallama tasliman kathira.",
            translation = "Semoga Allah melimpahkan selawat ke atas junjungan kami Muhammad dan ke atas keluarga serta para sahabatnya, dan sejahterakanlah dengan penuh kesejahteraan.",
            translationEn = "And may Allah bestow mercy upon our master Muhammad and upon his family and companions, and grant them abundant peace.",
            targetCount = 1,
            sessions = setOf(Session.MORNING, Session.EVENING),
            versions = setOf(Version.SUGHRA, Version.KUBRA)
        )
    )

    fun getZikr(session: Session, version: Version): List<ZikrItem> {
        return allZikr
            .filter { session in it.sessions && version in it.versions }
            .sortedBy { it.sortOrder }
            .map { it.copy(currentCount = 0) }
    }
}
