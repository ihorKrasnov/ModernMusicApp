package com.example.modernmusic.data.repository

import androidx.lifecycle.LiveData
import com.example.modernmusic.data.entity.album.Album
import com.example.modernmusic.data.entity.album.AlbumDao


//class AlbumRepository(private val albumDao: AlbumDao, private val noteApi: NoteApi? = null) {
class AlbumRepository(private val albumDao: AlbumDao) {
    val albums: LiveData<List<Album>> = albumDao.getAll()

    fun insert(album: Album) {
        albumDao.insert(album)
    }

    fun update(album: Album) {
        albumDao.update(album)
    }

    fun delete(album: Album) {
        albumDao.delete(album)
    }

    fun search(query: String?): LiveData<List<Album>> {
        if (query.isNullOrEmpty()) return albums
        return albumDao.search(query)
    }

    fun getById(albumId: Long): LiveData<Album> {
        return albumDao.getById(albumId)
    }
}