package `fun`.aragaki.screw.ui.sms

import `fun`.aragaki.screw.R
import `fun`.aragaki.screw.Screw
import `fun`.aragaki.screw.data.services.RouterApiWrapper
import `fun`.aragaki.screw.ui.base.RequestViewModel
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import org.kodein.di.instance


class InboxViewModel(application: Screw) : RequestViewModel(application) {
    val app = getApplication<Screw>()
    private val apiWrapper by app.instance<RouterApiWrapper>()
    val phone by lazy { MutableLiveData<String>() }

    fun sendMessage(to: String, content: String) {
        request({ apiWrapper.sendSMS(to, content) }, onFailure = {
            Toast.makeText(app, it.message, Toast.LENGTH_SHORT).show()
        }, onSuccess = {
            onResult(it.message?.flag?.smsCmdStatusResult, failed = {
                Toast.makeText(app, R.string.sms_send_failed, Toast.LENGTH_SHORT).show()
            })
        })
    }

    fun deleteMessage(ids: List<String?>) {
        request({ apiWrapper.deleteSMS(ids) }, onFailure = {
            Toast.makeText(app, it.message, Toast.LENGTH_SHORT).show()
        }, onSuccess = {
            onResult(
                it.message?.flag?.smsCmdStatusResult, failed = {
                    Toast.makeText(app, R.string.sms_delete_failed, Toast.LENGTH_SHORT).show()
                })
        })
    }

    fun smsFlow() = Pager(
        // pageSize is ignored.
        PagingConfig(pageSize = 10)
    ) {
        MessageDataSource(apiWrapper)
    }.flow.cachedIn(viewModelScope)


    private inline fun onResult(result: Int?, success: () -> Unit = {}, failed: () -> Unit = {}) {
        if (result == 3) {
            success()
        } else {
            failed()
        }
    }
}