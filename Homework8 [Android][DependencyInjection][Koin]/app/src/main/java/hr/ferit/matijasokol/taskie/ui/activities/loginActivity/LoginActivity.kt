package hr.ferit.matijasokol.taskie.ui.activities.loginActivity

import android.content.Intent
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.displayMessageFromCode
import hr.ferit.matijasokol.taskie.common.displayToast
import hr.ferit.matijasokol.taskie.common.onClick
import hr.ferit.matijasokol.taskie.ui.activities.MainActivity
import hr.ferit.matijasokol.taskie.ui.activities.base.BaseActivity
import hr.ferit.matijasokol.taskie.ui.activities.registerActivity.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity(), LoginActivityContract.View {

    private val presenter by inject<LoginActivityContract.Presenter>()

    override fun getLayoutResourceId(): Int = R.layout.activity_login

    override fun setUpUi() {
        login.onClick { signInClicked() }
        goToLogin.onClick { goToRegistrationClicked() }
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    private fun signInClicked() {
        presenter.loginUser(password.text.toString(), email.text.toString())
    }

    override fun onLoginResponseSuccess() {
        this.displayToast(getString(R.string.success_log))
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginResponseFailure() {
        displayToast(getString(R.string.no_internet))
    }

    override fun onLoginResponseFailureWithCode(code: Int) {
        displayMessageFromCode(code)
    }

    override fun onLoginResponseWrong() {
        this.displayToast(getString(R.string.sth_wrong))
    }

    private fun goToRegistrationClicked() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }


}
