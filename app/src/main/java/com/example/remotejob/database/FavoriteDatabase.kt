package com.example.remotejob.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remotejob.models.JobToSave

@Database(entities = [JobToSave::class], version = 1)
abstract class FavoriteDatabase: RoomDatabase() {

    abstract fun getFavJobDao(): FavoriteJobDAO

    companion object{
        @Volatile
        private var instance: FavoriteDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
           //initialize  database
            instance?: createDatabase(context).also { instance = it }
        }
        // creating database
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FavoriteDatabase::class.java,
                "fav_job_db"
            ).build()


    }

}