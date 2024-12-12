package com.example.modernmusic.utils

interface ItemClickListener<T> {
    fun onViewItem(Item: T)
    fun deleteItem(item: T)
}
