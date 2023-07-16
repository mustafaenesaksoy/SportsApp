package com.enesaksoy.sportsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.enesaksoy.sportsapp.databinding.ResultRowBinding
import com.enesaksoy.sportsapp.model.ResultResponse
import javax.inject.Inject

class ResultAdapter @Inject constructor(private val glide : RequestManager): RecyclerView.Adapter<ResultAdapter.ResultHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<ResultResponse>(){
        override fun areItemsTheSame(
            oldItem: ResultResponse,
            newItem: ResultResponse
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ResultResponse,
            newItem: ResultResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerList =  AsyncListDiffer(this,diffUtil)

    var resultList : List<ResultResponse>
    get() = recyclerList.currentList
    set(value) = recyclerList.submitList(value)


    class ResultHolder (val binding : ResultRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        val binding = ResultRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ResultHolder(binding)
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        val result = resultList.get(position)
        holder.binding.matchDay.visibility = View.INVISIBLE
        holder.binding.homeName.text = result.homeTeam
        holder.binding.homeScore.text = result.homeTeamScore
        glide.load(result.homeLogo).into(holder.binding.homeImage)
        holder.binding.awayName.text = result.awayTeam
        holder.binding.awayScore.text = result.awayTeamScore
        glide.load(result.awayLogo).into(holder.binding.awayImage)


    }
}