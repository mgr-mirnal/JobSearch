package com.example.remotejob.models

import com.google.gson.annotations.SerializedName

data class RemoteJobResponse(
    @SerializedName("0-legal-notice")
    val legalNotice: String,
    @SerializedName("00-warning")
    val warning: String,
    @SerializedName("job-count")
    val jobCount: Int,
    val jobs: List<Job>
)