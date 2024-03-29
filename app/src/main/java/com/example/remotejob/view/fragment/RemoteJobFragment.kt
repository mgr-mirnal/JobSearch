package com.example.remotejob.view.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.remotejob.MainActivity
import com.example.remotejob.R
import com.example.remotejob.view.adapter.RemoteJobAdapter
import com.example.remotejob.databinding.FragmentRemoteJobBinding
import com.example.remotejob.viewModel.RemoteJobViewModel


class RemoteJobFragment : Fragment(R.layout.fragment_remote_job), SwipeRefreshLayout.OnRefreshListener {


    private var _binding: FragmentRemoteJobBinding? = null
    private val binding: FragmentRemoteJobBinding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var remoteJobAdapter: RemoteJobAdapter
    private lateinit var swipeLayout: SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRemoteJobBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()

        swipeLayout = binding.swipeContainer
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(
            Color.GREEN, Color.RED,
            Color.BLUE, Color.CYAN
        )

        swipeLayout.post {
            swipeLayout.isRefreshing = true
            setUpRecyclerView()
        }
    }

    private fun setUpRecyclerView(){
        remoteJobAdapter = RemoteJobAdapter()

        binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.HORIZONTAL){})
            adapter = remoteJobAdapter
        }
        fetchingData()
    }

    private fun fetchingData(){
        viewModel.remoteJobResult().observe(viewLifecycleOwner) { remoteJob ->
            if (remoteJob != null) {
                remoteJobAdapter.differ.submitList(remoteJob.jobs)
                swipeLayout.isRefreshing = false
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRefresh() {
        setUpRecyclerView()
    }
}