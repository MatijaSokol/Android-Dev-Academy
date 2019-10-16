package hr.ferit.matijasokol.movieapp.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matijasokol.movieapp.R
import hr.ferit.matijasokol.movieapp.common.loadImage
import hr.ferit.matijasokol.movieapp.model.Movie
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

class MoviesGridViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindItem(movie: Movie, onMovieClickListener: (Movie) -> Unit, onFavoriteClickListener: (Movie) -> Unit) {
        movieImage.loadImage(movie.poster)
        movieFavorite.setImageResource(if (movie.isFavorite) R.drawable.ic_favorite_full else R.drawable.ic_favorite_empty)

        containerView.setOnClickListener {
            onMovieClickListener(movie)
        }

        movieFavorite.setOnClickListener {
            onFavoriteClickListener(movie)
        }
    }

}