package `fun`.aragaki.screw.kodein

import `fun`.aragaki.screw.BuildConfig
import `fun`.aragaki.screw.Settings
import `fun`.aragaki.screw.data.entities.AsciiedUnicode
import `fun`.aragaki.screw.data.services.DigestAuth
import `fun`.aragaki.screw.data.services.RouterApi
import `fun`.aragaki.screw.data.services.RouterApiWrapper
import `fun`.aragaki.screw.data.services.TcpService
import android.util.Log
import com.tickaroo.tikxml.TikXml
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.*
import java.util.concurrent.TimeUnit

val serviceModule = DI.Module("serviceModule") {
    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder()
            .addInterceptor(instance<DigestAuth.Interceptor>())
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(HttpLoggingInterceptor { message ->
                        Log.d(
                            "defaultClient",
                            message
                        )
                    }.apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .retryOnConnectionFailure(true)
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .build()
    }

    bind<TcpService>() with singleton { TcpService(instance()) }

    bind<TikXml>() with singleton {
        TikXml.Builder()
            .addTypeConverter(AsciiedUnicode::class.java, AsciiedUnicode.Converter())
            .build()
    }

    bind<RouterApi>() with multiton { baseUrl: String ->
        RouterApi(instance(), baseUrl)
    }

    bind<RouterApiWrapper>() with singleton {
        RouterApiWrapper(
            instance(), instance(arg = "http://${instance<Settings>().gateway}"), instance()
        )
    }
}