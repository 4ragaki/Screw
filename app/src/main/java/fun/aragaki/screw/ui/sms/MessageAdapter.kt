package `fun`.aragaki.screw.ui.sms

import `fun`.aragaki.screw.data.entities.SMS
import `fun`.aragaki.screw.databinding.ItemMessageBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

class MessageAdapter(private val viewModel: InboxViewModel) :
    PagingDataAdapter<SMS.SmsMessage.SmsMsgGetMessage.SmsMsgGetMessageMsg, MessageHolder>(
        MsgComparator
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MessageHolder {
        return MessageHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), viewModel
        )
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        object MsgComparator :
            DiffUtil.ItemCallback<SMS.SmsMessage.SmsMsgGetMessage.SmsMsgGetMessageMsg>() {
            override fun areItemsTheSame(
                oldItem: SMS.SmsMessage.SmsMsgGetMessage.SmsMsgGetMessageMsg,
                newItem: SMS.SmsMessage.SmsMsgGetMessage.SmsMsgGetMessageMsg
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SMS.SmsMessage.SmsMsgGetMessage.SmsMsgGetMessageMsg,
                newItem: SMS.SmsMessage.SmsMsgGetMessage.SmsMsgGetMessageMsg
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}