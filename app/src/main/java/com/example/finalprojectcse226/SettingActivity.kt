package com.example.finalprojectcse226

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class SettingActivity : AppCompatActivity() {
    private lateinit var setting_profile : TextView
    private lateinit var setting_notification : TextView
    private lateinit var setting_feedback : TextView
    private lateinit var contact : TextView
    private lateinit var about : TextView
    private lateinit var privacy : TextView
    private lateinit var setting_logout : TextView
    private lateinit var back : ImageView
    private lateinit var profile_image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setting_profile = findViewById(R.id.setting_profile)
        setting_notification = findViewById(R.id.setting_notification)
        setting_feedback = findViewById(R.id.setting_feedback)
        contact = findViewById(R.id.contact)
        about = findViewById(R.id.about)
        privacy = findViewById(R.id.privacy)
        setting_logout = findViewById(R.id.setting_logout)
        back = findViewById(R.id.back)
        profile_image = findViewById(R.id.profile_image)


        profile_image.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        setting_profile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        back.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        setting_notification.setOnClickListener{
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
        setting_logout.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        about.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://connect.punjab.gov.in/about-us"))
            startActivity(intent)
        }
        privacy.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://escavo.com/privacy-policy/"))
            startActivity(intent)
        }
        contact.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+919328046344")
            startActivity(intent)
        }
        setting_feedback.setOnClickListener{
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
        }
    }
}