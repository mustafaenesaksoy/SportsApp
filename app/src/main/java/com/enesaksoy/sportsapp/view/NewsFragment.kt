package com.enesaksoy.sportsapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.enesaksoy.sportsapp.R
import com.enesaksoy.sportsapp.adapter.NewsAdapter
import com.enesaksoy.sportsapp.databinding.NewsFragmentBinding
import com.enesaksoy.sportsapp.util.Status
import com.enesaksoy.sportsapp.viewmodel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFragment @Inject constructor(private val adapter : NewsAdapter): Fragment(R.layout.news_fragment) {

    private lateinit var binding : NewsFragmentBinding
    private lateinit var viewmodel : NewsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NewsFragmentBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        viewmodel.refreshdata("premierleague")
        var job : Job? = null
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                it?.let {
                    if(it.isNotEmpty()){
                        viewmodel.getNewsList(it.toString())
                    }
                }
            }
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerNewsList.visibility = View.GONE
            viewmodel.getNewsList("premierleague")
            binding.swipeRefreshLayout.isRefreshing = false
        }



        adapter.OnItemClicked {
            viewmodel.setSelectedNew(it)
            val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }

        binding.recyclerNewsList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerNewsList.adapter = adapter

        observeOn()
    }

    private fun observeOn(){
        viewmodel.newsList.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS){
                it.data?.let {
                    binding.recyclerNewsList.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    adapter.news = it
                    adapter.notifyDataSetChanged()
                }
            }else if(it.status == Status.ERROR){
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
            }else if(it.status == Status.LOADING){
                binding.progressBar.visibility = View.VISIBLE
            }
        })
    }
}