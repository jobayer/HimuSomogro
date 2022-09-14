package com.mazeit.himusomogro.data.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "reading_record_table")
data class Record(
    @PrimaryKey
    @ColumnInfo(name = "content_id")
    var contentId: Int,
    @ColumnInfo(name = "scroll_position")
    var scrollPosition: Int
)
