package ui

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.level2.databinding.AddContactBinding
import com.example.level2.model.User

class DialogFragmentAddContact : DialogFragment() {

    private lateinit var binding: AddContactBinding;
    private lateinit var activityContext: MyContactsActivity
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var contactPhoto: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddContactBinding.inflate(inflater, container, false)
        //Open gallery
        binding.ivUserPhoto.setOnClickListener() {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intent)
        }
        //Add user photo
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    contactPhoto = data?.data!!
                    binding.ivUserPhoto.setImageURI(data.data!!)
                }
            }

        binding.btnSaveContact.setOnClickListener() {
            dismiss()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context as MyContactsActivity
    }

    override fun onDismiss(dialog: DialogInterface) {
        activityContext.onContactSave(
            User(
                0,
                contactPhoto.toString(),
                binding.tietUsername.text.toString(),
                binding.tietCareer.text.toString(),
                binding.tietEmail.text.toString(),
                binding.tietPhone.text.toString(),
                binding.tietAddress.text.toString(),
                binding.tietDataOfBirth.text.toString()
            )
        )
        super.onDismiss(dialog)
    }
}