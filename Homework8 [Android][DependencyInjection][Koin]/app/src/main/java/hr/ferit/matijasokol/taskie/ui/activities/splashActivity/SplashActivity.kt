package hr.ferit.matijasokol.taskie.ui.activities.splashActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.ferit.matijasokol.taskie.prefs.provideSharedPrefs
import hr.ferit.matijasokol.taskie.presenters.SplashActivityPresenter
import hr.ferit.matijasokol.taskie.ui.activities.MainActivity
import hr.ferit.matijasokol.taskie.ui.activities.registerActivity.RegisterActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity(), SplashActivityContract.View {

    private val presenter by inject<SplashActivityContract.Presenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setView(this)
        presenter.checkPrefs()
    }

    override fun onTokenEmpty() = startApp()

    override fun onTokenNotEmpty() = startSignIn()

    private fun startApp() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun startSignIn() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }
}
