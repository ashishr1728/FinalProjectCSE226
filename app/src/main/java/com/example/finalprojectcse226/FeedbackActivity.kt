package com.example.finalprojectcse226

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class FeedbackActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelperComplaint
    private lateinit var back: ImageView

    // Define a unique ID for the notification
    private val CHANNEL_ID = "feedback_channel"
    private val NOTIFICATION_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        db = DatabaseHelperComplaint(this)

        val feedbackInput: EditText = findViewById(R.id.feedbackInput)
        val submitButton: Button = findViewById(R.id.submitButton)
        back = findViewById(R.id.back)

        // Create a notification channel (for Android 8.0+)
        createNotificationChannel()

        submitButton.setOnClickListener {
            val feedbackMessage = feedbackInput.text.toString().trim()
            if (feedbackMessage.isNotEmpty()) {
                // Insert feedback into the database
                db.insertFeedback(feedbackMessage)
                db.insertNotification("Feedback Submitted", "Your feedback has been recorded.")

                // Show a notification
                showNotification("Feedback Submitted", "Your feedback has been recorded.")

                Toast.makeText(this, "Feedback Submitted", Toast.LENGTH_SHORT).show()
                feedbackInput.text.clear()
                val intent = Intent(this, FeebackReviewActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter your feedback!", Toast.LENGTH_SHORT).show()
            }
        }

        back.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    // Create a notification channel (for Android 8.0+)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Feedback Channel"
            val descriptionText = "Channel for feedback notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Show a notification
    private fun showNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_dialog_info) // Replace with your notification icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}