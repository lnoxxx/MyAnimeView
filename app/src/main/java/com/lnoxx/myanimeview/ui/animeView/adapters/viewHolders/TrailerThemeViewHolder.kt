package com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.BottomSheetThemeBinding
import com.lnoxx.myanimeview.databinding.ItemAnimeViewTrailerThemeBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Theme
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewHolderInterface
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

class TrailerThemeViewHolder(view: View)
    : RecyclerView.ViewHolder(view), AnimeViewHolderInterface {

    private val binding = ItemAnimeViewTrailerThemeBinding.bind(view)
    override fun bind(anime: Anime) {
        if (anime.trailer?.url == null){
            binding.TrailerCardView.visibility = View.GONE
        } else {
            Picasso.get().load(anime.trailer.images.image_url)
                .transform(BlurTransformation(itemView.context,5))
                .into(binding.TrailerImageView)
        }
        binding.ThemeCardView.setOnClickListener {
            if (anime.theme.endings.isEmpty() && anime.theme.openings.isEmpty()){
                Toast.makeText(itemView.context,R.string.anime_dont_have_themes,Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val context = itemView.context
            if (context is FragmentActivity){
                val bottomSheet = ThemeBottomSheet(anime.theme)
                bottomSheet.show(context.supportFragmentManager, ThemeBottomSheet.bottomSheetTag)
            }
        }
        binding.TrailerCardView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(anime.trailer?.url))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(itemView.context,intent,null)
        }
    }
}

class ThemeBottomSheet(private val themes: Theme): BottomSheetDialogFragment(){
    private lateinit var bottomSheetBinding : BottomSheetThemeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bottomSheetBinding = BottomSheetThemeBinding.inflate(inflater)
        return bottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBinding.themeScrollView.isNestedScrollingEnabled = true
        var openingText = ""
        themes.openings.forEach { opening ->
            openingText += "$opening\n"
        }
        var endingText = ""
        themes.endings.forEach { opening ->
            endingText += "$opening\n"
        }
        val text = "${context?.getString(R.string.openings) ?: "Openings:"}\n$openingText" +
                "\n${context?.getString(R.string.endings) ?: "Endings:"}\n$endingText"
        bottomSheetBinding.openingEndingsText.text = text
    }
    companion object{
        const val bottomSheetTag = "ThemeBottomSheet"
    }
}