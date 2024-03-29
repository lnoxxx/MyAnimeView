package com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.databinding.ItemAnimeViewRelatedBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewHolderInterface

class RelatedViewHolder(view: View): RecyclerView.ViewHolder(view), AnimeViewHolderInterface{
    private val binding = ItemAnimeViewRelatedBinding.bind(view)
    override fun bind(anime: Anime) {

    }
}