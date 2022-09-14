package com.mazeit.himusomogro.data.db.spf

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.mazeit.himusomogro.data.Config.ASKED_FOR_RATING
import com.mazeit.himusomogro.data.Config.DEFAULT_FONT_SIZE
import com.mazeit.himusomogro.data.Config.DEFAULT_LINE_HEIGHT
import com.mazeit.himusomogro.data.Config.FIRST_LAUNCH
import com.mazeit.himusomogro.data.Config.FIRST_LAUNCH_TIME
import com.mazeit.himusomogro.data.Config.INT_SHOW_TIME
import com.mazeit.himusomogro.data.Config.LAST_READ_STORY_KEY
import com.mazeit.himusomogro.data.Config.LAST_UPDATE_CHECK_TIME
import com.mazeit.himusomogro.data.Config.READING_FONT_SIZE_KEY
import com.mazeit.himusomogro.data.Config.READING_LINE_HEIGHT_KEY
import java.util.*

class HimuDb {

    companion object {

        private lateinit var pref: SharedPreferences

        fun init(context: Context) {
            pref = PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun firstLaunchTime(): Long =
            pref.getLong(FIRST_LAUNCH_TIME, 0)

        fun isFirstLaunch(): Boolean =
            pref.getBoolean(FIRST_LAUNCH, false)

        fun finishFirstLaunch() {
            pref.edit().putLong(FIRST_LAUNCH_TIME, Date().time).apply()
            pref.edit().putBoolean(FIRST_LAUNCH, true).apply()
        }

        fun askedForRating(): Boolean =
            pref.getBoolean(ASKED_FOR_RATING, false)

        fun setAskedForRating() {
            pref.edit().putBoolean(ASKED_FOR_RATING, true).apply()
        }

        fun readingTextSize(size: Int) {
            pref.edit().putInt(READING_FONT_SIZE_KEY, size).apply()
        }

        fun readingTextSize(): Int =
            pref.getInt(READING_FONT_SIZE_KEY, DEFAULT_FONT_SIZE)

        fun readingLineHeight(height: Int) {
            pref.edit().putInt(READING_LINE_HEIGHT_KEY, height).apply()
        }

        fun readingLineHeight(): Int =
            pref.getInt(READING_LINE_HEIGHT_KEY, DEFAULT_LINE_HEIGHT)

        fun lastReadStory(chapterId: Int) {
            pref.edit().putInt(LAST_READ_STORY_KEY, chapterId).apply()
        }

        fun lastReadStory() =
            pref.getInt(LAST_READ_STORY_KEY, -1)

        fun newIntShow() =
            pref.edit().putLong(INT_SHOW_TIME, intShowTime() + 1).apply()

        fun intShowTime(): Long =
            pref.getLong(INT_SHOW_TIME, 0)

        fun cleanIntShowTime() =
            pref.edit().remove(INT_SHOW_TIME).apply()

        fun lastUpdateChkTime(time: Long) =
            pref.edit().putLong(LAST_UPDATE_CHECK_TIME, time).apply()

        fun lastUpdateChkTime(): Long =
            pref.getLong(LAST_UPDATE_CHECK_TIME, 0L)

    }

}

fun askedForRating() =
    HimuDb.askedForRating()

fun setAskedForRating() =
    HimuDb.setAskedForRating()

fun firstLaunchTime() =
    HimuDb.firstLaunchTime()

fun isFirstLaunch() =
    HimuDb.isFirstLaunch()

fun finishFirstLaunch() =
    HimuDb.finishFirstLaunch()

fun readingTextSize(size: Int) =
    HimuDb.readingTextSize(size)

fun readingTextSize(): Int =
    HimuDb.readingTextSize()

fun readingLineHeight(height: Int) =
    HimuDb.readingLineHeight(height)

fun readingLineHeight(): Int =
    HimuDb.readingLineHeight()

fun resetReadingTextSize() =
    HimuDb.readingTextSize(DEFAULT_FONT_SIZE)

fun resetReadingLineHeight() =
    HimuDb.readingLineHeight(DEFAULT_LINE_HEIGHT)

fun lastReadStory(chapterId: Int) =
    HimuDb.lastReadStory(chapterId)

fun lastReadStory() =
    HimuDb.lastReadStory()

fun newIntShow() =
    HimuDb.newIntShow()

fun intShowTime() =
    HimuDb.intShowTime()

fun cleanIntShowTime() =
    HimuDb.cleanIntShowTime()

fun lastUpdateChkTime(time: Long) =
    HimuDb.lastUpdateChkTime(time)

fun lastUpdateChkTime() =
    HimuDb.lastUpdateChkTime()