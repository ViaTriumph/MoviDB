package com.practice.movidb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practice.movidb.databinding.RowMovieBinding
import com.practice.movidb.shared.domain.movie.Movie

class MovieAdapter : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieCallback()) {

    class MovieViewHolder(private val bindingView: RowMovieBinding) :
        RecyclerView.ViewHolder(bindingView.root) {
        fun bind(obj: Movie) {
            bindingView.obj = obj
            bindingView.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): MovieViewHolder {
                val binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context))
                return MovieViewHolder(binding)
            }
        }
    }

    class MovieCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}