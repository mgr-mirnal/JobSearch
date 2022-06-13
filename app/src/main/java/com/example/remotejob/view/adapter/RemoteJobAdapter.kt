package com.example.remotejob.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.remotejob.databinding.JobLayoutAdapterBinding
import com.example.remotejob.models.Job
import com.example.remotejob.view.fragment.MainFragment
import com.example.remotejob.view.fragment.MainFragmentDirections

class RemoteJobAdapter : RecyclerView.Adapter<RemoteJobAdapter.RemoteJobViewHolder>() {

    private var binding: JobLayoutAdapterBinding? = null

    inner class RemoteJobViewHolder(itemBinding: JobLayoutAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object :
        DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, differCallback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobViewHolder {
        binding = JobLayoutAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return RemoteJobViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: RemoteJobViewHolder, position: Int) {
        val currentJob = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(currentJob.company_logo)
                .into(binding?.ivCompanyLogo!!)
            binding?.tvCompanyName?.text = currentJob.company_name
            binding?.tvJobLocation?.text = currentJob.candidate_required_location
            binding?.tvJobTitle?.text = currentJob.title
            binding?.tvJobType?.text = currentJob.job_type

            val dateJob = currentJob.publication_date.split("T")
            binding?.tvDate?.text = dateJob[0]

        }.setOnClickListener {mView ->
            val direction = MainFragmentDirections.actionMainFragmentToJobDetailsFragment(currentJob)

            mView.findNavController().navigate(direction)
        }


        }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}