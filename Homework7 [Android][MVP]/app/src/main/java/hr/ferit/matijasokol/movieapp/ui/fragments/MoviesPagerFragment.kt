package hr.ferit.matijasokol.movieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hr.ferit.matijasokol.movieapp.R
import hr.ferit.matijasokol.movieapp.model.Movie
import hr.ferit.matijasokol.movieapp.ui.adapters.MoviePagerAdapter
import kotlinx.android.synthetic.main.fragment_pager.*

class MoviesPagerFragment : Fragment() {

    companion object {
        private const val PAGER_LIST_EXTRA = "list_extra"
        private const val PAGER_SELECTED_MOVIE_EXTRA = "selected_movie"

        fun getInstance(movieList: ArrayList<Movie>, selectedMovie: Movie): MoviesPagerFragment {
            val pagerFragment = MoviesPagerFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(PAGER_LIST_EXTRA, movieList)
            bundle.putParcelable(PAGER_SELECTED_MOVIE_EXTRA, selectedMovie)
            pagerFragment.arguments = bundle
            return pagerFragment
        }
    }

    private val movieList = mutableListOf<Movie>()
    private val pagerAdapter by lazy {
        MoviePagerAdapter(
            childFragmentManager
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        movieList.addAll(arguments?.getParcelableArrayList(PAGER_LIST_EXTRA)!!)

        moviePager.adapter = pagerAdapter

        pagerAdapter.setMovies(movieList)

        moviePager.currentItem = arguments?.getParcelable<Movie>(
            PAGER_SELECTED_MOVIE_EXTRA).let { movieList.indexOf(it) }
    }

}