package com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemAnimeViewBaseInfoBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewHolderInterface
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

class BaseInfoViewHolder(view: View): RecyclerView.ViewHolder(view), AnimeViewHolderInterface{
    private val binding = ItemAnimeViewBaseInfoBinding.bind(view)
    override fun bind(anime: Anime) {
        if (anime.images.jpg.image_url != null){
            Picasso.get().load(anime.images.jpg.image_url)
                .transform(BlurTransformation(itemView.context,50))
                .into(binding.AnimeViewBackgroundImage)
            Picasso.get().load(anime.images.jpg.image_url).into(binding.AnimeMainImage)
        }
        val title = anime.title ?: "?"
        val type = anime.type ?: "?"
        val status = anime.status ?: "?"
        if (anime.rating != null){
            val ratingList = anime.rating.split(" ")
            val rating = if (ratingList.isNotEmpty()) ratingList[0] else "?"
            binding.AnimeAgeRaiting.text = rating
        }
        val episodes  = (if (anime.episodes != 0) anime.episodes.toString() else "?") + " " + itemView.context.getString(R.string.episodes)
        binding.AnimeTitle.text = title
        binding.AnimeType.text = type
        binding.AnimeStatus.text = status
        binding.Episodes.text = episodes
    }
}