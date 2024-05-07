package com.example.testimages

import android.app.NotificationChannel
import android.content.Context
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.testimages.Api.RetrofitService
import com.example.testimages.ui.App
import com.example.testimages.ui.HomeActivity
import com.example.testimages.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroundFloor : AppCompatActivity() {
    var myShared: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ground_floor)
        val floor2: Button = findViewById(R.id.floor2)

        val back: ImageView = findViewById(R.id.back)
        myShared = getSharedPreferences("myshared", 0)


        floor2.setOnClickListener {
            val intent = Intent(this@GroundFloor, FirstFloor::class.java)
            startActivity(intent)
            finish()
        }

        back.setOnClickListener {
            val intent = Intent(this@GroundFloor, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }


        val sharedPref = getSharedPreferences("myshared", 0)
        val token = sharedPref.getString("tokenLogin", "")
        getReadingGroundFloor(token!!)

    }


    fun getReadingGroundFloor(
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
                    val responseList = response.body()

                    val ultrasonicValueList =
                        if (response.body() != null && response.body()!!.size >= 2) {
                            val secondItem = responseList?.get(1)
                            listOfNotNull(secondItem?.readingStatus)
                        } else {
                            emptyList()
                        }


                    //==========================================

                    //=========================================


                    // response.body()?.forEach{
                    val ultrasonicValue = response.body()?.get(1)?.readingStatus
                    val place1: ImageView = findViewById(R.id.place11)
                    val place2: ImageView = findViewById(R.id.place12)
//                    val place3: ImageView = findViewById(R.id.place13)
//                    val place4: ImageView = findViewById(R.id.place14)
//                    val place5: ImageView = findViewById(R.id.place15)
//                    val place6: ImageView = findViewById(R.id.place16)
//                    val place7: ImageView = findViewById(R.id.place17)
//                    val place8: ImageView = findViewById(R.id.place18)
//                    val place9: ImageView = findViewById(R.id.place19)
//                    val place10: ImageView = findViewById(R.id.place20)

                    if (ultrasonicValue == "1") {
                        // place1.setImageResource(R.drawable.available)
                        place1.setImageResource(R.drawable.place1)


                        // place4.setImageResource(R.drawable.available)
//                        showLocalNotification(
//                            this@GroundFloor,
//                            "New Notification",
//                            "This is a local notification"
//                        )

                    } else if (ultrasonicValue != "1") {
                        place1.setImageResource(R.drawable.car1)
                        place1.layoutParams.width = 250
                        place1.layoutParams.height = 250




                        showLocalNotification(
                            this@GroundFloor,
                            "New Notification",
                            "This is a local notification"
                        )
                    }
                    //}

                    else {
                        Toast.makeText(
                            this@GroundFloor,
                            "You are not Authorized",
                            Toast.LENGTH_LONG
                        ).show()
                        intent = Intent(this@GroundFloor, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }


                override fun onFailure(call: Call<List<UltrasonicResponseItem>>, t: Throwable) {
                    Toast.makeText(this@GroundFloor, "No ${t.message}", Toast.LENGTH_LONG).show()
                    Log.d("msg", "this is ${t.message}")

                }
            })
    }


    fun showLocalNotification(context: Context, title: String, message: String) {
        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java)
        val channelId = "my_channel_id"


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    channelId,
                    "My Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            notificationManager?.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.logo)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)



        notificationManager?.notify(1, notificationBuilder.build())
    }
}



























