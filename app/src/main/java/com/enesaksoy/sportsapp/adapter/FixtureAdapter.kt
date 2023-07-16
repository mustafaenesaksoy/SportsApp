package com.enesaksoy.sportsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.enesaksoy.sportsapp.databinding.FixtureRowBinding
import com.enesaksoy.sportsapp.model.FixtureResponse
import javax.inject.Inject

class FixtureAdapter@Inject constructor(private val glide : RequestManager) : RecyclerView.Adapter<FixtureAdapter.FixtureHolder>(){

    private val diffUtil = object : DiffUtil.ItemCallback<FixtureResponse>(){
        override fun areItemsTheSame(
            oldItem: FixtureResponse,
            newItem: FixtureResponse
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: FixtureResponse,
            newItem: FixtureResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerList =  AsyncListDiffer(this,diffUtil)

    var fixtureList : List<FixtureResponse>
        get() = recyclerList.currentList
        set(value) = recyclerList.submitList(value)

    class FixtureHolder (val binding : FixtureRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixtureHolder {
        val binding = FixtureRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FixtureHolder(binding)
    }

    override fun getItemCount(): Int {
        return fixtureList.size
    }

    override fun onBindViewHolder(holder: FixtureHolder, position: Int) {
        val fixture = fixtureList.get(position)
        holder.binding.matchDay.text = fixture.MatchDay
        holder.binding.homeName.text = fixture.homeTeam
        glide.load(fixture.homeLogo).into(holder.binding.homeImage)
        holder.binding.awayName.text = fixture.awayTeam
        glide.load(fixture.awayLogo).into(holder.binding.awayImage)
    }
}