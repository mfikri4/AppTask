package com.example.apptask.repository

import androidx.lifecycle.LiveData
import com.example.apptask.model.Note
import com.example.apptask.utils.DataSource
import com.example.apptask.utils.RemoteDataSource

class Repository (private val remoteDataSource: RemoteDataSource) : DataSource {
    companion object {

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            remoteDataSource: RemoteDataSource
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(remoteDataSource).apply { instance = this }
            }
    }

    override fun getNote(): LiveData<List<Note>> {
        return remoteDataSource.getNote()
    }

    override fun getNoteFew(): LiveData<List<Note>> {
        return remoteDataSource.getNoteFew()
    }

    override fun getDeleteNote(): LiveData<List<Note>> {
        return remoteDataSource.getDeleteNote()
    }

}