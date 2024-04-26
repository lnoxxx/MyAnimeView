package com.lnoxx.myanimeview.ui.related

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lnoxx.myanimeview.MainActivity
import com.lnoxx.myanimeview.databinding.FragmentRelatedBinding
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Entry

class RelatedFragment : Fragment() {
    lateinit var binding: FragmentRelatedBinding

    private var currentFragmentRelatedData = mutableListOf<Entry>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRelatedBinding.inflate(inflater)
        if (currentFragmentRelatedData.isEmpty()){
            currentFragmentRelatedData.addAll(relatedData)
        }
        if (currentFragmentRelatedData.isEmpty())
            binding.EmptyRelationsTextView.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (currentFragmentRelatedData.isEmpty())
            binding.EmptyRelationsTextView.visibility = View.VISIBLE
        binding.RelatedRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.RelatedRecyclerView.adapter = RelatedAdapter(currentFragmentRelatedData)
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
        var relatedData = mutableListOf<Entry>()
    }
}