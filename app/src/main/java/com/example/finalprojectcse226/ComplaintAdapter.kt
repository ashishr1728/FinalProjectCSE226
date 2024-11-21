package com.example.finalprojectcse226

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ComplaintAdapter(private var complaintList: List<Complaint>,context : Context) : RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>() {

    // ViewHolder class for holding the views for each list item
    private val db : DatabaseHelperComplaint = DatabaseHelperComplaint(context)
    class ComplaintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val resolveButton: Button = itemView.findViewById(R.id.resolved)
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val regNoTextView: TextView = itemView.findViewById(R.id.textViewRegNo)
        val phoneNoTextView: TextView = itemView.findViewById(R.id.textViewPhoneNo)
        val subTextView: TextView = itemView.findViewById(R.id.textViewSub)
        val descTextView: TextView = itemView.findViewById(R.id.textViewDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_complaint, parent, false)
        return ComplaintViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {
        val complaint = complaintList[position]
        holder.nameTextView.text = complaint.Name
        holder.regNoTextView.text = complaint.Reg_No
        holder.phoneNoTextView.text = complaint.Phone_No
        holder.subTextView.text = complaint.Sub
        holder.descTextView.text = complaint.Desc

        holder.deleteButton.setOnClickListener {
            db.deleteComplaint(complaint.id) // Delete from the current table
            refreshData(db.getAllComplaints())
            Toast.makeText(holder.itemView.context, "Complaint Deleted", Toast.LENGTH_SHORT).show()
        }

        // Handle the "Resolved" button
        holder.resolveButton.setOnClickListener {
            db.moveComplaintToResolved(complaint) // Add to Resolved table
            db.deleteComplaint(complaint.id) // Remove from Complaints table

            // Show notification and save to database
            val title = "Complaint Resolved"
            val message = "Complaint by ${complaint.Name} has been resolved."
            showNotificationAndSave(holder.itemView.context, title, message)

            refreshData(db.getAllComplaints())
            Toast.makeText(holder.itemView.context, "Complaint Resolved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showNotificationAndSave(context: Context, title: String, message: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        val channelId = "complaint_resolved_channel"

        // Create notification channel for Android 8.0+ (API 26+)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                channelId,
                "Complaint Notifications",
                android.app.NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = androidx.core.app.NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_dialog_info) // Replace with your app's icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)

        // Save notification in the database
        db.insertNotification(title, message)
    }



    override fun getItemCount(): Int {
        return complaintList.size
    }

    fun refreshData(allComplaints: List<Complaint>) {
        complaintList = allComplaints
        notifyDataSetChanged()
    }
}