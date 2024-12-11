package com.example.modernmusic.utils.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modernmusic.data.entity.comment.Comment
import com.example.modernmusic.databinding.CommentItemBinding
import com.example.modernmusic.utils.ItemClickListener

class CommentAdapter (
    private var items: List<Comment>,
    private val onClick: ItemClickListener<Comment>
): RecyclerView.Adapter<CommentViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CommentItemBinding.inflate(from, parent, false)
        return CommentViewHolder(parent.context, binding, onClick)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateData(comments: List<Comment>) {
        items = comments
        notifyDataSetChanged()  // Notify the adapter that the data has changed
    }
}