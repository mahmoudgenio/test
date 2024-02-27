package com.example.testimages.common

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import androidx.fragment.app.Fragment

fun Fragment.showDialog(message:String,
                        posActionName:String?=null,
                        posAction : DialogInterface.OnClickListener?=null,
                        NegActionName:String?=null,
                        NegAction : DialogInterface.OnClickListener?=null,

                        ): AlertDialog {
    val dialog = AlertDialog.Builder(context)
    dialog.setMessage(message)
    if(posActionName!=null){

        dialog.setPositiveButton(posActionName,posAction)
    }
    if (NegActionName!=null){

        dialog.setNegativeButton(NegActionName,NegAction)
    }
    return dialog.show()
}

fun Activity.showDialog(message:String,
                        posActionName:String?=null,
                        posAction : DialogInterface.OnClickListener?=null,
                        NegActionName:String?=null,
                        NegAction : DialogInterface.OnClickListener?=null,

                        ): AlertDialog {
    val dialog = AlertDialog.Builder(this)
    dialog.setMessage(message)
    if(posActionName!=null){

        dialog.setPositiveButton(posActionName,posAction)
    }
    if (NegActionName!=null){

        dialog.setNegativeButton(NegActionName,NegAction)
    }
    return dialog.show()
}


fun Activity.showLoadingProgressDialog(message:String,
                                       isCancelable:Boolean = true):AlertDialog{
    val alertDialog = ProgressDialog(this)
    alertDialog.setMessage(message)
    alertDialog.setCancelable(isCancelable)
    return alertDialog

}

fun Fragment.showLoadingProgressDialog(message:String,
                                       isCancelable:Boolean = true):AlertDialog{
    val alertDialog = ProgressDialog(context)
    alertDialog.setMessage(message)
    alertDialog.setCancelable(isCancelable)
    return alertDialog

}