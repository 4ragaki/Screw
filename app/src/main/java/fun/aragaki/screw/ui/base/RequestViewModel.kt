package `fun`.aragaki.screw.ui.base

import `fun`.aragaki.screw.Screw
import `fun`.aragaki.screw.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.instance

abstract class RequestViewModel(app: Screw) : AndroidViewModel(app) {
    protected val settings by app.instance<Settings>()

    /**
     * perform a typical request
     */
    fun <E> request(
        block: suspend () -> E?,
        data: MutableLiveData<E>? = null,
        check: () -> Boolean = { settings.password.value.isNotBlank() },
        checkFail: (() -> Unit)? = null,
        onSuccess: ((result: E) -> Unit)? = null,
        onFailure: ((throwable: Throwable) -> Unit)? = null
    ) {
        if (check()) {
            viewModelScope.launch(Dispatchers.Main) {
                kotlin.runCatching {
                    withContext(Dispatchers.IO) {
                        block()
                    }.also {
                        it?.let { body ->
                            onSuccess?.invoke(body)
                            data?.value = body
                        }
                    }
                }.onFailure {
                    onFailure?.invoke(it)
                }
            }
        } else {
            checkFail?.invoke()
        }
    }
}