package hr.ferit.matijasokol.taskie.persistence

import android.preference.PreferenceManager
import hr.ferit.matijasokol.taskie.app.Taskie

object PreferenceManager {

    const val KEY_NAME = "KEY_NAME"

    private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(Taskie.instance)

    fun savePriority(value: String){
        val editor = sharedPrefs().edit()
        editor.putString(KEY_NAME, value)
        editor.apply()
    }

    fun retrievePriority(): String? = sharedPrefs().getString(
        KEY_NAME, "unknown")

}