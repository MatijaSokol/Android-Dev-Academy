package hr.ferit.matijasokol.movieapp.ui.detailsFragment

import hr.ferit.matijasokol.movieapp.model.Review

interface MoviesDetailsFragmentContract {

    interface View{
        fun onRequestSuccess(reviews: List<Review>)
        fun onRequestFailed()
    }

    interface Presenter{
        fun setView(view: View)
        fun onReviewRequest(id: Int)
    }
}