//package com.example.wpms.Model
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.example.wpms.Entities.PressureData
//import com.example.wpms.R
//
////fingers crossed pressureviewholder error will leave once implemented with no errors
//class PressureListAdapter : ListAdapter<PressureData, PressureViewHolder>(PressureComparator()){
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PressureViewHolder {
//        return PressureViewHolder.create(parent)
//    }
//
//    override fun onBindViewHolder(holder: PressureViewHolder, position: Int) {
//        val current = getItem(position)
//        //not sure where this error is coming from
//        holder.bind(current.pressure)
//    }
//
//    class PressureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        //this also needs to change depending on right id name
//        private val pressureItemView: TextView = itemView.findViewById(R.id.testPressure)
//
//        fun bind(text: String?) {
//            pressureItemView.text = text
//        }
//
//        companion object {
//            fun create(parent: ViewGroup): PressureViewHolder {
//                val view: View = LayoutInflater.from(parent.context)
//                    //this need to change depending on layout tag of xml
//                    .inflate(R.layout.recyclerview_item, parent, false)
//                return PressureViewHolder(view)
//            }
//        }
//    }
//
//    class PressureComparator : DiffUtil.ItemCallback<PressureData>() {
//        override fun areItemsTheSame(oldItem: PressureData, newItem: PressureData): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: PressureData, newItem: PressureData): Boolean {
//            return oldItem.pressureValue == newItem.pressureValue
//        }
//    }
//}