package `fun`.aragaki.screw.ui.configs

import `fun`.aragaki.screw.R
import `fun`.aragaki.screw.ui.ViewModelFactory
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

class ConfigsFragment : PreferenceFragmentCompat(), DIAware {
    override val di: DI by closestDI()
    private val viewModel by viewModels<ConfigViewModel>({ requireActivity() }) { ViewModelFactory }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.config)

        handleClick<Preference>(getString(R.string.cfg_key_wifi)) {
            findNavController().navigate(R.id.action_show_WifiSettings)
            true
        }
        handleClick<Preference>(getString(R.string.cfg_key_mobile)) { true }
        handleClick<Preference>(getString(R.string.cfg_key_admin)) { true }
        handleClick<Preference>(getString(R.string.cfg_key_smart_mng)) { true }
        handleClick<Preference>(getString(R.string.cfg_key_upgrade)) { true }
        handleClick<Preference>(getString(R.string.cfg_key_router_mng)) { true }
        handleClick<Preference>(getString(R.string.cfg_key_self_check)) { true }
        handleClick<Preference>(getString(R.string.cfg_key_about)) { true }

        handleClick<Preference>(getString(R.string.cfg_key_reboot)) {
            alert(R.string.reboot_alert_title, R.string.reboot_alert_message) { viewModel.reboot() }
            true
        }

        handleClick<Preference>(getString(R.string.cfg_key_power_off)) {
            alert(R.string.poweroff_alert_title, R.string.poweroff_alert_message) {
                viewModel.powerOff()
            }
            true
        }

    }

    fun <P : Preference> PreferenceFragmentCompat.handleClick(
        key: String, click: (Preference) -> Boolean
    ) {
        findPreference<P>(key)?.setOnPreferenceClickListener {
            click(it)
        }
    }

    private fun alert(
        @StringRes title: Int,
        @StringRes message: Int,
        yes: () -> Unit
    ) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
                yes()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .show()
    }
}