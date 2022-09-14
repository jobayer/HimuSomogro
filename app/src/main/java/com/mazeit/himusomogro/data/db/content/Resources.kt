package com.mazeit.himusomogro.data.db.content

import com.mazeit.himusomogro.data.models.Chapter
import com.mazeit.himusomogro.data.models.Content
import com.mazeit.himusomogro.data.models.Story

fun getAllStories(): List<Story> {
    return listOf(
        Story(1, "আঙ্গুল কাটা জগলু", 12, 2005),
        Story(2, "ময়ূরাক্ষী", 8, 1990),
        Story(3, "দরজার ওপাশে", 11, 1992),
        Story(4, "হিমু", 13, 1993),
        Story(5, "পারাপার", 11, 1993),
        Story(6, "এবং হিমু", 7, 1995),
        Story(7, "হিমুর হাতে কয়েকটি নীলপদ্ম", 13, 1996),
        Story(8, "হিমুর দ্বিতীয় প্রহর", 11, 1997),
        Story(9, "হিমুর রূপালী রাত্রি", 14, 1998),
        Story(10, "একজন হিমু কয়েকটি ঝিঁ ঝিঁ পোকা", 9, 1999),
        Story(11, "তোমাদের এই নগরে", 10, 2000),
        Story(12, "চলে যায় বসন্তের দিন", 9, 2002),
        Story(13, "সে আসে ধীরে", 9, 2003),
        Story(14, "হিমু মামা", 6, 2004),
        Story(15, "হলুদ হিমু কালো র\u200C্যাব", 11, 2006),
        Story(16, "আজ হিমুর বিয়ে", 8, 2007),
        Story(17, "হিমু রিমান্ডে", 8, 2008),
        Story(18, "হিমুর মধ্যদুপুর", 7, 2009),
        Story(19, "হিমুর বাবার কথামালা", 4, 2009),
        Story(20, "হিমুর নীল জোছনা", 7, 2010),
        Story(21, "হিমুর আছে জল", 7, 2011),
        Story(22, "হিমু এবং একটি রাশিয়ান পরী", 10, 2011),
        Story(23, "হিমু এবং হার্ভার্ড পিএইচ.ডি বল্টু ভাই", 7, 2011)
    )
}

fun getAllChapters(storyId: Int): List<Chapter> {
    val data = mutableListOf<Chapter>()
    when (storyId) {
        1 -> {
            data.add(Chapter(1, "আঙ্গুল কাটা জগলু ১"))
            data.add(Chapter(2, "আঙ্গুল কাটা জগলু ২"))
            data.add(Chapter(3, "আঙ্গুল কাটা জগলু ৩"))
            data.add(Chapter(4, "আঙ্গুল কাটা জগলু ৪"))
            data.add(Chapter(5, "আঙ্গুল কাটা জগলু ৫"))
            data.add(Chapter(6, "আঙ্গুল কাটা জগলু ৬"))
            data.add(Chapter(7, "আঙ্গুল কাটা জগলু ৭"))
            data.add(Chapter(8, "আঙ্গুল কাটা জগলু ৮"))
            data.add(Chapter(9, "আঙ্গুল কাটা জগলু ৯"))
            data.add(Chapter(10, "আঙ্গুল কাটা জগলু ১০"))
            data.add(Chapter(11, "আঙ্গুল কাটা জগলু ১১"))
            data.add(Chapter(12, "আঙ্গুল কাটা জগলু ১২"))
        }
        2 -> {
            data.add(Chapter(13, "ময়ূরাক্ষী ১"))
            data.add(Chapter(14, "ময়ূরাক্ষী ২"))
            data.add(Chapter(15, "ময়ূরাক্ষী ৩"))
            data.add(Chapter(16, "ময়ূরাক্ষী ৪"))
            data.add(Chapter(17, "ময়ূরাক্ষী ৫"))
            data.add(Chapter(18, "ময়ূরাক্ষী ৬"))
            data.add(Chapter(19, "ময়ূরাক্ষী ৭"))
            data.add(Chapter(20, "ময়ূরাক্ষী ৮"))
        }
        3 -> {
            data.add(Chapter(21, "দরজার ওপাশে ১"))
            data.add(Chapter(22, "দরজার ওপাশে ২"))
            data.add(Chapter(23, "দরজার ওপাশে ৩"))
            data.add(Chapter(24, "দরজার ওপাশে ৪"))
            data.add(Chapter(25, "দরজার ওপাশে ৫"))
            data.add(Chapter(26, "দরজার ওপাশে ৬"))
            data.add(Chapter(27, "দরজার ওপাশে ৭"))
            data.add(Chapter(28, "দরজার ওপাশে ৮"))
            data.add(Chapter(29, "দরজার ওপাশে ৯"))
            data.add(Chapter(30, "দরজার ওপাশে ১০"))
            data.add(Chapter(31, "দরজার ওপাশে ১১"))
        }
        4 -> {
            data.add(Chapter(32, "কি নাম বললেন আপনার?"))
            data.add(Chapter(33, "ঘরের ভিতর দুটা চিঠি"))
            data.add(Chapter(34, "ধুম ধুম করে দরজায় কিল পড়ছে"))
            data.add(Chapter(35, "বড় রাস্তার ফুটপাতে"))
            data.add(Chapter(36, "নিতুর সাইকিয়াট্রিস্ট"))
            data.add(Chapter(37, "মোরশেদ সাহেব"))
            data.add(Chapter(38, "এমা দরজা খুলে তাকিয়ে রইলো"))
            data.add(Chapter(39, "টেলিফোন করার জায়গা পাচ্ছি না"))
            data.add(Chapter(40, "নিতুর একটি চিঠি এসেছে"))
            data.add(Chapter(41, "রাট এগারোটায় ইয়াদের সন্ধানে"))
            data.add(Chapter(42, "রুপার চিঠি এসেছে"))
            data.add(Chapter(43, "ইয়াদের বাড়ি সব সময় আলোয় ঝলমল করে"))
            data.add(Chapter(44, "আমি বাস করছি অন্ধকারে এবং আলোয়"))
        }
        5 -> {
            data.add(Chapter(45, "পারাপার ১"))
            data.add(Chapter(46, "পারাপার ২"))
            data.add(Chapter(47, "পারাপার ৩"))
            data.add(Chapter(48, "পারাপার ৪"))
            data.add(Chapter(49, "পারাপার ৫"))
            data.add(Chapter(50, "পারাপার ৬"))
            data.add(Chapter(51, "পারাপার ৭"))
            data.add(Chapter(52, "পারাপার ৮"))
            data.add(Chapter(53, "পারাপার ৯"))
            data.add(Chapter(54, "পারাপার ১০"))
            data.add(Chapter(55, "পারাপার ১১"))
        }
        6 -> {
            data.add(Chapter(56, "এবং হিমু ১"))
            data.add(Chapter(57, "এবং হিমু ২"))
            data.add(Chapter(58, "এবং হিমু ৩"))
            data.add(Chapter(59, "এবং হিমু ৪"))
            data.add(Chapter(60, "এবং হিমু ৫"))
            data.add(Chapter(61, "এবং হিমু ৬"))
            data.add(Chapter(62, "এবং হিমু ৭"))
        }
        7 -> {
            data.add(Chapter(63, "হিমুর হাতে কয়েকটি নীলপদ্ম ১"))
            data.add(Chapter(64, "হিমুর হাতে কয়েকটি নীলপদ্ম ২"))
            data.add(Chapter(65, "হিমুর হাতে কয়েকটি নীলপদ্ম ৩"))
            data.add(Chapter(66, "হিমুর হাতে কয়েকটি নীলপদ্ম ৪"))
            data.add(Chapter(67, "হিমুর হাতে কয়েকটি নীলপদ্ম ৫"))
            data.add(Chapter(68, "হিমুর হাতে কয়েকটি নীলপদ্ম ৬"))
            data.add(Chapter(69, "হিমুর হাতে কয়েকটি নীলপদ্ম ৭"))
            data.add(Chapter(70, "হিমুর হাতে কয়েকটি নীলপদ্ম ৮"))
            data.add(Chapter(71, "হিমুর হাতে কয়েকটি নীলপদ্ম ৯"))
            data.add(Chapter(72, "হিমুর হাতে কয়েকটি নীলপদ্ম ১০"))
            data.add(Chapter(73, "হিমুর হাতে কয়েকটি নীলপদ্ম ১১"))
            data.add(Chapter(74, "হিমুর হাতে কয়েকটি নীলপদ্ম ১২"))
            data.add(Chapter(75, "হিমুর হাতে কয়েকটি নীলপদ্ম ১৩"))
        }
        8 -> {
            data.add(Chapter(76, "ভীতু মানুষ বলতে যা বুঝায়"))
            data.add(Chapter(77, "আমি গলিটার মুখে দাঁড়িয়ে আছি"))
            data.add(Chapter(78, "পাশের চেয়ারে বাদল বসে আছে"))
            data.add(Chapter(79, "সন্তান প্রসবজনিত জটিলতায় স্ত্রী বিয়োগ"))
            data.add(Chapter(80, "ঘ্যাস করে গা ঘেঁষে গাড়ি থেমেছে"))
            data.add(Chapter(81, "দরজায় কোনো কলিংবেল নেই"))
            data.add(Chapter(82, "সাদেক সাহেব শুকনোমুখে"))
            data.add(Chapter(83, "ময়লা বাবার আস্তানা কুড়াইল গ্রামে"))
            data.add(Chapter(84, "আমি জবাব দিচ্ছি না"))
            data.add(Chapter(85, "আজ পূর্ণিমা"))
            data.add(Chapter(86, "আজ রাতের জোছনাটা কেমন?"))
        }
        9 -> {
            data.add(Chapter(87, "ফাতেমা খালার চিরকুট"))
            data.add(Chapter(88, "ইয়াকুবের সন্ধানে যাত্রা শুরু হলো"))
            data.add(Chapter(89, "কে? হিমু না?"))
            data.add(Chapter(90, "আমি ইয়াকুব সাহেবকে স্বপ্নে দেখলাম"))
            data.add(Chapter(91, "মেসের ম্যানেজার খবর পাঠিয়েছে"))
            data.add(Chapter(92, "তামান্নার জন্য অপেক্ষা"))
            data.add(Chapter(93, "কুড়ি হাজার টাকা"))
            data.add(Chapter(94, "ভদ্রলোকের বয়স চল্লিশ"))
            data.add(Chapter(95, "তামান্না ডাকছে"))
            data.add(Chapter(96, "চাইনিজ রেস্টুরেন্টগুলোর ব্যবসা"))
            data.add(Chapter(97, "সেই পাথর"))
            data.add(Chapter(98, "সুন্দরী মেয়েদের হাতের লেখা"))
            data.add(Chapter(99, "ফাতেমা খালার বসার ঘরে"))
            data.add(Chapter(100, "হাত বাড়ালেই চাঁদ"))
        }
        10 -> {
            data.add(Chapter(101, "একজন হিমু কয়েকটি ঝিঁ ঝিঁ পোকা ১"))
            data.add(Chapter(102, "একজন হিমু কয়েকটি ঝিঁ ঝিঁ পোকা ২"))
            data.add(Chapter(103, "একজন হিমু কয়েকটি ঝিঁ ঝিঁ পোকা ৩"))
            data.add(Chapter(104, "একজন হিমু কয়েকটি ঝিঁ ঝিঁ পোকা ৪"))
            data.add(Chapter(105, "একজন হিমু কয়েকটি ঝিঁ ঝিঁ পোকা ৫"))
            data.add(Chapter(106, "একজন হিমু কয়েকটি ঝিঁ ঝিঁ পোকা ৬"))
            data.add(Chapter(107, "একজন হিমু কয়েকটি ঝিঁ ঝিঁ পোকা ৭"))
            data.add(Chapter(108, "একজন হিমু কয়েকটি ঝিঁ ঝিঁ পোকা ৮"))
            data.add(Chapter(109, "একজন হিমু কয়েকটি ঝিঁ ঝিঁ পোকা ৯"))
        }
        11 -> {
            data.add(Chapter(110, "তোমাদের এই নগরে ১"))
            data.add(Chapter(111, "তোমাদের এই নগরে ২"))
            data.add(Chapter(112, "তোমাদের এই নগরে ৩"))
            data.add(Chapter(113, "তোমাদের এই নগরে ৪"))
            data.add(Chapter(114, "তোমাদের এই নগরে ৫"))
            data.add(Chapter(115, "তোমাদের এই নগরে ৬"))
            data.add(Chapter(116, "তোমাদের এই নগরে ৭"))
            data.add(Chapter(117, "তোমাদের এই নগরে ৮"))
            data.add(Chapter(118, "তোমাদের এই নগরে ৯"))
            data.add(Chapter(119, "তোমাদের এই নগরে ১০"))
        }
        12 -> {
            data.add(Chapter(121, "চলে যায় বসন্তের দিন ১"))
            data.add(Chapter(122, "চলে যায় বসন্তের দিন ২"))
            data.add(Chapter(123, "চলে যায় বসন্তের দিন ৩"))
            data.add(Chapter(124, "চলে যায় বসন্তের দিন ৪"))
            data.add(Chapter(125, "চলে যায় বসন্তের দিন ৫"))
            data.add(Chapter(126, "চলে যায় বসন্তের দিন ৬"))
            data.add(Chapter(127, "চলে যায় বসন্তের দিন ৭"))
            data.add(Chapter(128, "চলে যায় বসন্তের দিন ৮"))
            data.add(Chapter(129, "চলে যায় বসন্তের দিন ৯"))
        }
        13 -> {
            data.add(Chapter(130, "আক্কেলগুড়ুম একটি প্রচলিত বাগধারা"))
            data.add(Chapter(131, "টিনের চালের একতালা বাড়ি"))
            data.add(Chapter(132, "মিসেস আসমা হক, পিএইচডি"))
            data.add(Chapter(133, "মাজেদা খালা দরজা খুলে"))
            data.add(Chapter(134, "ইমরুলের সাজসজ্জা"))
            data.add(Chapter(135, "হাবিবুর রহমান সাহেবের ব্রেইন"))
            data.add(Chapter(136, "বিস্ময়কর ঘটনা শেষ পর্যন্ত ঘটেছে"))
            data.add(Chapter(137, "ফরিদার চিঠি"))
            data.add(Chapter(138, "আমি সূর্যাস্ত দেখেছি"))
        }
        14 -> {
            data.add(Chapter(139, "টগরদের বাড়িতে ধুন্ধুমার কান্ড"))
            data.add(Chapter(140, "রচনার নাম হিমু"))
            data.add(Chapter(141, "চৌধুরী আজমল হোসেন"))
            data.add(Chapter(142, "মৌন দিবস"))
            data.add(Chapter(143, "টগরদের বাড়িতে সাইকিয়াট্রিস্ট"))
            data.add(Chapter(144, "টগরের লেখা ডায়েরির অংশ"))
        }
        15 -> {
            data.add(Chapter(145, "গল্প শুরু করছি"))
            data.add(Chapter(146, "কোথায় আছি কি ব্যাপার"))
            data.add(Chapter(147, "আজকের খবরের কাগজের প্রধান খবর"))
            data.add(Chapter(148, "বজলু ছেলেটা যথেষ্ট ভালো"))
            data.add(Chapter(149, "বড় খালু সাহেবের চিঠি"))
            data.add(Chapter(150, "ঘরের ভিতরের একটি দৃশ্য"))
            data.add(Chapter(151, "বালিশের নিচে পাখি ডাকছে"))
            data.add(Chapter(152, "ঘর আলো করে কে যেন বসে আছে"))
            data.add(Chapter(153, "মাজেদা খালার সঙ্গে খালুর সম্পর্ক"))
            data.add(Chapter(154, "কার্ড বিলি শুরু করলাম"))
            data.add(Chapter(155, "জানুয়ারী ৯ তারিখ"))
        }
        16 -> {
            data.add(Chapter(156, "আজ হিমুর বিয়ে ১"))
            data.add(Chapter(157, "আজ হিমুর বিয়ে ২"))
            data.add(Chapter(158, "আজ হিমুর বিয়ে ৩"))
            data.add(Chapter(159, "আজ হিমুর বিয়ে ৪"))
            data.add(Chapter(160, "আজ হিমুর বিয়ে ৫"))
            data.add(Chapter(161, "আজ হিমুর বিয়ে ৬"))
            data.add(Chapter(162, "আজ হিমুর বিয়ে ৭"))
            data.add(Chapter(163, "আজ হিমুর বিয়ে ৮"))
        }
        17 -> {
            data.add(Chapter(164, "নাম কি? হিমু।"))
            data.add(Chapter(165, "কিচ্ছুক্ষণের জন্য ঈশ্বরচন্দ্র বিদ্যাসাগর"))
            data.add(Chapter(166, "আমি আছি বাদলদের বাড়িতে"))
            data.add(Chapter(167, "সানগ্লাস চোখে পড়েছি"))
            data.add(Chapter(168, "টাইগার এখন বাদলের হেফাজতে"))
            data.add(Chapter(169, "হাবিব ভাইয়ের বাড়িতে"))
            data.add(Chapter(170, "আনন্দে হাবিব ভাইয়ের মাথা মোটামুটি খারাপ"))
            data.add(Chapter(171, "প্রকৃতির রিমান্ড"))
        }
        18 -> {
            data.add(Chapter(172, "একটি কিডনি দিতে পারবি?"))
            data.add(Chapter(173, "গৃহভৃত্য বিষয়ে কিছু উপদেশবাণী"))
            data.add(Chapter(174, "পল্টু স্যার"))
            data.add(Chapter(175, "রানু টাকা নিয়ে এসেছে"))
            data.add(Chapter(176, "আনন্দিত মানুষের বর্ণনা"))
            data.add(Chapter(177, "থানা-হাজত দখল করে আছি"))
            data.add(Chapter(178, "নৈশব্দের সময়"))
        }
        19 -> {
            data.add(Chapter(179, "উৎসর্গ ও ভূমিকা"))
            data.add(Chapter(180, "হিমুর বাবার নাম কি?"))
            data.add(Chapter(181, "পোশাকের গুরুত্ব"))
            data.add(Chapter(182, "থাকুক হিমু প্রসঙ্গ"))
        }
        20 -> {
            data.add(Chapter(183, "ঝুম বৃষ্টির শব্দে ঘুম ভাঙলো"))
            data.add(Chapter(184, "ওসি সাহেবের খাস কামরায়"))
            data.add(Chapter(185, "রাতটা কমলকুটিরে কাটাতে হলো"))
            data.add(Chapter(186, "চতুর্থ অধ্যায়ের শুরু"))
            data.add(Chapter(187, "বঙ্গবন্ধু গবেষণা কেন্দ্র"))
            data.add(Chapter(188, "ধানমন্ডি থানা থেকে ক্লোজ করে খাগড়াছড়ি"))
            data.add(Chapter(189, "আজি রজনীতে হয়েছে সময়, এসেছি বাসবদত্তা"))
        }
        21 -> {
            data.add(Chapter(190, "হিমু, চোখ মেল"))
            data.add(Chapter(191, "লঞ্চ মোহনায় দুলছে"))
            data.add(Chapter(192, "তৃষ্ঞার কাছে ফিরে এসেছি "))
            data.add(Chapter(193, "লঞ্চের নিয়ন্ত্রণ এখন আতর মিয়ার হাতে"))
            data.add(Chapter(194, "তৃষ্ঞার কোলে ল্যাপটপ"))
            data.add(Chapter(195, "গণ তওবার সিদ্ধান্ত"))
            data.add(Chapter(196, "বাজিল বুকে সুখের মতো ব্যথা"))
        }
        22 -> {
            data.add(Chapter(197, "হিমু এবং একটি রাশিয়ান পরী"))
            data.add(Chapter(198, "একটি চৈনিক প্রবাদ"))
            data.add(Chapter(199, "গত সাতদিনের ঘটনা বা দুর্ঘটনা"))
            data.add(Chapter(200, "এলিতা ঢাকা এয়ারপোর্টে পৌঁছলো"))
            data.add(Chapter(201, "এলিতা তার প্রথম ছবির সন্ধানে বের হবে"))
            data.add(Chapter(202, "বুটারফ্লাই ইফেক্ট"))
            data.add(Chapter(203, "এলিতা আমার ঘরে বসে আছে"))
            data.add(Chapter(204, "বৃষ্টিতে ভেজার ফল"))
            data.add(Chapter(205, "এলিটার চিঠি"))
            data.add(Chapter(206, "পক্ষীমানবের সন্ধানে"))
        }
        23 -> {
            data.add(Chapter(207, "হার্ভার্ডের পিএইচডি দেখেছিস?"))
            data.add(Chapter(208, "মোবাইল ফোন ব্যবহারের পার্শপ্রতিক্রিয়া"))
            data.add(Chapter(209, "মাইকেল এঞ্জেলো বলেছেন"))
            data.add(Chapter(210, "বল্টু স্যারের ঘরের দরজা"))
            data.add(Chapter(211, "আমরা আয়োজন করে ইফতার খেতে বসেছি"))
            data.add(Chapter(212, "পীর বাচ্চা-বাবার মাজারে"))
            data.add(Chapter(213, "মাজার জমজমাট অবস্থা"))
        }
    }
    return data
}

fun getContent(chapterId: Int): Content? {
    return when (chapterId) {
        in 1..10 -> {
            getContent110(chapterId)
        }
        in 11..20 -> {
            getContent1120(chapterId)
        }
        in 21..30 -> {
            getContent2130(chapterId)
        }
        in 31..40 -> {
            getContent3140(chapterId)
        }
        in 41..50 -> {
            getContent4150(chapterId)
        }
        in 51..60 -> {
            getContent5160(chapterId)
        }
        in 61..70 -> {
            getContent6170(chapterId)
        }
        in 71..80 -> {
            getContent7180(chapterId)
        }
        in 81..90 -> {
            getContent8190(chapterId)
        }
        in 91..100 -> {
            getContent91100(chapterId)
        }
        in 101..110 -> {
            getContent101110(chapterId)
        }
        in 111..120 -> {
            getContent111120(chapterId)
        }
        in 121..130 -> {
            getContent121130(chapterId)
        }
        in 131..140 -> {
            getContent131140(chapterId)
        }
        in 141..150 -> {
            getContent141150(chapterId)
        }
        in 151..160 -> {
            getContent151160(chapterId)
        }
        in 161..170 -> {
            getContent161170(chapterId)
        }
        in 171..180 -> {
            getContent171180(chapterId)
        }
        in 181..190 -> {
            getContent181190(chapterId)
        }
        in 191..200 -> {
            getContent191200(chapterId)
        }
        in 201..210 -> {
            getContent201210(chapterId)
        }
        in 211..220 -> {
            getContent211220(chapterId)
        }
        else -> null
    }
}