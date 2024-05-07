package com.example.testimages.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.testimages.R
import com.example.testimages.ui.register.RegisterActivity

class SplashActivity4 : AppCompatActivity() {
    private lateinit var btnNext: Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash4)

        btnNext = findViewById(R.id.btn_splash4)
        startSplashActivity4()

    }
    private fun startSplashActivity4() {
        btnNext.setOnClickListener {
//            val intent = Intent(this, App::class.java)

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }
    }

}
