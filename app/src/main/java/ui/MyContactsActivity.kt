package ui

import adapter.RecyclerAdapterUserContacts
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.level2.databinding.MyContactsBinding
import model.User
import model.UsersViewModel
import util.MySimpleCallBack

class MyContactsActivity : AppCompatActivity() {

    private lateinit var binding: MyContactsBinding
    private val viewmodel: UsersViewModel by viewModels()

    private val usersAdapter by lazy {
        RecyclerAdapterUserContacts(
            viewmodel,
            onDeleteUser = { user ->
                viewmodel.userListLiveData.value?.remove(user)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MyContactsBinding.inflate(layoutInflater)
        setObservers()
        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.rvContacts
        recyclerView.adapter = usersAdapter

        ItemTouchHelper(
            MySimpleCallBack(
                viewmodel,
                usersAdapter
            ).simpleCallback
        ).attachToRecyclerView(recyclerView)

        binding.tvAddContact.setOnClickListener {
            DialogFragmentAddContact().apply {
                show(
                    supportFragmentManager,
                    DialogFragmentAddContact.TAG
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        println("Show me usersAdapter.currentList onPause this activity: ${usersAdapter.currentList}")
    }

    private fun setObservers() {
        viewmodel.userListLiveData.observe(this) {
            println("Show me usersAdapter.currentList before submitList ${usersAdapter.currentList}")
            usersAdapter.submitList(viewmodel.userListLiveData.value)
            println("Show me usersAdapter.currentList after submitList ${usersAdapter.currentList}")
        }
    }

    fun onContactSave(user: User) {
        viewmodel.userListLiveData.value?.add(user)
        usersAdapter.notifyItemRangeChanged(0, usersAdapter.itemCount)
    }
}