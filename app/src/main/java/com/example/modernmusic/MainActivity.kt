package com.example.modernmusic

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modernmusic.data.entity.album.Album
import com.example.modernmusic.databinding.ActivityMainBinding
import com.example.modernmusic.utils.album.AlbumAdapter
import com.example.modernmusic.utils.ItemClickListener
import com.example.modernmusic.viewmodel.AlbumViewModelFactory
import com.example.modernmusic.view.CreateOrEditAlbum
import com.example.modernmusic.view.ViewAlbum
import com.example.modernmusic.viewmodel.AlbumViewModel

class MainActivity : AppCompatActivity(), ItemClickListener<Album>
{
    private lateinit var binding: ActivityMainBinding
    private val albumViewModel: AlbumViewModel by viewModels {
        AlbumViewModelFactory((application as StartApp).albumRepository)
    }
    private lateinit var adapter: AlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            val intent = Intent(this, CreateOrEditAlbum::class.java)
            startActivity(intent)
        }

        adapter = AlbumAdapter(emptyList(), this)
        setRecyclerView()
        setupSearch()
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                albumViewModel.search(newText)
                    .observe(this@MainActivity) { albums ->
                        adapter.updateData(albums)
                    }
                return true
            }
        })
    }

    private fun setRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = this@MainActivity.adapter
        }

        albumViewModel.albums.observe(this) { albums ->
            if (::adapter.isInitialized) {
                adapter.updateData(albums)
            }
        }
    }

    override fun onViewItem(item: Album) {
        val intent = Intent(this, ViewAlbum::class.java)
        intent.putExtra("ALBUM_ID", item.id)
        startActivity(intent)
    }

    override fun deleteItem(item: Album) {
        albumViewModel.delete(item)
    }
}