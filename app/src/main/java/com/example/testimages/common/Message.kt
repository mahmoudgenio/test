package com.example.testimages.common

data class Message(
    val msg:String?=null,
    val posActionName:String?=null,
    val posActionClick:OnDialogActionClick?=null,
    val negActionName:String?=null,
    val negActionClick:OnDialogActionClick?=null,
    val isCancelable:Boolean = true

)

fun interface OnDialogActionClick{
    fun onDialogActionClick()

}
