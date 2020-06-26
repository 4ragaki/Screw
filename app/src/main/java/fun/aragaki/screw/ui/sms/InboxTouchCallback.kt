package `fun`.aragaki.screw.ui.sms

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class InboxTouchCallback(
    private val viewModel: InboxViewModel,
    private val adapter: MessageAdapter
) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (viewHolder is MessageHolder && direction == ItemTouchHelper.LEFT) {
            viewModel.deleteMessage(listOf(viewHolder.msg.ind3x))
            adapter.refresh()
        }
    }
}