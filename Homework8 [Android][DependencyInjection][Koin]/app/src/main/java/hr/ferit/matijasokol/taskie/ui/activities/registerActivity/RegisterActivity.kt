package hr.ferit.matijasokol.taskie.ui.activities.registerActivity

import android.content.Intent
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.*
import hr.ferit.matijasokol.taskie.networking.BackendFactory
import hr.ferit.matijasokol.taskie.presenters.RegisterActivityPresenter
import hr.ferit.matijasokol.taskie.ui.activities.loginActivity.LoginActivity
import hr.ferit.matijasokol.taskie.ui.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject

class RegisterActivity : BaseActivity(), RegisterActivityContract.View {

    private val presenter by inject<RegisterActivityContract.Presenter>()

    override fun getLayoutResourceId(): Int = R.layout.activity_register

    override fun setUpUi() {
        register.onClick { signInClicked() }
        goToLogin.onClick { goToLoginClicked() }
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    override fun onRegisterResponseSuccess() {
        this.displayToast(getString(R.string.success_reg))
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onRegisterResponseFailure() {
        displayToast(getString(R.string.no_internet))
    }

    override fun onRegisterResponseFailureWithCode(code: Int) {
        displayMessageFromCode(code)
    }

    override fun onRegisterResponseWrong() {
        this.displayToast(getString(R.string.sth_wrong))
    }

    private fun signInClicked() {
        presenter.registerUser(email.text.toString(), password.text.toString(), name.text.toString())
    }

    private fun goToLoginClicked() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
