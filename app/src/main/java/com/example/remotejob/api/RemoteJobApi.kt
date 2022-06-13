package com.bersyte.remojob.api


import com.example.remotejob.models.RemoteJobResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RemoteJobApi {

    @GET("remote-jobs?limit=10")
    fun getRemoteJobResponse(): Call<RemoteJobResponse>

    @GET("remote-jobs")
    fun searchJob(
        @Query("search") search : String?
    ): Call<RemoteJobResponse>

}