package com.zibran.githubapp.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.zibran.githubapp.R
import com.zibran.githubapp.adapters.IssueAdapter
import com.zibran.githubapp.databinding.ActivityIssuesBinding
import com.zibran.githubapp.factory.RepoViewFactory
import com.zibran.githubapp.model.RepoIssueModel
import com.zibran.githubapp.viewModel.RepoViewModel


class IssuesActivity : AppCompatActivity() {
    lateinit var binding: ActivityIssuesBinding
    lateinit var viewModel: RepoViewModel
    lateinit var adapter: IssueAdapter
    var repoName = ""
    private var filterMenuClicked = false
    private val list = mutableListOf<RepoIssueModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIssuesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // get Repo Name
        repoName = intent.getStringExtra("repoName").toString()


        // set on click listener on filter icon to filter the  list with help of label
        binding.filter.setOnClickListener {
            setFilterMenu(it)
        }


        // set all issues in the list
        getAllIssues()


        // set search view
        binding.searchIssue.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false

            }

            override fun onQueryTextChange(newText: String): Boolean {

                adapter.filter.filter(newText)

                return false
            }
        })
    }

    private fun setFilterMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { it1 ->
            when (it1.itemId) {
                R.id.all -> {
                    if (filterMenuClicked) {
                        adapter.updateList(list)
                    }
                    filterMenuClicked = true

                    true
                }
                R.id.priority -> {
                    if (filterMenuClicked) {
                        adapter.updateList(list)
                    }
                    adapter.filterByLabel().filter(it1.title)
                    filterMenuClicked = true

                    true
                }
                R.id.enhancement -> {
                    if (filterMenuClicked) {
                        adapter.updateList(list)
                    }
                    adapter.filterByLabel().filter(it1.title)
                    filterMenuClicked = true
                    true
                }
                R.id.bug -> {
                    if (filterMenuClicked) {
                        adapter.updateList(list)
                    }
                    adapter.filterByLabel().filter(it1.title)
                    filterMenuClicked = true
                    true
                }
                R.id.usecases -> {
                    if (filterMenuClicked) {
                        adapter.updateList(list)
                    }
                    adapter.filterByLabel().filter(it1.title)
                    filterMenuClicked = true
                    true
                }
                else -> {

                    false
                }
            }

        }
        popupMenu.show()
    }

    // get all issues in the list &  update adapter list
    private fun getAllIssues() {
        // initialize
        viewModel =
            ViewModelProvider(this, RepoViewFactory(repoName))[RepoViewModel::class.java]
        adapter = IssueAdapter(this)
        binding.issueRV.adapter = adapter
        viewModel.getAllIssuesOfRepo().observeForever {
            list.clear()
            list.addAll(it)
            adapter.updateList(list)
        }

    }

}