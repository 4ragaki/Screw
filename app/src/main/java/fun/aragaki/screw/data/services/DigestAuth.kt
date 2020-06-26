package `fun`.aragaki.screw.data.services

import `fun`.aragaki.screw.Settings
import `fun`.aragaki.screw.ext.MD5
import okhttp3.Response
import java.io.StringReader
import java.util.*

class DigestAuth(private val settings: Settings) {
    val user = "admin"
    var realm = "Highwmg"
    var qop = "auth"
    var nonce = ""
    var nc = 0

    fun buildAuthorization(method: String, uri: String): String {
        val cnonce = cnonce()
        return " Digest username=\"$user\",realm=\"$realm\",nonce=\"$nonce\",uri=\"$uri\",response=\"${
            buildDigestAuthResponse(
                cnonce, method, uri
            )
        }\",qop=$qop,nc=${
            String.format(
                "%08d",
                nc++
            )
        },cnonce=\"$cnonce\",client=APP"
    }

    /**
     * https://tools.ietf.org/html/rfc2617
     *
     * md5(md5(username:realm:password):nonce:nc:cnonce:qop:md5(uri))
     */
    fun buildDigestAuthResponse(cnonce: String, method: String, uri: String): String {
        return "${"$user:$realm:${settings.password.value}".MD5()}:$nonce:${
            String.format(
                "%08d", nc
            )
        }:$cnonce:${qop}:${"$method:$uri".MD5()}".MD5()
    }

    fun cnonce() =
//        the official one
//        "${floor(Math.random() * 100001.0).toLong()}${Date().time}".MD5().substring(0, 16)
        "${Random().nextLong()}${Date().time}".MD5().substring(0, 16)

    /**
     * it.first == true if nonce updated.
     */
    fun <E> updateNonce(response: retrofit2.Response<E>): Pair<Boolean, E?> {
        response.headers().forEach {
            if (it.first == "WWW-Authenticate") {
                it.second.removePrefix("Digest ").replace("\",", "\n")
                    .replace("\"", "").let { prop ->
                        Properties().apply {
                            load(StringReader(prop))
                            realm = getProperty("realm")
                            nonce = getProperty("nonce")
                            qop = getProperty("qop")
                            nc = 0
                            return Pair(true, response.body())
                        }
                    }
            }
        }
        return Pair(false, response.body())
    }

    inner class Interceptor : okhttp3.Interceptor {
        override fun intercept(chain: okhttp3.Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder().addHeader(
                "Authorization",
                buildAuthorization(request.method, request.url.encodedPath)
            ).build()
            return chain.proceed(request)
        }
    }
}