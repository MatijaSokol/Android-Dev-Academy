package hr.ferit.matijasokol.taskie.ui.fragments.aboutFragments

import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.ui.fragments.base.BaseFragment

class AboutAppFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int = R.layout.fragment_about_app

    companion object {
        fun newInstance(): AboutAppFragment {
            return AboutAppFragment()
        }
    }
}