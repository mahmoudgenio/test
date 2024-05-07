package com.example.testimages.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testimages.Api.LoginResponse
import com.example.testimages.Api.RetrofitService
import com.example.testimages.EncryptSharedPreference
import com.example.testimages.common.Message
import com.example.testimages.common.SingleLiveEvent
import com.example.testimages.ui.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "LoginViewModel"
class LoginViewModel : ViewModel() {

    val loginInf = MutableLiveData<LoginResponse>()
    val serverResponse = MutableLiveData("")
    val messageLiveData = SingleLiveEvent<Message>()
    val events = SingleLiveEvent<LoginViewEvent>()

    var myShared : SharedPreferences?=null

    val response: LiveData<String>
        get() = serverResponse

    val error: LiveData<String>
        get() = serverResponse


    fun upload(
        email: String,
        password: String,
        context: Context
    ) {
        RetrofitService.getInstance()
            .login(email,password)
        .enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                if (response.body() != null && response.isSuccessful) {
                    try {

                        if (response.code() == 200) {
                            //serverResponse.value = "uploaded"
                                Toast.makeText(context,"Success Login",Toast.LENGTH_LONG).show()
                            // Navigate to home

                            myShared = context.getSharedPreferences("myshared", 0)
                            var editor: SharedPreferences.Editor = myShared!!.edit()
                            editor.putString("tokenLogin", response.body()!!.token)
                            editor.commit()

                            EncryptSharedPreference().encryptToken(context,response.body()!!.token)

                            events.postValue(LoginViewEvent.NavigateToHome)



                        } else if(response.code() == 401) {
                            Toast.makeText(context,"Email And Password Not Valid",Toast.LENGTH_LONG).show()

                        }
                    } catch (e: Exception) {
                      //  connectionError.value = e.message.toString()
                        Toast.makeText(context,"Error exception",Toast.LENGTH_LONG).show()
                    }

                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show()

            }


        })

      }
    }


