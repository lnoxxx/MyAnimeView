package com.lnoxx.myanimeview.ui.animeView.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.AdditionalViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.BaseInfoViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.CommentsViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.GenresViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.RatingViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.StatisticViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.SynopsysViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.TrailerThemeViewHolder

class AnimeViewAdapter(private val anime: Anime)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            BASE_INFO -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_base_info, parent, false)
                BaseInfoViewHolder(view)
            }
            SYNOPSYS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_synopsys, parent, false)
                SynopsysViewHolder(view)
            }
            RATING -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_ranking, parent, false)
                RatingViewHolder(view)
            }
            GENRES -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_genres, parent, false)
                GenresViewHolder(view)
            }
            TRAILER_THEME -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_trailer_theme, parent, false)
                TrailerThemeViewHolder(view)
            }
            STATISTIC -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_statistic, parent, false)
                StatisticViewHolder(view)
            }
            ADDITIONAL -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_additionally, parent, false)
                AdditionalViewHolder(view)
            }
            COMMENTS -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_comments, parent, false)
                CommentsViewHolder(view)
            }
            else -> throw IllegalArgumentException("Illegal viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AnimeViewHolderInterface).bind(anime)
    }

    override fun getItemCount(): Int = TYPE_COUNT

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> BASE_INFO
            1 -> GENRES
            2 -> RATING
            3 -> SYNOPSYS
            4 -> TRAILER_THEME
            5 -> ADDITIONAL
            6 -> STATISTIC
            7 -> COMMENTS
            else -> throw IllegalArgumentException("Illegal position")
        }
    }

    companion object{
        const val TYPE_COUNT = 8

        const val BASE_INFO = 1
        const val SYNOPSYS = 2
        const val RATING = 3
        const val GENRES = 4
        const val TRAILER_THEME = 5
        const val STATISTIC = 6
        const val ADDITIONAL = 7
        const val COMMENTS = 8
    }

    fun notifyCommentsLoaded(){
        notifyItemChanged(ADDITIONAL)
    }
    fun notifyStatisticLoaded(){
        notifyItemChanged(STATISTIC)
    }
}