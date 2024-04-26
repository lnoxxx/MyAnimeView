package com.lnoxx.myanimeview.ui.search_result

import androidx.lifecycle.ViewModel
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime

class SearchResultViewModel: ViewModel() {
    val animeList: MutableList<Anime> = mutableListOf()
    var page = 1
    var hasNextPage = false
}