package `fun`.aragaki.screw.databinding

import `fun`.aragaki.screw.R
import android.text.format.Formatter
import android.widget.TextView
import androidx.databinding.BindingAdapter


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("format", "bytes", requireAll = true)
    fun bytes(textView: TextView, format: String, size: Long?) {
        size?.let {
            val context = textView.context
            textView.text =
                format.format(Formatter.formatFileSize(context, it))
        }
    }

    @JvmStatic
    @BindingAdapter("format", "seconds", requireAll = true)
    fun seconds(textView: TextView, format: String, seconds: Long?) {
        seconds?.let {
            textView.text =
                format.format(
                    String.format(
                        "%d:%02d:%02d",
                        seconds / 3600,
                        (seconds % 3600) / 60,
                        (seconds % 60)
                    )
                )
        }
    }

    @JvmStatic
    @BindingAdapter("date")
    fun date(textView: TextView, date: String?) {
        date?.let {
            textView.apply {
                text =
                    context.getString(R.string.fmt_msg_date).format(*it.split(',').toTypedArray())
            }
        }
    }
}