package com.lnoxx.myanimeview.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.lnoxx.myanimeview.MainActivity
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentSearchFragementBinding
import com.lnoxx.myanimeview.ui.search_result.SearchResultFragment

class SearchFragment : Fragment(), GenreListAdapter.SelectedGenresRv {
    private lateinit var binding: FragmentSearchFragementBinding
    private lateinit var adapter: GenreListAdapter

    private val searchFilter = SearchFilter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchFragementBinding.inflate(inflater)
        adapter = GenreListAdapter(searchFilter.genres, this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDropDownItems()
        binding.rangeSlider.addOnChangeListener { rangeSlider, fl, b ->
            val values = rangeSlider.values
            searchFilter.minScore = values[0]
            searchFilter.maxFloat = values[1]
        }
        binding.searchButton.setOnClickListener {
            search()
        }
        binding.genresRecyclreView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.genresRecyclreView.adapter = adapter
        binding.addGenreButton.setOnClickListener {
            val dialogView = LayoutInflater.from(context)
                .inflate(R.layout.alert_dialog_checkbox_list, null)
            val recyclerView = dialogView.findViewById<RecyclerView>(R.id.CheckboxListRv)
            recyclerView.layoutManager = LinearLayoutManager(context)
            val genreAdapter = GenreCheckListAdapter(Genre.entries, searchFilter.genres)
            recyclerView.adapter = genreAdapter
            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.choseGenre)
                .setView(dialogView)
                .setPositiveButton("Ok") { dialog, _ ->
                    searchFilter.genres.addAll(genreAdapter.getSelectedGenres())
                    dialog.dismiss()
                }
                .setOnDismissListener {
                    adapter.newGenres(searchFilter.genres)
                }
                .create()
            dialog.show()
        }
    }

    private fun setDropDownItems(){
        val typesArray: Array<String> =
            AnimeTvType.entries.map { getString(it.localizeTextResId) }.toTypedArray()
        (binding.typeText as MaterialAutoCompleteTextView).setSimpleItems(typesArray)

        val statusArray: Array<String> =
            AnimeStatus.entries.map { getString(it.localizeTextResId) }.toTypedArray()
        (binding.statusText as MaterialAutoCompleteTextView).setSimpleItems(statusArray)

        val ratingArray: Array<String> =
            AnimeRating.entries.map {getString(it.localizeTextResId)}.toTypedArray()
        (binding.ratingText as MaterialAutoCompleteTextView).setSimpleItems(ratingArray)

        val sortByArray: Array<String> =
            SortFilter.entries.map {getString(it.localizeTextResId)}.toTypedArray()
        (binding.sortByText as MaterialAutoCompleteTextView).setSimpleItems(sortByArray)

        val sortTypeArray: Array<String> =
            SortType.entries.map {getString(it.localizeTextResId)}.toTypedArray()
        (binding.sortTypeText as MaterialAutoCompleteTextView).setSimpleItems(sortTypeArray)

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomNavBar(false)
        setDropDownItems()
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).showBottomNavBar(true)
    }

    override fun removeGenre(genre: Genre) {
        searchFilter.genres.remove(genre)
        adapter.newGenres(searchFilter.genres)
    }

    private fun search(){
        searchFilter.type = when(binding.typeText.text.toString()){
            getString(R.string.tv) -> AnimeTvType.TV
            getString(R.string.movie) -> AnimeTvType.MOVIE
            getString(R.string.ova) -> AnimeTvType.OVA
            getString(R.string.special) -> AnimeTvType.SPECIAL
            else -> null
        }
        searchFilter.status = when(binding.statusText.text.toString()){
            getString(R.string.airing) -> AnimeStatus.AIRING
            getString(R.string.complete) -> AnimeStatus.COMPLETE
            getString(R.string.upcoming) -> AnimeStatus.UPCOMING
            else -> null
        }
        searchFilter.sortFilter = when (binding.sortByText.text.toString()){
            getString(R.string.title_sort) -> SortFilter.TITLE
            getString(R.string.date_sort) -> SortFilter.START_DATE
            getString(R.string.episodes_sort) -> SortFilter.EPISODES
            getString(R.string.score_sort) -> SortFilter.SCORE
            getString(R.string.rank_sort) -> SortFilter.RANK
            getString(R.string.popylarity_sort) -> SortFilter.POPULARITY
            else -> SortFilter.POPULARITY
        }
        searchFilter.sortType = when(binding.sortTypeText.text.toString()){
            getString(R.string.desc) -> SortType.DESC
            getString(R.string.asc) -> SortType.ASC
            else -> SortType.DESC
        }
        searchFilter.rating = when(binding.ratingText.text.toString()){
            getString(R.string.G) -> AnimeRating.G
            getString(R.string.PG) -> AnimeRating.PG
            getString(R.string.PG13) -> AnimeRating.PG13
            getString(R.string.R17) -> AnimeRating.R17
            getString(R.string.R) -> AnimeRating.R
            getString(R.string.RX) -> AnimeRating.RX
            else -> null
        }
        searchFilter.query = binding.queryEditText.text.toString()
        searchFilter.sfw = binding.sfwSwitch.isChecked
        SearchResultFragment.searchFilter = searchFilter
        findNavController().navigate(R.id.action_searchFragment_to_searchResultFragment2)
    }
}


