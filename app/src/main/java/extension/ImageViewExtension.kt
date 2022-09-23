package com.example.level2.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.level2.model.User

fun ImageView.addImage(user: User) {
    Glide.with(this.context)
        .load(user.photo)
        .into(this)
}

fun ImageView.addImage(image:String){
    Glide.with(this).load(image).into(this)
}