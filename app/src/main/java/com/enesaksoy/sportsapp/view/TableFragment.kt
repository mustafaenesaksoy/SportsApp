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
import com.enesaksoy.sportsapp.adapter.TableAdapter
import com.enesaksoy.sportsapp.databinding.TableFragmentBinding
import com.enesaksoy.sportsapp.util.Status
import com.enesaksoy.sportsapp.viewmodel.TablesViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
class TableFragment @Inject constructor(private val adapter : TableAdapter,private val auth: FirebaseAuth): Fragment(R.layout.table_fragment) {
    private lateinit var binding : TableFragmentBinding
    private lateinit var viewmodel : TablesViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TableFragmentBinding.bind(view)
        viewmodel = ViewModelProvider(requireActivity()).get(TablesViewModel::class.java)
        if (auth.currentUser != null) {
            var job: Job? = null
            viewmodel.getTableList("premierleague")
            binding.searchText.addTextChangedListener {
                job?.cancel()
                job = lifecycleScope.launch {
                    viewmodel.getTableList(it.toString())
                }
            }
            binding.swipeRefreshLayout.setOnRefreshListener {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerTableList.visibility = View.GONE
                viewmodel.getTableList("premierleague")
            }

            binding.recyclerTableList.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerTableList.adapter = adapter
            observeOn()
        }else{
            binding.searchText.visibility = View.INVISIBLE
            Toast.makeText(requireContext(),"PLEASE LOGIN.",Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeOn(){
        viewmodel.gettableList.observe(viewLifecycleOwner, Observer {
            if(it.status == Status.SUCCESS){
                binding.recyclerTableList.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                it.data?.let {
                    adapter.teamList = it
                    adapter.notifyDataSetChanged()
                }
            }else if (it.status == Status.ERROR){
                binding.progressBar.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
            }else if(it.status == Status.LOADING){
                binding.progressBar.visibility = View.VISIBLE
            }
        })
    }
}