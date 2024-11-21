package com.example.finalprojectcse226

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalprojectcse226.DatabaseHelperlogin
import com.example.finalprojectcse226.MainActivity
import com.example.finalprojectcse226.R
import com.example.finalprojectcse226.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelperlogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelperlogin(this)
        binding.loginButton.setOnClickListener {
            val username = binding.loginName.text.toString()
            val password = binding.loginPassword.text.toString()
            loginDatabase(username, password)
        }
        binding.loginRedirect.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun loginDatabase(username: String, password: String){
        val userExists =  databaseHelper.readUser(username, password)
        if (userExists){
            Toast.makeText(this,"Login Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this,"Login Failed", Toast.LENGTH_SHORT).show()
        }
    }
}