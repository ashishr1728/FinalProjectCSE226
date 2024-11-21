package com.example.finalprojectcse226

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val back = findViewById<ImageView>(R.id.back)
        val username = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)
        val mobile = findViewById<EditText>(R.id.mobile)
        val address = findViewById<EditText>(R.id.adress)
        val gender = findViewById<EditText>(R.id.gender)
        val dob = findViewById<EditText>(R.id.dob)
        val age = findViewById<EditText>(R.id.age)
        val city = findViewById<EditText>(R.id.city)
        val state = findViewById<EditText>(R.id.state)
        val country = findViewById<EditText>(R.id.country)
        val update = findViewById<Button>(R.id.update)

        back.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        update.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java)
                .putExtra("username",username.text.toString())
                .putExtra("email",email.text.toString())
                .putExtra("mobile",mobile.text.toString())
                .putExtra("address",address.text.toString())
                .putExtra("gender",gender.text.toString())
                .putExtra("dob",dob.text.toString())
                .putExtra("age",age.text.toString())
                .putExtra("city",city.text.toString())
                .putExtra("state",state.text.toString())
                .putExtra("country",country.text.toString()))
        }
    }
}