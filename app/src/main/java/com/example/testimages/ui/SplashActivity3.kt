package com.example.testimages.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import com.example.testimages.R

class SplashActivity3 : AppCompatActivity() {
    private lateinit var btnNext: Button
    private lateinit var btnSkip: TextView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash3)
        btnNext = findViewById(R.id.btn_splash3)
        btnSkip = findViewById(R.id.skip)
        startSplashActivity3()

    }

    private fun startSplashActivity3() {
        btnNext.setOnClickListener{
            val intent = Intent(this, SplashActivity4::class.java)
            startActivity(intent)

        }
        btnSkip.setOnClickListener {
            val intent = Intent(this, SplashActivity4::class.java)
            startActivity(intent)

        }

    }
    }
