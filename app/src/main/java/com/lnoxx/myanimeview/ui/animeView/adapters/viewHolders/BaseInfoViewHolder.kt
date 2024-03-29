package com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.databinding.ItemAnimeViewBaseInfoBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewHolderInterface
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

class BaseInfoViewHolder(view: View): RecyclerView.ViewHolder(view), AnimeViewHolderInterface{
    private val binding = ItemAnimeViewBaseInfoBinding.bind(view)
    override fun bind(anime: Anime) {
//        Picasso.get().load(anime.images.jpg.image_url).into(binding.AnimeMainImage)
//        Picasso.get().load(anime.images.jpg.image_url)
//            .transform(BlurTransformation(itemView.context,10))
//            .into(binding.AnimeViewBackgroundImage)
        binding.AnimeTitle.text = anime.title
        binding.AnimeType.text = anime.type
        binding.AnimeStatus.text = anime.status
        binding.AnimeAgeRaiting.text = anime.rating
        binding.Episodes.text = anime.episodes.toString()
    }
}