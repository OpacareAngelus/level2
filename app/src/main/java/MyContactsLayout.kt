package com.example.level2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level2.adapter.RecyclerAdapter
import com.example.level2.model.UsersViewModel

class MyContactsLayout : AppCompatActivity() {

    private lateinit var adapter: RecyclerAdapter
    private lateinit var binding: MyContactsLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_contacts)
        val recyclerView: RecyclerView = findViewById(R.id.rv_contacts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter()
    }


//    private fun getCatList(): List<String> {
//        return this.resources.getStringArray(R.array.cat_names).toList()
//    }
}