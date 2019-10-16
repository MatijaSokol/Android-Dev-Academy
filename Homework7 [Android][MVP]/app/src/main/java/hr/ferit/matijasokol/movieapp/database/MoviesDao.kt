package hr.ferit.matijasokol.movieapp.database

import androidx.room.*
import hr.ferit.matijasokol.movieapp.model.Movie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavoriteMovie(movie: Movie)

    @Delete
    fun deleteFavoriteMovie(movie: Movie)

    @Query("SELECT * FROM Movie")
    fun getFavoriteMovies(): List<Movie>

}