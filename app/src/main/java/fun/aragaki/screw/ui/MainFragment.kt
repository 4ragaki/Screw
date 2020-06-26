package `fun`.aragaki.screw.ui

import `fun`.aragaki.screw.R
import `fun`.aragaki.screw.databinding.FragmentMainBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)
        val icons = arrayOf(
            R.drawable.ic_tab_config, R.drawable.ic_tab_dashboard, R.drawable.ic_tab_preferences
        )

        binding.pagerMain.apply {
            adapter = MainPagerAdapter(childFragmentManager, lifecycle)
        }

        TabLayoutMediator(binding.tabMain, binding.pagerMain, true, true) { tab, position ->
            tab.setIcon(icons[position])
            binding.pagerMain.setCurrentItem(position, true)
        }.attach()

        binding.pagerMain.currentItem = 1
    }
}