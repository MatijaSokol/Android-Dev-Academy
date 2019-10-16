package hr.ferit.matijasokol.taskie.ui.activities.loginActivity

interface LoginActivityContract {

    interface View{
        fun onLoginResponseSuccess()
        fun onLoginResponseFailure()
        fun onLoginResponseFailureWithCode(code: Int)
        fun onLoginResponseWrong()
    }

    interface Presenter{
        fun setView(view: View)
        fun loginUser(password: String, email: String)
    }
}