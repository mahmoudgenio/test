package com.example.testimages.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.testimages.R

class SplashActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash1)
        startSplashActivity1()
    }

    private fun startSplashActivity1() {

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, SplashActivity2::class.java)
            startActivity(intent)

        },3000)

    }
}