package hr.ferit.matijasokol.movieapp.ui.app

import android.app.Application
import android.content.Context

class App: Application(){

    companion object {
        private lateinit var instance: App

        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

}