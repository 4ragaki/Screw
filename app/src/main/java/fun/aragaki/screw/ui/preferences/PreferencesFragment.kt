package `fun`.aragaki.screw.ui.preferences

import `fun`.aragaki.screw.R
import `fun`.aragaki.screw.ext.stopService
import `fun`.aragaki.screw.ext.tryForeground
import `fun`.aragaki.screw.services.NotificationService
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat

class PreferencesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        findPreference<CheckBoxPreference>(getString(R.string.pref_key_notification))?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue as Boolean)
                requireContext().tryForeground<NotificationService>()
            else requireActivity().stopService<NotificationService>()
            true
        }
    }
}