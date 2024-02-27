package com.example.testimages.Api


import com.example.testimages.UltrasonicResponseItem
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface RetrofitService {

    @Multipart
    @POST("api/Accounts/Register")
    fun register(
        @Part name: MultipartBody.Part,
        @Part email: MultipartBody.Part,
        @Part phone: MultipartBody.Part,
        @Part password: MultipartBody.Part,
        @Part images: List<MultipartBody.Part>,
    ): Call<RegisterResponse>


    @FormUrlEncoded
    @POST("api/Accounts/Login")
    fun login(
        @Field("Email") email : String,
        @Field("Password") password : String
    ):Call<LoginResponse>



    @GET("api/Ultrasonics")
    fun sendToken(
        @Header("Authorization") authToken: String
    ):Call<UltrasonicResponseItem>


    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val client = OkHttpClient()

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://theftparking.somee.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }

}