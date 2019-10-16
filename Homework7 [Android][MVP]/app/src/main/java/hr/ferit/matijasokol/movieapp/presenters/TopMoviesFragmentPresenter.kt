package hr.ferit.matijasokol.movieapp.presenters

import hr.ferit.matijasokol.movieapp.database.MoviesDatabase
import hr.ferit.matijasokol.movieapp.model.Movie
import hr.ferit.matijasokol.movieapp.model.MoviesResponse
import hr.ferit.matijasokol.movieapp.networking.interactors.MovieInteractor
import hr.ferit.matijasokol.movieapp.ui.topMoviesFragment.TopMoviesFragmentContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopMoviesFragmentPresenter(private val apiInteractor: MovieInteractor, private val appDatabase: MoviesDatabase) : TopMoviesFragmentContract.Presenter{

    private lateinit var view: TopMoviesFragmentContract.View

    override fun setView(view: TopMoviesFragmentContract.View) {
        this.view = view
    }

    override fun onMovieRequest() {
        apiInteractor.getTopMovies(topMoviesCallback())
    }

    private fun topMoviesCallback(): Callback<MoviesResponse> = object : Callback<MoviesResponse> {
        override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
            view.onRequestFailed()
        }

        override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
            if (response.isSuccessful) {
                response.body()?.movies?.run {
                    view.onRequestSucces(this)
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