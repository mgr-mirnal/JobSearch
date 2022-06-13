package com.example.remotejob.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remotejob.MainActivity
import com.example.remotejob.R
import com.example.remotejob.databinding.FragmentSavedJobBinding
import com.example.remotejob.models.JobToSave
import com.example.remotejob.view.adapter.FavJobAdapter
import com.example.remotejob.viewModel.RemoteJobViewModel


class SavedJobFragment : Fragment(R.layout.fragment_saved_job), FavJobAdapter.OnItemClickListener {

    private var _binding: FragmentSavedJobBinding? = null
    private val binding: FragmentSavedJobBinding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var favAdapter: FavJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentSavedJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        favAdapter = FavJobAdapter(this)

        binding.rvJobsSaved.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(
                activity, LinearLayout.VERTICAL
            ){})
            adapter = favAdapter
        }
        viewModel.getAllFavJob().observe(viewLifecycleOwner) {jobToSave ->
            favAdapter.differ.submitList(jobToSave)
            updateUI(jobToSave)

        }
    }

    private fun updateUI(job:  List<JobToSave>){
        if(job.isNotEmpty()){
            binding.rvJobsSaved.visibility = View.VISIBLE
            binding.cardNoAvailable.visibility = View.GONE
        }else{
            binding.rvJobsSaved.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }
    }



    override fun onItemClick(job: JobToSave, view: View, position: Int) {
        deleteJob(job)

    }

    private fun deleteJob(job : JobToSave){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Job")
            setMessage("Are you sure you want to delete this job ")
            setPositiveButton("Delete"){ _,_ ->
                viewModel.deleteJob(job)
                Toast.makeText(
                    activity, "Job deleted",Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}