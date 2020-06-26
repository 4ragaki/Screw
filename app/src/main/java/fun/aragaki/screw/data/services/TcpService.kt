package `fun`.aragaki.screw.data.services

import `fun`.aragaki.screw.Settings
import `fun`.aragaki.screw.data.entities.MiFiInformation
import `fun`.aragaki.screw.ext.toHex
import android.util.Log
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.ByteOrder

class TcpService(private val settings: Settings) {
    private val tag = javaClass.simpleName
    private var socket = connect()
    val status = MiFiInformation()

    fun trySend(packet: ByteArray, initOnFail: Boolean = true) {
        kotlin.runCatching { send(packet) }.onFailure {
            it.printStackTrace()
            socket = connect()
            if (initOnFail) initService()
            send(packet)
        }
    }

    private fun send(packet: ByteArray) {
        socket.getOutputStream().apply {
            write(packet.also { Log.i(tag, "--> ${it.toHex()}") })
            flush()
        }
        receive().let {
            Log.i(tag, "<-- ${it.toHex()}")
            dispatch(it)
        }
    }

    private fun receive(): Pair<ByteArray, ByteArray?> {
        val cmdBytes = ByteArray(3)
        val input = socket.getInputStream()
        if (input.read(cmdBytes) == 3 && cmdBytes[2] > 0) {
            val dataBytes = ByteArray(cmdBytes[2].toInt())
            input.read(dataBytes)
            return Pair(cmdBytes, dataBytes)
        }
        return Pair(cmdBytes, null)
    }

    private fun dispatch(bytes: Pair<ByteArray, ByteArray?>) = status.apply {
        val side = bytes.first[0]
        val cmd = bytes.first[1]
        val data = bytes.second
        if (side == SIDE_SERVER) {
            when (cmd) {
                CMD_MIFI_INFO -> setDeviceInformation(data)
                CMD_WAN_STATISTICS -> setWanStatistics(data)
                CMD_WAN_STATISTICS_PLAN -> setWanStatisticsPlan(data)
                CMD_AUTH -> setAuthFlagVersion(data).also { onAuth() }
                CMD_TF_FREE_SIZE -> setTFFreeSize(data)
                CMD_WAN_SPEED -> setWanSpeed(data)
//                CMD_SET_DEV_INFO_MASK -> set(data)
                CMD_BIND_MIFI -> setAuthFlagVersion(data)
                CMD_NEW_SMS_NUM -> setNewSmsNum(data)
                CMD_GET_NETWORK_NAME -> setNetworkName(data)
            }
        }

        Log.i(tag, "-------------------")
        Log.i(tag, this.toString())
        Log.i(tag, "-------------------")
    }

    private fun onAuth() {
        if (!status.mbAuth) {
            socket.close()
            socket = connect()
            initService(genPacket(CMD_BIND_MIFI, settings.password.value.toByteArray()))
        }
    }

    fun initService(auth: ByteArray = genPacket(CMD_AUTH, settings.password.value.toByteArray())) {
        kotlin.runCatching {
            doInitService(auth)
        }.onFailure {
            it.printStackTrace()
            socket = connect()
            doInitService(auth)
        }
    }

    private fun doInitService(auth: ByteArray) {
        send(auth)
        send(genPacket(CMD_WAN_STATISTICS_PLAN))
        send(genPacket(CMD_GET_NETWORK_NAME))
    }

    private fun connect() = Socket(settings.gateway, 28009).apply {
        keepAlive = true
        soTimeout = 5000
    }

    fun genSetDevInfoMask(mask: Int) = ByteArray(7).apply {
        this[0] = SIDE_CLIENT
        this[1] = CMD_SET_DEV_INFO_MASK
        this[2] = Int.SIZE_BYTES.toByte()
        val wrap = ByteBuffer.wrap(this)
        wrap.order(ByteOrder.LITTLE_ENDIAN)
        wrap.position(3)
        wrap.putInt(mask)
    }

    fun genPacket(cmd: Byte, data: ByteArray? = null) = data?.let {
        ByteArray(data.size + 3) {
            when (it) {
                0 -> SIDE_CLIENT
                1 -> cmd
                2 -> data.size.toByte()
                else -> data[it - 3]
            }
        }
    } ?: byteArrayOf(SIDE_CLIENT, cmd, 0)

    companion object {
        const val SIDE_SERVER = 0x1.toByte()
        const val SIDE_CLIENT = 0x2.toByte()

        const val CMD_MIFI_INFO = 0x1.toByte()
        const val CMD_WAN_STATISTICS = 0x2.toByte()
        const val CMD_WAN_STATISTICS_PLAN = 0x3.toByte()
        const val CMD_AUTH = 0x4.toByte()
        const val CMD_TF_FREE_SIZE = 0x5.toByte()
        const val CMD_WAN_SPEED = 0x6.toByte()
        const val CMD_SET_DEV_INFO_MASK = 0x7.toByte()
        const val CMD_BIND_MIFI = 0x8.toByte()
        const val CMD_NEW_SMS_NUM = 0x9.toByte()
        const val CMD_GET_NETWORK_NAME = 0xA.toByte()
    }
}