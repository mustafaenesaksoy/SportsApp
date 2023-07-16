package com.enesaksoy.sportsapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.enesaksoy.sportsapp.R
import com.enesaksoy.sportsapp.databinding.NewsDetailsBinding
import com.enesaksoy.sportsapp.viewmodel.NewsViewModel
import javax.inject.Inject

class NewsDetailsFragment @Inject constructor(private val glide : RequestManager): Fragment(R.layout.news_details) {
    private lateinit var binding : NewsDetailsBinding
    private lateinit var viewModel : NewsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NewsDetailsBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        observeOn()
    }
    private fun observeOn(){
        viewModel.selectednew.observe(viewLifecycleOwner, Observer {
            binding.newsDate.text = it.PublisherDate
            binding.newsTitle.text = it.Title
            glide.load(it.Image).into(binding.newsImageView)

            binding.newsDetailsBtn.setOnClickListener {view ->
                val uri = Uri.parse(it.NewsLink)
                val intent = Intent(Intent.ACTION_VIEW,uri)
                startActivity(intent)
            }
        })
    }
}