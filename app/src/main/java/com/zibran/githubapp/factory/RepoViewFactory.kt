package com.zibran.githubapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zibran.githubapp.viewModel.RepoViewModel

class RepoViewFactory(private val repoName: String) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RepoViewModel(repoName) as T
    }
}