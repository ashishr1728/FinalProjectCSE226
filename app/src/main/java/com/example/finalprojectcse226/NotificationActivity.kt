package com.example.finalprojectcse226

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelperComplaint
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var back: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        db = DatabaseHelperComplaint(this)
        back = findViewById(R.id.back)

        // Retrieve notifications
        val notifications = db.getAllNotifications()
        Log.d("Notifications", "Retrieved notifications: $notifications")

        // Bind to RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.notificationRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        notificationAdapter = NotificationAdapter(notifications)
        recyclerView.adapter = notificationAdapter

        back.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

}
