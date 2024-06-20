package com.example.testimages.ui

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.testimages.Api.RetrofitService
import com.example.testimages.FirstFloor
import com.example.testimages.GroundFloor
import com.example.testimages.R
import com.example.testimages.ui.login.LoginActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() {
    var myShared: SharedPreferences? = null
    private lateinit var nameUser: TextView
    private lateinit var startPark: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        val nameUser = findViewById<TextView?>(R.id.name_user)
//        nameUser.text = intent.getStringExtra("displayName")


//        nameUser = findViewById(R.id.name_user)
//        val intent = intent.getStringExtra("email")
//        nameUser.text = (intent)


        startPark = findViewById(R.id.start_park)

        val logout: TextView = findViewById(R.id.logout_home)
        myShared = getSharedPreferences("myshared", 0)


        logout.setOnClickListener {
           // logout()
            val message: String? = "Are you sure you want to log out"
            showCustomDialogBox(message)
        }

        startPark.setOnClickListener {

            val intent = Intent(this@HomeActivity, GroundFloor::class.java)
            startActivity(intent)
            finish()
        }



    }

    fun logout() {
        RetrofitService.getInstance()
            .logout(myShared?.getString("tokenLogin", ""))
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    Toast.makeText(this@HomeActivity, "Logout Success", Toast.LENGTH_LONG).show()
                    myShared?.edit()!!.clear().commit()

                    val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                    startActivity(intent)


                }


                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@HomeActivity, "Error connection", Toast.LENGTH_LONG).show()
                    Log.d("msg", "this is ${t.message}")

                }
            })
    }
    private fun showCustomDialogBox(message: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dailog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
        val btnYes: Button = dialog.findViewById(R.id.btnYes)
        val btnNo: Button = dialog.findViewById(R.id.btnNo)

        tvMessage.text = message


        btnYes.setOnClickListener {
            logout()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }




}