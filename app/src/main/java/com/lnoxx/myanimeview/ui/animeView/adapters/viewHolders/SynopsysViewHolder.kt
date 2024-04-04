package com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemAnimeViewSynopsysBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewHolderInterface

class SynopsysViewHolder(view: View): RecyclerView.ViewHolder(view), AnimeViewHolderInterface {
    val binding = ItemAnimeViewSynopsysBinding.bind(view)
    override fun bind(anime: Anime) {
        val text = anime.synopsis ?: itemView.context.getString(R.string.no_synopsys)
        binding.synopsys.text = text
    }
}