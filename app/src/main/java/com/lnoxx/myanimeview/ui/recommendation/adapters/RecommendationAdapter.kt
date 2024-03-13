package com.lnoxx.myanimeview.ui.recommendation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemRecommendationAnimeBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.AnimeRecommendation
import com.squareup.picasso.Picasso

class RecommendationAdapter(private var animeList: MutableList<AnimeRecommendation>)
    : RecyclerView.Adapter<RecommendationAdapter.RecViewHolder>() {
    class RecViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemRecommendationAnimeBinding.bind(view)
        fun bind(anime: AnimeRecommendation){
            Picasso.get().load(anime.images.jpg.large_image_url)
                .into(binding.animeRecommendationImageView)
            binding.animeRecommendationName.text = anime.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommendation_anime , parent, false)
        return RecViewHolder(view)
    }

    override fun getItemCount() = animeList.size

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        holder.bind(animeList[position])
    }

    fun setAnime(newAnimeList: MutableList<AnimeRecommendation>){
        animeList = newAnimeList
        notifyItemRangeInserted(0, animeList.size)
    }
}