package com.example.testimages.Api


import com.example.testimages.UltrasonicResponseItem
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level




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


    @FormUrlEncoded
    @POST("api/Ultrasonics/SelectPlace")
    fun selectPlace(
        @Field("Id") id : Int,
        @Field("Token") tokenPlace : String
    ):Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/Accounts/Logout")
    fun logout(
        @Field("Authorization") authToken : String?
    ):Call<ResponseBody>



    @GET("api/Ultrasonics")
    fun sendToken(
        @Header("Authorization") authToken: String
    ):Call<List<UltrasonicResponseItem>>




    companion object {

        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
               // val client = OkHttpClient()
                val logging = HttpLoggingInterceptor()
                logging.setLevel(Level.BODY)

                val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
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