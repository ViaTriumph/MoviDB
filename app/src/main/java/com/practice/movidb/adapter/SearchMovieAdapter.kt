package com.practice.movidb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practice.movidb.R
import com.practice.movidb.network.search.model.Result

class SearchMovieAdapter: ListAdapter<Result, SearchMovieAdapter.SearchMovieViewHolder>(SearchMovieCallback()) {

    class SearchMovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val view: TextView = itemView.findViewById(R.id.searchTitle)

        fun bind(obj: Result){
            view.text = obj.title
        }

        companion object{
            fun create(parent: ViewGroup): SearchMovieViewHolder{
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_recyclerview_item, parent, false)
                return SearchMovieViewHolder(itemView)
            }
        }
    }

    class SearchMovieCallback: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        return SearchMovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}