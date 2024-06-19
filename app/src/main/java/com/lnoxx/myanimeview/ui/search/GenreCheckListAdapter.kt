package com.lnoxx.myanimeview.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemCheckedTextBinding

class GenreCheckListAdapter(private val genreList: List<Genre>,
                            private val selectedGenres : MutableSet<Genre>)
    :RecyclerView.Adapter<GenreCheckListAdapter.GenreViewHolder>(){

    inner class GenreViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemCheckedTextBinding.bind(view)
        fun bind(genre: Genre){
            binding.GenreName.text = genre.text
            binding.checkBox.isChecked = genre in selectedGenres
            binding.checkBox.setOnClickListener {
                if (binding.checkBox.isChecked){
                    selectedGenres.add(genre)
                } else {
                    selectedGenres.remove(genre)
                }
            }
        }
    }

    fun getSelectedGenres(): MutableSet<Genre>{
        return selectedGenres
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_checked_text, parent, false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int = genreList.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genreList[position])
    }
}