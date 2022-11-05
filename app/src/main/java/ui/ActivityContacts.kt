package ui

import adapter.RecyclerAdapterUserContacts
import adapter.UserListController
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level2.databinding.ActivityContactsBinding
import model.User
import model.UsersViewModel
import util.SimpleCallBack

class ActivityContacts : AppCompatActivity(), UserListController {

    private lateinit var binding: ActivityContactsBinding
    private val viewModel: UsersViewModel by viewModels()

    private val usersAdapter by lazy {
        RecyclerAdapterUserContacts(userListController = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.rvContacts.apply { adapter = usersAdapter }
        recyclerView.layoutManager = LinearLayoutManager(this)

        ItemTouchHelper(
            SimpleCallBack(
                viewModel,
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

        setObservers()
    }

    private fun setObservers() {
        viewModel.userListLiveData.observe(this) {
            usersAdapter.submitList(viewModel.userListLiveData.value?.toMutableList())
        }
    }

    override fun onContactAdd(user: User) {
        usersAdapter.submitList(viewModel.userListLiveData.value.also {
            viewModel.userListLiveData.value?.add(user)
        }?.toMutableList())
    }

    override fun onDeleteUser(user: User) {
        usersAdapter.submitList(viewModel.userListLiveData.value.also {
            viewModel.userListLiveData.value?.remove(
                user
            )
        }?.toMutableList())
    }
}