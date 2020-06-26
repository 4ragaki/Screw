package `fun`.aragaki.screw.ext

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.annotation.TransitionRes

inline fun <reified C : Activity> Activity.startActivity() {
    startActivity(
        Intent(this, C::class.java),
        ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
    )
}

fun Activity.setupTransitions(
    @TransitionRes enter: Int, @TransitionRes exit: Int,
    @TransitionRes reenter: Int, @TransitionRes `return`: Int
) {
    val inflater = android.transition.TransitionInflater.from(this)
    window.enterTransition = inflater.inflateTransition(enter)
    window.exitTransition = inflater.inflateTransition(exit)
    window.reenterTransition = inflater.inflateTransition(reenter)
    window.returnTransition = inflater.inflateTransition(`return`)
}