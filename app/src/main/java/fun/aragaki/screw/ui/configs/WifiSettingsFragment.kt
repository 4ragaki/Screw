package `fun`.aragaki.screw.ui.configs

import `fun`.aragaki.screw.R
import `fun`.aragaki.screw.databinding.FragmentWifiSettingsBinding
import `fun`.aragaki.screw.ext.toUnicode
import `fun`.aragaki.screw.ui.ViewModelFactory
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

class WifiSettingsFragment : ConfigFragment(R.layout.fragment_wifi_settings) {
    private val viewModel by viewModels<ConfigViewModel>({ requireActivity() }) { ViewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWifiSettingsBinding.bind(view)

        viewModel.wifiSettings.observe(viewLifecycleOwner, Observer {
            binding.apply {
                etSsid.text = Editable.Factory().newEditable(it.wlanSecurity?.ssid?.toUnicode())
                tilPsk.hint = it.wlanSecurity?.mode
                etPsk.hint = it.wlanSecurity?.mode
                etPsk.text = when (it.wlanSecurity?.mode) {
//                    "WPA-PSK" -> Editable.Factory().newEditable(it.wlanSecurity.wpaPsk?.key)
                    "WPA2-PSK" -> Editable.Factory().newEditable(it.wlanSecurity.wpa2Psk?.key)
                    "Mixed" -> Editable.Factory().newEditable(it.wlanSecurity.mixed?.key)
//                    "WEP" -> Editable.Factory().newEditable(it.wlanSecurity.wep?.key1)
                    "WAPI-PSK" -> Editable.Factory().newEditable(it.wlanSecurity.wpaiPsk?.key)
                    else -> throw Exception(":(")
                }
                it.wlanSecurity.ssidBcast
            }
        })
    }
}