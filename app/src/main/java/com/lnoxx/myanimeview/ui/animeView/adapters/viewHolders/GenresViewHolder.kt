package com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemAnimeViewGenresBinding
import com.lnoxx.myanimeview.databinding.ItemGenerBinding
import com.lnoxx.myanimeview.databinding.ItemStudioBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Genre
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Studio
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewHolderInterface

class GenresViewHolder(view: View): RecyclerView.ViewHolder(view), AnimeViewHolderInterface{
    private val binding = ItemAnimeViewGenresBinding.bind(view)
    override fun bind(anime: Anime) {
        val genresList = anime.genres
        binding.GenresRecyclerView.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        val studio = if(anime.studios.isNotEmpty()) anime.studios[0] else null
        binding.GenresRecyclerView.adapter = GenresRecyclerViewAdapter(genresList, studio)
    }
    inner class GenresRecyclerViewAdapter(private val genres: List<Genre>,
                                          private val studio: Studio?,):
        RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        private val typeStudio = 0
        private val typeGenre = 1
        inner class GenreItemViewHolder (view: View): RecyclerView.ViewHolder(view){
            private val binding = ItemGenerBinding.bind(view)
            fun bind(genre: Genre){
                binding.GenerTextView.text = genre.name
            }
        }
        inner class StudioItemViewHolder(view: View): RecyclerView.ViewHolder(view){
            private val binding = ItemStudioBinding.bind(view)
            fun bind(studio: Studio?){
                if (studio != null){
                    binding.studiosText.text = studio.name
                } else {
                    itemView.visibility = View.GONE
                }
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when(viewType){
                typeGenre -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_gener, parent, false)
                    GenreItemViewHolder(view)
                }
                typeStudio -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_studio, parent, false)
                    StudioItemViewHolder(view)
                }
                else -> throw IllegalArgumentException("Illegal viewType")
            }
        }
        override fun getItemCount() = genres.size + if(studio != null) 1 else 0
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when(holder){
                is GenreItemViewHolder -> {
                    if (studio != null){
                        holder.bind(genres[position-1])
                    } else {
                        holder.bind(genres[position])
                    }
                }
                is StudioItemViewHolder -> {
                    holder.bind(studio)
                }
            }
        }
        override fun getItemViewType(position: Int): Int {
            return if (studio != null){
                when(position){
                    0 -> typeStudio
                    else -> typeGenre
                }
            } else {
                typeGenre
            }
        }
    }
}