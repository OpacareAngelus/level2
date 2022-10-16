package ui

import adapter.RecyclerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level2.databinding.MyContactsBinding
import com.google.android.material.snackbar.Snackbar
import model.User
import model.UsersViewModel

class MyContactsActivity : AppCompatActivity() {

    private lateinit var binding: MyContactsBinding
    var userList: UsersViewModel = UsersViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MyContactsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.rvContacts
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(userList, )

        ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView)

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
        userList.add(user)
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
                            "${user?.name} has deleted.",
                            Snackbar.LENGTH_LONG
                        )
                        userList.delete(viewHolder.adapterPosition)
                        binding.rvContacts.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                        binding.rvContacts.adapter?.notifyItemRangeChanged(
                            viewHolder.adapterPosition,
                            binding.rvContacts.adapter!!.itemCount
                        )
                        undoUserDeletion(user, delMessage, viewHolder.adapterPosition)
                        delMessage.show()
                    }
                }
            }
        }

    /**Method back to list of contacts deleted contact if user push "Cancel" on the Snackbar.*/
    private fun undoUserDeletion(user: User?, delMessage: Snackbar, position: Int) {
        delMessage.setAction("Cancel") {
            userList.add(user)
            binding.rvContacts.adapter?.notifyItemRangeChanged(
                position,
                binding.rvContacts.adapter!!.itemCount
            )
        }
    }
}