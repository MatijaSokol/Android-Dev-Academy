package hr.ferit.matijasokol.taskie.ui.fragments.aboutFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.ui.adapters.ScreenSlidePagerAdapter
import hr.ferit.matijasokol.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int = R.layout.fragment_about

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
    }

    private fun setUpViewPager(){
        viewPager.adapter = ScreenSlidePagerAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    companion object{
        fun newInstance(): Fragment {
            return AboutFragment()
        }
    }
}