package `fun`.aragaki.screw

import `fun`.aragaki.screw.data.entities.AsciiedUnicode
import `fun`.aragaki.screw.data.services.DigestAuth
import `fun`.aragaki.screw.data.services.RouterApi
import `fun`.aragaki.screw.data.services.RouterApiWrapper
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tickaroo.tikxml.TikXml
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient

import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class DigestAuthTest {

    @Test
    fun digest() {
        println(DigestAuth.buildDigestAuthResponse("3ed864248c47fd53", "GET", "/cgi/protected.cgi"))
    }

    @Test
    fun dump() {
        runBlocking {
            RouterApiWrapper(
                RouterApi(OkHttpClient(), "http://192.168.1.1"),
                TikXml.Builder()
                    .addTypeConverter(AsciiedUnicode::class.java, AsciiedUnicode.Converter())
                    .build()
            ).status1().let {
                File("/home/aragaki/Desktop/out.txt").writeText(it.toString())
            }
        }
    }
}