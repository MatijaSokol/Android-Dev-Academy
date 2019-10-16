package hr.ferit.matijasokol.movieapp.ui.gridFragment

import hr.ferit.matijasokol.movieapp.model.Movie

interface MoviesGridFragmentContract {

    interface View{
        fun onRequestSuccess(movies: ArrayList<Movie>)
        fun onRequestFailed()
        fun onAddFavourite(movie: Movie)
        fun onRemoveFavourite(movie: Movie)
    }

    interface Presenter{
        fun setView(view: View)
        fun onMoviesRequest()
        fun onFavouriteClicked(movie: Movie)
        fun getListOfFavourites(): List<Movie>
    }
}