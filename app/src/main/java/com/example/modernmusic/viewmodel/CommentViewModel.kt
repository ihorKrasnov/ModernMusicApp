package com.example.modernmusic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modernmusic.data.entity.comment.Comment
import com.example.modernmusic.data.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentViewModel(private val repository: CommentRepository) : ViewModel() {
    fun delete(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(comment)
        }
    }

    fun insert(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(comment)
        }
    }

    fun getAllAlbumComments(albumId: Long): LiveData<List<Comment>> {
        return repository.getById(albumId)
    }
}