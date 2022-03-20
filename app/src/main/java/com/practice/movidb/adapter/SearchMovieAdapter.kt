package com.practice.movidb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practice.movidb.databinding.RowSearchItemBinding
import com.practice.movidb.network.movie.domain.model.Movie

class SearchMovieAdapter :
    ListAdapter<Movie, SearchMovieAdapter.SearchMovieViewHolder>(SearchMovieCallback()) {

    class SearchMovieViewHolder(private val bindingView: RowSearchItemBinding) :
        RecyclerView.ViewHolder(bindingView.root) {

        fun bind(obj: Movie) {
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

    class SearchMovieCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
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