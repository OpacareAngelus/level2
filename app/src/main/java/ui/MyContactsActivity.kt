package ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level2.adapter.RecyclerAdapter
import com.example.level2.databinding.MyContactsBinding
import com.example.level2.model.User
import com.example.level2.model.UsersViewModel
import com.google.android.material.snackbar.Snackbar

open class MyContactsActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerAdapter
    private lateinit var binding: MyContactsBinding
    public var userList: UsersViewModel = UsersViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Binding
        binding = MyContactsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //Create RecyclerView
        val recyclerView: RecyclerView = binding.rvContacts
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(userList)
        //ItemTouch
        ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView)
        //Listener
        setListeners()
    }

    private fun setListeners() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.tvAddContact.setOnClickListener {
            val myDialogFragment = DialogFragmentAddContact()
            val manager = supportFragmentManager
            myDialogFragment.apply { show(manager, "myDialog") }
        }
    }

    fun onContactSave(user: User) {
        userList.add(userList.size(), user)
        binding.rvContacts.adapter?.notifyItemRangeChanged(
            0,
            binding.rvContacts.adapter!!.itemCount
        )
    }

    private var simpleCallback =
        object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        Snackbar.make(
                            viewHolder.itemView,
                            "${userList.getUser(viewHolder.adapterPosition).name} has deleted.",
                            Snackbar.LENGTH_LONG
                        )
                            .show()
                        userList.delete(viewHolder.adapterPosition)
                        binding.rvContacts.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                        binding.rvContacts.adapter?.notifyItemRangeChanged(
                            viewHolder.adapterPosition,
                            binding.rvContacts.adapter!!.itemCount
                        )
                    }
                }
            }
        }
}