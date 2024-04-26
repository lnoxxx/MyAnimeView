package com.lnoxx.myanimeview.ui.charactersFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.ItemCharactersBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.CharacterData
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.VoiceActor
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

const val japaneseLanguage = "Japanese"

class CharactersRvAdapter(private val charactersList: List<CharacterData>):
    RecyclerView.Adapter<CharactersRvAdapter.CharacterViewHolder>() {
    inner class CharacterViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemCharactersBinding.bind(view)
        fun bind(character: CharacterData){
            var voiceActor: VoiceActor? = null
            character.voice_actors.forEach {
                if (it.language == japaneseLanguage) voiceActor = it
            }
            binding.ActorLanguage.text = voiceActor?.language ?: ""
            binding.VoiceActorName.text = voiceActor?.person?.name ?: "?"
            binding.CharacterName.text = character.character.name ?: "?"
            binding.RoleTextView.text = character.role ?: "?"
            Picasso.get()
                .load(character.character.images.jpg.image_url).into(binding.CharacterImage)
            Picasso.get()
                .load(voiceActor?.person?.images?.jpg?.image_url).into(binding.VoiceActorImageView)
            Picasso.get().load(character.character.images.jpg.image_url)
                .transform(BlurTransformation(itemView.context, 50))
                .into(binding.CharecterBackground)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_characters, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount() = charactersList.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(charactersList[position])
    }
}