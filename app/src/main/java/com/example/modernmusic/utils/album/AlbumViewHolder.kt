package com.example.modernmusic.utils.album

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.modernmusic.data.entity.album.Album
import com.example.modernmusic.databinding.AlbumItemBinding
import com.example.modernmusic.utils.ItemClickListener

class AlbumViewHolder(
    private val context: Context,
    private val binding: AlbumItemBinding,
    private val onClick: ItemClickListener<Album>
) : RecyclerView.ViewHolder(binding.root) {
    fun bindItem(item: Album)
    {
        binding.title.text = item.title
        binding.text.text = item.description
        binding.artist.text = item.artist
        binding.createAt.text = item.createdAt

        binding.taskCellContainer.setOnClickListener{
            onClick.onViewItem(item)
        }

        binding.deleteButton.setOnClickListener {
            onClick.deleteItem(item)
        }
    }
}
