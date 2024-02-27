package com.example.testimages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.testimages.ui.App

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startSplashActivity()

    }

    private fun startSplashActivity() {

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, App::class.java)
            startActivity(intent)

        },2000)

    }
}