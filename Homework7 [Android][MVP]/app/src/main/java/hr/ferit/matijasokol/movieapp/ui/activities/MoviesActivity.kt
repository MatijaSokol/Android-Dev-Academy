package hr.ferit.matijasokol.movieapp.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import hr.ferit.matijasokol.movieapp.R
import hr.ferit.matijasokol.movieapp.common.showFragment
import hr.ferit.matijasokol.movieapp.ui.topMoviesFragment.TopMoviesFragments
import hr.ferit.matijasokol.movieapp.ui.favouriteFragment.FavouriteMoviesFragment
import hr.ferit.matijasokol.movieapp.ui.gridFragment.MoviesGridFragment
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        initUi()
    }

    private fun initUi(){
        showFragment(R.id.mainFragmentHolder,
            MoviesGridFragment()
        )
        bottomNavigation.setOnNavigationItemSelectedListener { openFragment(it) }
    }

    private fun openFragment(item: MenuItem): Boolean {
        var selectedFragment: Fragment? = null
        when(item.itemId){
            R.id.navList -> selectedFragment = MoviesGridFragment.newInstance()
            R.id.navFavourite -> selectedFragment = FavouriteMoviesFragment.newInstance()
            R.id.navTop -> selectedFragment = TopMoviesFragments.newInstance()
        }
        if (selectedFragment != null)
            showFragment(R.id.mainFragmentHolder, selectedFragment)

        return true
    }
}
