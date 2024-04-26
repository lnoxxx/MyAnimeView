package com.lnoxx.myanimeview.ui.search_result

import android.graphics.LinearGradient
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lnoxx.myanimeview.MainActivity
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentSearchResultBinding
import com.lnoxx.myanimeview.jikanApi.JikanMainClass
import com.lnoxx.myanimeview.ui.search.SearchFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchResultFragment : Fragment() {
    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var currentSearchFilter: SearchFilter
    lateinit var adapter: SearchResultRecyclerView

    private val viewModel: SearchResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentSearchFilter = searchFilter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.notFoundText.visibility = View.GONE
        binding.searchResultRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.searchProgressIndicator.show()
        if (viewModel.animeList.isEmpty()) setContent()
        else{
            adapter = SearchResultRecyclerView(viewModel.animeList)
            binding.searchResultRecyclerView.adapter = adapter
            binding.searchProgressIndicator.hide()
            setOnScrollListener()
        }
    }

    var serched = false
    private fun setOnScrollListener(){
        binding.searchResultRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (serched) return
                if (!recyclerView.canScrollVertically(1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE){
                    serched = true
                    addNextPage()
                }
            }
        })
    }

    private fun addNextPage(){
        binding.searchProgressIndicator.show()
        if (!viewModel.hasNextPage) return
        CoroutineScope(Dispatchers.IO).launch{
            val response = JikanMainClass.animeSearch(currentSearchFilter, viewModel.page + 1)
            if (response == null){
                withContext(Dispatchers.Main){
                    Snackbar.make(requireView(),getText(R.string.error_loading), Snackbar.LENGTH_SHORT)
                        .setAction(R.string.retry){
                            addNextPage()
                        }
                        .show()
                }
                return@launch
            }
            viewModel.page += 1
            withContext(Dispatchers.Main){
                viewModel.animeList.addAll(response.data)
                adapter.addNextPage(response.data)
                binding.searchProgressIndicator.hide()
                serched = false
            }
        }
    }

    private fun setContent(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = JikanMainClass.animeSearch(currentSearchFilter, viewModel.page)
            if (response == null){
                withContext(Dispatchers.Main){
                    Snackbar.make(requireView(),getText(R.string.error_loading),Snackbar.LENGTH_SHORT)
                        .setAction(R.string.retry){
                            setContent()
                        }
                        .show()
                }
                return@launch
            }
            withContext(Dispatchers.Main){
                if (response.data.isEmpty()) binding.notFoundText.visibility = View.VISIBLE
                viewModel.animeList.addAll(response.data)
                adapter = SearchResultRecyclerView(response.data.toMutableList())
                binding.searchResultRecyclerView.adapter = adapter
                viewModel.hasNextPage = response.pagination.has_next_page
                binding.searchProgressIndicator.hide()
                setOnScrollListener()
            }
        }
    }

    companion object{
        var searchFilter = SearchFilter()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomNavBar(false)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).showBottomNavBar(true)
    }
}