package `fun`.aragaki.screw.ui.sms

import `fun`.aragaki.screw.data.EmptyException
import `fun`.aragaki.screw.data.entities.SMS
import `fun`.aragaki.screw.data.services.RouterApiWrapper
import androidx.paging.PagingSource
import androidx.paging.PagingState


class MessageDataSource(private val apiWrapper: RouterApiWrapper) :
    PagingSource<Int, SMS.SmsMessage.SmsMsgGetMessage.SmsMsgGetMessageMsg>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SMS.SmsMessage.SmsMsgGetMessage.SmsMsgGetMessageMsg> {
        kotlin.runCatching {
            val page = params.key ?: 1

            kotlin.runCatching {
                apiWrapper.getSMS(page).message?.getMessage?.messageList?.let {
                    return LoadResult.Page(
                        it, null, page + 1
                    )
                }
            }.onFailure {
                return LoadResult.Error(it)
            }
            throw EmptyException
        }.onFailure { return LoadResult.Error(it) }
        return LoadResult.Error(Exception(";("))
    }

    override fun getRefreshKey(state: PagingState<Int, SMS.SmsMessage.SmsMsgGetMessage.SmsMsgGetMessageMsg>) =
        1
}