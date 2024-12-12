package com.example.modernmusic.data.comment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.modernmusic.data.AppDatabase
import com.example.modernmusic.data.entity.comment.Comment
import com.example.modernmusic.data.entity.comment.CommentDao
import com.example.modernmusic.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommentDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var commentDao: CommentDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        commentDao = database.commentsDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertComment() = runBlocking {
        // Arrange
        val comment = Comment(1, 1, "Great album!", "2024-12-12")

        // Act
        commentDao.insert(comment)
        val comments = commentDao.getCommentsByAlbumId(1).getOrAwaitValue()

        // Assert
        assertTrue(comments.any { it.text == comment.text })
    }

    @Test
    fun deleteComment() = runBlocking {
        // Arrange
        val comment = Comment(1, 1, "Great album!", "2024-12-12")
        commentDao.insert(comment)

        // Act
        commentDao.delete(comment)
        val comments = commentDao.getCommentsByAlbumId(1).getOrAwaitValue()

        // Assert
        assertFalse(comments.any { it.id == comment.id })
    }

    @Test
    fun getCommentsByAlbumId() = runBlocking {
        // Arrange
        val pivotAlbumId: Long = 1L;
        val comment1 = Comment(1, pivotAlbumId, "Maria", "Great album!")
        val comment2 = Comment(2, pivotAlbumId, "Nickolas", "Awesome!")

        commentDao.insert(comment1)
        commentDao.insert(comment2)

        // Act
        val comments = commentDao.getCommentsByAlbumId(1).getOrAwaitValue()

        // Assert
        assertEquals(2, comments.size)
        assertTrue(comments.any { it.albumId == pivotAlbumId })
        assertTrue(comments.any { it.albumId == pivotAlbumId })
    }
}