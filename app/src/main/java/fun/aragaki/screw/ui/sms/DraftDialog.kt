package `fun`.aragaki.screw.ui.sms

import `fun`.aragaki.screw.databinding.DialogDraftBinding
import android.app.Activity
import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog

class DraftDialog(activity: Activity, private val viewModel: InboxViewModel) :
    BottomSheetDialog(activity) {
    private var observer: Observer<String>

    init {
        val binding = DialogDraftBinding.inflate(LayoutInflater.from(activity))
        setContentView(binding.root)

        observer = Observer { t -> binding.etTo.setText(t) }
        viewModel.phone.observeForever(observer)

        binding.apply {
            btnContacts.setOnClickListener {
                activity.startActivityForResult(Intent(Intent.ACTION_PICK).apply {
                    type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
                }, InboxActivity.PICK_CONTACT)
            }

            btnSend.setOnClickListener {
                viewModel.sendMessage(etTo.text.toString(), etBody.text.toString())
                dismiss()
            }

            btnCancel.setOnClickListener {
                dismiss()
            }

/*            etBody.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                    viewModel.sendMessage(etTo.text.toString(), etBody.text.toString())
                    dismiss()
                }
                true
            }*/
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.phone.removeObserver(observer)
        viewModel.phone.value = null
    }

}