package hr.ferit.matijasokol.taskie.ui.fragments.aboutFragments

import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.ui.fragments.base.BaseFragment

class AboutAuthorFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int = R.layout.fragment_about_author

    companion object {
        fun newInstance(): AboutAuthorFragment {
            return AboutAuthorFragment()
        }
    }
}