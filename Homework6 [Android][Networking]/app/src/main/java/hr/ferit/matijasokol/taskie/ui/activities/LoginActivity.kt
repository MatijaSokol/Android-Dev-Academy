package hr.ferit.matijasokol.taskie.ui.activities

import android.content.Intent
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.RESPONSE_OK
import hr.ferit.matijasokol.taskie.common.displayMessageFromCode
import hr.ferit.matijasokol.taskie.common.displayToast
import hr.ferit.matijasokol.taskie.common.onClick
import hr.ferit.matijasokol.taskie.model.request.UserDataRequest
import hr.ferit.matijasokol.taskie.model.response.LoginResponse
import hr.ferit.matijasokol.taskie.networking.BackendFactory
import hr.ferit.matijasokol.taskie.prefs.provideSharedPrefs
import hr.ferit.matijasokol.taskie.ui.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {

    private val interactor = BackendFactory.getTaskieInteractor()
    private val prefs = provideSharedPrefs()

    override fun getLayoutResourceId(): Int = R.layout.activity_login

    override fun setUpUi() {
        login.onClick { signInClicked() }
        goToLogin.onClick { goToRegistrationClicked() }
    }

    private fun signInClicked() {
        interactor.login(
            request = UserDataRequest(password = password.text.toString(), email = email.text.toString()),
            loginCallback = loginCallback()
        )
    }

    private fun loginCallback(): Callback<LoginResponse> = object : Callback<LoginResponse> {
        override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
            displayToast(getString(R.string.no_internet))
        }

        override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse(response.body())
                    else -> handleSomethingWentWrong()
                }
            }
            else displayMessageFromCode(response.code())
        }
    }

    private fun goToRegistrationClicked() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleOkResponse(loginReponse: LoginResponse?) {
        this.displayToast(getString(R.string.success_log))
        loginReponse?.token?.let { prefs.storeUserToken(it) }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleSomethingWentWrong() = this.displayToast(getString(R.string.sth_wrong))
}
