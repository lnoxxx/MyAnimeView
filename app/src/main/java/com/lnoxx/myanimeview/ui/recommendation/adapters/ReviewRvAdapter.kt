package com.lnoxx.myanimeview.ui.recommendation.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.BottomSheetReviewBinding
import com.lnoxx.myanimeview.databinding.ItemReviewBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Review
import com.lnoxx.myanimeview.recomendationDatabase.ReviewCache
import com.lnoxx.myanimeview.ui.reviewBottomSheet.ReviewBottomSheet
import com.squareup.picasso.Picasso

class ReviewRvAdapter(private var reviewList: MutableList<ReviewCache>)
    : RecyclerView.Adapter<ReviewRvAdapter.ReviewViewHolder>() {
    class ReviewViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemReviewBinding.bind(view)
        fun bind(review: ReviewCache){
            Picasso.get().load(review.userImage).into(binding.userImage)
            Picasso.get().load(review.animeImage).into(binding.animeImage)
            binding.userNameTextView.text = review.userName
            binding.AnimeOnReviewName.text = review.animeName
            val score = "${review.score}/10"
            binding.score.text = score
            itemView.setOnClickListener {
                val context = itemView.context
                if (context is FragmentActivity){
                    ReviewCacheBottomSheet(review)
                        .show(context.supportFragmentManager, ReviewCacheBottomSheet.BOTTOM_SHEET_TAG)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount() = reviewList.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviewList[position])
    }

    fun setReview(newReview: MutableList<ReviewCache>){
        reviewList = newReview
        notifyDataSetChanged()
    }
}

class ReviewCacheBottomSheet(private val review: ReviewCache): BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetReviewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetReviewBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Picasso.get().load(review.userImage).into(binding.userReviewImageView)
        binding.usernameTextView.text = review.userName
        binding.scoreReviewText.text = review.score.toString()
        val reviewText =  review.review + "\n"
        binding.ReviewText.text = reviewText
    }

    companion object{
        const val BOTTOM_SHEET_TAG = "ReviewCacheBottomSheet"
    }
}