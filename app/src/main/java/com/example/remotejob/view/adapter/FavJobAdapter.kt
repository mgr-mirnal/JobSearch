package com.example.remotejob.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.remotejob.databinding.JobLayoutAdapterBinding
import com.example.remotejob.models.Job
import com.example.remotejob.models.JobToSave
import com.example.remotejob.view.fragment.MainFragmentDirections

class FavJobAdapter (
    private val itemClick : OnItemClickListener)
    : RecyclerView.Adapter<FavJobAdapter.RemoteJobViewHolder>() {

    private var binding: JobLayoutAdapterBinding? = null

    inner class RemoteJobViewHolder(itemBinding: JobLayoutAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object :
        DiffUtil.ItemCallback<JobToSave>() {

        override fun areItemsTheSame(oldItem: JobToSave, newItem: JobToSave): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: JobToSave, newItem: JobToSave): Boolean {
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
            binding?.ibDelete?.visibility = View.VISIBLE

        }.setOnClickListener {mView ->
            val tags = arrayListOf<String>()
            val job = Job(
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
                tags,
                currentJob.title,
                currentJob.url,
            )
            val direction = MainFragmentDirections.actionMainFragmentToJobDetailsFragment(job)

            mView.findNavController().navigate(direction)
        }

            holder.itemView.apply {
                binding?.ibDelete?.setOnClickListener {
                    itemClick.onItemClick(currentJob,binding?.ibDelete!!,position)
                }
            }
        }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    interface OnItemClickListener{
        fun onItemClick(
            job: JobToSave,
            view: View,
            position: Int
            )
    }

}