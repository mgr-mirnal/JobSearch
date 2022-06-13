package com.bersyte.remojob.api


import com.example.remotejob.models.RemoteJobResponse
import retrofit2.Call
import retrofit2.http.GET


interface RemoteJobApi {

    @GET("remote-jobs?limit=5")
    fun getRemoteJobResponse(): Call<RemoteJobResponse>



}