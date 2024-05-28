package com.lnoxx.myanimeview.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.lnoxx.myanimeview.AnimeViewApplication
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentFavoriteBinding
import com.lnoxx.myanimeview.favoriteDatabase.FavoriteAnimeEntity
import com.lnoxx.myanimeview.jikanApi.JikanMainClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Firebase.auth.currentUser == null){
            binding.noUserLayout.visibility = View.VISIBLE
            binding.registerButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_favorite_to_registrationFragment)
            }
            binding.singInButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_favorite_to_loginFragment)
            }
            binding.favProgressIndicator.hide()
        }else {
            addUserData()
            binding.noUserLayout.visibility = View.GONE
        }
        setActionBar()
    }

    private fun addUserData(){
        val userAnimeList = mutableListOf<Int>()
        FirebaseDatabase
            .getInstance()
            .getReference()
            .child("users")
            .child(Firebase.auth.uid!!).get().addOnSuccessListener {
                for (catalog in it.children){
                    userAnimeList.add(catalog.key?.toInt() ?: -1)
                }
                showUserAnimeData(userAnimeList)
            }
    }


    private fun setActionBar(){
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_setings_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.settingsIcon -> {
                        findNavController().navigate(R.id.action_navigation_favorite_to_accountSettingsFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showUserAnimeData(userAnimeList: MutableList<Int>){
        if(userAnimeList.isEmpty()){
            binding.hintDontHaveFavorite.visibility = View.VISIBLE
        }else{
            binding.hintDontHaveFavorite.visibility = View.GONE
        }
        val favAnimeList = mutableListOf<FavoriteAnimeEntity>()
        val application = requireContext().applicationContext as AnimeViewApplication
        binding.favProgressIndicator.show()
        CoroutineScope(Dispatchers.IO).launch {
            for (id in userAnimeList){
                var anime = application.favoriteAnimeDatabase.getDao().getByMalId(id)
                if (anime == null){
                    val response = JikanMainClass.getBaseAnimeInfo(id)
                    if (response == null){
                        withContext(Dispatchers.Main){
                            Snackbar.make(requireView(),getText(R.string.error_loading), Snackbar.LENGTH_SHORT)
                                .show()
                        }
                        return@launch
                    }
                    anime = FavoriteAnimeEntity(id,
                        response.title ?: "?",
                        response.images.jpg.image_url,
                        response.episodes,
                        response.score ?: 0.0,)
                    application.favoriteAnimeDatabase.getDao().cache(anime)
                }
                favAnimeList.add(anime)
            }
            withContext(Dispatchers.Main){
                binding.favProgressIndicator.hide()
                binding.favoriteAnimeList.layoutManager = LinearLayoutManager(context)
                binding.favoriteAnimeList.adapter = FavoriteAdapter(favAnimeList)
            }
        }
    }
}