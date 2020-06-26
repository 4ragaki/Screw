package `fun`.aragaki.screw

import `fun`.aragaki.screw.kodein.serviceModule
import `fun`.aragaki.screw.kodein.supportModule
import android.app.Application
import android.content.Context
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.singleton

class Screw : Application(), DIAware {
    override val di: DI = DI.lazy {
        bind<Context>() with singleton { this@Screw }

        import(supportModule)
        import(serviceModule)
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        lateinit var app: Screw
    }
}