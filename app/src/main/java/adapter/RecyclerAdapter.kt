package com.example.level2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.level2.R
import com.example.level2.extension.addImage
import com.example.level2.model.User
import com.example.level2.model.UsersViewModel


class RecyclerAdapter() : RecyclerView
.Adapter<RecyclerAdapter.MyViewHolder>(), View.OnClickListener {

    private val userList: UsersViewModel = UsersViewModel()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameField: TextView = itemView.findViewById(R.id.tv_name)
        val careerField: TextView = itemView.findViewById(R.id.tv_career)
        val userPhoto: ImageView = itemView.findViewById(R.id.iv_user_photo)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.img_btn_trash_can)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameField.text = userList.getUser(position).name
        holder.careerField.text = userList.getUser(position).career
        holder.userPhoto.addImage(userList.getUser(position))
        holder.deleteBtn.tag = userList.getUser(position)
        userList.getUser(position).id = holder.adapterPosition
        holder.deleteBtn.setOnClickListener(this)
    }

    override fun getItemCount() = userList.size()

    override fun onClick(v: View?) {
        val user = v?.tag as User
        val context = v?.context
        when (v?.id) {
            R.id.img_btn_trash_can -> {
                userList.delete(user.id)
                notifyItemRemoved(user.id)
                notifyItemRangeChanged(user.id, userList.size())
                Toast.makeText(context, "${user.name} has deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}