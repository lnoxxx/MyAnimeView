package com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemAnimeViewRankingBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewHolderInterface

class RatingViewHolder(view: View): RecyclerView.ViewHolder(view), AnimeViewHolderInterface {
    private val binding = ItemAnimeViewRankingBinding.bind(view)
    override fun bind(anime: Anime) {
        val ranking = anime.rank?.toString() ?: "?"
        val score = anime.score?.toString() ?: "?"
        val favoriteCount = anime.favorites?.toString() ?: "?"
        val inListCount = anime.members?.toString() ?: "?"
        val popularity = anime.popularity?.toString() ?: "?"

        val rankingText = "#$ranking"
        val favoriteText = "$favoriteCount ${itemView.context.getText(R.string.likes)}"
        val inListCountText = "${itemView.context.getText(R.string.`in`)} $inListCount ${itemView.context.getText(R.string.user_list)}"
        val popularityText = "${itemView.context.getText(R.string.popularity)} $popularity"

        with(binding){
            GlobalRank.text = rankingText
            Score.text = score
            InFavoriteCount.text = favoriteText
            binding.InListCount.text = inListCountText
            ByPopylarity.text = popularityText
        }
    }
}