package com.lnoxx.myanimeview.ui.tops.topListFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lnoxx.myanimeview.AnimeViewApplication
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentTopListBinding
import com.lnoxx.myanimeview.jikanApi.JikanMainClass
import com.lnoxx.myanimeview.topsDatabase.AnimeInTop
import com.lnoxx.myanimeview.topsDatabase.TopUpdateTime
import com.lnoxx.myanimeview.topsDatabase.TopsTypeDatabase
import com.lnoxx.myanimeview.topsDatabase.animeListToTopList
import com.lnoxx.myanimeview.ui.animeView.AnimeViewFragment
import com.lnoxx.myanimeview.ui.tops.adapters.TopListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_TOP_FILTER = "filterKey"

class TopListFragment : Fragment(), TopListAdapter.AnimeClickListener {
    private lateinit var topFilter: String
    private lateinit var binding: FragmentTopListBinding
    private lateinit var application: AnimeViewApplication
    private lateinit var topsTypeDatabase: TopsTypeDatabase
    private lateinit var currentViewModel: TopListViewModel
    private lateinit var adapter: TopListAdapter

    var hasNextPage = true
    var isUpdating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            topFilter = it.getString(ARG_TOP_FILTER) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopListBinding.inflate(inflater)
        application = requireContext().applicationContext as AnimeViewApplication
        topsTypeDatabase = application.topsTypeDatabase
        currentViewModel = ViewModelProvider(this)[TopListViewModel::class.java]
        adapter = TopListAdapter(mutableListOf(), this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topListRecyclerView.adapter = adapter
        setRefreshLayout()
        setOnScrollListener()
        binding.topListRecyclerView.layoutManager = LinearLayoutManager(context)
        if (currentViewModel.currentList != null) {
            val topList = currentViewModel.currentList ?: mutableListOf()
            adapter.setList(topList)
        }else{
            binding.refreshLayout.isRefreshing = true
            CoroutineScope(Dispatchers.IO).launch {
                setContent()
            }
        }
    }

    private fun setOnScrollListener(){
        binding.topListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (adapter.itemCount == 0) return
                if (!hasNextPage) return
                if(isUpdating) return
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onAddMore()
                }
            }
        })
    }

    private fun onAddMore(){
        binding.refreshLayout.isRefreshing = true
        isUpdating = true
        CoroutineScope(Dispatchers.IO).launch {
            currentViewModel.currentPage++
            val animeTop = JikanMainClass.getAnimeTop(topFilter, currentViewModel.currentPage)
            if (animeTop == null){
                withContext(Dispatchers.Main){
                    Snackbar.make(requireView(),getText(R.string.error_loading),Snackbar.LENGTH_SHORT)
                        .setAction(R.string.retry){
                            onAddMore()
                        }
                        .show()
                }
                isUpdating = false
                binding.refreshLayout.isRefreshing = false
                return@launch
            }
            hasNextPage = animeTop.pagination.has_next_page
            val topList = animeListToTopList(animeTop.data, topFilter)
            withContext(Dispatchers.Main){
                adapter.addInList(topList)
                isUpdating = false
                binding.refreshLayout.isRefreshing = false
            }
        }
    }

    private fun setRefreshLayout(){
        binding.refreshLayout.setOnRefreshListener {
            currentViewModel.currentPage = 1
            binding.refreshLayout.isRefreshing = true
            hasNextPage = true
            CoroutineScope(Dispatchers.IO).launch {
                setContentFromApi()
            }
        }
    }

    private suspend fun setContent(){
        if (getUpdateStatus()){
            setContentFromApi()
        } else {
            setContentFromDatabase()
        }
    }

    private suspend fun setContentFromApi(){
        val animeTop = JikanMainClass.getAnimeTop(topFilter)
        if (animeTop == null){
            withContext(Dispatchers.Main){
                Snackbar.make(requireView(),getText(R.string.error_loading),Snackbar.LENGTH_SHORT)
                    .setAction(R.string.retry){
                        this.launch { setContentFromApi() }
                    }
                    .show()
            }
            binding.refreshLayout.isRefreshing = false
            return
        }
        val topList = animeListToTopList(animeTop.data, topFilter)
        cacheAnimeTop(topList)
        withContext(Dispatchers.Main){
            adapter.setList(topList)
            currentViewModel.currentList = topList
            binding.refreshLayout.isRefreshing = false
        }
    }

    private suspend fun setContentFromDatabase(){
        val topList = topsTypeDatabase.getTopsAnimeDao().getAnimeInTop(topFilter)
        withContext(Dispatchers.Main){
            adapter.setList(topList)
            currentViewModel.currentList = topList
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun cacheAnimeTop(topList: List<AnimeInTop>){
        val currentDay = application.currentDay
        val currentMount = application.currentMount
        val topsDao = topsTypeDatabase.getTopsAnimeDao()
        val timeDao = topsTypeDatabase.getTopUpdateTimeDao()
        topsTypeDatabase.runInTransaction{
            topsDao.deleteOldTop(topFilter)
            topsDao.insertAll(topList)
            timeDao.update(TopUpdateTime(topFilter, currentDay, currentMount))
        }
    }

    private fun getUpdateStatus(): Boolean{
        val currentDay = application.currentDay
        val currentMount = application.currentMount

        val lastUpdateTime = topsTypeDatabase.getTopUpdateTimeDao()
            .getTimeByTopType(topFilter)

        return if (currentDay > lastUpdateTime.day){
            true
        } else currentDay == lastUpdateTime.day && currentMount > lastUpdateTime.month
    }
    companion object {
        @JvmStatic
        fun newInstance(topFilter: String) =
            TopListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TOP_FILTER, topFilter)
                }
            }
    }

    override fun onPause() {
        super.onPause()
        binding.refreshLayout.isRefreshing = false
    }

    override fun onClick(anime: AnimeInTop) {
        AnimeViewFragment.animeId = anime.malId
        findNavController().navigate(R.id.action_navigation_tops_to_animeViewFragment)
    }
}