package com.lnoxx.myanimeview.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentFavoriteBinding

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
        binding.accountSettingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_favorite_to_accountSettingsFragment)
        }
        if (Firebase.auth.currentUser == null){
            binding.userCardView.visibility = View.GONE
            binding.noUserLayout.visibility = View.VISIBLE
            binding.registerButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_favorite_to_registrationFragment)
            }
            binding.singInButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_favorite_to_loginFragment)
            }
        }else {
            binding.userCardView.visibility = View.VISIBLE
            binding.noUserLayout.visibility = View.GONE
            binding.userMail.text = Firebase.auth.currentUser?.email ?: "error!"
        }
    }
}