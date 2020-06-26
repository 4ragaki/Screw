package `fun`.aragaki.screw.ui.sms

import `fun`.aragaki.screw.data.entities.SMS
import `fun`.aragaki.screw.databinding.DialogMessageBinding
import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import com.google.android.material.bottomsheet.BottomSheetDialog

class MessageDialog(
    context: Context,
    viewModel: InboxViewModel,
    msg: SMS.SmsMessage.SmsMsgGetMessage.SmsMsgGetMessageMsg
) : BottomSheetDialog(context) {
    init {
        val binding = DialogMessageBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        binding.apply {
            btnSend.setOnClickListener {
                viewModel.sendMessage(tvFrom.text.toString(), etReply.text.toString())
                dismiss()
            }

            etReply.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                    viewModel.sendMessage(tvFrom.text.toString(), etReply.text.toString())
                    dismiss()
                }
                true
            }

            tvFrom.text = msg.from.toString().substring(1)
            tvMsg.text = msg.subject.toString()
        }
    }
}