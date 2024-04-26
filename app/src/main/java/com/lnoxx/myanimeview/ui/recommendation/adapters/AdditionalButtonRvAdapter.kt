package com.lnoxx.myanimeview.ui.recommendation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.AdditionalRecItemBinding
import com.lnoxx.myanimeview.ui.recommendation.adapters.additionalButtons.AdditionalButton

class AdditionalButtonRvAdapter
    : RecyclerView.Adapter<AdditionalButtonRvAdapter.AdditionalButtonViewHolder>() {

    private val buttonList = AdditionalButton.entries

    class AdditionalButtonViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = AdditionalRecItemBinding.bind(view)
        fun bind(additionalButton: AdditionalButton){
            val title = itemView.context.getString(additionalButton.titleResId)
            val icon = AppCompatResources.getDrawable(itemView.context, additionalButton.iconResId)
            binding.AdditionalButton
                .setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null)
            binding.AdditionalButton.text = title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditionalButtonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.additional_rec_item , parent, false)
        return AdditionalButtonViewHolder(view)
    }

    override fun getItemCount() = buttonList.size

    override fun onBindViewHolder(holder: AdditionalButtonViewHolder, position: Int) {
        holder.bind(buttonList[position])
    }
}