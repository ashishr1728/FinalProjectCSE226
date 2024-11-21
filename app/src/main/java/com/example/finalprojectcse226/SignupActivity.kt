package com.example.finalprojectcse226

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalprojectcse226.DatabaseHelperlogin
import com.example.finalprojectcse226.R
import com.example.finalprojectcse226.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var databaseHelper: DatabaseHelperlogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelperlogin(this)
        binding.signupButton.setOnClickListener {
            val username = binding.signupName.text.toString()
            val password = binding.signupPassword.text.toString()
            signupDatabase(username, password)
        }
        binding.loginRedirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
    private fun signupDatabase(username: String, password: String){
        val insertedRowId = databaseHelper.insertData(username, password)
        if (insertedRowId != -1L) {
            Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Signup failed", Toast.LENGTH_SHORT).show()
        }

    }
}