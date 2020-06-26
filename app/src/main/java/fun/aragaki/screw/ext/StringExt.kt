package `fun`.aragaki.screw.ext

import okhttp3.internal.and
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest

fun String.MD5(): String {
    MessageDigest.getInstance("MD5").apply {
        update(toByteArray())
        return BigInteger(1, digest()).toString(16)
    }
}

fun String.toUnicode() = buildString {
    this@toUnicode.chunked(4).forEach {
        append(it.toLong(16).toChar())
    }
}

fun String.toAscii() = buildString {
    this@toAscii.toByteArray(Charset.forName("UTF-16BE")).run {
        for (i in indices) {
            append(((get(i).and(255)) + 256).toString(16).substring(1))
        }
    }
}