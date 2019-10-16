package hr.ferit.matijasokol.movieapp.ui.detailsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.matijasokol.movieapp.R
import hr.ferit.matijasokol.movieapp.common.displayToast
import hr.ferit.matijasokol.movieapp.common.loadImage
import hr.ferit.matijasokol.movieapp.model.Movie
import hr.ferit.matijasokol.movieapp.model.Review
import hr.ferit.matijasokol.movieapp.networking.BackendFactory
import hr.ferit.matijasokol.movieapp.presenters.MoviesDetailsFragmentPresenter
import hr.ferit.matijasokol.movieapp.ui.adapters.ReviewAdapter
import kotlinx.android.synthetic.main.fragemnt_details.*

class MovieDetailsFragment : Fragment(), MoviesDetailsFragmentContract.View {

    private val reviewsAdapter by lazy { ReviewAdapter() }

    private val presenter: MoviesDetailsFragmentContract.Presenter by lazy {
        MoviesDetailsFragmentPresenter(BackendFactory.getMovieInteractor())
    }

    companion object {
        private const val MOVIE_EXTRA = "movie_extra"

        fun getInstance(movie: Movie): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_EXTRA, movie)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var movie: Movie

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragemnt_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie = arguments?.getParcelable(MOVIE_EXTRA) as Movie

        initUi()
        presenter.onReviewRequest(movie.id)
        initListeners()
    }

    private fun initUi(){
        movieImage.loadImage(movie.poster)
        movieTitle.text = movie.title
        movieOverview.text = movie.overview
        movieReleaseDate.text = movie.releaseDate
        movieVoteAverage.text = movie.averageVote.toString()

        movieReviewList.apply {
            adapter = reviewsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initListeners(){
        movieFavorite.setOnClickListener {
            onFavoriteClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    private fun onFavoriteClicked(){

    }

    override fun onRequestSuccess(reviews: List<Review>) {
        reviewsAdapter.setReviewList(reviews)
    }

    override fun onRequestFailed() {
        activity?.displayToast(getString(R.string.failed))
    }

}