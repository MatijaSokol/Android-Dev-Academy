package hr.ferit.matijasokol.taskie.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import hr.ferit.matijasokol.taskie.prefs.provideSharedPrefs

class SplashActivity : AppCompatActivity() {

    private val prefs = provideSharedPrefs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPrefs()
    }

    private fun checkPrefs() {
        Log.d("TOKEN", prefs.getUserToken())
        if (prefs.getUserToken().isEmpty()) startSignIn() else startApp()
    }

    private fun startApp() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun startSignIn() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }
}
