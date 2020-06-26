package `fun`.aragaki.screw

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes

class Settings(private val context: Context, private val preferences: SharedPreferences) {

    var gateway = "192.168.21.1"
    val password = StringValue(R.string.pref_key_password)
    val notification = BooleanValue(R.string.pref_key_notification)
    val notificationInterval = IntValue(R.string.pref_key_notification_interval, 30)
    val netSpeed = BooleanValue(R.string.pref_key_traffic_speed)
    val netSpeedInterval = IntValue(R.string.pref_key_net_speed_interval, 3)

    fun edit(block: (SharedPreferences.Editor) -> Unit) {
        preferences.edit().apply {
            block(this)
            apply()
        }
    }

    private fun getString(@StringRes id: Int) = context.getString(id)

    open inner class Setting<V>(
        @StringRes private val keyId: Int,
        private inline val valueGetter: (key: String) -> V
    ) {
        val key get() = getString(keyId)
        val value: V get() = valueGetter(key)
    }

    inner class StringValue(@StringRes keyId: Int, default: String = "") :
        Setting<String>(keyId, { preferences.getString(it, default) ?: default })

    inner class IntValue(@StringRes keyId: Int, default: Int = 0) :
        Setting<Int>(keyId, { preferences.getInt(it, default) })

    inner class BooleanValue(@StringRes keyId: Int, default: Boolean = false) :
        Setting<Boolean>(keyId, { preferences.getBoolean(it, default) })
}