package com.example.apptask.utils

import com.example.apptask.model.Note

interface Callback {

    fun onCallCallback(note: Note)
}