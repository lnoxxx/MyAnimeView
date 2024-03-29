package com.lnoxx.myanimeview.ui.recommendation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemReviewBinding
import com.lnoxx.myanimeview.recomendationDatabase.ReviewCache
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