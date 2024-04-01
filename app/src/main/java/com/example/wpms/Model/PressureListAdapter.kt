//package com.example.wpms.Model
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.ListAdapter
//import com.example.wpms.Entities.PressureData
//
//
//class PressureListAdapter : ListAdapter<PressureData, PressureViewHolder>(PressureComparator()){
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PressureViewHolder {
//        return PressureViewHolder.create(parent)
//    }
//
//    override fun onBindViewHolder(holder: PressureViewHolder, position: Int) {
//        val current = getItem(position)
//        holder.bind(current.pressure)
//    }
//
//    class PressureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val pressureItemView: TextView = itemView.findViewById.(R.id.textView)
//
//        fun bind(text: String?) {
//            pressureItemView.text = text
//        }
//
//        companion object {
//            fun create(parent: ViewGroup): PressureViewHolder {
//                val view: View = LayoutInflater.from(parent.context)
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
//            return oldItem.pressure == newItem.pressure
//        }
//    }
//}