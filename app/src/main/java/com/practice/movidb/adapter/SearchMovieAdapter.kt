package com.practice.movidb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.practice.movidb.R
import com.practice.movidb.databinding.RowRecyclerviewItemBinding
import com.practice.movidb.network.movie.domain.model.Result

class SearchMovieAdapter: ListAdapter<Result, SearchMovieAdapter.SearchMovieViewHolder>(SearchMovieCallback()) {

    class SearchMovieViewHolder(private val bindingView: RowRecyclerviewItemBinding): RecyclerView.ViewHolder(bindingView.root){

        fun bind(obj: Result){
            bindingView.obj = obj
            bindingView.executePendingBindings()
        }

        companion object{
            fun create(parent: ViewGroup): SearchMovieViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemView = RowRecyclerviewItemBinding.inflate(layoutInflater, parent, false)
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