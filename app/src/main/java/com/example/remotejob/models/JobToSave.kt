package com.example.remotejob.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job")
data class JobToSave(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val candidate_required_location: String,
    val category: String,
    val company_logo: String,
    val company_logo_url: String,
    val company_name: String,
    val description: String,
    val JobId: Int,
    val job_type: String,
    val publication_date: String,
    val salary: String,
    val title: String,
    val url: String
)