package ui

import android.os.Bundle
import android.view.View
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
    var userList: UsersViewModel = UsersViewModel()

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
                        val user = userList.getUser(viewHolder.adapterPosition)
                        val delMessage = Snackbar.make(
                            viewHolder.itemView,
                            "${user.name} has deleted.",
                            Snackbar.LENGTH_LONG
                        )
                        userList.delete(viewHolder.adapterPosition)
                        binding.rvContacts.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                        binding.rvContacts.adapter?.notifyItemRangeChanged(
                            viewHolder.adapterPosition,
                            binding.rvContacts.adapter!!.itemCount
                        )
                        backUser(user, delMessage, viewHolder.adapterPosition)
                        delMessage.show()
                    }
                }
            }
        }

    /**Method back to list of contacts deleted contact if user push "Cancel" on the Snackbar.*/
    private fun backUser(user: User, delMessage: Snackbar, position: Int) {
        delMessage.setAction("Cancel", View.OnClickListener() {
            userList.add(user.id, user)
            binding.rvContacts.adapter?.notifyItemRangeChanged(
                position,
                binding.rvContacts.adapter!!.itemCount
            )
        })
    }
}