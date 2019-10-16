package hr.ferit.matijasokol.movieapp.presenters

import hr.ferit.matijasokol.movieapp.database.MoviesDatabase
import hr.ferit.matijasokol.movieapp.model.Movie
import hr.ferit.matijasokol.movieapp.ui.favouriteFragment.FavouriteMoviesFragmentContract

class FavouriteMoviesFragmentPresenter(private val appDatabase: MoviesDatabase) : FavouriteMoviesFragmentContract.Preseneter{

    private lateinit var view: FavouriteMoviesFragmentContract.View

    override fun setView(view: FavouriteMoviesFragmentContract.View) {
        this.view = view
    }

    override fun onRequestFavouriteMovies() {
        val favouriteMovies = appDatabase.moviesDao().getFavoriteMovies()
        if (favouriteMovies.isEmpty())
            view.onEmptyListReceived()
        else {
            setListAsFavourite(favouriteMovies)
            view.onMoviesReceived(favouriteMovies)
        }

    }

    override fun onFavouriteClicked(movie: Movie) {
        appDatabase.moviesDao().deleteFavoriteMovie(movie)
        movie.isFavorite = !movie.isFavorite
        view.onFavouriteRemoved(movie)
    }

    private fun setListAsFavourite(movies: List<Movie>) = movies.forEach { it.isFavorite = true }
}