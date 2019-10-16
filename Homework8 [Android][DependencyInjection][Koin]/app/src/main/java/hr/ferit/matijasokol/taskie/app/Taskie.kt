package hr.ferit.matijasokol.taskie.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import hr.ferit.matijasokol.taskie.di.interactorModule
import hr.ferit.matijasokol.taskie.di.networkingModule
import hr.ferit.matijasokol.taskie.di.presentationModule
import hr.ferit.matijasokol.taskie.di.repositoryModule
import hr.ferit.matijasokol.taskie.prefs.PREFERENCES_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Taskie: Application() {

    companion object {
        lateinit var instance: Taskie
            private set
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(interactorModule, networkingModule, presentationModule, repositoryModule))
            androidContext(this@Taskie)
        }
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    fun providePreferences(): SharedPreferences = instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
}