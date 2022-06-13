package com.example.remotejob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.remotejob.database.FavoriteDatabase
import com.example.remotejob.databinding.ActivityMainBinding
import com.example.remotejob.repository.RemoteJobRepository
import com.example.remotejob.viewModel.RemoteJobViewModel
import com.example.remotejob.viewModel.RemoteJobViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: RemoteJobViewModel

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val remoteJobRepository = RemoteJobRepository(
            FavoriteDatabase(this)
        )

        val viewModelProviderFactory = RemoteJobViewModelFactory(
            application,
            remoteJobRepository
        )
        viewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        )[RemoteJobViewModel::class.java]


    }


}