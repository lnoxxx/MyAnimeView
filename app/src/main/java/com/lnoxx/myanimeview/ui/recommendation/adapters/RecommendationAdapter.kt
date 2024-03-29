package com.lnoxx.myanimeview.ui.recommendation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemRecommendationAnimeBinding
import com.lnoxx.myanimeview.recomendationDatabase.RecommendationCache
import com.squareup.picasso.Picasso

class RecommendationAdapter(private var animeList: MutableList<RecommendationCache>)
    : RecyclerView.Adapter<RecommendationAdapter.RecViewHolder>() {
    class RecViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemRecommendationAnimeBinding.bind(view)
        fun bind(anime: RecommendationCache){
            if (anime.images != ""){
                Picasso.get().load(anime.images)
                    .into(binding.animeRecommendationImageView)
            }
            binding.animeRecommendationName.text = anime.title
            itemView.setOnClickListener {
            }
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

    fun setAnime(newAnimeList: MutableList<RecommendationCache>){
        animeList = newAnimeList
        notifyDataSetChanged()
    }

    companion object PreLoading{
        private val emptyItem = RecommendationCache(-1,"","","...",-1)
        val emptyList = MutableList(10){emptyItem}
    }
}