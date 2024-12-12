package com.example.modernmusic.data.entity.album

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
class Album(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "artist") var artist: String,
    @ColumnInfo(name = "publishedAt") var createdAt: String,

    )