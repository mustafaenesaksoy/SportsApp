package com.enesaksoy.sportsapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesaksoy.sportsapp.R
import com.enesaksoy.sportsapp.adapter.ResultAdapter
import com.enesaksoy.sportsapp.adapter.FixtureAdapter
import com.enesaksoy.sportsapp.databinding.FixtureFragmentBinding
import com.enesaksoy.sportsapp.util.Status
import com.enesaksoy.sportsapp.viewmodel.FixturesViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class FixtureFragment  @Inject constructor(private val adapter: ResultAdapter,
                                           private val adapter2 : FixtureAdapter,
                                           private val auth : FirebaseAuth
                                           ): Fragment(R.layout.fixture_fragment) {
    private lateinit var binding: FixtureFragmentBinding
    private lateinit var viewModel : FixturesViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FixtureFragmentBinding.bind(view)
        if(auth.currentUser != null) {
            viewModel = ViewModelProvider(requireActivity()).get(FixturesViewModel::class.java)
            viewModel.getResult("premierleague")
            viewModel.getFixture("premierleague")
            var job: Job? = null
            binding.searchText.addTextChangedListener {
                job?.cancel()
                job = lifecycleScope.launch {
                    it?.let {
                        if (it.isNotEmpty()) {
                            viewModel.getResult(it.toString())
                            viewModel.getFixture(it.toString())
                        }
                    }
                }
            }
            binding.swipeRefreshLayout.setOnRefreshListener {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerResultList.visibility = View.GONE
                viewModel.getResult("premierleague")
                viewModel.getFixture("premierleague")
            }
            binding.recyclerResultList.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerFixtureList.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerResultList.adapter = adapter
            binding.recyclerFixtureList.adapter = adapter2
            observeOn()
        }else{
            binding.searchText.visibility = View.INVISIBLE
            Toast.makeText(requireContext(),"PLEASE LOGIN.",Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeOn(){
        viewModel.getresult.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS){
                binding.progressBar.visibility = View.GONE
                binding.recyclerResultList.visibility = View.VISIBLE
                it.data?.let {
                    adapter.resultList = it
                    adapter.notifyDataSetChanged()
                }
            }else if (it.status == Status.ERROR){
                binding.progressBar.visibility = View.GONE
                binding.recyclerResultList.visibility = View.GONE
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
            }else if (it.status == Status.LOADING){
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerResultList.visibility = View.GONE
            }
        })

        viewModel.getfixture.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS){
                it.data?.let {
                    adapter2.fixtureList = it
                    adapter.notifyDataSetChanged()
                }
            }else if (it.status == Status.ERROR){
                binding.progressBar.visibility = View.GONE
                binding.recyclerResultList.visibility = View.GONE
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
            }else if (it.status == Status.LOADING){
                binding.recyclerResultList.visibility = View.GONE
            }
        })
    }
}