package com.mazeit.himusomogro.data.db.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mazeit.himusomogro.data.db.room.dao.ReadingRecordDao
import com.mazeit.himusomogro.data.models.Record

@Database(entities = [Record::class], version = 1, exportSchema = false)
abstract class ReadingRecordDatabase : RoomDatabase() {

    abstract fun readingRecordDao(): ReadingRecordDao

    companion object {

        @Volatile
        private var instance: ReadingRecordDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, ReadingRecordDatabase::class.java, "reading_record_db").build()

    }

}