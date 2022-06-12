package com.example.apptask.di

import android.content.Context
import com.example.apptask.utils.RemoteDataSource
import com.example.apptask.repository.Repository

object Injection {

    fun provideRepository(context: Context): Repository {

        val remoteDataSource = RemoteDataSource.getInstance()

        return Repository.getInstance(remoteDataSource)
    }
}