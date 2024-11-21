package com.example.finalprojectcse226

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.widget.Toast
import com.example.finalprojectcse226.databinding.ActivityComplainBinding

class ComplainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityComplainBinding
    private lateinit var db: DatabaseHelperComplaint

    companion object {
        const val CHANNEL_ID = "complaint_channel"
        const val NOTIFICATION_ID = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create Notification Channel
        createNotificationChannel()

        db = DatabaseHelperComplaint(this)
        binding.buttonSubmit.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val regNo = binding.editTextRegNumber.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val sub = binding.editTextSubject.text.toString()
            val desc = binding.editTextDescription.text.toString()

            if (name.isNotEmpty() && regNo.isNotEmpty() && phone.isNotEmpty() && sub.isNotEmpty() && desc.isNotEmpty()) {
                val complaint = Complaint(0, name, regNo, phone, sub, desc)
                db.insertNote(complaint)

                // Show and save notification
                showNotificationAndSave("Complaint Submitted", "Your complaint on '$sub' has been added.")
                Toast.makeText(this, "Complaint Submitted", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Complaint Notifications"
            val descriptionText = "Notifications for submitted complaints"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun markComplaintAsResolved(complaintId: Int) {
        val complaint = db.getComplaintById(complaintId)
        complaint?.let {
            db.moveComplaintToResolved(it) // Move to resolved table
            db.deleteComplaint(complaintId) // Remove from unresolved table

            // Show notification and save it
            val title = "Complaint Resolved"
            val message = "Complaint with ID: ${it.id} has been resolved."
            showNotificationAndSave(title, message)
        }
    }

    private fun showNotificationAndSave(title: String, message: String) {
        // Show notification
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }

        // Save notification to database
        db.insertNotification(title, message)
    }
}
