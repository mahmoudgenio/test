package com.example.testimages

import android.app.Dialog
import android.app.NotificationChannel
import android.content.Context
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.testimages.Api.RetrofitService
import com.example.testimages.ui.HomeActivity
import com.example.testimages.ui.login.LoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroundFloor : AppCompatActivity() {
    var myShared: SharedPreferences? = null
    lateinit var firebase:FirebaseMessage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ground_floor)
        val floor2: Button = findViewById(R.id.floor2)
        //val text: EditText = findViewById(R.id.exit)
        var Token:String = ""
       // var selectedImage:Int = 0


        val imageViewsList = mutableListOf<ImageView>()

        val imageView1 = findViewById<ImageView>(R.id.place1)
        val imageView2 = findViewById<ImageView>(R.id.place2)
        val imageView3 = findViewById<ImageView>(R.id.place3)
        val imageView4 = findViewById<ImageView>(R.id.place4)
        val imageView5 = findViewById<ImageView>(R.id.place5)
        val imageView6 = findViewById<ImageView>(R.id.place6)
        val imageView7 = findViewById<ImageView>(R.id.place7)
        val imageView8 = findViewById<ImageView>(R.id.place8)

// قم بتعريف ImageViews إضافية حسب الحاجة

        imageViewsList.add(imageView1)
        imageViewsList.add(imageView2)
        imageViewsList.add(imageView3)
        imageViewsList.add(imageView4)
        imageViewsList.add(imageView5)
        imageViewsList.add(imageView6)
        imageViewsList.add(imageView7)
        imageViewsList.add(imageView8)

// قم بإضافة ImageViews إضافية إلى القائمة

        var imageSelected = false

        for (imageView in imageViewsList) {
            imageView.setOnClickListener {
                if(!imageSelected){
                    imageSelected = true
                }

                showCustomDialogBox("Are you sure you want to select this place")
                val selectedImage = imageViewsList.indexOf(imageView) + 1
                Toast.makeText(this@GroundFloor, "You clicked on place $selectedImage", Toast.LENGTH_SHORT).show()
                sendSelectPlace(selectedImage,Token)

            }
        }


        //========================== fcm

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            Token = task.result

            // Log and toast
           // val msg = getString(R.string.msg_token_fmt, token)
           // Log.d(TAG)
            //Toast.makeText(baseContext, "Hello$Token", Toast.LENGTH_SHORT).show()
            //text.setText(Token)
//            sendSelectPlace(8,Token)
        })



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

//        sendSelectPlace(8,"dOzHHJVuT3WtkT3j-xfGsk:APA91bGOctY78HNqLnEnE-apmkp4Um3-MiUOSldwQCjQosGCd6fQY7ETBQVZgevtfxHKILoVXNakJQkbnbnF9uEi2ReWcIL2zKVMheCOcUpYQRnWIXfXbUbAr8xd55wAP67_xneryM25")

    }


    fun sendSelectPlace(id:Int , tokenPlace:String){

        RetrofitService.getInstance()
            .selectPlace(id,tokenPlace)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    if(response.code() == 200){
                        Toast.makeText(this@GroundFloor,"Success select place",Toast.LENGTH_LONG).show()
                    }
                    else if(response.code() == 401) {
                        Toast.makeText(this@GroundFloor,"you must select place",Toast.LENGTH_LONG).show()

                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@GroundFloor,"server error",Toast.LENGTH_LONG).show()
                }

                })

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


                    // ============================================================ new for list

                    val parkingImages = listOf(
                        R.drawable.place1,
                        R.drawable.place2,
                        R.drawable.place3,
                        R.drawable.place4,
                        R.drawable.place5,
                        R.drawable.place6,
                        R.drawable.place7,
                        R.drawable.place8
                        // وهكذا، قم بإضافة صور المواقف الأخرى هنا
                    )

                    val ultrasonicValues = response.body()?.map { it.readingStatus } ?: emptyList()
                    val imageViewIds = listOf(R.id.place1, R.id.place2, R.id.place3, R.id.place4, R.id.place5, R.id.place6, R.id.place7, R.id.place8)
                    imageViewIds.forEachIndexed { index, imageViewId ->
                        val ultrasonicValue = ultrasonicValues.getOrNull(index)

                        val imageView: ImageView = findViewById(imageViewId)

                        if (ultrasonicValue == "0") {


                            if (index < parkingImages.size){
                                imageView.setImageResource(parkingImages[index])
                            }
//                            else
//                            {
////                                imageView.setImageResource(R.drawable.place2)
//                                imageView.setImageResource(R.drawable.place2)
//                            }

//                                                    showLocalNotification(
//                            this@GroundFloor,
//                            "New Notification",
//                            "Your Car is Moving From its Place "
//                        )

                        }

                        else if (ultrasonicValue != "0") {
                            imageView.setImageResource(R.drawable.car1)
                        imageView.layoutParams.width = 250
                        imageView.layoutParams.height = 250


//                        val firebaseMessage = FirebaseMessage()
//                        firebaseMessage.showLocalNotification(applicationContext, "New Notification", "This is a local notification")

                       // firebase.onMessageSent("dfgdsv")

//                        showLocalNotification(
//                            this@GroundFloor,
//                            "New Notification",
//                            "This is a local notification"
//                        )
                    }else {

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




                    //==============================================================

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




                    // response.body()?.forEach{
//                    val ultrasonicValue = response.body()?.get(1)?.readingStatus
//                    val place1: ImageView = findViewById(R.id.place11)
//                    val place2: ImageView = findViewById(R.id.place12)
//
//                    if (ultrasonicValue == "1") {
//                        // place1.setImageResource(R.drawable.available)
//                        place1.setImageResource(R.drawable.place1)
//
//
//                        showLocalNotification(
//                            this@GroundFloor,
//                            "New Notification",
//                            "This is a local notification"
//                        )
//
//                    } else if (ultrasonicValue != "1") {
//                        place1.setImageResource(R.drawable.car1)
//                        place1.layoutParams.width = 250
//                        place1.layoutParams.height = 250
//
////                        val firebaseMessage = FirebaseMessage()
////                        firebaseMessage.showLocalNotification(applicationContext, "New Notification", "This is a local notification")
//
//                       // firebase.onMessageSent("dfgdsv")
//
////                        showLocalNotification(
////                            this@GroundFloor,
////                            "New Notification",
////                            "This is a local notification"
////                        )
//                    }
                    //}

//                    else {
//                        Toast.makeText(
//                            this@GroundFloor,
//                            "You are not Authorized",
//                            Toast.LENGTH_LONG
//                        ).show()
//                        intent = Intent(this@GroundFloor, LoginActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//
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

            Toast.makeText(this@GroundFloor, "Success", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}



























