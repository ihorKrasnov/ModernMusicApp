package com.example.modernmusic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.modernmusic.data.repository.AlbumRepository
import com.example.modernmusic.data.repository.CommentRepository

class CommentViewModelFactory(@get:JvmName("getAdapterContext") private val repository: CommentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            return CommentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}