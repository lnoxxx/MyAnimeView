package com.lnoxx.myanimeview.ui.related

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemRelatedAnimeBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Entry
import com.lnoxx.myanimeview.ui.animeView.AnimeViewFragment

class RelatedAdapter(private val entryList: MutableList<Entry>)
        : RecyclerView.Adapter<RelatedAdapter.RelatedItemViewHolder>(){
        inner class RelatedItemViewHolder(view: View): RecyclerView.ViewHolder(view){
            private val binding = ItemRelatedAnimeBinding.bind(view)
            fun bind(entry: Entry){
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
            holder.bind(entryList[position])
        }
    }