package com.example.modernmusic.data.entity.album

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(album: Album)

    @Update
    fun update(album: Album)

    @Delete
    fun delete(album: Album)

    @Query(
        "SELECT * FROM albums " +
                "ORDER BY id " +
                "DESC"
    )
    fun getAll(): LiveData<List<Album>>

    @Query(
        "SELECT * FROM albums " +
                "WHERE LOWER(title) LIKE LOWER('%' || :query || '%') OR " +
                "LOWER(description) LIKE LOWER('%' || :query || '%') " +
                "OR LOWER(artist) LIKE LOWER('%' || :query || '%')" +
                "ORDER BY id " +
                "DESC"
    )
    // - LIKE: Оператор, який дозволяє порівнювати рядки, шукаючи певний шаблон.
    // - '%' || :query || '%': Цей вираз додає символи '%' до введеного запиту,
    //   що означає "будь-яка кількість символів перед і після введеного тексту".
    fun search(query: String): LiveData<List<Album>>

    @Query("SELECT * FROM albums WHERE id = :albumId")
    fun getById(albumId: Long): LiveData<Album>

}