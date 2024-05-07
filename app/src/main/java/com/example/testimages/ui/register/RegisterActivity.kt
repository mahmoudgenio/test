package com.example.testimages.ui.register

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.testimages.R
import com.example.testimages.common.showDialog
import com.example.testimages.databinding.ActivityRegisterBinding
import com.example.testimages.ui.login.LoginActivity
import kotlinx.coroutines.delay
import okhttp3.internal.notify

private const val TAG = "RegisterActivity"

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var viewBinding: ActivityRegisterBinding
    private lateinit var button1: Button
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var password: EditText
    private lateinit var signUp: Button
    private lateinit var pressLogin: TextView
    //private lateinit var progressBar: ProgressBar

    private val REQUEST_SELECT_IMAGES = 1

    private val imageUrls = ArrayList<Uri>()
    private val STORAGE_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        button1 = findViewById(R.id.btn1)
        signUp = findViewById(R.id.sign_up_btn)
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        password = findViewById(R.id.password)
      //  progressBar = findViewById(R.id.progressBar_SignUp)

        pressLogin = findViewById(R.id.press_login)

        photoPickerListener


        button1.setOnClickListener {
            checkPermissionAndOpenGallery()
        }


        pressLogin.setOnClickListener{

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        signUp.setOnClickListener {

            if (imageUrls.isEmpty()) {
                Toast.makeText(applicationContext, "Please select at least one Image", Toast.LENGTH_SHORT).show()
            }
            else if (!(valid())) return@setOnClickListener

            else{
                viewModel.upload(
                    name.text.toString(),
                    email.text.toString(),
                    phone.text.toString(),
                    password.text.toString(),
                    imageUrls,
                    applicationContext
                )
                val message: String? = "Congratulations"
                showCustomDialogCongrats(message)
                }


            }




        viewModel.events.observe(this){
            when(it){
                RegisterViewEvents.NavigateToLogin->{
                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }


        }

//                viewModel.showLoading.observe(this){ show->
//                    if(show)
//                        viewBinding.progressBarSignUp.isVisible = true
//                    else
//                        viewBinding.progressBarSignUp.isVisible = false
//                }




        viewModel.messageLiveData.observe(this){it->
            showDialog(message = it?.msg?:" wrong",
                posActionName = it.posActionName,
                posAction = { dialog: DialogInterface?, which: Int ->
                    dialog?.dismiss()
                })
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

    private val photoPickerListener = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(
            4
        )
    ) {
        if (it.isNotEmpty()) {
            imageUrls.addAll(it)
        } else {
            Toast.makeText(applicationContext, "PLease select at least one image", Toast.LENGTH_SHORT).show()
        }
    }



    private fun checkPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission already granted, open gallery
            photoPickerListener.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        } else {
            // Permission not granted, request permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                   Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_MEDIA_IMAGES
                ),
                STORAGE_PERMISSION_CODE
            )
        }
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, REQUEST_SELECT_IMAGES)
    }


//    private fun openGallery(buttonIndex: Int) {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//
//        startActivityForResult(intent, PICK_IMAGE_REQUEST + buttonIndex)
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open gallery
                openGallery()
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "No Permission Granted",
                    Toast.LENGTH_SHORT
                )
                    .show()
                // Permission denied, handle accordingly
                // ...
            }
        }
    }



    val emailPattern = Regex("[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+")
    val phonePattern = Regex("^\\+?0(10|11|12|15)\\d{8}$")
    val passwordPattern = Regex("^(?=.*[A-Z])(?=.*[@#\$%])(?=.*[a-zA-Z0-9]).{10,}$")

    private fun valid(): Boolean {

        var isValid = true

        if(name.text.toString().isNullOrBlank()){
            name.error = "please enter your name"
            isValid = false
        }
        else{
            name.error = null

        }

        if(email.text.toString().isNullOrBlank()){
            email.error = "please enter your email"
            isValid = false
        }
        else if(!(email.text.toString().matches(emailPattern))){

            email.error = "email must contain @"
            isValid = false
        }
        else{
            email.error = null

        }

        if(phone.text.toString().isNullOrBlank()){
            phone.error = "please enter your phone"
            isValid = false
        }
        else if(!(phone.text.toString().matches(phonePattern))){

            phone.error = "phone must contain egyptian numbers that include 11 digits"
            isValid = false
        }

        else{
            phone.error = null

        }


        if(password.text.toString().isNullOrBlank()){
            password.error = "please enter your password"
            isValid = false
        }
        else if(!(password.text.toString().matches(passwordPattern))){

            password.error = "password must contain at least one capital letter and @ or # or $ or %"
            isValid = false
        }

        else{
            password.error = null

        }


        return isValid
    }

    private fun showCustomDialogCongrats(message: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dailog_congrats)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)

        tvMessage.text = message

        dialog.show()
    }


}
