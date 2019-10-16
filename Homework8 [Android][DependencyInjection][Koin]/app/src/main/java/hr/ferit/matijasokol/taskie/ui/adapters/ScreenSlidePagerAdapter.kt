package hr.ferit.matijasokol.taskie.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import hr.ferit.matijasokol.taskie.ui.fragments.aboutFragments.AboutAppFragment
import hr.ferit.matijasokol.taskie.ui.fragments.aboutFragments.AboutAuthorFragment

class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object{
        const val NUMBER_OF_PAGES = 2
        const val TAB_TEXT_APPLICATION = "APPLICATION"
        const val TAB_TEXT_AUTHOR = "AUTHOR"
    }

    private val fragments = arrayOf(AboutAppFragment.newInstance(), AboutAuthorFragment.newInstance())
    private val titles = arrayOf(
        TAB_TEXT_APPLICATION,
        TAB_TEXT_AUTHOR
    )

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    override fun getCount(): Int = NUMBER_OF_PAGES

}