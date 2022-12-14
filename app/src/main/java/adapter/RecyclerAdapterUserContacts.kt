package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.level2.R
import com.example.level2.databinding.RecyclerviewContactsItemBinding
import com.google.android.material.snackbar.Snackbar
import extension.addImage
import model.User
import util.DiffUtil

interface UserListController {
    fun onDeleteUser(user: User)
    fun onContactAdd(user: User)
}

class RecyclerAdapterUserContacts(private val userListController: UserListController) :
    ListAdapter<User, RecyclerAdapterUserContacts.ViewHolder>(DiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_contacts_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    private fun deleteUser(user: User, view: View) {
        val delMessage = Snackbar.make(view, "${user.name} has deleted.", Snackbar.LENGTH_LONG)
        userListController.onDeleteUser(user)
        undoUserDeletion(user, delMessage)
        delMessage.show()
    }

    /**Method back to list of contacts deleted contact if user push "Cancel" on the Snackbar.*/
    private fun undoUserDeletion(user: User, delMessage: Snackbar) {
        delMessage.setAction(R.string.cancel) {
            userListController.onContactAdd(user)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            RecyclerviewContactsItemBinding.bind(itemView).run {
                tvName.text = currentList[absoluteAdapterPosition]?.name
                tvCareer.text = currentList[absoluteAdapterPosition]?.career
                currentList[absoluteAdapterPosition]?.let { ivUserPhoto.addImage(it) }
                currentList[absoluteAdapterPosition]?.id = absoluteAdapterPosition

                imgBtnTrashCan.setOnClickListener {
                    deleteUser(currentList[absoluteAdapterPosition]!!, itemView)
                }
            }
        }
    }
}


