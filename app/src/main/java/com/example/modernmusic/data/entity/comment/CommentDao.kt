package com.example.modernmusic.data.entity.comment

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CommentDao {
    // Метод для вставки нового коментаря
    @Insert
    fun insert(comment: Comment)

    // Метод для отримання всіх коментарів по ID альбому
    @Query("SELECT * FROM comments " +
            "WHERE albumId = :albumId " +
            "ORDER BY id " +
            "DESC")
    fun getCommentsByAlbumId(albumId: Long): LiveData<List<Comment>>

    // Метод для видалення коментаря
    @Delete
    fun delete(comment: Comment)
}