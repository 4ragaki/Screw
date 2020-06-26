package `fun`.aragaki.screw.ui.configs

import `fun`.aragaki.screw.R
import `fun`.aragaki.screw.Screw
import `fun`.aragaki.screw.data.entities.WlanSettings
import `fun`.aragaki.screw.data.services.RouterApiWrapper
import `fun`.aragaki.screw.ui.base.RequestViewModel
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import org.kodein.di.instance

class ConfigViewModel(app: Screw) : RequestViewModel(app) {
    private val app = getApplication<Screw>()
    private val apiWrapper by getApplication<Screw>().instance<RouterApiWrapper>()

    val wifiSettings by lazy {
        MutableLiveData<WlanSettings>().also { retrieveWifiSettings(it) }
    }

    private fun retrieveWifiSettings(data: MutableLiveData<WlanSettings> = wifiSettings) {
        request(block = { apiWrapper.getWifiSettings() }, data = data)
    }

    fun powerOff() {
        request(
            block = { apiWrapper.poweroff() },
            checkFail = {
                Toast.makeText(app, R.string.require_password, Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                Toast.makeText(app, R.string.poweroff_success, Toast.LENGTH_SHORT).show()
            },
            onFailure = {
                Toast.makeText(
                    app, app.getString(R.string.fmt_poweroff_fail).format(it.message),
                    Toast.LENGTH_SHORT
                ).show()
            })
    }

    fun reboot() {
        request(
            block = { apiWrapper.reboot() },
            checkFail = {
                Toast.makeText(app, R.string.require_password, Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                Toast.makeText(app, R.string.reboot_success, Toast.LENGTH_SHORT).show()
            },
            onFailure = {
                Toast.makeText(
                    app, app.getString(R.string.fmt_reboot_fail).format(it.message),
                    Toast.LENGTH_SHORT
                )
                    .show()
            })
    }
}