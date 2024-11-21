package com.example.finalprojectcse226

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectcse226.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DatabaseHelperComplaint
    private lateinit var complaintAdapter: ComplaintAdapter
    private lateinit var refresh: ImageButton
    lateinit var addFab: FloatingActionButton
    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        back = findViewById(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        addFab = findViewById(R.id.idFABAdd)
        refresh = findViewById(R.id.refresh)

        // Initialize database and adapter
        db = DatabaseHelperComplaint(this)
        complaintAdapter = ComplaintAdapter(db.getAllComplaints(), this)

        // Set up RecyclerView
        binding.complaintRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.complaintRecyclerView.adapter = complaintAdapter

        // Add Complaint FAB click listener
        addFab.setOnClickListener {
            val intent = Intent(this, ComplainActivity::class.java)
            startActivity(intent)
        }

        // Refresh button click listener
        refresh.setOnClickListener {
            refreshData()
        }
    }
    fun resolveComplaint(complaintId: Int, subject: String) {
        db.moveComplaintToResolved(db.getComplaintById(complaintId)!!) // Move to resolved
        db.deleteComplaint(complaintId) // Remove from active complaints

        // Show and save resolved notification
        showNotificationAndSave("Complaint Resolved", "Complaint on '$subject' has been resolved.")
        Toast.makeText(this, "Complaint marked as resolved", Toast.LENGTH_SHORT).show()
        refreshData() // Update the UI
    }

    private fun showNotificationAndSave(title: String, message: String) {
        val channelId = "complaint_channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Complaint Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_dialog_info) // Replace with your app's icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(1, builder.build())

        // Save the notification in the database
        db.insertNotification(title, message)
    }


    override fun onResume() {
        super.onResume()
        // Refresh data whenever the activity resumes
        refreshData()
    }

    // Function to refresh data
    private fun refreshData() {
        val complaints = db.getAllComplaints() // Fetch updated data from the database
        complaintAdapter.refreshData(complaints) // Notify adapter of the new data
    }
}
