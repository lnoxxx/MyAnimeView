package com.lnoxx.myanimeview.ui.charactersFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lnoxx.myanimeview.AnimeViewApplication
import com.lnoxx.myanimeview.MainActivity
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentCharactersBinding
import com.lnoxx.myanimeview.jikanApi.JikanMainClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding
    private var currentAnimeId = 0
    private lateinit var application: AnimeViewApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentAnimeId = animeId
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater)
        application = requireContext().applicationContext as AnimeViewApplication
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.NoCharactersTextView.visibility = View.GONE
        binding.CharactersProgressIndicator.show()
        CoroutineScope(Dispatchers.IO).launch {
            var charactersList = application.charactersCache[currentAnimeId]
            if (charactersList == null){
                charactersList = JikanMainClass.getAnimeCharacters(currentAnimeId)
                if (charactersList == null){
                    withContext(Dispatchers.Main){
                        Snackbar.make(requireView(),getText(R.string.error_loading), Snackbar.LENGTH_SHORT)
                            .show()
                    }
                    return@launch
                }
                application.charactersCache[currentAnimeId] = charactersList
            }
            launch(Dispatchers.Main){
                binding.CharactersProgressIndicator.hide()
                binding.CharactersRecyclerView.layoutManager = LinearLayoutManager(context)
                binding.CharactersRecyclerView.adapter = CharactersRvAdapter(charactersList.data)
                if (charactersList.data.isEmpty())
                    binding.NoCharactersTextView.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomNavBar(false)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).showBottomNavBar(true)
    }

    companion object {
        var animeId = 0
    }
}