package com.example.remotejob.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.remotejob.models.JobToSave

@Dao
interface FavoriteJobDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteJob(job: JobToSave)

    //sort the order in decending order
    @Query("SELECT * FROM job ORDER BY id DESC")
    fun getAllFavJob() : LiveData<List<JobToSave>>

    @Delete
    suspend fun deleteFavJob(job: JobToSave)
}