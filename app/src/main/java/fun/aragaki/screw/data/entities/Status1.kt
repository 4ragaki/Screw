package `fun`.aragaki.screw.data.entities

import com.tickaroo.tikxml.annotation.*

@Xml
data class Status1(
    @PropertyElement(name = "login_status")
    override val loginStatus: String?,

    @Element(name = "sysinfo")
    val sysInfo: Status1SysInfo?,
    @Element(name = "batteryinfo")
    val batteryInfo: Status1BatteryInfo?,
    @Element(name = "wan")
    val wan: Status1Wan?,
    @Element(name = "wlan_settings")
    val wlanSettings: Status1WlanSettings?,
    @Element(name = "wlan_security")
    val wlanSecurity: Status1WlanSecurity?,
    @Element(name = "storage")
    val storage: Status1Storage?,
    @Element(name = "statistics")
    val statistics: Stautus1Statistics?,
    @Element(name = "lan")
    val lan: Status1Lan?,
    @Element(name = "device_management")
    val deviceManagement: Status1DeviceManagement?,
    @Element(name = "message")
    val message: Status1Message?,
    @Element(name = "auto_reboot")
    val autoReboot: Status1AutoReboot?
) : LoginStatus {
    @Xml
    data class Status1SysInfo(
        @PropertyElement(name = "hardware_version")
        val hardwareVersion: String?,
        @PropertyElement(name = "device_name")
        val deviceName: String?,
        @PropertyElement(name = "version_num")
        val versionNum: String?,
        @PropertyElement(name = "version_date")
        val versionDate: String?,
        @PropertyElement(name = "model_name")
        val modelName: String?,
        @PropertyElement(name = "main_chip_name")
        val mainChipName: String?,
        @PropertyElement(name = "ssg_version")
        val ssgVersion: String?,
        @PropertyElement(name = "ssg_compile_time")
        val ssgCompileTime: String?,
        @PropertyElement(name = "current_device_mac")
        val currentDeviceMac: String?
    )

    @Xml
    data class Status1BatteryInfo(
        @PropertyElement(name = "Output_current")
        val outputCurrent: String?,
        @PropertyElement(name = "Battery_status")
        val batteryStatus: String?,
        @PropertyElement(name = "Battery_level")
        val batteryLevel: String?,
        @PropertyElement(name = "Battery_percent")
        val batteryPercent: String?,
        @PropertyElement(name = "Charger_status")
        val chargerStatus: String?
    )

    @Xml
    data class Status1Wan(
        @PropertyElement(name = "NW_register_status")
        val NWRegisterStatus: String?,
        @PropertyElement(name = "ip")
        val ip: String?,
        @PropertyElement(name = "ConnType")
        val connType: String?,
        @PropertyElement(name = "proto")
        val proto: String?,
        @PropertyElement(name = "connect_disconnect")
        val connectDisconnect: String?,
        @PropertyElement(name = "gateway")
        val gateway: String?,
        @PropertyElement(name = "dns1")
        val dns1: String?,
        @PropertyElement(name = "dns2")
        val dns2: String?,
        @PropertyElement(name = "mask")
        val mask: String?,
        @PropertyElement(name = "wan_link_status")
        val wanLinkStatus: String?,
        @PropertyElement(name = "wan_conn_status")
        val wanConnStatus: String?,
        @PropertyElement(name = "sys_mode")
        val sysMode: String?,
        @PropertyElement(name = "sys_submode")
        val sysSubmode: String?,
        @PropertyElement(name = "data_conn_mode")
        val dataConnMode: String?,
        @PropertyElement(name = "tx_rate")
        val txRate: String?,
        @PropertyElement(name = "rx_rate")
        val rxRate: String?,
        @PropertyElement(name = "ISP")
        val ISP: String?,
        @PropertyElement(name = "IMEI")
        val IMEI: String?,
        @PropertyElement(name = "ISP_name")
        val ISP_name: String?,
        @PropertyElement(name = "connect_mode")
        val connectMode: String?,
        @PropertyElement(name = "ICCID")
        val ICCID: String?,
        @PropertyElement(name = "IMEI_SV")
        val IMEI_SV: String?,
        @PropertyElement(name = "MSISDN")
        val MSISDN: String?,
        @PropertyElement(name = "network_name")
        val networkName: String?,
        @PropertyElement(name = "auto_apn")
        val autoApn: String?,
        @PropertyElement(name = "roaming_network_name")
        val roamingNetworkName: String?,
        @Element(name = "cellular")
        val cellular: Status1WanCellular?,
        @Element(name = "wifi")
        val wifi: Statu1WanWifi?,
        @PropertyElement(name = "LWG_flag")
        val LWG_flag: String?
    ) {
        @Xml
        data class Status1WanCellular(
            @PropertyElement(name = "sim_status")
            val simStatus: String?,
            @PropertyElement(name = "pin_status")
            val pinStatus: String?,
            @PropertyElement(name = "pin_attempts")
            val pinAttempts: String?,
            @PropertyElement(name = "puk_attempts")
            val pukAttempts: String?,
            @PropertyElement(name = "rssi")
            val rssi: String?,
            @PropertyElement(name = "roaming")
            val roaming: String?,
            @Path("pdp_context_list")
            @Element(name = "Item")
            val pdpContextList: List<Status1WanCellularPdpContext?>,
            @Path("pdp_auto_list")
            @Element(name = "Item")
            val pdpAutoList: List<Status1WanCellularPdpAuto?>
        ) {
            @Xml
            data class Status1WanCellularPdpContext(
                @Attribute(name = "index")
                val index: String?,

                @PropertyElement(name = "rulename")
                val ruleName: String?,
                @PropertyElement(name = "connnum")
                val connNum: String?,
                @PropertyElement(name = "pconnnum")
                val pConnNum: String?,
                @PropertyElement(name = "success")
                val success: String?,
                @PropertyElement(name = "default")
                val d3fault: String?,
                @PropertyElement(name = "secondary")
                val secondary: String?,
                @PropertyElement(name = "ipv4")
                val ipv4: String?,
                @PropertyElement(name = "v4dns1")
                val v4dns1: String?,
                @PropertyElement(name = "v4dns2")
                val v4dns2: String?,
                @PropertyElement(name = "v4gateway")
                val v4gateway: String?,
                @PropertyElement(name = "v4netmask")
                val v4netmask: String?,
                @PropertyElement(name = "ipv6")
                val ipv6: String?,
                @PropertyElement(name = "g_ipv6")
                val g_ipv6: String?,
                @PropertyElement(name = "v6dns1")
                val v6dns1: String?,
                @PropertyElement(name = "v6dns2")
                val v6dns2: String?,
                @PropertyElement(name = "v6gateway")
                val v6gateway: String?,
                @PropertyElement(name = "v6netmask")
                val v6netmask: String?,
                @PropertyElement(name = "curconntime")
                val curConnTime: Long?,
                @PropertyElement(name = "totalconntime")
                val totalConnTime: Long?
            )

            @Xml
            data class Status1WanCellularPdpAuto(
                @Attribute(name = "index")
                val index: String?,

                @PropertyElement(name = "mmc")
                val mmc: String?,
                @PropertyElement(name = "mnc")
                val mnc: String?,
                @PropertyElement(name = "operator_name")
                val operatorName: String?,
                @PropertyElement(name = "apn")
                val apn: String?,
                @PropertyElement(name = "lte_apn")
                val lteApn: String?,
                @PropertyElement(name = "network_type")
                val networkType: String?,
                @PropertyElement(name = "authtype2g3g")
                val authType2g3g: String?,
                @PropertyElement(name = "username2g3g")
                val username2g3g: String?,
                @PropertyElement(name = "password2g3g")
                val password2g3g: String?,
                @PropertyElement(name = "authtype4g")
                val authType4g: String?,
                @PropertyElement(name = "username4g")
                val username4g: String?,
                @PropertyElement(name = "password4g")
                val password4g: String?,
                @PropertyElement(name = "iptype")
                val ipType: String?
            )
        }

        @Xml
        data class Statu1WanWifi(
            @PropertyElement(name = "ssid")
            val ssid: String?,
            @PropertyElement(name = "enc")
            val enc: String?,
            @PropertyElement(name = "cipher")
            val cipher: String?,
            @PropertyElement(name = "signal")
            val signal: String?
        )
    }

    @Xml
    data class Status1WlanSettings(
        @PropertyElement(name = "channel")
        val channel: String?,
        @PropertyElement(name = "wlan_enable")
        val wlanEnable: String?,
        @PropertyElement(name = "current_channel")
        val currentChannel: String?
    )

    @Xml
    data class Status1WlanSecurity(
        @PropertyElement(name = "ssid")
        val ssid: String?,
        @PropertyElement(name = "mode")
        val mode: String?,
        @Element(name = "WPA2-PSK")
        val wpa2psk: Status1WlanSecurityWpa2Psk?,
        @Element(name = "Mixed")
        val mixed: Status1WlanSecurityMixed?,
        @Element(name = "WPA-PSK")
        val wpaPsk: Status1WlanSecurityWpaPsk?,
        @Element(name = "WEP")
        val wep: Status1WlanSecurityWep?,
        @Element(name = "WAPI-PSK")
        val wapiPsk: Status1WlanSecurityWapiPsk?
    ) {
        @Xml
        data class Status1WlanSecurityWpa2Psk(
            @PropertyElement(name = "key")
            val key: String?,
            @PropertyElement(name = "rekey")
            val rekey: String?
        )

        @Xml
        data class Status1WlanSecurityMixed(
            @PropertyElement(name = "key")
            val key: String?,
            @PropertyElement(name = "rekey")
            val rekey: String?
        )

        @Xml
        data class Status1WlanSecurityWpaPsk(
            @PropertyElement(name = "key")
            val key: String?,
            @PropertyElement(name = "rekey")
            val rekey: String?
        )

        @Xml
        data class Status1WlanSecurityWep(
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
        data class Status1WlanSecurityWapiPsk(
            @PropertyElement(name = "key_type")
            val keyType: String?,
            @PropertyElement(name = "key")
            val key: String?
        )
    }

    @Xml
    data class Status1Storage(
        @PropertyElement(name = "enabled")
        val enabled: String?
    )

    @Xml
    data class Stautus1Statistics(
        @Element(name = "WanStatistics")
        val wanStatistics: Stautus1StatisticsWanStatistics
    ) {
        @Xml
        data class Stautus1StatisticsWanStatistics(
            @PropertyElement(name = "rx")
            val rx: String?,
            @PropertyElement(name = "tx")
            val tx: String?,
            @PropertyElement(name = "errors")
            val errors: String?,
            @PropertyElement(name = "rx_byte")
            val rxByte: Long?,
            @PropertyElement(name = "tx_byte")
            val txByte: Long?,
            @PropertyElement(name = "rx_byte_all")
            val rxByteAll: Long?,
            @PropertyElement(name = "tx_byte_all")
            val txByteAll: Long?,
            @PropertyElement(name = "mi_set_flag")
            val miSetFlag: String?,
            @PropertyElement(name = "stat_mang_method")
            val statMangMethod: String?,
            @PropertyElement(name = "upper_value_total")
            val upperValueTotal: String?,
            @PropertyElement(name = "upper_value_month")
            val upperValueMonth: String?,
            @PropertyElement(name = "upper_value_period")
            val upperValuePeriod: String?,
            @PropertyElement(name = "upper_value_daily")
            val upperValueDaily: String?,
            @PropertyElement(name = "upper_value_unlimit")
            val upperValueUnlimit: String?,
            @PropertyElement(name = "total_used_month")
            val totalUsedMonth: Long,
            @PropertyElement(name = "total_used_period")
            val totalUsedPeriod: Long,
            @PropertyElement(name = "total_used_unlimit")
            val totalUsedUnlimit: String?,
            @PropertyElement(name = "total_used_daily")
            val totalUsedDaily: Long,
            @PropertyElement(name = "warning_value")
            val warningValue: String?
        )
    }

    @Xml
    data class Status1Lan(
        @Element(name = "dhcp")
        val dhcp: Status1LanDhcp?,
        @PropertyElement(name = "ip")
        val ip: String?,
        @PropertyElement(name = "option_43")
        val option43: String?,
        @PropertyElement(name = "mac")
        val mac: String?,
        @PropertyElement(name = "mask")
        val mask: String?,
        @PropertyElement(name = "run_days")
        val runDays: Int?,
        @PropertyElement(name = "run_hours")
        val runHours: Int?,
        @PropertyElement(name = "run_minutes")
        val runMinutes: Int?,
        @PropertyElement(name = "run_seconds")
        val runSeconds: Int?
    ) {
        @Xml
        data class Status1LanDhcp(
            @PropertyElement(name = "status")
            val status: String?
        )
    }

    @Xml
    data class Status1DeviceManagement(
        @PropertyElement(name = "nr_connected_dev")
        val nrConnectedDev: String?
    )

    @Xml
    data class Status1Message(
        @Element(name = "sms_capacity_info")
        val smsCapacityInfo: Status1MessageSmsCapacityInfo?,
        @PropertyElement(name = "new_sms_num")
        val newSmsNum: String?
    ) {
        @Xml
        data class Status1MessageSmsCapacityInfo(
            @PropertyElement(name = "sms_unread_long_num")
            val smsUnreadLongNum: String?
        )
    }

    @Xml
    data class Status1AutoReboot(
        @PropertyElement(name = "autoreboot_enabled")
        val autoRebootEnabled: Boolean?,
        @PropertyElement(name = "autoreboot_time")
        val autoRebootTime: String?
    )
}