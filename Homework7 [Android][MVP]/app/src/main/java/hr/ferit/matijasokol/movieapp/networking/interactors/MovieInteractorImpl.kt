package hr.ferit.matijasokol.movieapp.networking.interactors

import hr.ferit.matijasokol.movieapp.networking.MovieApiService
import hr.ferit.matijasokol.movieapp.model.Movie
import hr.ferit.matijasokol.movieapp.model.MoviesResponse
import hr.ferit.matijasokol.movieapp.model.ReviewsResponse
import retrofit2.Callback

class MovieInteractorImpl(private val apiService: MovieApiService):
    MovieInteractor {

    override fun getPopularMovies(popularMoviesCallback: Callback<MoviesResponse>) {
        apiService.getPopularMovies().enqueue(popularMoviesCallback)
    }

    override fun getTopMovies(topMoviesCallback: Callback<MoviesResponse>) {
        apiService.getTopMovies().enqueue(topMoviesCallback)
    }

    override fun getMovie(movieId: Int, movieCallback: Callback<Movie>) {
        apiService.getMovie(movieId).enqueue(movieCallback)
    }

    override fun getReviewsForMovie(movieId: Int, callback: Callback<ReviewsResponse>) {
        apiService.getReviews(movieId).enqueue(callback)
    }
}