package `fun`.aragaki.screw.ext

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast

inline fun <reified S : Service> Context.tryForeground() {
    val intent = Intent(this, S::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        startForegroundService(intent)
    else
        startService(intent)
}

inline fun <reified S : Service> Context.stopService() = stopService(Intent(this, S::class.java))

fun Context.toast(string: String?, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, string, length).show()
}