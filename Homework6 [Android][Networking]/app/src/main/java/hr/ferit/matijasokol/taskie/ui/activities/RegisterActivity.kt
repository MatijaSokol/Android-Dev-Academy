package hr.ferit.matijasokol.taskie.ui.activities

import android.content.Intent
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.common.*
import hr.ferit.matijasokol.taskie.model.request.UserDataRequest
import hr.ferit.matijasokol.taskie.model.response.RegisterResponse
import hr.ferit.matijasokol.taskie.networking.BackendFactory
import hr.ferit.matijasokol.taskie.ui.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : BaseActivity() {

    private val interactor = BackendFactory.getTaskieInteractor()

    override fun getLayoutResourceId(): Int = R.layout.activity_register

    override fun setUpUi() {
        register.onClick { signInClicked() }
        goToLogin.onClick { goToLoginClicked() }
    }

    private fun signInClicked() {
        interactor.register(
            UserDataRequest(
                email = email.text.toString(),
                password = password.text.toString(),
                name = name.text.toString()
            ), registerCallback()
        )
    }

    private fun registerCallback(): Callback<RegisterResponse> = object : Callback<RegisterResponse> {
        override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
            displayToast(getString(R.string.no_internet))
        }

        override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse()
                    else -> handleSomethingWentWrong()
                }
            }
            else displayMessageFromCode(response.code())
        }
    }

    private fun goToLoginClicked() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleOkResponse() {
        this.displayToast(getString(R.string.success_reg))
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleSomethingWentWrong() = this.displayToast(getString(R.string.sth_wrong))
}
