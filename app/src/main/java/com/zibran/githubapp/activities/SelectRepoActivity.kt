package com.zibran.githubapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zibran.githubapp.databinding.ActivitySelectRepoBinding
import com.zibran.githubapp.viewModel.RepoViewModel

class SelectRepoActivity : AppCompatActivity() {
    lateinit var dataBinding: ActivitySelectRepoBinding
    lateinit var repoViewModel: RepoViewModel
    var repoName = ""
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        dataBinding = ActivitySelectRepoBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        // initialize viewModel
        repoViewModel = ViewModelProvider(this)[RepoViewModel::class.java]


        // set all repository in the drop down list
        getRepository()

        dataBinding.nextBtn.setOnClickListener {
            repoName = dataBinding.selectRepo.text.toString()
            if (repoName.isEmpty()) {
                Toast.makeText(this, "Please select any Repository.", Toast.LENGTH_SHORT).show()

            } else {
                intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("repoName", repoName)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }


    }

    private fun getRepository() {

        // initialize adapter & set adapter on recyclerview
        arrayAdapter =
            ArrayAdapter(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                repoViewModel.getAllRepos())
        dataBinding.selectRepo.setAdapter(arrayAdapter)


    }
}