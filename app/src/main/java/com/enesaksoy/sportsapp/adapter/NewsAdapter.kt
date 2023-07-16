package com.enesaksoy.sportsapp.adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.enesaksoy.sportsapp.databinding.NewsRowBinding
import com.enesaksoy.sportsapp.model.NewsResponseItem
import javax.inject.Inject

class NewsAdapter @Inject constructor(): RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private var OnClickListener : ((NewsResponseItem) -> Unit)? = null
    private val diffutil = object : DiffUtil.ItemCallback<NewsResponseItem>(){
        override fun areItemsTheSame(
            oldItem: NewsResponseItem,
            newItem: NewsResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: NewsResponseItem,
            newItem: NewsResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerList = AsyncListDiffer(this,diffutil)

    var news : List<NewsResponseItem>
    get() = recyclerList.currentList
    set(value) = recyclerList.submitList(value)

    class NewsHolder (val binding : NewsRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = NewsRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsHolder(binding)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun OnItemClicked(listener : (NewsResponseItem) -> Unit){
        OnClickListener = listener
    }
    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val new = news.get(position)
        holder.binding.rowNewsTitle.text = new.Title
        holder.itemView.setOnClickListener {
            OnClickListener?.let {
                it(new)
            }
        }
    }
}