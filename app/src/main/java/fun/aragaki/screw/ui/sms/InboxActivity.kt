package `fun`.aragaki.screw.ui.sms

import `fun`.aragaki.screw.R
import `fun`.aragaki.screw.data.EmptyException
import `fun`.aragaki.screw.databinding.ActivityInboxBinding
import `fun`.aragaki.screw.ext.setupTransitions
import `fun`.aragaki.screw.ext.withCircularReveal
import `fun`.aragaki.screw.ui.ViewModelFactory
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.imid.swipebacklayout.lib.app.SwipeBackActivity
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.android.retainedDI
import kotlin.math.hypot

class InboxActivity : SwipeBackActivity(), DIAware {
    private val parentDI by closestDI()
    override val di: DI by retainedDI {
        extend(parentDI)
    }
    private val viewModel by viewModels<InboxViewModel> { ViewModelFactory }
    private lateinit var msgAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInboxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupTransitions(
            R.transition.slide, R.transition.fade,
            R.transition.slide, R.transition.fade
        )

        binding.apply {
            content.btnRetry.setOnClickListener {
                launch()
            }
            content.rvInbox.apply {
                layoutManager = LinearLayoutManager(this@InboxActivity)
                adapter = MessageAdapter(viewModel).also { msgAdapter = it }
                ItemTouchHelper(
                    InboxTouchCallback(
                        viewModel,
                        msgAdapter
                    )
                ).attachToRecyclerView(this)
            }
            fab.setOnClickListener {
                DraftDialog(this@InboxActivity, viewModel).show()
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            msgAdapter.loadStateFlow.collectLatest { loadStates ->
                val refresh = loadStates.refresh
                binding.apply {
                    content.progressInbox.isVisible = refresh is LoadState.Loading
                    if (refresh is LoadState.Error && refresh.error is EmptyException) {
                        content.ivEmpty.apply {
                            withCircularReveal(
                                this.left + this.width / 2, this.top + this.height / 2, 0f,
                                hypot(this.width / 2.0f, this.height / 2.0f)
                            ) { isVisible = true }
                        }
                    }
                    content.btnRetry.isVisible =
                        refresh is LoadState.Error && refresh.error !is EmptyException
                }
            }
        }

        launch()
    }

    private fun launch() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.smsFlow().collectLatest { pagingData ->
                msgAdapter.submitData(pagingData)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_CONTACT -> data?.data?.let {
                    val query = contentResolver.query(it, null, null, null, null)
                    query?.apply {
                        moveToFirst()
                        viewModel.phone.value =
                            getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                .replace(" ", "").replace("-", "")
                    }

                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val PICK_CONTACT = 0x0
    }
}