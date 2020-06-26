package `fun`.aragaki.screw.data.services

import `fun`.aragaki.screw.data.entities.LoginStatus
import `fun`.aragaki.screw.data.entities.SMS
import `fun`.aragaki.screw.data.entities.Status1
import `fun`.aragaki.screw.data.entities.WlanSettings
import `fun`.aragaki.screw.ext.toAscii
import com.tickaroo.tikxml.TikXml
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okio.BufferedSource
import retrofit2.Response
import java.util.*

@Suppress("BlockingMethodInNonBlockingContext")
class RouterApiWrapper(
    private val digestAuth: DigestAuth,
    private val service: RouterApi,
    private val tikXml: TikXml
) {

    private suspend fun updateNonce() {
        digestAuth.updateNonce(service.login())
    }

    suspend fun login(): Response<ResponseBody> {
        val cnonce = digestAuth.cnonce()
        val response = digestAuth.buildDigestAuthResponse(cnonce, "GET", "/cgi/protected.cgi")
        return service.login(
            Action = "Digest",
            username = digestAuth.user,
            realm = digestAuth.realm,
            nonce = digestAuth.nonce,
            response = response,
            qop = digestAuth.qop,
            cnonce = cnonce,
            temp = "marvell",
            client = "APP"
        )
    }

    suspend fun getSMS(page: Int): SMS {
        val flag = "GET_RCV_SMS_LOCAL"
        return parse(requestResponse {
            service.set(
                module = "duster",
                file = "message",
                body = "<?xml version=\"1.0\" encoding=\"US-ASCII\"?> <RGW><message><flag><message_flag>$flag</message_flag></flag><get_message><page_number>$page</page_number></get_message></message></RGW>"
                    .toRequestBody()
            )
        }.body()?.source())
    }

    suspend fun sendSMS(phone: String, content: String): SMS {
        var gsm7 = true
        content.forEach {
            if (!(((it >= ' ' && it.toInt() <= 127) || it.toInt() in arrayOf(
                    8364, 12, 10, 13, 161, 163, 165, 167, 191, 196, 197, 198, 199,
                    201, 209, 214, 216, 220, 223, 224, 228, 229, 230, 232, 233, 236,
                    3857, 242, 246, 248, 249, 252, 966, 937, 936, 931, 928, 926, 923,
                    920, 916, 915
                )) && '`' != it)
            ) {
                gsm7 = false
            }
        }
        val encodeType = if (gsm7) "GSM7_default" else "UNICODE"

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR).toString()
        val shortYear = year.substring(2, year.length)
        val month: Int = calendar.get(Calendar.MONTH) + 1
        val day: Int = calendar.get(Calendar.DATE)
        val hour: Int = calendar.get(Calendar.HOUR)
        val min: Int = calendar.get(Calendar.MINUTE)
        val sec: Int = calendar.get(Calendar.SECOND)
        val timezone: Int =
            (calendar.get(Calendar.DST_OFFSET) + calendar.get(Calendar.ZONE_OFFSET)) / 60000 / 60
        val str = if (timezone > 0) "%2B$timezone" else "-$timezone"

        val smsTime = "$shortYear,$month,$day,$hour,$min,$sec,$str"

        return parse(
            requestResponse {
                service.set(
                    module = "duster",
                    file = "message",
                    body = "<?xml version=\"1.0\" encoding=\"US-ASCII\"?> <RGW><message><flag><message_flag>SEND_SMS</message_flag><sms_cmd>4</sms_cmd></flag><send_save_message><contacts>$phone</contacts><content>${content.toAscii()}</content><encode_type>$encodeType</encode_type><sms_time>$smsTime</sms_time></send_save_message></message></RGW>".toRequestBody()
                )
            }.body()?.source()
        )
    }

    suspend fun deleteSMS(ids: List<String?>): SMS {
        val tags = 12

        val idList = buildString {
            ids.forEach { str ->
                append(str)
                append(','.takeIf { str?.last() != ',' })
            }
        }
        return parse(
            requestResponse {
                service.set(
                    module = "duster",
                    file = "message",
                    body = "<?xml version=\"1.0\" encoding=\"US-ASCII\"?> <RGW><message><flag><message_flag>DELETE_SMS</message_flag><sms_cmd>6</sms_cmd></flag><get_message><tags>$tags</tags><mem_store>1</mem_store></get_message><set_message><delete_message_id>$idList</delete_message_id></set_message></message></RGW>".toRequestBody()
                )
            }.body()?.source()
        )
    }

    suspend fun baseInfo() = service.get(Action = "GetInfo", Id = "Base")

    suspend fun status1() =
        request<Status1> { service.get(module = "duster", file = "status1") }


    suspend fun getWifiSettings() =
        request<WlanSettings> {
            service.get(
                module = "duster",
                file = "uapxb_wlan_security_settings"
            )
        }

    /**
    <?xml version="1.0" encoding="US-ASCII"?><RGW>
    <wlan_security>
    <ssid>00410072006100670061006b0069005f005a</ssid>
    <mode>WPA2-PSK</mode>
    <WPA2-PSK>
    <key>123456</key>
    </WPA2-PSK>
    </wlan_security>
    </RGW>
     */
    suspend fun setWifiSettings(settings: RequestBody) =
        requestResponse {
            service.set(
                module = "duster",
                file = "uapxb_wlan_security_settings",
                body = settings
            )
        }


    suspend fun setPassword(password: RequestBody) =
        requestResponse {
            service.set(
                module = "duster",
                file = "admin",
                body = password
            )
        }


    suspend fun reboot() =
        requestResponse {
            service.get(
                module = "duster",
                file = "reset"
            )
        }

    suspend fun poweroff() =
        requestResponse {
            service.get(
                module = "duster",
                file = "poweroff"
            )
        }


    suspend fun dumpConfig() =
        requestResponse { service.dumpConfig() }

    private suspend inline fun requestResponse(block: () -> Response<ResponseBody>): Response<ResponseBody> {
        val response = block()
        digestAuth.updateNonce(response).let {
            if (it.first) {
                login()
                return parse(block().body()?.source())
            }
        }
        return response
    }

    private suspend inline fun <reified E : LoginStatus> request(
        block: () -> Response<ResponseBody>
    ): E {
        val response = requestResponse { block() }
        val requestResponse = tikXml.read<E>(
            response.body()?.source(), E::class.java
        )
        when (requestResponse.loginStatus) {
            "UNAUTHORIZED" -> {
                updateNonce()
                login()
                return parse(block().body()?.source())
            }

            "KICKOFF" -> {
                throw KickOffException
            }

        }
        return requestResponse
    }

    private inline fun <reified E> parse(source: BufferedSource?): E {
        return tikXml.read(source, E::class.java)
    }

    object KickOffException : Exception("admin logged in on another device.")
}