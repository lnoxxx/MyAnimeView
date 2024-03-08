package com.lnoxx.myanimeview.ui.tops.topListFragment

import android.content.res.Resources.Theme
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lnoxx.myanimeview.AnimeViewApplication
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentTopListBinding
import com.lnoxx.myanimeview.jikanApi.JikanMainClass
import com.lnoxx.myanimeview.topsDatabase.AnimeInTop
import com.lnoxx.myanimeview.topsDatabase.TopUpdateTime
import com.lnoxx.myanimeview.topsDatabase.TopsTypeDatabase
import com.lnoxx.myanimeview.topsDatabase.animeListToTopList
import com.lnoxx.myanimeview.ui.tops.adapters.TopListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

private const val ARG_TOP_FILTER = ""

class TopListFragment : Fragment() {
    private lateinit var binding: FragmentTopListBinding

    private lateinit var adapter: TopListAdapter
    private var topFilter: String = ""
    private lateinit var topsTypeDatabase: TopsTypeDatabase

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
        adapter = TopListAdapter(mutableListOf())
        binding.topListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.topListRecyclerView.adapter = adapter
        setContent()
        binding.refreshLayout.setColorSchemeResources(R.color.md_theme_light_inverseSurface)
        binding.refreshLayout.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                setContentFromApi()
                Thread.sleep(1000)
                withContext(Dispatchers.Main){
                    binding.refreshLayout.isRefreshing = false
                }
            }
        }
        return binding.root
    }

    private fun setContent(){
        CoroutineScope(Dispatchers.IO).launch {
            if (getUpdateStatus()){
                setContentFromApi()
            } else {
                setContentFromDatabase()
            }
        }
    }

    private suspend fun setContentFromApi(){
        try {
            val animeTop = JikanMainClass.getAnimeTop(topFilter)
            val topList = animeListToTopList(animeTop.data, topFilter)
            cacheAnimeTop(topList)
            withContext(Dispatchers.Main){
                adapter.addAnime(topList)

            }
        }catch (e: Exception){
            Log.d("mylog", e.message.toString())
        }
    }

    private suspend fun setContentFromDatabase(){
        try {
            val topList = topsTypeDatabase.getTopsAnimeDao().getAnimeInTop(topFilter)
            withContext(Dispatchers.Main){
                adapter.addAnime(topList)
            }
        }catch (e: Exception){
            Log.d("mylog", e.message.toString())
        }
    }

    private fun cacheAnimeTop(topList: List<AnimeInTop>){
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMount = calendar.get(Calendar.MONTH)

        val topsDao = topsTypeDatabase.getTopsAnimeDao()
        val timeDao = topsTypeDatabase.getTopUpdateTimeDao()
        topsTypeDatabase.runInTransaction{
            topsDao.deleteOldTop(topFilter)
            topsDao.insertAll(topList)
            timeDao.update(TopUpdateTime(topFilter, currentDay, currentMount))
        }
    }

    private fun getUpdateStatus(): Boolean{
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMount = calendar.get(Calendar.MONTH)

        val application = requireContext().applicationContext as AnimeViewApplication
        topsTypeDatabase = application.topsTypeDatabase

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

}