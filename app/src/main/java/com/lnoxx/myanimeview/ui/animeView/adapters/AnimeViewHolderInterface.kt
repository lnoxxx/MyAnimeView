package com.lnoxx.myanimeview.ui.animeView.adapters

import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime

interface AnimeViewHolderInterface {
    fun bind(anime: Anime)
}