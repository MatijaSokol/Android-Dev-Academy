package hr.ferit.matijasokol.movieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matijasokol.movieapp.R
import hr.ferit.matijasokol.movieapp.model.Movie

class TopMoviesAdapter (private val onMovieClickListener: (Movie) -> Unit, private val onFavoriteClickListener:(Movie) -> Unit)
    : RecyclerView.Adapter<MoviesGridViewHolder>(){

    private val topMovies = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesGridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesGridViewHolder(view)
    }

    override fun getItemCount(): Int = topMovies.size

    override fun onBindViewHolder(holder: MoviesGridViewHolder, position: Int) {
        holder.bindItem(topMovies[position], onMovieClickListener, onFavoriteClickListener)
    }

    fun setData(movies: List<Movie>){
        this.topMovies.clear()
        this.topMovies.addAll(movies)
        notifyDataSetChanged()
    }

    fun setItemForFavourite(movie: Movie){
        topMovies[topMovies.indexOf(movie)].isFavorite = true
        notifyDataSetChanged()
    }

    fun setListForFavourite(favouriteMovies: List<Movie>){
        favouriteMovies.forEach {
            if (topMovies.contains(it)){
                topMovies[topMovies.indexOf(it)].isFavorite = true
            }
        }
        notifyDataSetChanged()
    }


    fun removeItemForFavourite(movie: Movie){
        topMovies[topMovies.indexOf(movie)].isFavorite = false
        notifyDataSetChanged()
    }
}