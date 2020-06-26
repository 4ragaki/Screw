package `fun`.aragaki.screw.receivers

import `fun`.aragaki.screw.Settings
import `fun`.aragaki.screw.ext.tryForeground
import `fun`.aragaki.screw.services.NotificationService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i(javaClass.simpleName, intent.action ?: "")

        val di by closestDI(context)
        val settings by di.instance<Settings>()
        if (settings.notification.value) context.tryForeground<NotificationService>()
    }
}