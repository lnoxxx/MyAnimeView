package com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemAnimeViewRelatedBinding
import com.lnoxx.myanimeview.databinding.ItemRelatedAnimeBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Entry
import com.lnoxx.myanimeview.ui.animeView.AnimeViewFragment
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewHolderInterface

const val animeType = "anime"
class RelatedViewHolder(view: View)
    : RecyclerView.ViewHolder(view), AnimeViewHolderInterface{
    private val binding = ItemAnimeViewRelatedBinding.bind(view)
    override fun bind(anime: Anime) {
        val relationEntryList = mutableListOf<Entry>()
        for (relation in anime.relations){
            for (entry in relation.entry){
                if (entry.type == animeType) relationEntryList.add(entry)
            }
        }
        binding.RelatedRecyclerView.layoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        binding.RelatedRecyclerView.adapter = RelatedAdapter(relationEntryList)
    }
    inner class RelatedAdapter(private val entryList: MutableList<Entry>)
        :RecyclerView.Adapter<RelatedAdapter.RelatedItemViewHolder>(){
        inner class RelatedItemViewHolder(view: View): RecyclerView.ViewHolder(view){
            private val binding = ItemRelatedAnimeBinding.bind(view)
            fun bind(entry: Entry, position: Int, size: Int){
                if (position == size) binding.helperLine.visibility = View.GONE
                binding.RelatedAnimeItem.text = entry.name
                itemView.setOnClickListener {
                    AnimeViewFragment.animeId = entry.mal_id
                    Navigation.findNavController(itemView).navigate(R.id.animeViewFragment)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_related_anime, parent, false)
            return RelatedItemViewHolder(view)
        }

        override fun getItemCount() = entryList.size

        override fun onBindViewHolder(holder: RelatedItemViewHolder, position: Int) {
            holder.bind(entryList[position], position, entryList.size-1)
        }
    }
}

