package ui

import adapter.RecyclerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.level2.databinding.MyContactsBinding
import com.google.android.material.snackbar.Snackbar
import model.User
import model.UsersViewModel

class MyContactsActivity : AppCompatActivity() {

    private lateinit var binding: MyContactsBinding
    var viewmodel: UsersViewModel = UsersViewModel()

    private val usersAdapter by lazy {
        RecyclerAdapter(
            viewmodel,
            onDeleteUser = { user ->
                viewmodel.userListLiveData.value?.remove(user)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.rvContacts
        recyclerView.adapter = usersAdapter

        ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView)

        binding.tvAddContact.setOnClickListener {
            DialogFragmentAddContact().apply {
                show(
                    supportFragmentManager,
                    DialogFragmentAddContact.TAG
                )
            }
        }

        setObservers()
    }

    private fun setObservers() {
        viewmodel.userListLiveData.observe(this) { users ->
            usersAdapter.submitList(users.toMutableList())
        }
    }

    fun onContactSave(user: User) {
        viewmodel.userListLiveData.value?.add(user)
        usersAdapter.notifyItemRangeChanged(0, usersAdapter.itemCount)
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
                        val user = viewmodel.getUser(viewHolder.adapterPosition)
                        val delMessage = Snackbar.make(
                            viewHolder.itemView,
                            "${user?.name} has deleted.",
                            Snackbar.LENGTH_LONG
                        )
                        viewmodel.userListLiveData.value?.removeAt(viewHolder.adapterPosition)
                        usersAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                        usersAdapter.notifyItemRangeChanged(
                            viewHolder.adapterPosition,
                            usersAdapter.itemCount
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
            user?.let { it1 -> viewmodel.userListLiveData.value?.add(it1) }
            usersAdapter.notifyItemRangeChanged(
                position,
                usersAdapter.itemCount
            )
        }
    }
}