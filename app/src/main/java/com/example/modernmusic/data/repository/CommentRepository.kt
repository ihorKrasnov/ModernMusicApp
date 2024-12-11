package com.example.modernmusic.data.repository

import androidx.lifecycle.LiveData
import com.example.modernmusic.data.entity.comment.Comment
import com.example.modernmusic.data.entity.comment.CommentDao

class CommentRepository (private val commentsDao: CommentDao) {
    fun getById(albumId: Long): LiveData<List<Comment>> {
        return commentsDao.getCommentsByAlbumId(albumId)
    }

    fun insert(comment: Comment) {
        return commentsDao.insert(comment)
    }

    fun delete(comment: Comment) {
        return commentsDao.delete(comment)
    }
}