package com.zibran.githubapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zibran.githubapp.R
import com.zibran.githubapp.databinding.AssigneeIssueLayoutBinding

class AssigneeAdapter(private val context: Context) :
    RecyclerView.Adapter<AssigneeAdapter.ViewHolder>() {

    var list = mutableListOf<String>()


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = AssigneeIssueLayoutBinding.bind(itemView)
        var title = binding.assignee

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.assignee_issue_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position]


    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(issueList: List<String>) {
        list.clear()
        list.addAll(issueList)
        notifyDataSetChanged()
    }


}