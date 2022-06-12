package com.example.apptask.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note (
    val uang : String?,
    val tanggal : String?,
    val ket : String?

) : Parcelable

