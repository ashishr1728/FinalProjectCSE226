package com.example.finalprojectcse226

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ProfileActivity : AppCompatActivity() {
    private lateinit var back: ImageView
    private lateinit var image: ImageView
    private lateinit var share: ImageView
    private lateinit var change: TextView
    private lateinit var edit: Button

    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        back = findViewById(R.id.back)
        change = findViewById(R.id.change)
        image = findViewById(R.id.image)
        val username = findViewById<TextView>(R.id.username)
        val email = findViewById<TextView>(R.id.email)
        val mobile = findViewById<TextView>(R.id.mobile)
        val address = findViewById<TextView>(R.id.adress)
        val gender = findViewById<TextView>(R.id.gender)
        val dob = findViewById<TextView>(R.id.dob)
        val age = findViewById<TextView>(R.id.age)
        val city = findViewById<TextView>(R.id.city)
        val state = findViewById<TextView>(R.id.state)
        val country = findViewById<TextView>(R.id.country)
        share = findViewById(R.id.share)
        edit = findViewById(R.id.edit)

        username.text = intent.getStringExtra("username")?.takeIf { it.isNotEmpty() } ?: username.text
        email.text = intent.getStringExtra("email")?.takeIf { it.isNotEmpty() } ?: email.text
        mobile.text = intent.getStringExtra("mobile")?.takeIf { it.isNotEmpty() } ?: mobile.text
        address.text = intent.getStringExtra("address")?.takeIf { it.isNotEmpty() } ?: address.text
        gender.text = intent.getStringExtra("gender")?.takeIf { it.isNotEmpty() } ?: gender.text
        dob.text = intent.getStringExtra("dob")?.takeIf { it.isNotEmpty() } ?: dob.text
        age.text = intent.getStringExtra("age")?.takeIf { it.isNotEmpty() } ?: age.text
        city.text = intent.getStringExtra("city")?.takeIf { it.isNotEmpty() } ?: city.text
        state.text = intent.getStringExtra("state")?.takeIf { it.isNotEmpty() } ?: state.text
        country.text = intent.getStringExtra("country")?.takeIf { it.isNotEmpty() } ?: country.text

        change.setOnClickListener {
            openGallery()
        }

        image.setOnClickListener {
            openGallery()
        }

        share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            startActivity(
                Intent.createChooser(intent, "Share Via")
                    .putExtra("username", username.text.toString())
                    .putExtra("email", email.text.toString())
                    .putExtra("mobile", mobile.text.toString())
                    .putExtra("address", address.text.toString())
                    .putExtra("gender", gender.text.toString())
                    .putExtra("dob", dob.text.toString())
                    .putExtra("age", age.text.toString())
                    .putExtra("city", city.text.toString())
                    .putExtra("state", state.text.toString())
                    .putExtra("country", country.text.toString())
            )
        }

        edit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        back.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            try {
                // Display the image in the ImageView
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                image.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}