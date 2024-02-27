package com.example.testimages

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

//@Parcelize
//data class UltrasonicResponse(
//
//	@field:SerializedName("UltrasonicResponse")
//	val ultrasonicResponse: List<UltrasonicResponseItem?>? = null
//) : Parcelable

@Parcelize
data class UltrasonicResponseItem(

	@field:SerializedName("readingStatus")
	val readingStatus: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
