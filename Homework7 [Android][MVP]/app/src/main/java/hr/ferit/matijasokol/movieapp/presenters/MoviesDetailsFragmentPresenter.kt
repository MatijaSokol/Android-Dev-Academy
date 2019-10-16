package hr.ferit.matijasokol.movieapp.presenters

import hr.ferit.matijasokol.movieapp.model.ReviewsResponse
import hr.ferit.matijasokol.movieapp.networking.interactors.MovieInteractor
import hr.ferit.matijasokol.movieapp.ui.detailsFragment.MoviesDetailsFragmentContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesDetailsFragmentPresenter(private val apiInteractor: MovieInteractor) :
    MoviesDetailsFragmentContract.Presenter {

    private lateinit var view: MoviesDetailsFragmentContract.View

    override fun setView(view: MoviesDetailsFragmentContract.View) {
        this.view = view
    }

    override fun onReviewRequest(id: Int) {
        apiInteractor.getReviewsForMovie(id, reviewsCallback())
    }

    private fun reviewsCallback(): Callback<ReviewsResponse> = object : Callback<ReviewsResponse> {
        override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
            t.printStackTrace()
        }

        override fun onResponse(call: Call<ReviewsResponse>, response: Response<ReviewsResponse>) {
            if (response.isSuccessful){
                response.body()?.reviews?.run {
                    view.onRequestSuccess(this)
                }
            }
        }
    }
}