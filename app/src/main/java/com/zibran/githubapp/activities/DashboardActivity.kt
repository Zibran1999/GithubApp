package com.zibran.githubapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zibran.githubapp.adapters.AssigneeAdapter
import com.zibran.githubapp.databinding.ActivityDashboardBinding
import com.zibran.githubapp.factory.RepoViewFactory
import com.zibran.githubapp.viewModel.RepoViewModel

class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    lateinit var viewModel: RepoViewModel
    var repoName = ""
    private var repoOpenIssues = 0
    private var repoLabelCount = 0
    private var repoHighPriorityCount = 0
    private val assigneeList = mutableListOf<String>()
    private val assigneeSetList = hashSetOf<String>()
    lateinit var assigneeAdapter: AssigneeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get Repo Name
        repoName = intent.getStringExtra("repoName").toString()

        // initialize view Model
        viewModel = ViewModelProvider(this, RepoViewFactory(repoName))[RepoViewModel::class.java]

        // set click listener on issues card to show all issues
        binding.issuesCardView.setOnClickListener {

            intent = Intent(this, IssuesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("repoName", repoName)
            startActivity(intent)

        }
        binding.backImgBtn.setOnClickListener {
            onBackPressed()
        }

        getAllIssues()

    }

    private fun getAllIssues() {
        viewModel.getAllIssuesOfRepo().observeForever {
            if (it.isNotEmpty()) {
                for (repoIssue in it) {
                    if (repoIssue.state == "open") {
                        repoOpenIssues++  // count open issues
                    }
                    if (repoIssue.labels != null) {
                        for (label in repoIssue.labels) {
                            if (label.name == "bug") {
                                repoLabelCount++
                            } else if (label.name == "high priority") {
                                repoHighPriorityCount++  // count high priority issues
                            }
                        }
                    }

                    if (repoIssue.assignees.isNotEmpty()) {
                        assigneeList.add(repoIssue.assignee.login)  // add all assignees
                    }
                }

                // set data on dashboard
                setDashBoardData(repoOpenIssues, repoLabelCount, repoHighPriorityCount)
                setAssignees(assigneeList)

            } else {
                binding.issueOverView.visibility = View.GONE
                binding.issuesCardView.visibility = View.GONE
                binding.totalAssignIssues.visibility = View.GONE
                binding.materialRVCardView.visibility = View.GONE
                binding.emptyMessage.visibility = View.VISIBLE
            }
        }

    }

    private fun setAssignees(assigneeList: MutableList<String>) {
        // initialize adapter
        assigneeAdapter = AssigneeAdapter(this)
        binding.totalAssignIssueRV.adapter = assigneeAdapter

        // count issue assignee
        val duplicateAssigneeList = mutableMapOf<String, Int>()
        for (w in assigneeList) {
            duplicateAssigneeList[w] = ((duplicateAssigneeList[w]) ?: 0) + 1
        }

        for ((key, value) in duplicateAssigneeList) {
            assigneeSetList.add("$key : $value issues")
        }

        assigneeList.clear()
        assigneeList.addAll(assigneeSetList)
        assigneeAdapter.updateList(assigneeList)

    }

    private fun setDashBoardData(
        repoOpenIssues: Int,
        repoLabelCount: Int,
        repoHighPriorityCount: Int,
    ) {

        binding.openIssues.text = repoOpenIssues.toString()
        binding.bugLabel.text = repoLabelCount.toString()
        binding.highIssues.text = repoHighPriorityCount.toString()


    }


}