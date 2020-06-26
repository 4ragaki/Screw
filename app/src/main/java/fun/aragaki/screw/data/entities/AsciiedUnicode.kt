package `fun`.aragaki.screw.data.entities

import `fun`.aragaki.screw.ext.toUnicode
import com.tickaroo.tikxml.TypeConverter

class AsciiedUnicode(
    val ascii: String?
) {
    private val unicode = ascii?.toUnicode()

    override fun toString(): String {
        return unicode ?: ""
    }

    class Converter : TypeConverter<AsciiedUnicode> {
        override fun write(value: AsciiedUnicode?): String {
            return value?.ascii ?: ""
        }

        override fun read(value: String?): AsciiedUnicode {
            return AsciiedUnicode(value)
        }
    }
}