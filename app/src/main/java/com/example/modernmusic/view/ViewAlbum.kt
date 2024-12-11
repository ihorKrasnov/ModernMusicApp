package com.example.modernmusic.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modernmusic.R
import com.example.modernmusic.StartApp
import com.example.modernmusic.data.entity.album.Album
import com.example.modernmusic.data.entity.comment.Comment
import com.example.modernmusic.databinding.ActivityViewAlbumBinding
import com.example.modernmusic.utils.ItemClickListener
import com.example.modernmusic.utils.comment.CommentAdapter
import com.example.modernmusic.viewmodel.AlbumViewModel
import com.example.modernmusic.viewmodel.AlbumViewModelFactory
import com.example.modernmusic.viewmodel.CommentViewModel
import com.example.modernmusic.viewmodel.CommentViewModelFactory

class ViewAlbum : AppCompatActivity(), ItemClickListener<Comment> {
    private val albumViewModel: AlbumViewModel by viewModels {
        AlbumViewModelFactory((application as StartApp).albumRepository)
    }
    private val commentViewModel: CommentViewModel by viewModels {
        CommentViewModelFactory((application as StartApp).commentRepository)
    }

    private lateinit var binding: ActivityViewAlbumBinding

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var artistTextView: TextView
    private lateinit var publishedAtTextView: TextView
    private var albumId: Long? = null
    private var currentAlbum: Album? = null
    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_album)
        binding = ActivityViewAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Підключаємо тулбар
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.addButton.setOnClickListener {
            if (albumId != null) NewNoteFragment(albumId!!).show(supportFragmentManager, "NewNoteFragment")
        }

        adapter = CommentAdapter(emptyList(), this)
        titleTextView = findViewById(R.id.titleTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        artistTextView = findViewById(R.id.artistTextView)
        publishedAtTextView = findViewById(R.id.publishedAtTextView)

        albumId = intent.getLongExtra("ALBUM_ID", -1L)

        if (albumId != -1L) {
            albumViewModel.getAlbumById(albumId!!).observe(this) { album ->
                if (album != null) {
                    currentAlbum = album
                    titleTextView.text = album.title
                    descriptionTextView.text = album.description
                    artistTextView.text = album.artist
                    publishedAtTextView.text = album.createdAt

                    setRecyclerView()
                } else {
                    Toast.makeText(this, "Album not found", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        } else {
            Toast.makeText(this, "Invalid album ID", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = this@ViewAlbum.adapter
        }

        commentViewModel.getAllAlbumComments(albumId!!).observe(this) { comments ->
            if (::adapter.isInitialized) {
                adapter.updateData(comments)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_album, menu) // Завантажуємо меню
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                val intent = Intent(this, CreateOrEditAlbum::class.java)
                intent.putExtra("ALBUM_ID", albumId)
                startActivity(intent)
                true
            }
            R.id.action_delete -> {
                albumViewModel.delete(currentAlbum!!)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewItem(Item: Comment) {

    }

    override fun deleteItem(item: Comment) {
        commentViewModel.delete(item)
    }
}