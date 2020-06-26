package `fun`.aragaki.screw.ui

import `fun`.aragaki.screw.R
import `fun`.aragaki.screw.databinding.ActivityMainBinding
import `fun`.aragaki.screw.ext.setupTransitions
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.android.retainedDI

class MainActivity : AppCompatActivity(), DIAware {
    private val parentDI by closestDI()
    override val di: DI by retainedDI {
        extend(parentDI)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTransitions(
            R.transition.explode, R.transition.fade,
            R.transition.explode, R.transition.fade
        )
    }
}