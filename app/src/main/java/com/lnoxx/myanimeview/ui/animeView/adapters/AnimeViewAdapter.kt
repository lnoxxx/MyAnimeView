package com.lnoxx.myanimeview.ui.animeView.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.BaseInfoViewHolder
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.RelatedViewHolder

class AnimeViewAdapter(private val anime: Anime): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            BaseInfo -> {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_anime_view_base_info, parent, false)
                BaseInfoViewHolder(view)
            }
            Related -> {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_anime_view_related, parent, false)
                RelatedViewHolder(view)
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
            1 -> Related
            else -> throw IllegalArgumentException("Illegal position")
        }
    }

    companion object{
        const val typeCount = 2

        const val BaseInfo = 1
        const val Related = 2
    }
}