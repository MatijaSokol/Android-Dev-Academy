package hr.ferit.matijasokol.movieapp.ui.gridFragment

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
import hr.ferit.matijasokol.movieapp.networking.BackendFactory
import hr.ferit.matijasokol.movieapp.presenters.MoviesGridFragmentPresenter
import hr.ferit.matijasokol.movieapp.ui.adapters.MoviesGridAdapter
import hr.ferit.matijasokol.movieapp.ui.app.App
import hr.ferit.matijasokol.movieapp.ui.fragments.MoviesPagerFragment
import kotlinx.android.synthetic.main.fragemnt_movie_grid.*

class MoviesGridFragment : Fragment(), MoviesGridFragmentContract.View{

    private val SPAN_COUNT = 2

    private val gridAdapter by lazy {
        MoviesGridAdapter(
            { onMovieClicked(it) },
            { onFavoriteClicked(it) })
    }

    private val presenter: MoviesGridFragmentContract.Presenter by lazy {
        MoviesGridFragmentPresenter(
            BackendFactory.getMovieInteractor(),
            MoviesDatabase.getInstance(App.getAppContext()))
    }

    private val movieList = arrayListOf<Movie>()
    private val favouriteMovieList = arrayListOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragemnt_movie_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesGrid.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }

        presenter.onMoviesRequest()
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        presenter.onMoviesRequest()
    }

    private fun onMovieClicked(movie: Movie) {
        activity?.showFragment(R.id.mainFragmentHolder, MoviesPagerFragment.getInstance(movieList, movie), true)
    }

    private fun onFavoriteClicked(movie: Movie) {
        presenter.onFavouriteClicked(movie)
    }

    override fun onRequestSuccess(movies: ArrayList<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
        gridAdapter.setMovies(movieList)
        favouriteMovieList.addAll(presenter.getListOfFavourites())
        gridAdapter.setListForFavourite(favouriteMovieList)
    }

    override fun onRequestFailed() {
        activity?.displayToast(getString(R.string.failed))
    }

    override fun onAddFavourite(movie: Movie) {
        gridAdapter.setItemForFavourite(movie)
        activity?.displayToast("${movie.title} " + getString(R.string.added_in_fav))
    }

    override fun onRemoveFavourite(movie: Movie) {
        gridAdapter.removeItemForFavourite(movie)
        activity?.displayToast("${movie.title} " + getString(R.string.remov_fr_fav))
    }

    companion object{
        fun newInstance(): MoviesGridFragment =
            MoviesGridFragment()
    }

}