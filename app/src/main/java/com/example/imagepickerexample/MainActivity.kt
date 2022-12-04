package com.example.imagepickerexample

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64

import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import com.example.imagepickerexample.databinding.ActivityMainBinding
import java.io.IOException
import java.util.Base64.getEncoder


class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding

    val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {

            var response = getBase64ForUriAndPossiblyCrash(uri)

            Log.i("info_deployment", response)
        } else {
            Log.i("info_deployment", "No seleccionado")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSend.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

    }

    private fun getBase64ForUriAndPossiblyCrash(uri: Uri): String {

        try {
            val bytes = contentResolver.openInputStream(uri)?.readBytes()

            return Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (error: IOException) {
            error.printStackTrace() // This exception always occurs
            return "ha ocurrido un error"
        }


    }
}