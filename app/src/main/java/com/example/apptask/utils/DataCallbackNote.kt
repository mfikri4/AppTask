package com.example.apptask.utils

import com.example.apptask.model.Note

interface DataCallbackNote {
    fun onCallback(note: Note)
}