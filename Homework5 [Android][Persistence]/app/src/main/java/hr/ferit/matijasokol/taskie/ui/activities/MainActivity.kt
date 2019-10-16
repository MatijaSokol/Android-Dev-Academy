package hr.ferit.matijasokol.taskie.ui.activities

import android.view.MenuItem
import androidx.fragment.app.Fragment
import hr.ferit.matijasokol.taskie.R
import hr.ferit.matijasokol.taskie.ui.activities.base.BaseActivity
import hr.ferit.matijasokol.taskie.ui.fragments.AboutFragment
import hr.ferit.matijasokol.taskie.ui.fragments.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {
        showFragment(TasksFragment.newInstance())
        bottomNavigation.setOnNavigationItemSelectedListener { openFragment(it) }
    }

    private fun openFragment(item: MenuItem): Boolean {
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.navList -> selectedFragment = TasksFragment.newInstance()
            R.id.navAbout -> selectedFragment = AboutFragment.newInstance()
        }
        if (selectedFragment != null)
            showFragment(selectedFragment)

        return true
    }
}
