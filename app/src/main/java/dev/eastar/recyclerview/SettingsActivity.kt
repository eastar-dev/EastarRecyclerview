package dev.eastar.recyclerview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setIntent("ArrayAdapter", ArrayAdapterDemo::class.java)
            setIntent("ArrayAdapter", ArrayAdapterDemo::class.java)
            setIntent("ArrayAdapter", ArrayAdapterDemo::class.java)
            setIntent("ArrayAdapter", ArrayAdapterDemo::class.java)
            setIntent("ArrayAdapter", ArrayAdapterDemo::class.java)
            setIntent("ArrayAdapter", ArrayAdapterDemo::class.java)
        }
    }
}

fun PreferenceFragmentCompat.setIntent(key: String, clz: Class<out Activity>) {
    findPreference<Preference>(key)?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
        startActivity(Intent(context, clz))
        true
    }
}
