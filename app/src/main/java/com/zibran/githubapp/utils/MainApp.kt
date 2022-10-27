package com.zibran.githubapp.utils

import android.app.Application
import com.google.android.material.color.DynamicColors

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // This is all you need.
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}