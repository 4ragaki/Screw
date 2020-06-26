package `fun`.aragaki.screw.data.entities

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class WlanSettings(
    @PropertyElement(name = "login_status")
    override val loginStatus: String?,

    @Element(name = "lan")
    val lan: WlanSettingsLan?,
    @Element(name = "wlan_settings")
    val wlanSettings: WlanSettingsWlanSettings?,
    @Element(name = "autosleep_mi")
    val autoSleepMi: WlanSettingsAutoSleepMi?,
    @Element(name = "wlan_security")
    val wlanSecurity: WlanSettingsWlanSecurity?
) : LoginStatus {
    @Xml
    data class WlanSettingsLan(
        @PropertyElement(name = "option_43")
        val option43: String
    )

    @Xml
    data class WlanSettingsWlanSettings(
        @PropertyElement(name = "wifi_sleep_time")
        val wifiSleepTime: String,
        @PropertyElement(name = "wifi_sleep_action")
        val wifiSleepAction: String,
        @PropertyElement(name = "net_mode")
        val netMode: String
    )

    @Xml
    data class WlanSettingsAutoSleepMi(
        @PropertyElement(name = "autosleep_status")
        val autoSleepStatus: String,
        @PropertyElement(name = "wpsbtneffect")
        val wpsBtnEffect: String,
        @PropertyElement(name = "zm_enable_wps")
        val zmEnableWps: String
    )

    @Xml
    data class WlanSettingsWlanSecurity(
        @PropertyElement(name = "wapi_support")
        val wapiSupport: String,
        @PropertyElement(name = "lock_enable")
        val lockEnable: String,
        @PropertyElement(name = "ssid")
        val ssid: String,
        @PropertyElement(name = "ssid_bcast")
        val ssidBcast: String,
        @PropertyElement(name = "mode")
        val mode: String,
        @PropertyElement(name = "client_mac")
        val clientMac: String,
        @PropertyElement(name = "client_ip")
        val clientIp: String,

        @Element(name = "WPA-PSK")
        val wpaPsk: WlanSettingsWlanSecurityWpaPsk?,
        @Element(name = "WPA2-PSK")
        val wpa2Psk: WlanSettingsWlanSecurityWpa2Psk?,
        @Element(name = "Mixed")
        val mixed: WlanSettingsWlanSecurityMixed?,
        @Element(name = "WEP")
        val wep: WlanSettingsWlanSecurityWep?,
        @Element(name = "WAPI-PSK")
        val wpaiPsk: WlanSettingsWlanSecurityWapiPsk?,
        @Element(name = "WPS")
        val wps: WlanSettingsWlanSecurityWps?,
        @PropertyElement(name = "default_key_type")
        val defaultKeyType: String
    ) {
        @Xml
        data class WlanSettingsWlanSecurityWpaPsk(
            @PropertyElement(name = "mode")
            val mode: String?,
            @PropertyElement(name = "key")
            val key: String?,
            @PropertyElement(name = "rekey")
            val rekey: String?
        )

        @Xml
        data class WlanSettingsWlanSecurityWpa2Psk(
            @PropertyElement(name = "mode")
            val mode: String?,
            @PropertyElement(name = "key")
            val key: String?,
            @PropertyElement(name = "rekey")
            val rekey: String?
        )

        @Xml
        data class WlanSettingsWlanSecurityMixed(
            @PropertyElement(name = "mode")
            val mode: String?,
            @PropertyElement(name = "key")
            val key: String?,
            @PropertyElement(name = "rekey")
            val rekey: String?
        )

        @Xml
        data class WlanSettingsWlanSecurityWep(
            @PropertyElement(name = "key1")
            val key1: String?,
            @PropertyElement(name = "key2")
            val key2: String?,
            @PropertyElement(name = "key3")
            val key3: String?,
            @PropertyElement(name = "key4")
            val key4: String?,
            @PropertyElement(name = "auth")
            val auth: String?,
            @PropertyElement(name = "encrypt")
            val encrypt: String?,
            @PropertyElement(name = "default_key")
            val defaultKey: String?
        )

        @Xml
        data class WlanSettingsWlanSecurityWapiPsk(
            @PropertyElement(name = "key_type")
            val keyType: String?,
            @PropertyElement(name = "key")
            val key: String?
        )

        @Xml
        data class WlanSettingsWlanSecurityWps(
            @PropertyElement(name = "connect_method")
            val connectMethod: String?,
            @PropertyElement(name = "wps_pin")
            val wpsPin: String?,
            @PropertyElement(name = "wps_status")
            val wpsStatus: String?,
            @PropertyElement(name = "wps_enable")
            val wpsEnable: String?
        )
    }
}