package com.lnoxx.myanimeview.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemAnimeWithoutNumberBinding
import com.lnoxx.myanimeview.favoriteDatabase.FavoriteAnimeEntity
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.ui.animeView.AnimeViewFragment
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val favAnimeList: List<FavoriteAnimeEntity>):RecyclerView.Adapter<FavoriteAdapter.AnimeInFavoriteViewHolder>() {
    inner class AnimeInFavoriteViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemAnimeWithoutNumberBinding.bind(view)
        fun bind(anime: FavoriteAnimeEntity){
            if (anime.image == ""){
                Picasso.get().load(R.drawable.no_comments_profile_photo_360).into(binding.animeImageView)
            }else{
                Picasso.get().load(anime.image).into(binding.animeImageView)
            }
            binding.animeTitle2.text = anime.title
            val episodes = if (anime.ep != 0) anime.ep.toString() else "?"
            val ratingText =  "$episodes ${itemView.context.getString(R.string.episodes)}  •  ${anime.score}"
            binding.animeRaiting2.text = ratingText
            itemView.setOnClickListener {
                AnimeViewFragment.animeId = anime.malId
                Navigation.findNavController(itemView)
                    .navigate(R.id.action_navigation_favorite_to_animeViewFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeInFavoriteViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_anime_without_number,parent,false)
        return AnimeInFavoriteViewHolder(view)
    }

    override fun getItemCount() = favAnimeList.size

    override fun onBindViewHolder(holder: AnimeInFavoriteViewHolder, position: Int) {
        holder.bind(favAnimeList[position])
    }
}