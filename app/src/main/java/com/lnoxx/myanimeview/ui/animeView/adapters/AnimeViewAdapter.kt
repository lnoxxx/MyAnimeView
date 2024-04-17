package com.lnoxx.myanimeview.ui.animeView.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.jikanApi.JikanMainClass
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.ReviewsResponse
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.AdditionalViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.BaseInfoViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.CommentsViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.GenresViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.RatingViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.StatisticViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.SynopsysViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.TrailerThemeViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeViewAdapter(private val anime: Anime)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            BaseInfo -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_base_info, parent, false)
                BaseInfoViewHolder(view)
            }
            Synopsys -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_synopsys, parent, false)
                SynopsysViewHolder(view)
            }
            Rating -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_ranking, parent, false)
                RatingViewHolder(view)
            }
            Genres -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_genres, parent, false)
                GenresViewHolder(view)
            }
            TrailerTheme -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_trailer_theme, parent, false)
                TrailerThemeViewHolder(view)
            }
            Statistic -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_statistic, parent, false)
                StatisticViewHolder(view)
            }
            Additional -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime_view_additionally, parent, false)
                AdditionalViewHolder(view)
            }
            Comments -> {
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

    override fun getItemCount(): Int = typeCount

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> BaseInfo
            1 -> Genres
            2 -> Rating
            3 -> Synopsys
            4 -> TrailerTheme
            5 -> Additional
            6 -> Statistic
            7 -> Comments
            else -> throw IllegalArgumentException("Illegal position")
        }
    }

    companion object{
        const val typeCount = 8

        const val BaseInfo = 1
        const val Synopsys = 2
        const val Rating = 3
        const val Genres = 4
        const val TrailerTheme = 5
        const val Statistic = 6
        const val Additional = 7
        const val Comments = 8
    }

    fun notifyCommentsLoaded(){
        notifyItemChanged(7)
    }
    fun notifyStatisticLoaded(){
        notifyItemChanged(6)
    }
}