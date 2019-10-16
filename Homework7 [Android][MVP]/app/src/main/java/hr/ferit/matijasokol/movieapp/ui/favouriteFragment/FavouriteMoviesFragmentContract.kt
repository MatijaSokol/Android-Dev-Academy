package hr.ferit.matijasokol.movieapp.ui.favouriteFragment

import hr.ferit.matijasokol.movieapp.model.Movie

interface FavouriteMoviesFragmentContract {

    interface View{
        fun onMoviesReceived(movies: List<Movie>)
        fun onEmptyListReceived()
        fun onFavouriteRemoved(movie: Movie)
    }

    interface Preseneter{
        fun setView(view: FavouriteMoviesFragmentContract.View)
        fun onRequestFavouriteMovies()
        fun onFavouriteClicked(movie: Movie)
    }

}