package hr.ferit.matijasokol.movieapp.ui.topMoviesFragment

import hr.ferit.matijasokol.movieapp.model.Movie

interface TopMoviesFragmentContract{

    interface View{
        fun onRequestSucces(movies: ArrayList<Movie>)
        fun onRequestFailed()
        fun onAddFavourite(movie: Movie)
        fun onRemoveFavourite(movie: Movie)
    }

    interface Presenter{
        fun setView(view: View)
        fun onMovieRequest()
        fun onFavouriteClicked(movie: Movie)
        fun getListOfFavourites(): List<Movie>
    }
}