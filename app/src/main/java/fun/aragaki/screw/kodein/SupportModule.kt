package `fun`.aragaki.screw.kodein

import `fun`.aragaki.screw.Settings
import `fun`.aragaki.screw.data.services.DigestAuth
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val supportModule = DI.Module("supportModule") {
    bind<SharedPreferences>() with singleton {
        PreferenceManager.getDefaultSharedPreferences(instance())
    }

    bind<Settings>() with singleton { Settings(instance(), instance()) }

    bind<DigestAuth>() with singleton { DigestAuth(instance()) }

    bind<DigestAuth.Interceptor>() with singleton { instance<DigestAuth>().Interceptor() }
}