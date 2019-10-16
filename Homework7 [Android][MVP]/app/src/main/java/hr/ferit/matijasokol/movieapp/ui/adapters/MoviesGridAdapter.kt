package hr.ferit.matijasokol.movieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matijasokol.movieapp.R
import hr.ferit.matijasokol.movieapp.model.Movie

class MoviesGridAdapter(private val onMovieClickListener: (Movie) -> Unit, private val onFavoriteClickListener:(Movie) -> Unit) : RecyclerView.Adapter<MoviesGridViewHolder>(){

    private val movies = mutableListOf<Movie>()

    fun setMovies(movies: List<Movie>){
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesGridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesGridViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MoviesGridViewHolder, position: Int) {
        holder.bindItem(movies[position], onMovieClickListener, onFavoriteClickListener)
    }

    fun setListForFavourite(favouriteMovies: List<Movie>){
        favouriteMovies.forEach {
            if (movies.contains(it)){
                movies[movies.indexOf(it)].isFavorite = true
            }
        }
        notifyDataSetChanged()
    }

    fun setItemForFavourite(movie: Movie){
        movies[movies.indexOf(movie)].isFavorite = true
        notifyDataSetChanged()
    }

    fun removeItemForFavourite(movie: Movie){
        movies[movies.indexOf(movie)].isFavorite = false
        notifyDataSetChanged()
    }
}