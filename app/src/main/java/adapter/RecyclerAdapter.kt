package com.example.level2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.level2.R
import com.example.level2.model.UserData

class RecyclerAdapter() : RecyclerView
.Adapter<RecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameField: TextView = itemView.findViewById(R.id.tv_name)
        val careerField: TextView = itemView.findViewById(R.id.tv_career)
        val userPhoto: ImageView = itemView.findViewById(R.id.iv_user_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameField.text = UserData.getUsers()[position].name
        holder.careerField.text = UserData.getUsers()[position].career
//        holder.userPhoto.background =
    }

    override fun getItemCount() = UserData.getSize()
}