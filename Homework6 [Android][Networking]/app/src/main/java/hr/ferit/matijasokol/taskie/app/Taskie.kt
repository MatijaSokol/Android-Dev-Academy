package hr.ferit.matijasokol.taskie.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import hr.ferit.matijasokol.taskie.prefs.PREFERENCES_NAME

class Taskie: Application() {

    companion object {
        lateinit var instance: Taskie
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    fun providePreferences(): SharedPreferences = instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
}