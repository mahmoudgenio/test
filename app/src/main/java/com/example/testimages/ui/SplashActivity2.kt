package com.example.testimages.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testimages.R


class SplashActivity2 : AppCompatActivity() {

    private lateinit var btnNext: Button
    private lateinit var btnSkip: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash2)

        btnNext = findViewById(R.id.btn_splash2)
        btnSkip = findViewById(R.id.skip)
        startSplashActivity2()

    }

    private fun startSplashActivity2() {
        btnNext.setOnClickListener {
            val intent = Intent(this, SplashActivity3::class.java)
            startActivity(intent)

        }

        btnSkip.setOnClickListener {
            val intent = Intent(this, SplashActivity3::class.java)
            startActivity(intent)

        }
    }

}