package hr.ferit.matijasokol.taskie.presenters

import android.util.Log
import hr.ferit.matijasokol.taskie.prefs.SharedPrefsHelper
import hr.ferit.matijasokol.taskie.ui.activities.splashActivity.SplashActivityContract

class SplashActivityPresenter(private val prefs: SharedPrefsHelper) : SplashActivityContract.Presenter{

    private lateinit var view: SplashActivityContract.View

    override fun setView(view: SplashActivityContract.View) {
        this.view = view
    }

    override fun checkPrefs() {
        Log.d("TOKEN", prefs.getUserToken())
        if (prefs.getUserToken().isEmpty()) view.onTokenEmpty() else view.onTokenNotEmpty()
    }
}