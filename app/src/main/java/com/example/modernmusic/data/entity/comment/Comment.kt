package com.example.modernmusic.data.entity.comment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = "comments")
    data class Comment(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        @ColumnInfo(name = "albumId") var albumId: Long,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "text") var text: String
    )
