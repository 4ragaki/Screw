package `fun`.aragaki.screw.ext

fun ByteArray.toHex() = buildString {
    append("{ ")
    append(this@toHex.joinToString(",") { "%02X".format(it) })
    append(" }")
}

fun Pair<ByteArray, ByteArray?>.toHex() = buildString {
    append("{ ")
    append(this@toHex.first.joinToString(",") { "%02X".format(it) })
    second?.let {
        append(',')
        append(it.joinToString(",") { "%02X".format(it) })
    }
    append(" }")
}