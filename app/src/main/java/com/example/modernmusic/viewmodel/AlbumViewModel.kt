package com.example.modernmusic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modernmusic.data.entity.album.Album
import com.example.modernmusic.data.repository.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumViewModel(private val repository: AlbumRepository) : ViewModel() {

    var albums: LiveData<List<Album>> = repository.albums

    fun insert(note: Album) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(note)
        }
    }

    fun update(note: Album) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(note)
        }
    }

    fun delete(note: Album) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(note)
        }
    }

    fun search(query: String?): LiveData<List<Album>> {
        return repository.search(query)
    }

    fun getAlbumById(albumId: Long): LiveData<Album> {
        return  repository.getById(albumId)
    }
}