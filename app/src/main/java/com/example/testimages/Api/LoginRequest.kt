package com.example.testimages.Api

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @field:SerializedName("Email")
    val email : String?=null,

    @field:SerializedName("Password")
    val password: String? = null
)
