package com.example.remotejob.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.remotejob.repository.RemoteJobRepository

class RemoteJobViewModelFactory(
    val app:Application,
    private val remoteJobRepository: RemoteJobRepository

): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RemoteJobViewModel( app ,remoteJobRepository) as T
    }
}