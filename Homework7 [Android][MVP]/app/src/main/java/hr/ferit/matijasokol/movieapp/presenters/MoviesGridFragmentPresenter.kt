package hr.ferit.matijasokol.movieapp.presenters

import hr.ferit.matijasokol.movieapp.database.MoviesDatabase
import hr.ferit.matijasokol.movieapp.model.Movie
import hr.ferit.matijasokol.movieapp.model.MoviesResponse
import hr.ferit.matijasokol.movieapp.networking.interactors.MovieInteractor
import hr.ferit.matijasokol.movieapp.ui.gridFragment.MoviesGridFragmentContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesGridFragmentPresenter(private val apiInteractor: MovieInteractor, private val appDatabase: MoviesDatabase) :
    MoviesGridFragmentContract.Presenter {

    private lateinit var view: MoviesGridFragmentContract.View

    override fun setView(view: MoviesGridFragmentContract.View) {
        this.view = view
    }

    override fun onMoviesRequest() {
        apiInteractor.getPopularMovies(popularMoviesCallback())
    }

    private fun popularMoviesCallback(): Callback<MoviesResponse> = object : Callback<MoviesResponse> {
        override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
            view.onRequestFailed()
        }

        override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
            if (response.isSuccessful) {
                response.body()?.movies?.run {
                    view.onRequestSuccess(this)
                }
            }
        }
    }

    override fun onFavouriteClicked(movie: Movie) {
        if (!isMovieInFavourites(movie)){
            appDatabase.moviesDao().addFavoriteMovie(movie)
            view.onAddFavourite(movie)
        } else {
            appDatabase.moviesDao().deleteFavoriteMovie(movie)
            view.onRemoveFavourite(movie)
        }
    }

    override fun getListOfFavourites(): List<Movie> = appDatabase.moviesDao().getFavoriteMovies()

    private fun isMovieInFavourites(movie: Movie): Boolean{
        if (appDatabase.moviesDao().getFavoriteMovies().contains(movie)) return true
        return false
    }
}