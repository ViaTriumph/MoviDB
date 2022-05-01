package com.practice.movidb.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practice.movidb.databinding.RowSearchItemBinding
import com.practice.movidb.ui.explore.MovieItemUI

class SearchMovieAdapter :
    ListAdapter<MovieItemUI, SearchMovieAdapter.SearchMovieViewHolder>(SearchMovieCallback()) {

    class SearchMovieViewHolder(private val bindingView: RowSearchItemBinding) :
        RecyclerView.ViewHolder(bindingView.root) {
        fun bind(obj: MovieItemUI) {
            bindingView.obj = obj
            bindingView.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): SearchMovieViewHolder {
                val binding = RowSearchItemBinding.inflate(LayoutInflater.from(parent.context))
                return SearchMovieViewHolder(binding)
            }
        }
    }

    class SearchMovieCallback : DiffUtil.ItemCallback<MovieItemUI>() {
        override fun areItemsTheSame(oldItem: MovieItemUI, newItem: MovieItemUI): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieItemUI, newItem: MovieItemUI): Boolean {
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