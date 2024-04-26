package com.lnoxx.myanimeview.ui.reviewBottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lnoxx.myanimeview.databinding.BottomSheetReviewBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Review
import com.squareup.picasso.Picasso

class ReviewBottomSheet(private val review: Review): BottomSheetDialogFragment() {
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
        Picasso.get().load(review.user.images.jpg.image_url).into(binding.userReviewImageView)
        binding.usernameTextView.text = review.user.username
        binding.scoreReviewText.text = review.score.toString()
        val reviewText =  review.review + "\n\n\n\n\n"
        binding.ReviewText.text = reviewText
    }

    companion object{
        const val BOTTOM_SHEET_TAG = "ReviewBottomSheet"
    }
}