package com.example.testimages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.testimages.Api.RetrofitService
import com.example.testimages.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstFloor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_floor)

        val backBtn: ImageView = findViewById(R.id.back)
        backBtn.setOnClickListener {
            val intent = Intent(this, GroundFloor::class.java)
            startActivity(intent)
            finish()
        }

        val sharedPref = getSharedPreferences("myshared", 0)
        val token = sharedPref.getString("tokenLogin", "")
        getReadingFirstFloor(token!!)

    }

    fun getReadingFirstFloor(
        tokenAuth: String
    ) {
        RetrofitService.getInstance()
            .sendToken("Bearer $tokenAuth")
            .enqueue(object : Callback<List<UltrasonicResponseItem>> {
                override fun onResponse(
                    call: Call<List<UltrasonicResponseItem>>,
                    response: Response<List<UltrasonicResponseItem>>
                ) {

                    // Toast.makeText(this@GroundFloor, "yees", Toast.LENGTH_LONG).show()
                    // val list = listOf<>()

                    // response.body()?.forEach{
                    val ultrasonicValue = response.body()?.get(10)?.readingStatus
                    val place11: ImageView = findViewById(R.id.place11)
//                    val place12: ImageView = findViewById(R.id.place12)
//                    val place13: ImageView = findViewById(R.id.place13)
//                    val place14: ImageView = findViewById(R.id.place14)
//                    val place15: ImageView = findViewById(R.id.place15)
//                    val place16: ImageView = findViewById(R.id.place16)
//                    val place17: ImageView = findViewById(R.id.place17)
//                    val place18: ImageView = findViewById(R.id.place18)
//                    val place19: ImageView = findViewById(R.id.place19)
//                    val place20: ImageView = findViewById(R.id.place20)

                    if (ultrasonicValue == "1") {
                        place11.setImageResource(R.drawable.place11)
                    } else if (ultrasonicValue != "1") {
                        place11.setImageResource(R.drawable.car1)
                        place11.layoutParams.width = 250
                        place11.layoutParams.height = 250
                    }
                    //}

                    else{
                        Toast.makeText(

                            
                            this@FirstFloor,
                            "You are not Authorized",
                            Toast.LENGTH_LONG
                        ).show()
                        intent = Intent(this@FirstFloor, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }


                override fun onFailure(call: Call<List<UltrasonicResponseItem>>, t: Throwable) {
                    Toast.makeText(this@FirstFloor,"No ${t.message}", Toast.LENGTH_LONG).show()
                    Log.d("msg","this is ${t.message}")

                }
            })

    }
    }

