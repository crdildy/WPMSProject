package com.example.wpms.Adapter// com.example.wpms.Adapter.PatientAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.wpms.Model.User
import com.example.wpms.R

class UserAdapter(private var users: List<User>, private val onUserClick: (User) -> Unit) : RecyclerView.Adapter<UserAdapter.PatientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return PatientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val currentUser = users[position]
        holder.nameTextView.text = currentUser.name
        holder.itemView.setOnClickListener {
            onUserClick(currentUser)
        }
    }

    override fun getItemCount() = users.size

    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tvName)
    }

    fun setData(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
}
