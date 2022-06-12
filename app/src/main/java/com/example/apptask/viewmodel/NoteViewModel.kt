package com.example.apptask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.apptask.repository.Repository
import com.example.apptask.model.Note

class NoteViewModel(private val repository: Repository) : ViewModel() {

    fun getNote () : LiveData<List<Note>>{
        return repository.getNote()
    }
    fun getNoteFew () : LiveData<List<Note>>{
        return repository.getNoteFew()
    }
}