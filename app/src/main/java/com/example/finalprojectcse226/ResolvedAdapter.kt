package com.example.finalprojectcse226

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResolvedAdapter(private var resolvedList: List<Complaint>, context: Context) : RecyclerView.Adapter<ResolvedAdapter.ResolvedViewHolder>() {

    class ResolvedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val regNoTextView: TextView = itemView.findViewById(R.id.textViewRegNo)
        val phoneNoTextView: TextView = itemView.findViewById(R.id.textViewPhoneNo)
        val subTextView: TextView = itemView.findViewById(R.id.textViewSub)
        val descTextView: TextView = itemView.findViewById(R.id.textViewDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResolvedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_resolved, parent, false)
        return ResolvedViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResolvedViewHolder, position: Int) {
        val complaint = resolvedList[position]
        holder.nameTextView.text = complaint.Name
        holder.regNoTextView.text = complaint.Reg_No
        holder.phoneNoTextView.text = complaint.Phone_No
        holder.subTextView.text = complaint.Sub
        holder.descTextView.text = complaint.Desc
    }

    override fun getItemCount(): Int {
        return resolvedList.size
    }
}
