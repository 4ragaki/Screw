package `fun`.aragaki.screw.ui.dashboard

import `fun`.aragaki.screw.R
import `fun`.aragaki.screw.Screw
import `fun`.aragaki.screw.data.entities.Status1
import `fun`.aragaki.screw.data.services.RouterApiWrapper
import `fun`.aragaki.screw.ui.base.RequestViewModel
import androidx.lifecycle.MutableLiveData
import org.kodein.di.instance

class DashboardViewModel(app: Screw) : RequestViewModel(app) {
    private val app = getApplication<Screw>()
    private val apiWrapper by this.app.instance<RouterApiWrapper>()

    val exception = MutableLiveData<Throwable>()
    val status1 = MutableLiveData<Status1>()

    fun status1(bind: Boolean = false) {
        request(
            {
                if (bind) apiWrapper.login()
                apiWrapper.status1()
            },
            status1,
            checkFail = {
                exception.value = java.lang.Exception(app.getString(R.string.require_password))
            },
            onFailure = {
                it.printStackTrace()
                exception.value = it
            })
    }
}