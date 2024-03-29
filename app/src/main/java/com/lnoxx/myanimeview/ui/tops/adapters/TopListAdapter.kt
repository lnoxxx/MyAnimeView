package com.lnoxx.myanimeview.ui.tops.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemAnimeBinding
import com.lnoxx.myanimeview.topsDatabase.AnimeInTop
import com.squareup.picasso.Picasso

class TopListAdapter(
    private var animeList: MutableList<AnimeInTop>,
    private val listener: AnimeClickListener,)
    : RecyclerView.Adapter<TopListAdapter.AnimeInTopViewHolder>() {
    class AnimeInTopViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemAnimeBinding.bind(view)
        fun bind(anime: AnimeInTop, position: Int, listener: AnimeClickListener){
            val ratingPos = (position+1).toString()
            binding.animePos.text = ratingPos
            binding.animeTitle.text = anime.title
            val episodes = if (anime.episodes != 0) anime.episodes.toString() else "?"
            val ratingText =  "$episodes episodes  •  ${anime.score}"
            binding.animeRaiting.text = ratingText
            binding.animeSynopsis.text = anime.synopsis
            Picasso.get().load(anime.imageUrl).into(binding.animeImageView)
            itemView.setOnClickListener {
                listener.onClick(anime)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeInTopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_anime, parent, false)
        return AnimeInTopViewHolder(view)
    }

    override fun getItemCount() = animeList.size

    override fun onBindViewHolder(holder: AnimeInTopViewHolder, position: Int) {
        holder.bind(animeList[position], position, listener)
    }

    fun addInList(addedAnimeItem: MutableList<AnimeInTop>){
        val oldSize = animeList.size
        animeList.addAll(addedAnimeItem)
        notifyItemRangeInserted(oldSize, animeList.size)
    }

    fun setList(newAnimeList: MutableList<AnimeInTop>){
        animeList = newAnimeList
        notifyDataSetChanged()
    }

    interface AnimeClickListener{
        fun onClick(anime: AnimeInTop)
    }
}

