package com.lnoxx.myanimeview.ui.search_result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemAnimeWithoutNumberBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.ui.animeView.AnimeViewFragment
import com.squareup.picasso.Picasso

class SearchResultRecyclerView(private val animeList: MutableList<Anime>):
    RecyclerView.Adapter<SearchResultRecyclerView.AnimeViewHolder>() {
    inner class AnimeViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemAnimeWithoutNumberBinding.bind(view)
        fun bind (anime: Anime){
            binding.animeTitle2.text = anime.title ?: "?"
            val episodes = if (anime.episodes != 0) anime.episodes.toString() else "?"
            val ratingText =  "$episodes ${itemView.context.getString(R.string.episodes)}  •  ${anime.score}"
            binding.animeRaiting2.text = ratingText
            Picasso.get().load(anime.images.jpg.image_url).into(binding.animeImageView)
            itemView.setOnClickListener {
                AnimeViewFragment.animeId = anime.mal_id
                Navigation.findNavController(itemView)
                    .navigate(R.id.action_searchResultFragment2_to_animeViewFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_anime_without_number, parent, false)
        return AnimeViewHolder(view)
    }

    override fun getItemCount() = animeList.size

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animeList[position])
    }

    fun addNextPage(new: List<Anime>){
        val oldSize = animeList.size
        animeList.addAll(new)
        notifyItemRangeInserted(oldSize, animeList.size)
    }
}