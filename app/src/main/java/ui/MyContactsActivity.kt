package ui

import adapter.RecyclerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.level2.databinding.MyContactsBinding
import model.User
import model.UsersViewModel
import util.MySimpleCallBack

class MyContactsActivity : AppCompatActivity() {

    private lateinit var binding: MyContactsBinding
    var viewmodel: UsersViewModel = UsersViewModel()

    private val usersAdapter by lazy {
        RecyclerAdapter(
            viewmodel,
            onDeleteUser = { user ->
                viewmodel.deleteUser(user)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.rvContacts
        recyclerView.adapter = usersAdapter

        ItemTouchHelper(MySimpleCallBack(viewmodel, usersAdapter).simpleCallback).attachToRecyclerView(recyclerView)

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

    override fun onPause() {
        super.onPause()
        println("Show me usersAdapter.currentList onPause this activity: ${usersAdapter.currentList}")
    }
    private fun setObservers() {
        viewmodel.userListLiveData.observe(this) {
            println("Show me usersAdapter.currentList before submitList ${usersAdapter.currentList}")
            usersAdapter.submitList(viewmodel.userListLiveData.value?.toMutableList())
            println("Show me usersAdapter.currentList after submitList ${usersAdapter.currentList}")
        }
    }

    fun onContactSave(user: User) {
        viewmodel.userListLiveData.value?.add(user)
        usersAdapter.notifyItemRangeChanged(0, usersAdapter.itemCount)
    }
}