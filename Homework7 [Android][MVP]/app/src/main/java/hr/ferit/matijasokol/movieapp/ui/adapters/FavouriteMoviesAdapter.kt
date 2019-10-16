package hr.ferit.matijasokol.movieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matijasokol.movieapp.R
import hr.ferit.matijasokol.movieapp.model.Movie

class FavouriteMoviesAdapter(private val onMovieClickListener: (Movie) -> Unit, private val onFavoriteClickListener:(Movie) -> Unit)
    : RecyclerView.Adapter<MoviesGridViewHolder>() {

    private val favouriteMovies = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesGridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesGridViewHolder(view)
    }

    override fun getItemCount(): Int = favouriteMovies.size

    override fun onBindViewHolder(holder: MoviesGridViewHolder, position: Int) {
        holder.bindItem(favouriteMovies[position], onMovieClickListener, onFavoriteClickListener)
    }

    fun setData(movies: List<Movie>){
        this.favouriteMovies.clear()
        this.favouriteMovies.addAll(movies)
        notifyDataSetChanged()
        setListForFavourite()
    }

    fun removeData(movie: Movie){
        val index = this.favouriteMovies.indexOf(movie)
        this.favouriteMovies.remove(movie)
        notifyItemRemoved(index)
    }

    private fun setListForFavourite(){
        favouriteMovies.forEach {it.isFavorite = true }
        notifyDataSetChanged()
    }
}