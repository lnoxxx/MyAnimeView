package com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders

import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemAnimeViewAdditionallyBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Entry
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewHolderInterface
import com.lnoxx.myanimeview.ui.related.RelatedFragment

const val animeType = "anime"
class AdditionalViewHolder(view: View): RecyclerView.ViewHolder(view), AnimeViewHolderInterface {
    private val binding = ItemAnimeViewAdditionallyBinding.bind(view)
    override fun bind(anime: Anime) {
        binding.RelatedCardView.setOnClickListener {
            val relationEntryList = mutableListOf<Entry>()
            for (relation in anime.relations){
                for (entry in relation.entry){
                    if (entry.type == animeType) relationEntryList.add(entry)
                }
            }
            RelatedFragment.relatedData = relationEntryList
            Navigation.findNavController(itemView)
                .navigate(R.id.action_animeViewFragment_to_relatedFragment)
        }
        binding.charectersCardView.setOnClickListener {

        }
    }
}