package hr.ferit.matijasokol.movieapp.networking.interactors

import hr.ferit.matijasokol.movieapp.model.Movie
import hr.ferit.matijasokol.movieapp.model.MoviesResponse
import hr.ferit.matijasokol.movieapp.model.ReviewsResponse
import retrofit2.Callback

interface MovieInteractor {

    fun getPopularMovies(popularMoviesCallback: Callback<MoviesResponse>)

    fun getTopMovies(topMoviesCallback: Callback<MoviesResponse>)

    fun getMovie(movieId: Int, movieCallback: Callback<Movie>)

    fun getReviewsForMovie(movieId: Int, callback: Callback<ReviewsResponse>)

}