package com.example.modernmusic.data

import MIGRATION_1_2
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.modernmusic.data.entity.album.Album
import com.example.modernmusic.data.entity.album.AlbumDao
import com.example.modernmusic.data.entity.comment.Comment
import com.example.modernmusic.data.entity.comment.CommentDao

@Database(entities = [Album::class, Comment::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun commentsDao(): CommentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase
        {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db_2"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}