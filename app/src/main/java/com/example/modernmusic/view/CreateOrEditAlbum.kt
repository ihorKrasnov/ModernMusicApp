package com.example.modernmusic.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.modernmusic.R
import com.example.modernmusic.StartApp
import com.example.modernmusic.data.entity.album.Album
import com.example.modernmusic.viewmodel.AlbumViewModelFactory
import com.example.modernmusic.viewmodel.AlbumViewModel

class CreateOrEditAlbum : AppCompatActivity() {
    private val albumViewModel: AlbumViewModel by viewModels {
        AlbumViewModelFactory((application as StartApp).albumRepository)
    }
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var artistEditText: EditText
    private lateinit var publishedAtEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var pageTitle: TextView
    private var albumId: Long? = null  // Id альбому для редагування

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_album)

        titleEditText = findViewById(R.id.titleEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        artistEditText = findViewById(R.id.artistEditText)
        publishedAtEditText = findViewById(R.id.publishedAtEditText)
        saveButton = findViewById(R.id.saveButton)
        pageTitle = findViewById(R.id.pageTitle)

        // Перевірка, чи це редагування чи створення нового альбому
        albumId = intent.getLongExtra("ALBUM_ID", -1)
        pageTitle.text = "New album"
        if (albumId != -1L) {
            // Якщо ми отримали альбом для редагування, заповнюємо поля
            albumViewModel.getAlbumById(albumId!!).observe(this) { album ->
                pageTitle.text = "Edit album"
                titleEditText.setText(album.title)
                descriptionEditText.setText(album.description)
                artistEditText.setText(album.artist)
                publishedAtEditText.setText(album.createdAt)
            }
        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val artist = artistEditText.text.toString()
            val publishedAt = publishedAtEditText.text.toString()

            if (title.isEmpty() || description.isEmpty() || artist.isEmpty() || publishedAt.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val album = Album(0, title = title, description = description, artist = artist, createdAt = publishedAt)

            if (albumId == -1L) {
                albumViewModel.insert(album)
                Toast.makeText(this, "Album created", Toast.LENGTH_SHORT).show()
            } else {
                album.id = albumId!!
                albumViewModel.update(album)
                Toast.makeText(this, "Album updated", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }
}