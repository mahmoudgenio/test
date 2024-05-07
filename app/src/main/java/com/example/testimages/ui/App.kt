package com.example.testimages.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.testimages.GroundFloor
import com.example.testimages.SplashActivity
import com.example.testimages.ui.login.LoginActivity
import com.example.testimages.ui.register.RegisterActivity

class App : AppCompatActivity() {

    var myShared : SharedPreferences?=null

    override fun onStart() {
        super.onStart()
        myShared = getSharedPreferences("myshared", 0)
        var token = myShared?.getString("tokenLogin","")

        if (token != ""){
//            val intent = Intent(this@App , GroundFloor::class.java)
            val intent = Intent(this@App , HomeActivity::class.java)
            startActivity(intent)
        }else if(token == ""){
            val intent2 = Intent(this@App, SplashActivity1::class.java)
            startActivity(intent2)
        }

    }
}