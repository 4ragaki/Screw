package `fun`.aragaki.screw.services

import `fun`.aragaki.screw.R
import `fun`.aragaki.screw.Settings
import `fun`.aragaki.screw.data.entities.MiFiInformation
import `fun`.aragaki.screw.data.services.TcpService
import `fun`.aragaki.screw.ext.toast
import `fun`.aragaki.screw.ui.MainActivity
import android.app.NotificationChannel
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_LOW
import androidx.core.content.getSystemService
import kotlinx.coroutines.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class NotificationService : Service(), DIAware {
    override val di: DI by closestDI()
    private val mgr by lazy { NotificationManagerCompat.from(this) }
    private val settings by instance<Settings>()
    private lateinit var job: Job

    override fun onBind(intent: Intent): Nothing? = null

    override fun onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            mgr.createNotificationChannel(
                NotificationChannel(CHANNEL_ID, CHANNEL_ID, IMPORTANCE_LOW)
            )
        startForeground(NOTIFICATION_ID, buildNotification(null))

        getSystemService<ConnectivityManager>()?.registerNetworkCallback(
            NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build(), NetworkCallback()
        )
    }

    private fun update(service: TcpService) = service.apply out@{
        repeat(3) {
            kotlin.runCatching {
                trySend(genPacket(TcpService.CMD_MIFI_INFO))
                trySend(genPacket(TcpService.CMD_WAN_STATISTICS))
                trySend(genPacket(TcpService.CMD_NEW_SMS_NUM))
                return@out
            }.onFailure { it.printStackTrace() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::job.isInitialized) job.cancel()
    }

    private fun buildNotification(status: MiFiInformation?) =
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_status)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    this, 0x0, Intent(this, MainActivity::class.java),
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
                )
            ).setCustomContentView(RemoteViews(packageName, R.layout.remote_notification).apply {
                status?.let {
                    setViewVisibility(R.id.tv_disconnected, View.GONE)
                    setViewVisibility(R.id.ll_info, View.VISIBLE)
                    setTextViewText(
                        R.id.tv_active_users, status.mActiveWlanClientNumber.toString()
                    )
                    setTextViewText(
                        R.id.tv_newSms, status.mNewSmsNumber.toString()
                    )
                    setTextViewText(
                        R.id.tv_traffic, "${status.mWanStatisticsValue / 1024 / 1024 / 1024}GB"
                    )
                    setTextViewText(R.id.tv_battery, "${status.mBatteryValue}%")

                } ?: this.run {
                    setViewVisibility(R.id.tv_disconnected, View.VISIBLE)
                    setViewVisibility(R.id.ll_info, View.GONE)
                }

            })
            .build()

    inner class NetworkCallback : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            job = GlobalScope.launch(Dispatchers.Main) {
                kotlin.runCatching {
                    withContext(Dispatchers.IO) {
                        val service by instance<TcpService>()
                        service.initService()
                        update(service)
                        startForeground(NOTIFICATION_ID, buildNotification(service.status))

                        while (true) {
                            delay(settings.notificationInterval.value * 1000L)
                            update(service)
                            mgr.notify(NOTIFICATION_ID, buildNotification(service.status))
                        }
                    }
                }.onFailure {
                    it.printStackTrace()
                    toast(it.message)
                    disconnected()
                }
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            disconnected()
        }

        private fun disconnected() {
            job.cancel()
            mgr.notify(NOTIFICATION_ID, buildNotification(null))
        }
    }

    companion object {
        const val NOTIFICATION_ID = 0x1
        const val CHANNEL_ID = "Notification Service"
    }
}