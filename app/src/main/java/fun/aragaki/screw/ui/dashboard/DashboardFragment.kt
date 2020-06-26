package `fun`.aragaki.screw.ui.dashboard

import `fun`.aragaki.screw.R
import `fun`.aragaki.screw.data.services.RouterApiWrapper
import `fun`.aragaki.screw.databinding.FragmentDashboardBinding
import `fun`.aragaki.screw.ext.startActivity
import `fun`.aragaki.screw.ui.ViewModelFactory
import `fun`.aragaki.screw.ui.sms.InboxActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class DashboardFragment : Fragment(), DIAware {
    override val di: DI by closestDI()
    private val viewModel by viewModels<DashboardViewModel> { ViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDashboardBinding.inflate(inflater, container, false).apply {
        swipeDashboard.apply {
            val value = TypedValue()
            requireContext().theme.resolveAttribute(R.attr.colorPrimary, value, true)
            setColorSchemeColors(value.data)
            isRefreshing = true
            setOnRefreshListener {
                viewModel.status1()
            }
        }
        chipNewMessage.setOnClickListener {
            requireActivity().startActivity<InboxActivity>()
        }
        viewModel.exception.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RouterApiWrapper.KickOffException -> it.message?.let { msg ->
                    Snackbar.make(clDashboard, msg, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.login) {
                            viewModel.status1(true)
                        }
                        .show()
                }

                else -> it.message?.let { msg ->
                    Snackbar.make(clDashboard, msg, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.retry) { viewModel.status1() }
                        .show()
                }
            }
            swipeDashboard.isRefreshing = false
        })

        viewModel.status1.observe(viewLifecycleOwner) {
            status = it
            progress.isGone = true
            clDashboard.isVisible = true
            swipeDashboard.isRefreshing = false
        }

        viewModel.status1()
    }.root
}