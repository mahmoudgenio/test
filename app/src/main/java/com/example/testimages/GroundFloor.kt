package com.example.testimages

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Button
import android.widget.Toast
import com.example.testimages.Api.LoginResponse
import com.example.testimages.Api.RetrofitService
import com.example.testimages.ui.login.LoginActivity
import com.example.testimages.ui.login.LoginViewEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroundFloor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ground_floor)
        val firstFloor: Button = findViewById(R.id.first_floor)
        var myShared: SharedPreferences? = null

        firstFloor.setOnClickListener {
            val intent = Intent(this@GroundFloor, FirstFloor::class.java)
            startActivity(intent)
            finish()
        }

//        val place1: ImageView = findViewById(R.id.place1)
//        val place2: ImageView = findViewById(R.id.place2)
//        val place3: ImageView = findViewById(R.id.place3)
//        val place4: ImageView = findViewById(R.id.place4)
//        val place5: ImageView = findViewById(R.id.place5)
//        val place6: ImageView = findViewById(R.id.place6)
//        val place7: ImageView = findViewById(R.id.place7)
//        val place8: ImageView = findViewById(R.id.place8)
//        val place9: ImageView = findViewById(R.id.place9)
//        val place10: ImageView = findViewById(R.id.place10)

// Check the state of each parking place
//        val isCarInPlace1: Boolean = false
//        val isCarInPlace2: Boolean = true
//        val isCarInPlace3: Boolean = true
//        val isCarInPlace4: Boolean = true

// Set the image resource based on the state of each parking place
//        place1.setImageResource(if (isCarInPlace1) R.drawable.car_image else R.drawable.available)
//        place2.setImageResource(if (isCarInPlace2) R.drawable.car_image else R.drawable.available)
//        place3.setImageResource(if (isCarInPlace3) R.drawable.car_image else R.drawable.available)
//        place4.setImageResource(if (isCarInPlace4) R.drawable.car_image else R.drawable.available)

        getReading("")

    }


//    val sharedPref = getSharedPreferences("myshared", 0)
//    val token = sharedPref.getString("tokenLogin", "false")

    fun getReading(
        tokenAuth: String
    ) {
       // Toast.makeText(this@GroundFloor,"",Toast.LENGTH_LONG).show()
       // if (token != null) {
            RetrofitService.getInstance()
                .sendToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9naXZlbm5hbWUiOiJNYWhtb3VkIG1vc3RhZmEiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9lbWFpbGFkZHJlc3MiOiJnZW5pb29AZ21haWwuY29tIiwiZXhwIjoxNzA5OTA0NTUxLCJpc3MiOiJodHRwOi8vdGhlZnRwYXJraW5nLnNvbWVlLmNvbS8iLCJhdWQiOiJNeVNlY3VyZVVzZXJzIn0.p-RgkfrCeF4BUWXacwvNGnk1X_0QXlMelFSMb9O3aiM")
                .enqueue(object : Callback<UltrasonicResponseItem> {
                    override fun onResponse(
                        call: Call<UltrasonicResponseItem>,
                        response: Response<UltrasonicResponseItem>
                    ) {

                            if (response.isSuccessful) {
                                val ultrasonicValue = response.body()?.readingStatus
                                val place1: ImageView = findViewById(R.id.place1)
                                val place2: ImageView = findViewById(R.id.place2)
                                val place3: ImageView = findViewById(R.id.place3)
                                val place4: ImageView = findViewById(R.id.place4)
                                val place5: ImageView = findViewById(R.id.place5)
                                val place6: ImageView = findViewById(R.id.place6)
                                val place7: ImageView = findViewById(R.id.place7)
                                val place8: ImageView = findViewById(R.id.place8)
                                val place9: ImageView = findViewById(R.id.place9)
                                val place10: ImageView = findViewById(R.id.place10)

                                if (ultrasonicValue == "0") {
                                    place2.setImageResource(R.drawable.available)
                                } else if (ultrasonicValue != "0") {
                                    place2.setImageResource(R.drawable.car_image)
                                }else if(response.code() == 401){
                                    // un Authorized
                                    // Navigate to login
                                    Toast.makeText(this@GroundFloor,"You are not Authorized",Toast.LENGTH_LONG).show()
                                    intent = Intent(this@GroundFloor, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                            }
                        }


                    override fun onFailure(call: Call<UltrasonicResponseItem>, t: Throwable) {
                        Toast.makeText(this@GroundFloor,"No Internet Connection",Toast.LENGTH_LONG).show()

                    }
                })

        }
    }
















