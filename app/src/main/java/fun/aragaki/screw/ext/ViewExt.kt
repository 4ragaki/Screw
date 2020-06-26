package `fun`.aragaki.screw.ext

import android.animation.Animator
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.animation.addListener
import com.google.android.material.snackbar.Snackbar

fun View.withCircularReveal(
    cX: Int, cY: Int, startR: Float,
    endR: Float, onStart: (Animator) -> Unit
): Animator = ViewAnimationUtils.createCircularReveal(this, cX, cY, startR, endR).apply {
    addListener(onStart = onStart)
    duration = 800
    start()
}

fun View.snack(string: String, len: Int = Snackbar.LENGTH_LONG) =
    Snackbar.make(this, string, len).show()

fun View.snack(strId: Int, len: Int = Snackbar.LENGTH_LONG) =
    Snackbar.make(this, strId, len).show()