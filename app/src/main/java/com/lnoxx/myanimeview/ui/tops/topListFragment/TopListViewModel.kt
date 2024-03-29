package com.lnoxx.myanimeview.ui.tops.topListFragment

import androidx.lifecycle.ViewModel
import com.lnoxx.myanimeview.topsDatabase.AnimeInTop

class TopListViewModel: ViewModel(){
    var currentList: MutableList<AnimeInTop>? = null
    var currentPage = 1
}