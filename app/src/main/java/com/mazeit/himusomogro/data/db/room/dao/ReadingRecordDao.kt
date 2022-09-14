package com.mazeit.himusomogro.data.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mazeit.himusomogro.data.models.Record

@Dao
interface ReadingRecordDao {

    @Query("SELECT * FROM reading_record_table WHERE content_id = :contentId")
    fun getReadingRecord(contentId: Int): Record?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReadingRecord(record: Record)

}