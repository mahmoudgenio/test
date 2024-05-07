package com.example.testimages.ui.register


import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testimages.Api.RegisterResponse
import com.example.testimages.Api.RetrofitService
import com.example.testimages.common.Message
import com.example.testimages.common.SingleLiveEvent
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

private const val TAG = "RegisterViewModel"


class RegisterViewModel : ViewModel() {

    val connectionError = MutableLiveData("")
    val serverResponse = MutableLiveData("")
    val messageLiveData = SingleLiveEvent<Message>()
    val events = SingleLiveEvent<RegisterViewEvents>()
    val showLoading = MutableLiveData<Boolean>()


    val response: LiveData<String>
        get() = serverResponse

    val error: LiveData<String>
        get() = serverResponse

    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val userNameError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val phoneError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String?>()

    var myShared : SharedPreferences?=null

    val imagesError = MutableLiveData<String>()

    fun upload(
        userName: String,
        email: String,
        phone: String,
        password: String,
        list: List<Uri>,
        context: Context

    ) {

        showLoading.postValue(true)

        val imageParts = ArrayList<MultipartBody.Part>()
        for (i in list.indices) {
            val file = File(getRealPathFromUri(list[i], context))
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val imagePart = MultipartBody.Part.createFormData("images", file.name, requestFile)
            imageParts.add(imagePart)
        }

        RetrofitService.getInstance().register(
            MultipartBody.Part.createFormData("DisplayName", userName),
            MultipartBody.Part.createFormData("Email", email),
            MultipartBody.Part.createFormData("PhoneNumber", phone),
            MultipartBody.Part.createFormData("Password", password),
            imageParts
        ).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {


                Log.i(TAG, response.headers().toString())
                Log.i(TAG, response.toString())

                Log.i(TAG, "DONE")
                Log.i(TAG, response.body().toString())
                Log.i(TAG, response.message().toString())
                Log.i(TAG, response.code().toString())


                if (response.body() != null && response.isSuccessful) {

                    try {

                        if (response.code() == 200) {
                          //  showLoading.postValue(false)
                            //serverResponse.value = "uploaded"

                            Toast.makeText(context,"success",Toast.LENGTH_LONG).show()

                                    // Navigate to login
//                            myShared = context.getSharedPreferences("myshared", 0)
//                            var editor: SharedPreferences.Editor = myShared!!.edit()
//                            editor.putString("token", response.body()!!.token)
//                            editor.commit()

                                    events.postValue(RegisterViewEvents.NavigateToLogin)


//                                    myShared = context.getSharedPreferences("myshared", 0)
//                                    var editor: SharedPreferences.Editor = myShared!!.edit()
//                                    editor.putString("token", response.body()!!.token)
//                                    editor.commit()


                        } else if(response.code() == 400) {
                           // showLoading.postValue(false)
                            Toast.makeText(context,"Account is already Exist",Toast.LENGTH_LONG).show()
                        //connectionError.value = response.errorBody().toString()
                        }
                    } catch (e: Exception) {
                       // showLoading.postValue(false)
                        connectionError.value = e.message.toString()
                    }
                }

            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
              //  showLoading.postValue(false)
                Log.e(TAG, t.message.toString())
                //connectionError.value = t.message.toString()
                Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show()

            }
        })

    }

    private fun getRealPathFromUri(uri: Uri, context: Context): String {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val filePath = cursor?.getString(columnIndex!!)
        cursor?.close()
        return filePath!!
    }







}