package com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.AnimeViewApplication
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemAnimeViewCommentsBinding
import com.lnoxx.myanimeview.databinding.ItemCommentBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Review
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.ReviewsResponse
import com.lnoxx.myanimeview.ui.allComments.AllCommentsFragment
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewHolderInterface
import com.lnoxx.myanimeview.ui.reviewBottomSheet.ReviewBottomSheet
import com.squareup.picasso.Picasso

class CommentsViewHolder(view: View): RecyclerView.ViewHolder(view), AnimeViewHolderInterface {
    private val binding = ItemAnimeViewCommentsBinding.bind(view)
    private val application = itemView.context.applicationContext as AnimeViewApplication
    init {
        binding.commentsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
    }
    override fun bind(anime: Anime) {
        val comments = application.commentsCache[anime.mal_id]
        if (comments != null){
            binding.CommentsLoadingIndicator.hide()
            binding.commentsRecyclerView.adapter = CommentsRecyclerViewAdapter(comments, true)
            binding.MoreCommentsButton.setOnClickListener {
                AllCommentsFragment.animeID = anime.mal_id
                AllCommentsFragment.comments = comments
                Navigation.findNavController(itemView)
                    .navigate(R.id.action_animeViewFragment_to_allCommentsFragment)
            }
        }
    }
}

class CommentsRecyclerViewAdapter(private val comments: ReviewsResponse,
                                  private val preview: Boolean,)
    : RecyclerView.Adapter<CommentsRecyclerViewAdapter.CommentItemViewHolder>(){
    private val previewCount = 3
    private var isEmpty = false
    class CommentItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemCommentBinding.bind(view)
        fun bind(review: Review?){
            if (review != null){
                Picasso.get().load(review.user.images.jpg.image_url).into(binding.userImageComment)
                binding.userCommentText.text = review.review
                itemView.setOnClickListener {
                    val context = itemView.context
                    if (context is FragmentActivity){
                        val reviewSheet = ReviewBottomSheet(review)
                        reviewSheet.show(context.supportFragmentManager, ReviewBottomSheet.BOTTOM_SHEET_TAG)
                    }
                }
            } else {
                Picasso.get().load(R.drawable.no_comments_profile_photo_360).into(binding.userImageComment)
                binding.userCommentText.text = itemView.context.getText(R.string.empty_comment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent, false)
        return CommentItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (comments.data == null){
            isEmpty = true
            return 1
        }
        return if(comments.data.size < previewCount){
            if (comments.data.isEmpty()) {
                isEmpty = true
                1
            } else{
                comments.data.size
            }
        }else{
            if(preview) previewCount else comments.data.size
        }
    }

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
        if (isEmpty) {
            holder.bind(null)
        } else {
            holder.bind(comments.data?.get(position))
        }
    }
}