package util

import adapter.RecyclerAdapterUserContacts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.level2.R
import com.google.android.material.snackbar.Snackbar
import model.User
import model.UsersViewModel

/**This is my callBack for ItemTouchHelper*/
class SimpleCallBack(
    var viewModel: UsersViewModel,
    var userAdapter: RecyclerAdapterUserContacts,
) {

    var simpleCallback =
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
                        val user = viewModel.getUser(viewHolder.adapterPosition)
                        val delMessage = Snackbar.make(
                            viewHolder.itemView,
                            "${user?.name} has deleted.",
                            Snackbar.LENGTH_LONG
                        )
                        userAdapter.submitList(viewModel.userListLiveData.value.also {
                            viewModel.userListLiveData.value?.removeAt(
                                viewHolder.adapterPosition
                            )
                        }?.toMutableList())
                        undoUserDeletion(user, delMessage, viewHolder.adapterPosition)
                        delMessage.show()
                    }
                }
            }
        }

    /**Method back to list of contacts deleted contact if user push "Cancel" on the Snackbar.*/
    private fun undoUserDeletion(user: User?, delMessage: Snackbar, position: Int) {
        delMessage.setAction(R.string.cancel) {
          userAdapter.submitList(viewModel.userListLiveData.value.also { viewModel.userListLiveData.value?.add(
              user!!
          ) }?.toMutableList())
        }
    }
}