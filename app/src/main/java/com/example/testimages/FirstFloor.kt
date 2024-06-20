package com.example.testimages

import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
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
import com.example.testimages.ui.login.LoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstFloor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_floor)
        var Token:String = ""

        val backBtn: ImageView = findViewById(R.id.back)
        backBtn.setOnClickListener {
            val intent = Intent(this, GroundFloor::class.java)
            startActivity(intent)
            finish()
        }

        val sharedPref = getSharedPreferences("myshared", 0)
        val token = sharedPref.getString("tokenLogin", "")
        getReadingFirstFloor(token!!)


        val imageViewsList1 = mutableListOf<ImageView>()

        val imageView9 = findViewById<ImageView>(R.id.place9)
        val imageView10 = findViewById<ImageView>(R.id.place10)
        val imageView11 = findViewById<ImageView>(R.id.place11)
        val imageView12 = findViewById<ImageView>(R.id.place12)


        imageViewsList1.add(imageView9)
        imageViewsList1.add(imageView10)
        imageViewsList1.add(imageView11)
        imageViewsList1.add(imageView12)


        var imageSelected = false

        for (imageView in imageViewsList1) {
            imageView.setOnClickListener {
                if(!imageSelected){
                    imageSelected = true
                }

                showCustomDialogBox("Are you sure you want to select this place")
                val selectedImage = imageViewsList1.indexOf(imageView) + 9
                Toast.makeText(this@FirstFloor, "You clicked on place $selectedImage", Toast.LENGTH_SHORT).show()
                sendSelectPlace(selectedImage,Token)

            }
        }



        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
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

                    val parkingImages = listOf(
                        R.drawable.place9,
                        R.drawable.place10,
                        R.drawable.place11,
                        R.drawable.place12,
                        // وهكذا، قم بإضافة صور المواقف الأخرى هنا
                    )

                    val ultrasonicValues = response.body()?.map { it.readingStatus } ?: emptyList()
                    val imageViewIds = listOf(R.id.place9, R.id.place10, R.id.place11, R.id.place12)

                    imageViewIds.forEachIndexed { index, imageViewId ->
                        val ultrasonicValue = ultrasonicValues.getOrNull(index)

                        val imageView: ImageView = findViewById(imageViewId)


                        // Toast.makeText(this@GroundFloor, "yees", Toast.LENGTH_LONG).show()
                        // val list = listOf<>()

                        // response.body()?.forEach{
                        // ===
//                    val ultrasonicValue = response.body()?.get(10)?.readingStatus
//                    val place11: ImageView = findViewById(R.id.place11)
                        // ===
//                    val place12: ImageView = findViewById(R.id.place12)
//                    val place13: ImageView = findViewById(R.id.place13)
//                    val place14: ImageView = findViewById(R.id.place14)
//                    val place15: ImageView = findViewById(R.id.place15)
//                    val place16: ImageView = findViewById(R.id.place16)
//                    val place17: ImageView = findViewById(R.id.place17)
//                    val place18: ImageView = findViewById(R.id.place18)
//                    val place19: ImageView = findViewById(R.id.place19)
//                    val place20: ImageView = findViewById(R.id.place20)

                        if (ultrasonicValue == "0") {
                            // place11.setImageResource(R.drawable.place11)
                            if (index < parkingImages.size) {
                                imageView.setImageResource(parkingImages[index])
                            } else {
                                imageView.setImageResource(R.drawable.place2)
                            }
                        } else if (ultrasonicValue != "0") {
//                        place11.setImageResource(R.drawable.car1)
//                        place11.layoutParams.width = 250
//                        place11.layoutParams.height = 250
                            imageView.setImageResource(R.drawable.car1)
                            imageView.layoutParams.width = 250
                            imageView.layoutParams.height = 250
                        }
                        //}

                        else {
                            Toast.makeText(


                                this@FirstFloor,
                                "You are not Authorized",
                                Toast.LENGTH_LONG
                            ).show()
                            intent = Intent(this@FirstFloor, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        val responseList = response.body()

                        val ultrasonicValueList =
                            if (response.body() != null && response.body()!!.size >= 2) {
                                val secondItem = responseList?.get(1)
                                listOfNotNull(secondItem?.readingStatus)
                            } else {
                                emptyList()
                            }

                    }
                }


                override fun onFailure(call: Call<List<UltrasonicResponseItem>>, t: Throwable) {
                    Toast.makeText(this@FirstFloor,"No ${t.message}", Toast.LENGTH_LONG).show()
                    Log.d("msg","this is ${t.message}")

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

            Toast.makeText(this@FirstFloor, "Success", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
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
                        Toast.makeText(this@FirstFloor,"Success select place",Toast.LENGTH_LONG).show()
                    }
                    else if(response.code() == 401) {
                        Toast.makeText(this@FirstFloor,"you must select place",Toast.LENGTH_LONG).show()

                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@FirstFloor,"server error",Toast.LENGTH_LONG).show()
                }

            })

    }
    }

