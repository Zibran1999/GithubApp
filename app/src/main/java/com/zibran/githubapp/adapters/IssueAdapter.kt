package com.zibran.githubapp.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.zibran.githubapp.R
import com.zibran.githubapp.databinding.IssuesLayoutBinding
import com.zibran.githubapp.model.RepoIssueModel

class IssueAdapter(private val context: Context) : RecyclerView.Adapter<IssueAdapter.ViewHolder>(),
    Filterable {

    var list = mutableListOf<RepoIssueModel>()
    var repoIssuesList = mutableListOf<RepoIssueModel>()
    var labelIsPresent = ""

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = IssuesLayoutBinding.bind(itemView)
        var title = binding.issueTitle
        var issueNumber = binding.issueNumber
        var issueAssign = binding.issueAssign
        var issueLable = binding.issueLabel


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.issues_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].title
        "# ${list[position].number}".also { holder.issueNumber.text = it }

        if (list[position].assignee != null) {
            val assign = list[position].assignee
            holder.issueAssign.visibility = View.VISIBLE
            holder.issueAssign.text = assign.login
        } else {
            holder.issueAssign.visibility = View.GONE
        }

        for (label in list[position].labels) {
            if (label.name.isNotEmpty()) {
                holder.issueLable.apply {
                    visibility = View.VISIBLE
                    text = label.name.plus(" ")
                    var labelColor = "#${label.color}"
                    backgroundTintList = ColorStateList.valueOf(Color.parseColor(labelColor))

                }

            } else {
                holder.issueLable.visibility = View.GONE
            }
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(issueList: List<RepoIssueModel>) {
        list.clear()
        repoIssuesList.clear()
        list.addAll(issueList)
        repoIssuesList.addAll(issueList)

        notifyDataSetChanged()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults? {

                val filterResult = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResult.values = repoIssuesList
                    filterResult.count = repoIssuesList.size
                } else {
                    var searchStr = constraint.toString().lowercase()
                    val issueList = mutableListOf<RepoIssueModel>()
                    for (issueModel in list) {
                        if (issueModel.title.lowercase().contains(searchStr)) {
                            issueList.add(issueModel)
                        }
                    }
                    filterResult.values = issueList
                    filterResult.count = issueList.size
                }
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list = results!!.values as MutableList<RepoIssueModel>
                notifyDataSetChanged()

            }
        }
    }

    fun filterByLabel(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults? {

                val filterResult = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResult.values = repoIssuesList
                    filterResult.count = repoIssuesList.size
                } else {
                    var searchStr = constraint.toString().lowercase()
                    val issueList = mutableListOf<RepoIssueModel>()
                    for (issueModel in list) {
                        for (label in issueModel.labels) {
                            labelIsPresent = label.name
                            if (label.name.lowercase().contains(searchStr)) {
                                issueList.add(issueModel)
                            }
                        }
                    }
                    if (labelIsPresent.isEmpty()) {
                        filterResult.values = repoIssuesList
                        filterResult.count = repoIssuesList.size
                    }
                    filterResult.values = issueList
                    filterResult.count = issueList.size
                }
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list = results!!.values as MutableList<RepoIssueModel>
                notifyDataSetChanged()

            }
        }


    }
}