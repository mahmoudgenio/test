package com.example.testimages.ui.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.testimages.GroundFloor
import com.example.testimages.R
import com.example.testimages.databinding.ActivityLoginBinding
import com.example.testimages.ui.HomeActivity
import com.example.testimages.ui.register.RegisterActivity


class LoginActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel


    var myShared: SharedPreferences? = null


    private lateinit var email: EditText
    private lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this


        email = findViewById(R.id.login_email)
        password = findViewById(R.id.login_password)

        viewBinding.pressSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }


       viewModel.loginInf.observe(this){
           val name = it.displayName
           val intent = Intent(this@LoginActivity, HomeActivity::class.java)
           intent.putExtra("displayName", name)
           startActivity(intent)
       }


        viewBinding.logInBtn.setOnClickListener {

            if (!(valid())) return@setOnClickListener

            viewModel.upload(email.text.toString(), password.text.toString(), applicationContext)

        }
        viewBinding.backToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }


        viewModel.events.observe(this) {
            when (it) {
                LoginViewEvent.NavigateToHome -> {
                    intent = Intent(this, HomeActivity::class.java)
                  //  intent = Intent(this, GroundFloor::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }





        viewModel.response.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }

        }

        viewModel.error.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }


    }
        fun valid(): Boolean {

            var isValid = true

            if (email.text.toString().isNullOrBlank()) {
                email.error = "Your Email Not Valid"
                isValid = false
            } else {

                email.error = null
            }

            if (password.text.toString().isNullOrBlank()) {
                password.error = "Your Password Not Valid"
                isValid = false
            } else {
                password.error = null
            }


            return isValid
        }



        // ====================================================

//    fun signIn(email:String , password:String){
//        val retrofit = Retrofit.Builder().baseUrl("http://theftparking-001-site1.etempurl.com/")
//            .addConverterFactory(GsonConverterFactory.create()).build()
//
//        val api:RetrofitService = retrofit.create(RetrofitService::class.java)
//        val call = api.login(email,password)
//        call.enqueue(object :Callback<LoginResponse>{
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                if (response.body() != null && response.isSuccessful) {
//                    try {
//
//                        if (response.code() == 200) {
//                            //serverResponse.value = "uploaded"
//                            Toast.makeText(this@LoginActivity,"Success Login",Toast.LENGTH_LONG).show()
//
//                            // Navigate to home
//                            val intent = Intent(this@LoginActivity , HomeActivity::class.java)
//                            startActivity(intent)
//                            finish()
//
//                            myShared = getSharedPreferences("myshared", 0)
//                            var editor: SharedPreferences.Editor = myShared!!.edit()
//                            editor.putString("token", response.body()!!.token)
//                            editor.commit()
//
//
//
//                        } else {
//
//                            connectionError.value = response.errorBody().toString()
//                        }
//                    } catch (e: Exception) {
//                        connectionError.value = e.message.toString()
//                    }
//                }
//
//            }
//
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                Toast.makeText(this@LoginActivity , "Server Error",Toast.LENGTH_LONG)
//                    .show()
//            }
//
//        })
//    }

    }

