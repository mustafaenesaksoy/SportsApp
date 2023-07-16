package com.enesaksoy.sportsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.enesaksoy.sportsapp.databinding.TableRowBinding
import com.enesaksoy.sportsapp.model.TableResponseItem
import javax.inject.Inject

class TableAdapter @Inject constructor(private val glide : RequestManager): RecyclerView.Adapter<TableAdapter.TableHolder>() {

    private val diffutil = object : DiffUtil.ItemCallback<TableResponseItem>(){
        override fun areItemsTheSame(
            oldItem: TableResponseItem,
            newItem: TableResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: TableResponseItem,
            newItem: TableResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerList = AsyncListDiffer(this,diffutil)

    var teamList : List<TableResponseItem>
    get() = recyclerList.currentList
    set(value) = recyclerList.submitList(value)

    class TableHolder (val binding: TableRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableHolder {
        val binding = TableRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TableHolder(binding)
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(holder: TableHolder, position: Int) {
        val team = teamList.get(position)
        holder.binding.teamA.text = team.GoalDifference
        holder.binding.teamL.text = team.Loosed
        holder.binding.teamMP.text = team.Played
        holder.binding.teamName.text = team.Name
        holder.binding.teamP.text = team.Points
        holder.binding.teamT.text = team.Tie
        holder.binding.teamW.text = team.Winned
        holder.binding.teamPosition.text = team.Position
        glide.load(team.SquadLogo).into(holder.binding.teamImage)
    }
}