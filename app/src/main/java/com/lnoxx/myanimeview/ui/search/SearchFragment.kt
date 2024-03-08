package com.lnoxx.myanimeview.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.lnoxx.myanimeview.MainActivity
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentSearchFragementBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchFragementBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchFragementBinding.inflate(inflater)
        return binding.root
    }
}