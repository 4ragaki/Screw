package `fun`.aragaki.screw.ui

import `fun`.aragaki.screw.R
import `fun`.aragaki.screw.Settings
import `fun`.aragaki.screw.data.services.RouterApiWrapper
import `fun`.aragaki.screw.data.services.TcpService
import `fun`.aragaki.screw.databinding.ActivitySplashBinding
import `fun`.aragaki.screw.ext.setupTransitions
import `fun`.aragaki.screw.ext.snack
import `fun`.aragaki.screw.ext.startActivity
import `fun`.aragaki.screw.ext.tryForeground
import `fun`.aragaki.screw.services.NotificationService
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.android.retainedDI
import org.kodein.di.instance
import java.net.InetAddress

class SplashActivity : AppCompatActivity(), DIAware {
    private val parentDI by closestDI()
    override val di: DI by retainedDI {
        extend(parentDI)
    }
    private val settings by instance<Settings>()
    private val tcpService by instance<TcpService>()
    private val apiWrapper by instance<RouterApiWrapper>()
    var isMobileRouter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTransitions(
            R.transition.slide, R.transition.fade,
            R.transition.slide, R.transition.slide
        )
        val start = System.currentTimeMillis()

        lifecycleScope.launch(Dispatchers.Main) {
            @Suppress("BlockingMethodInNonBlockingContext")
            withContext(Dispatchers.IO) {
//                check if mobile router
                run retries@{
                    kotlin.runCatching {
                        (applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).run {
                            val gateway = dhcpInfo.gateway
                            val bytes = byteArrayOf(
                                gateway.and(0xFF).toByte(),
                                gateway.shr(8).and(0xFF).toByte(),
                                gateway.shr(16).and(0xFF).toByte(),
                                gateway.shr(24).and(0xFF).toByte()
                            )
                            settings.gateway = InetAddress.getByAddress(bytes).canonicalHostName
                        }

                        repeat(5) {
                            tcpService.apply {
                                trySend(genPacket(TcpService.CMD_MIFI_INFO),false)
                            }

                            apiWrapper.baseInfo()
                            isMobileRouter = true

                            withContext(Dispatchers.Main) {
                                val delay = 3340 + start - System.currentTimeMillis()
                                if (delay > 0) delay(delay)

                                if (settings.notification.value) tryForeground<NotificationService>()
                                startActivity<MainActivity>()
                                finishAfterTransition()
                            }
                            return@retries
                        }
                    }.onFailure { it.printStackTrace() }
                }
            }

            if (!isMobileRouter) {
                binding.root.snack(R.string.not_router)
                delay(3000)
                finishAfterTransition()
            }
        }
    }
}