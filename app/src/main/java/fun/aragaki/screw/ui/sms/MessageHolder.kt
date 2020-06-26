package `fun`.aragaki.screw.ui.sms

import `fun`.aragaki.screw.data.entities.SMS
import `fun`.aragaki.screw.databinding.ItemMessageBinding
import androidx.recyclerview.widget.RecyclerView

class MessageHolder(
    private val binding: ItemMessageBinding,
    viewModel: InboxViewModel
) :
    RecyclerView.ViewHolder(binding.root) {
    lateinit var msg: SMS.SmsMessage.SmsMsgGetMessage.SmsMsgGetMessageMsg

    init {
        binding.root.apply {
            setOnClickListener {
                MessageDialog(context, viewModel, msg).show()
            }
        }
    }

    fun bind(msg: SMS.SmsMessage.SmsMsgGetMessage.SmsMsgGetMessageMsg?) {
        msg?.let {
            binding.msg = it
            this.msg = it
        }
    }
}