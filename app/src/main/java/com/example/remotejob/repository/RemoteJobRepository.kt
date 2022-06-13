package com.example.remotejob.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bersyte.remojob.api.RetrofitInstance
import com.bersyte.remojob.utils.Constants.TAG
import com.example.remotejob.database.FavoriteDatabase
import com.example.remotejob.models.JobToSave
import com.example.remotejob.models.RemoteJobResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJobRepository(private val db :FavoriteDatabase ) {

    private val remoteJobsService = RetrofitInstance.api
    private val remoteJobResponseLiveData: MutableLiveData<RemoteJobResponse?> = MutableLiveData()
    private val searchJobResponseLiveData: MutableLiveData<RemoteJobResponse?> = MutableLiveData()
    init {
        getRemoteJobResponse()
    }
    private fun getRemoteJobResponse() {
        remoteJobsService.getRemoteJobResponse().enqueue(
            object : Callback<RemoteJobResponse> {
                override fun onResponse(
                    call: Call<RemoteJobResponse>,
                    response: Response<RemoteJobResponse>
                ) {
                    remoteJobResponseLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<RemoteJobResponse>, t: Throwable) {
                    remoteJobResponseLiveData.postValue(null)
                    Log.e(TAG,"onFailure: ${t.message}")
                }
            }


        )
    }
    fun searchJobResponse(search : String?){
        remoteJobsService.searchJob(search).enqueue(
            object : Callback<RemoteJobResponse>{
                override fun onResponse(
                    call: Call<RemoteJobResponse>,
                    response: Response<RemoteJobResponse>
                ) {
                    searchJobResponseLiveData.postValue(response.body())
                    }

                override fun onFailure(call: Call<RemoteJobResponse>, t: Throwable) {
                    searchJobResponseLiveData.postValue(null)
                    Log.e(TAG,"onFailure: ${t.message}")
                }

            }
        )
    }
    fun searchJobResult(): MutableLiveData<RemoteJobResponse?> {
        return searchJobResponseLiveData
    }

    fun remoteJobResult(): MutableLiveData<RemoteJobResponse?> {
        return remoteJobResponseLiveData
    }
    suspend fun addFavoriteJob(job: JobToSave) = db.getFavJobDao().addFavoriteJob(job)
    suspend fun deleteJob(job: JobToSave) = db.getFavJobDao().deleteFavJob(job)
    fun getAllFavJobs() = db.getFavJobDao().getAllFavJob()
}