package com.example.androidassignment.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidassignment.R
import com.example.androidassignment.core.component
import com.example.androidassignment.utils.ActivityService

class MainActivity : AppCompatActivity() {
    private lateinit var activityService: ActivityService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(supportActionBar != null)
        supportActionBar?.hide()
        activityService = applicationContext.component.activityService()
        activityService.onCreate(this)
    }


    override fun onDestroy() {
        activityService.onDestory(this)
        super.onDestroy()

    }
}