package com.lnoxx.myanimeview.ui.tops.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemAnimeBinding
import com.lnoxx.myanimeview.topsDatabase.AnimeInTop
import com.squareup.picasso.Picasso

class TopListAdapter(private val animeList: MutableList<AnimeInTop>)
    : RecyclerView.Adapter<TopListAdapter.AnimeInTopViewHolder>() {
    class AnimeInTopViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemAnimeBinding.bind(view)
        fun bind(anime: AnimeInTop){
            binding.animeTitle.text = anime.title
            val ratingText = "${anime.episodes} episodes  |  ${anime.score}"
            binding.animeRaiting.text = ratingText
            binding.animeSynopsis.text = anime.synopsis
            Picasso.get().load(anime.imageUrl).into(binding.animeImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeInTopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_anime, parent, false)
        return AnimeInTopViewHolder(view)
    }

    override fun getItemCount() = animeList.size

    override fun onBindViewHolder(holder: AnimeInTopViewHolder, position: Int) {
        holder.bind(animeList[position])
    }

    fun addAnime(newAnime: MutableList<AnimeInTop>){
        val oldSize = animeList.size
        animeList += newAnime
        notifyItemRangeInserted(oldSize, animeList.size)
    }
}

