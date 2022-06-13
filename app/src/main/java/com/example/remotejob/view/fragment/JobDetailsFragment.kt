package com.example.remotejob.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.remotejob.MainActivity
import com.example.remotejob.R
import com.example.remotejob.databinding.FragmentJobDetailsBinding
import com.example.remotejob.models.Job
import com.example.remotejob.models.JobToSave
import com.example.remotejob.viewModel.RemoteJobViewModel
import com.google.android.material.snackbar.Snackbar


class JobDetailsFragment : Fragment(R.layout.fragment_job_details) {

    private var _binding: FragmentJobDetailsBinding? = null
    private val binding: FragmentJobDetailsBinding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var currentJob: Job
    private val args: JobDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        currentJob = args.job!!
        setUPWebView()
        binding.fabAddFavorite.setOnClickListener {
            addFavJob(view)
        }
    }

    private fun addFavJob(view: View) {
        val favJob = JobToSave(
            0,
            currentJob.candidate_required_location,
            currentJob.category,
            currentJob.company_logo,
            currentJob.company_logo_url,
            currentJob.company_name,
            currentJob.description,
            currentJob.id,
            currentJob.job_type,
            currentJob.publication_date,
            currentJob.salary,
            currentJob.title,
            currentJob.url,
        )
        viewModel.addFavJob(favJob)
        Snackbar.make(view, "Job Saved Successfully", Snackbar.LENGTH_LONG).show()

    }

    private fun setUPWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(currentJob.url)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}