package com.example.modernmusic.utils.comment

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.modernmusic.data.entity.comment.Comment
import com.example.modernmusic.databinding.CommentItemBinding
import com.example.modernmusic.utils.ItemClickListener

class CommentViewHolder (
    private val context: Context,
    private val binding: CommentItemBinding,
    private val onClick: ItemClickListener<Comment>
) : RecyclerView.ViewHolder(binding.root) {
    fun bindItem(item: Comment)
    {
        binding.author.text = item.name
        binding.message.text = item.text

        binding.deleteButton.setOnClickListener {
            onClick.deleteItem(item)
        }
    }

}