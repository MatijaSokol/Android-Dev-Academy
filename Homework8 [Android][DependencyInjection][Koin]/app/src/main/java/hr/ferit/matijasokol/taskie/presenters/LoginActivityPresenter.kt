package hr.ferit.matijasokol.taskie.presenters

import hr.ferit.matijasokol.taskie.common.RESPONSE_OK
import hr.ferit.matijasokol.taskie.model.request.UserDataRequest
import hr.ferit.matijasokol.taskie.model.response.LoginResponse
import hr.ferit.matijasokol.taskie.networking.interactors.TaskieInteractor
import hr.ferit.matijasokol.taskie.prefs.SharedPrefsHelper
import hr.ferit.matijasokol.taskie.ui.activities.loginActivity.LoginActivityContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivityPresenter(private val interactor: TaskieInteractor, private val prefs: SharedPrefsHelper) : LoginActivityContract.Presenter{

    private lateinit var view: LoginActivityContract.View

    override fun setView(view: LoginActivityContract.View) {
        this.view = view
    }

    override fun loginUser(password: String, email: String) {
        interactor.login(
            request = UserDataRequest(password = password, email = email),
            loginCallback = loginCallback()
        )
    }

    private fun loginCallback(): Callback<LoginResponse> = object : Callback<LoginResponse> {
        override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
            view.onLoginResponseFailure()
        }

        override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse(response.body())
                    else -> handleSomethingWentWrong()
                }
            }
            else view.onLoginResponseFailureWithCode(response.code())
        }
    }

    private fun handleOkResponse(loginReponse: LoginResponse?) {
        loginReponse?.token?.let { prefs.storeUserToken(it) }
        view.onLoginResponseSuccess()
    }

    private fun handleSomethingWentWrong(){
        view.onLoginResponseWrong()
    }
}