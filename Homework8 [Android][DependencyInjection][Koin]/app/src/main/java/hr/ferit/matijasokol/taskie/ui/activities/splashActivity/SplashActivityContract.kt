package hr.ferit.matijasokol.taskie.ui.activities.splashActivity

interface SplashActivityContract {

    interface View{
        fun onTokenEmpty()
        fun onTokenNotEmpty()
    }

    interface Presenter{
        fun setView(view: View)
        fun checkPrefs()
    }
}