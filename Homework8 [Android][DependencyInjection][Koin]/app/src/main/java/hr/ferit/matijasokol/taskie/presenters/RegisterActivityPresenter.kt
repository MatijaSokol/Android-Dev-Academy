package hr.ferit.matijasokol.taskie.presenters

import hr.ferit.matijasokol.taskie.common.RESPONSE_OK
import hr.ferit.matijasokol.taskie.model.request.UserDataRequest
import hr.ferit.matijasokol.taskie.model.response.RegisterResponse
import hr.ferit.matijasokol.taskie.networking.interactors.TaskieInteractor
import hr.ferit.matijasokol.taskie.ui.activities.registerActivity.RegisterActivityContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivityPresenter(private val interactor: TaskieInteractor) : RegisterActivityContract.Presenter{

    private lateinit var view: RegisterActivityContract.View

    override fun setView(view: RegisterActivityContract.View) {
        this.view = view
    }

    override fun registerUser(email: String, password: String, name: String) {
        interactor.register(
            UserDataRequest(
                email = email,
                password = password,
                name = name
            ), registerCallback()
        )
    }

    private fun registerCallback(): Callback<RegisterResponse> = object : Callback<RegisterResponse> {
        override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
            view.onRegisterResponseFailure()
        }

        override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse()
                    else -> handleSomethingWentWrong()
                }
            }
            else view.onRegisterResponseFailureWithCode(response.code())
        }
    }

    private fun handleOkResponse() {
        view.onRegisterResponseSuccess()
    }

    private fun handleSomethingWentWrong(){
        view.onRegisterResponseWrong()
    }
}