package com.example.finalprojectcse226

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FeebackReviewActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelperComplaint
    private lateinit var back:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeback_review)

        back = findViewById(R.id.back)

        db = DatabaseHelperComplaint(this)
        val feedbackList = db.getAllFeedback()
        val feedbackTextView: TextView = findViewById(R.id.feedbackTextView)

        feedbackTextView.text = feedbackList.joinToString("\n\n") { "- $it" }

        back.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
