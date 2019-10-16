package hr.ferit.matijasokol.taskie.ui.activities.registerActivity

interface RegisterActivityContract {

    interface View{
        fun onRegisterResponseSuccess()
        fun onRegisterResponseFailure()
        fun onRegisterResponseFailureWithCode(code: Int)
        fun onRegisterResponseWrong()
    }

    interface Presenter{
        fun setView(view: View)
        fun registerUser(email: String, password: String, name: String)
    }
}