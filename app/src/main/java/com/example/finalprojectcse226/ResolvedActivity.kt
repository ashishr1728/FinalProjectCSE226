package com.example.finalprojectcse226

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectcse226.databinding.ActivityResolvedBinding

class ResolvedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResolvedBinding
    private lateinit var db: DatabaseHelperComplaint
    private lateinit var resolvedAdapter: ResolvedAdapter
    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize binding for activity_resolved.xml
        binding = ActivityResolvedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Clear the existing database (for testing purposes only)
//        this.deleteDatabase(DatabaseHelperComplaint.DATABASE_NAME)

        // Initialize database helper
        db = DatabaseHelperComplaint(this)
        back = findViewById(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // Fetch resolved complaints from the database
        val resolvedComplaints = db.getAllResolvedComplaints()

        // Set up RecyclerView with the resolved complaints
        resolvedAdapter = ResolvedAdapter(resolvedComplaints, this) // Pass the context
        binding.resolvedRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.resolvedRecyclerView.adapter = resolvedAdapter


    }
}
