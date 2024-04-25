package com.example.wpms.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wpms.Model.Patient
import com.example.wpms.R

class UserAdapter(private var patients: List<Patient>, private val clickListener: (Patient) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tvName)
        val roomTextView: TextView = view.findViewById(R.id.tvRoomNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val patient = patients[position]
        holder.nameTextView.text = patient.name
        holder.roomTextView.text = patient.room
        holder.view.setOnClickListener { clickListener(patient) }
    }

    override fun getItemCount() = patients.size

    fun setData(newPatients: List<Patient>) {
        patients = newPatients
        notifyDataSetChanged()
    }
}
