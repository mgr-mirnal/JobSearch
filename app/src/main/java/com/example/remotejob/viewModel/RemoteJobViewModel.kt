package com.example.remotejob.viewModel

import com.example.remotejob.repository.RemoteJobRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.remotejob.models.JobToSave
import kotlinx.coroutines.launch

class RemoteJobViewModel(
    app: Application,
    private val remoteJobRepository: RemoteJobRepository
) : AndroidViewModel(app){
    fun remoteJobResult() = remoteJobRepository.remoteJobResult()

    fun addFavJob(job: JobToSave) = viewModelScope.launch {
        remoteJobRepository.addFavoriteJob(job)

    }

    fun deleteJob(job: JobToSave) = viewModelScope.launch {
        remoteJobRepository.deleteJob(job)

    }

    fun getAllFavJob() = remoteJobRepository.getAllFavJobs()

    fun searchRemoteJob(search : String?) = remoteJobRepository.searchJobResponse(search)
    fun searchResult() = remoteJobRepository.searchJobResult()
}


