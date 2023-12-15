package com.brigoli.finalproject

import androidx.appcompat.app.AppCompatActivity

object NavigationUtil {
    fun setupBackButton(activity: AppCompatActivity) {
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}