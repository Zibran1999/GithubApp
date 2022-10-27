package com.zibran.githubapp.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zibran.githubapp.builder.RetrofitBuilder
import com.zibran.githubapp.model.RepoIssueModel
import com.zibran.githubapp.model.RepoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoViewModel() : ViewModel() {

    lateinit var repoName: String
    val repoList = mutableListOf<String>()
    var repoIssueLiveData = MutableLiveData<List<RepoIssueModel>>()


    constructor(repoName: String) : this() {
        this.repoName = repoName
    }


    fun getAllRepos(): MutableList<String> {

        RetrofitBuilder.apiService.getAllRepository().enqueue(object : Callback<List<RepoModel>> {
            override fun onResponse(
                call: Call<List<RepoModel>>, response: Response<List<RepoModel>>,
            ) {
                if (response.isSuccessful) {
                    for (repoModel in response.body()!!) {
                        repoList.add(repoModel.full_name)
                        Log.d(TAG, repoModel.full_name)
                    }
                }
            }

            override fun onFailure(call: Call<List<RepoModel>>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

        })

        return repoList
    }

    fun getAllIssuesOfRepo(): LiveData<List<RepoIssueModel>> {
        RetrofitBuilder.apiService.getRepoIssues("repos/$repoName/issues?per_page=100")
            .enqueue(object : Callback<List<RepoIssueModel>> {
                override fun onResponse(
                    call: Call<List<RepoIssueModel>>, response: Response<List<RepoIssueModel>>,
                ) {
                    if (response.isSuccessful) {
                        repoIssueLiveData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<List<RepoIssueModel>>, t: Throwable) {
                    Log.d("Error: $TAG", t.message.toString())
                }

            })

        return repoIssueLiveData
    }


}