package com.example.apptask.vo

object Const {
    val PATH_COLLECTION = "NOTES"
    val PATH_AGE = "intAge"

    fun setTimeStamp(): Long {
        val time = (-1 * System.currentTimeMillis())
        return time
    }
}