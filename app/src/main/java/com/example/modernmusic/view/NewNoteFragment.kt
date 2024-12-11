package com.example.modernmusic.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.modernmusic.StartApp
import com.example.modernmusic.data.entity.comment.Comment
import com.example.modernmusic.databinding.FragmentNewNoteBinding
import com.example.modernmusic.viewmodel.CommentViewModel
import com.example.modernmusic.viewmodel.CommentViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewNoteFragment(var albumId: Long) : BottomSheetDialogFragment() {
    private val commentViewModel: CommentViewModel by viewModels {
        CommentViewModelFactory((requireActivity().application as StartApp).commentRepository)
    }

    private lateinit var binding: FragmentNewNoteBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        binding.taskTitle.text = "New Note"
        binding.saveButton.setOnClickListener {
            saveAction()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun saveAction()
    {
        val title = binding.name.text.toString()
        val text = binding.text.text.toString()

        val newNote = Comment(0, albumId, title, text)
        commentViewModel.insert(newNote)

        dismiss()
    }
}