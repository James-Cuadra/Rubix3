package com.example.cameratest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PictureAnalysis : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_analysis)

        val identifier = resources.getIdentifier("pic1", "drawable", "android.demo")
    }
}