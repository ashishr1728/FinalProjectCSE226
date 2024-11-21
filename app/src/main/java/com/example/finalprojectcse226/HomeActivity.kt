package com.example.finalprojectcse226

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class HomeActivity : AppCompatActivity() {
    private lateinit var img_complaint : ImageView
    private lateinit var img_resolved : ImageView
    private lateinit var img_lost : ImageView
    private lateinit var img_status : ImageView
    private lateinit var img_notification : ImageView
    private lateinit var img_feedback : ImageView
    private lateinit var img_setting : ImageView

    private lateinit var txt_complaint : TextView
    private lateinit var txt_resolved : TextView
    private lateinit var txt_lost : TextView
    private lateinit var txt_status : TextView
    private lateinit var txt_notification : TextView
    private lateinit var txt_feedback : TextView
    private lateinit var txt_setting : TextView

    private lateinit var home : ImageView
    private lateinit var feedback : ImageView
    private lateinit var add_complaint : ImageView
    private lateinit var profile : ImageView
    private lateinit var setting : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        img_complaint = findViewById(R.id.complaint_image)
        img_resolved = findViewById(R.id.resolved_image)
        img_lost = findViewById(R.id.lost_image)
        img_status = findViewById(R.id.status_image)
        img_notification = findViewById(R.id.Notification_image)
        img_feedback = findViewById(R.id.feedback_image)
        img_setting = findViewById(R.id.setting_image)

        txt_complaint = findViewById(R.id.complaint_text)
        txt_resolved = findViewById(R.id.resolved_text)
        txt_lost = findViewById(R.id.lost_text)
        txt_status = findViewById(R.id.status_text)
        txt_notification = findViewById(R.id.notification_text)
        txt_feedback = findViewById(R.id.feedback_text)
        txt_setting = findViewById(R.id.setting_text)

        home = findViewById(R.id.home)
        feedback = findViewById(R.id.feedback)
        add_complaint = findViewById(R.id.add_complaint)
        profile = findViewById(R.id.profile)
        setting = findViewById(R.id.setting)

        img_complaint.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        txt_complaint.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        img_resolved.setOnClickListener{
            val intent = Intent(this, ResolvedActivity::class.java)
            startActivity(intent)
        }
        txt_resolved.setOnClickListener{
            val intent = Intent(this, ResolvedActivity::class.java)
            startActivity(intent)
        }

        img_notification.setOnClickListener{
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        txt_notification.setOnClickListener{
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        img_setting.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        txt_setting.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        img_feedback.setOnClickListener{
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
        }

        txt_feedback.setOnClickListener{
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
        }

        home.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        add_complaint.setOnClickListener{
            val intent = Intent(this, ComplainActivity::class.java)
            startActivity(intent)
        }

        setting.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        feedback.setOnClickListener{
            val intent = Intent(this, FeebackReviewActivity::class.java)
            startActivity(intent)
        }

        profile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}