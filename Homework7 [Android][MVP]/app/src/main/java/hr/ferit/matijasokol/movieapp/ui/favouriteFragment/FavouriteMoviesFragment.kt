package hr.ferit.matijasokol.movieapp.ui.favouriteFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import hr.ferit.matijasokol.movieapp.R
import hr.ferit.matijasokol.movieapp.common.displayToast
import hr.ferit.matijasokol.movieapp.common.showFragment
import hr.ferit.matijasokol.movieapp.database.MoviesDatabase
import hr.ferit.matijasokol.movieapp.model.Movie
import hr.ferit.matijasokol.movieapp.presenters.FavouriteMoviesFragmentPresenter
import hr.ferit.matijasokol.movieapp.ui.adapters.FavouriteMoviesAdapter
import hr.ferit.matijasokol.movieapp.ui.app.App
import hr.ferit.matijasokol.movieapp.ui.fragments.MoviesPagerFragment
import kotlinx.android.synthetic.main.fragment_favorite_movies.*

class FavouriteMoviesFragment : Fragment(), FavouriteMoviesFragmentContract.View {

    private val SPAN_COUNT = 2

    private val presenter: FavouriteMoviesFragmentContract.Preseneter by lazy {
        FavouriteMoviesFragmentPresenter(MoviesDatabase.getInstance(App.getAppContext()))
    }

    private val gridFavouriteAdapter by lazy {
        FavouriteMoviesAdapter(
            { onMovieClicked(it) },
            { onFavoriteClicked(it) })
    }

    private var favouriteMoviesList = arrayListOf<Movie>()

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        presenter.onRequestFavouriteMovies()
    }

    private fun onMovieClicked(movie: Movie) {
        activity?.showFragment(
            R.id.mainFragmentHolder,
            MoviesPagerFragment.getInstance(
                favouriteMoviesList,
                movie
            ), true
        )
    }

    private fun onFavoriteClicked(movie: Movie){
        presenter.onFavouriteClicked(movie)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteMoviesGrid.apply {
            adapter = gridFavouriteAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
    }

    override fun onMoviesReceived(movies: List<Movie>) {
        favouriteMoviesList.clear()
        favouriteMoviesList.addAll(movies)
        gridFavouriteAdapter.setData(favouriteMoviesList)
    }

    override fun onFavouriteRemoved(movie: Movie) {
        gridFavouriteAdapter.removeData(movie)
        activity?.displayToast("${movie.title} " + getString(R.string.remov_fr_fav))
    }

    override fun onEmptyListReceived() {
        activity?.displayToast(getString(R.string.no_fav))
    }

    companion object{
        fun newInstance(): FavouriteMoviesFragment =
            FavouriteMoviesFragment()
    }
}