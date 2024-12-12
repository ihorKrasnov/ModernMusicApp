package com.example.modernmusic.utils.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modernmusic.data.entity.album.Album
import com.example.modernmusic.databinding.AlbumItemBinding
import com.example.modernmusic.utils.ItemClickListener

class AlbumAdapter (
    private var items: List<Album>,
    private val onClick: ItemClickListener<Album>
): RecyclerView.Adapter<AlbumViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = AlbumItemBinding.inflate(from, parent, false)
        return AlbumViewHolder(parent.context, binding, onClick)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateData(albums: List<Album>) {
        items = albums
        notifyDataSetChanged()  // Notify the adapter that the data has changed
    }
}
