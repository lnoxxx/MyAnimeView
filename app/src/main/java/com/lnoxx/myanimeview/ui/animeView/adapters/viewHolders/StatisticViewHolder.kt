package com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders

import android.content.res.ColorStateList
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.faskn.lib.PieChart
import com.faskn.lib.Slice
import com.faskn.lib.legend.LegendAdapter
import com.faskn.lib.legend.LegendItemViewHolder
import com.lnoxx.myanimeview.AnimeViewApplication
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.CustomItemLegendBinding
import com.lnoxx.myanimeview.databinding.ItemAnimeViewStatisticBinding
import com.lnoxx.myanimeview.jikanApi.JikanMainClass
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewHolderInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatisticViewHolder(view: View): RecyclerView.ViewHolder(view), AnimeViewHolderInterface {
    private val binding = ItemAnimeViewStatisticBinding.bind(view)
    private val application = itemView.context.applicationContext as AnimeViewApplication
    private val watchingText = itemView.context.getText(R.string.watching).toString()
    private val completedText = itemView.context.getText(R.string.completed).toString()
    private val onHoldText = itemView.context.getText(R.string.onHold).toString()
    private val droppedText = itemView.context.getText(R.string.dropped).toString()
    private val planToWatch = itemView.context.getText(R.string.planToWatch).toString()
    override fun bind(anime: Anime) {
        val statistic = application.animeStatisticCache[anime.mal_id] ?: return
        binding.progressCircle.hide()
        val sliceWatching = Slice(statistic.watching.toFloat(), R.color.watching, watchingText)
        val sliceCompleted = Slice(statistic.completed.toFloat(), R.color.completed, completedText)
        val sliceOnHold = Slice(statistic.on_hold.toFloat(), R.color.onHold, onHoldText)
        val sliceDropped = Slice(statistic.dropped.toFloat(), R.color.dropped, droppedText)
        val slicePlanToWatch = Slice(statistic.plan_to_watch.toFloat(), R.color.planToWatch, planToWatch)

        val pieChart = PieChart(
            arrayListOf(sliceWatching, sliceCompleted, sliceOnHold, sliceDropped, slicePlanToWatch),
            null, 0f, 96f
        ).build()

        binding.statisticGraphic.showPopup(false)
        if (isDarkTheme()){
            binding.statisticGraphic.setCenterColor(R.color.backgroundPieDark)
        }else{
            binding.statisticGraphic.setCenterColor(R.color.backgroundPieWhite)
        }
        binding.statisticGraphic.setPieChart(pieChart)
        binding.statisticGraphic.showLegend(binding.legendLayout, CustomLegendAdapter())
        binding.statisticGraphic.animate().apply {
            duration = 400
            alpha(1f)
        }
    }
    private fun isDarkTheme(): Boolean {
        val mode = itemView.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }
}

class CustomLegendAdapter: LegendAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomLegendItemViewHolder {
        return CustomLegendItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_item_legend, parent, false)
        )
    }

    class CustomLegendItemViewHolder(view: View) : LegendItemViewHolder(view) {
        private val binding = CustomItemLegendBinding.bind(view)
        override fun bind(slice: Slice) {
            this.boundItem = slice
            binding.imageView2.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(itemView.context, slice.color))
            val text = slice.name
            binding.textView4.text = text
        }
    }
}