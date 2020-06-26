package `fun`.aragaki.screw.ui

import `fun`.aragaki.screw.ui.configs.ConfigsFragment
import `fun`.aragaki.screw.ui.dashboard.DashboardFragment
import `fun`.aragaki.screw.ui.preferences.PreferencesFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ConfigsFragment()
            1 -> DashboardFragment()
            2 -> PreferencesFragment()
            else -> throw Exception("this shouldn't happen ;(")
        }
    }
}