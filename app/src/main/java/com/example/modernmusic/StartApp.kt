package com.example.modernmusic

import android.app.Application
import com.example.modernmusic.data.AppDatabase
import com.example.modernmusic.data.repository.AlbumRepository
import com.example.modernmusic.data.repository.CommentRepository

class StartApp : Application()
{
    private val database by lazy { AppDatabase.getDatabase(this) }
    val albumRepository by lazy { AlbumRepository(database.albumDao()) }
    val commentRepository by lazy { CommentRepository(database.commentsDao()) }
}