package com.lnoxx.myanimeview.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemGenreSearchBinding

class GenreListAdapter(private var genreSet: MutableSet<Genre>,
                       private val listener: SelectedGenresRv)
    : RecyclerView.Adapter<GenreListAdapter.GenreSearchViewHolder>() {
    private var genreList = genreSet.toList()
    class GenreSearchViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemGenreSearchBinding.bind(view)
        fun bind(genre: Genre, listener: SelectedGenresRv){
            binding.SearchGenreTextView.text = genre.text
            binding.SearchGenreTextView.setOnClickListener {
                listener.removeGenre(genre)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreSearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genre_search, parent, false)
        return GenreSearchViewHolder(view)
    }

    fun newGenres(newGenres : MutableSet<Genre>){
        genreSet = newGenres
        genreList = genreSet.toList()
        notifyDataSetChanged()
    }
    override fun getItemCount() = genreList.size
    override fun onBindViewHolder(holder: GenreSearchViewHolder, position: Int) {
        holder.bind(genreList[position], listener)
    }

    interface SelectedGenresRv{
        fun removeGenre(genre: Genre)
    }
}