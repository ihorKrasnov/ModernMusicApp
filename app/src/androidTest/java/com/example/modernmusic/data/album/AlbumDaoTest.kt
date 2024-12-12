package com.example.modernmusic.data.album

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.modernmusic.data.AppDatabase
import com.example.modernmusic.data.entity.album.Album
import com.example.modernmusic.data.entity.album.AlbumDao
import com.example.modernmusic.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var albumDao: AlbumDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        albumDao = database.albumDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAlbum() = runBlocking {
        // Arrange
        val album = Album(1, "Album Title", "Description", "Artist", "2024-12-12")

        // Act
        albumDao.insert(album)
        val albums = albumDao.getAll().getOrAwaitValue()

        // Assert
        assertTrue(albums.any { it.title == album.title && it.artist == album.artist })
    }

    @Test
    fun updateAlbum() = runBlocking {
        // Arrange
        val album = Album(1, "Album Title", "Description", "Artist", "2024-12-12")
        albumDao.insert(album)

        // Act
        val updatedAlbum = Album(1, "Updated Title", "Updated Description", "Updated Artist", "2024-12-12")
        albumDao.update(updatedAlbum)
        val albums = albumDao.getAll().getOrAwaitValue()

        // Assert
        assertTrue(albums.any { it.title == updatedAlbum.title && it.artist == updatedAlbum.artist })
    }

    @Test
    fun deleteAlbum() = runBlocking {
        // Arrange
        val album = Album(1, "Album Title", "Description", "Artist", "2024-12-12")
        albumDao.insert(album)

        // Act
        albumDao.delete(album)
        val albums = albumDao.getAll().getOrAwaitValue()

        // Assert
        assertFalse(albums.any { it.id == album.id })
    }

    @Test
    fun searchAlbums() = runBlocking {
        // Arrange
        val album1 = Album(1, "Rock Album", "Description 1", "Artist 1", "2024-12-12")
        val album2 = Album(2, "Pop Album", "Description 2", "Artist 2", "2024-12-12")
        albumDao.insert(album1)
        albumDao.insert(album2)

        // Act
        val query = "album"
        val result = albumDao.search(query).getOrAwaitValue()

        // Assert
        assertTrue(result.any { it.title.contains(query, ignoreCase = true) })
        assertTrue(result.size == 2)
    }

    @Test
    fun getAlbumById() = runBlocking {
        // Arrange
        val album = Album(1, "Rock Album", "Description", "Artist", "2024-12-12")
        albumDao.insert(album)

        // Act
        val fetchedAlbum = albumDao.getById(1).getOrAwaitValue()

        // Assert
        assertEquals(album.id, fetchedAlbum.id)
    }
}