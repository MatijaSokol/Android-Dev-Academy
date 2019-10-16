package hr.ferit.matijasokol.movieapp.ui.topMoviesFragment

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
import hr.ferit.matijasokol.movieapp.presenters.TopMoviesFragmentPresenter
import hr.ferit.matijasokol.movieapp.ui.adapters.TopMoviesAdapter
import hr.ferit.matijasokol.movieapp.ui.app.App
import hr.ferit.matijasokol.movieapp.ui.fragments.MoviesPagerFragment
import kotlinx.android.synthetic.main.fragment_top_movies.*

class TopMoviesFragments : Fragment(),
    TopMoviesFragmentContract.View {

    private val SPAN_COUNT = 2

    private val presenter: TopMoviesFragmentContract.Presenter by lazy {
        TopMoviesFragmentPresenter(
            BackendFactory.getMovieInteractor(),
            MoviesDatabase.getInstance(App.getAppContext()))
    }

    private val topMoviesAdapter by lazy {
        TopMoviesAdapter(
            { onMovieClicked(it) },
            { onFavoriteClicked(it) })
    }

    private var topMoviesList = arrayListOf<Movie>()
    private val favouriteMovieList = arrayListOf<Movie>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topMoviesGrid.apply {
            adapter = topMoviesAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }

        presenter.onMovieRequest()
    }

    private fun onMovieClicked(movie: Movie) {
        activity?.showFragment(R.id.mainFragmentHolder, MoviesPagerFragment.getInstance(topMoviesList, movie), true)
    }

    private fun onFavoriteClicked(movie: Movie) {
        presenter.onFavouriteClicked(movie)
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        presenter.onMovieRequest()
    }

    override fun onRequestSucces(movies: ArrayList<Movie>) {
        topMoviesList.clear()
        topMoviesList.addAll(movies)
        topMoviesAdapter.setData(topMoviesList)
        favouriteMovieList.addAll(presenter.getListOfFavourites())
        topMoviesAdapter.setListForFavourite(favouriteMovieList)
    }

    override fun onRequestFailed(){
        activity?.displayToast(getString(R.string.failed))
    }

    override fun onAddFavourite(movie: Movie) {
        /*movie.isFavorite = true
        favouriteMovieList.add(movie)*/
        topMoviesAdapter.setItemForFavourite(movie)
        activity?.displayToast("${movie.title} " + getString(R.string.added_in_fav))
    }

    override fun onRemoveFavourite(movie: Movie) {
        topMoviesAdapter.removeItemForFavourite(movie)
        activity?.displayToast("${movie.title} " + getString(R.string.remov_fr_fav))
    }

    companion object{
        fun newInstance(): TopMoviesFragments =
            TopMoviesFragments()
    }
}