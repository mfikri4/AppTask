package com.example.apptask.utils

import androidx.lifecycle.LiveData
import com.example.apptask.model.Note

interface DataSource {
    fun getNote() : LiveData<List<Note>>
    fun getNoteFew() : LiveData<List<Note>>
    fun getDeleteNote() : LiveData<List<Note>>
}