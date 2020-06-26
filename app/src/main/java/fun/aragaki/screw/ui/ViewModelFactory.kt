package `fun`.aragaki.screw.ui

import `fun`.aragaki.screw.Screw.Companion.app
import `fun`.aragaki.screw.ui.configs.ConfigViewModel
import `fun`.aragaki.screw.ui.dashboard.DashboardViewModel
import `fun`.aragaki.screw.ui.sms.InboxViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
object ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = when {
        modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(app) as T
        modelClass.isAssignableFrom(ConfigViewModel::class.java) -> ConfigViewModel(app) as T
        modelClass.isAssignableFrom(InboxViewModel::class.java) -> InboxViewModel(app) as T
        else -> throw Exception("can't create viewModel of ${modelClass.canonicalName}")
    }
}