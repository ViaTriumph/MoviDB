package com.practice.movidb.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practice.movidb.databinding.RowMovieBinding
import com.practice.movidb.ui.explore.MovieItemUI
import com.practice.movidb.ui.explore.MovieModel

class MovieAdapter(private val model: MovieModel) :
    ListAdapter<MovieItemUI, MovieAdapter.MovieViewHolder>(MovieCallback()) {

    class MovieViewHolder(private val bindingView: RowMovieBinding) :
        RecyclerView.ViewHolder(bindingView.root) {
        fun bind(obj: MovieItemUI, model: MovieModel) {
            bindingView.obj = obj
            bindingView.model = model
            bindingView.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): MovieViewHolder {
                val binding = RowMovieBinding.inflate(LayoutInflater.from(parent.context))
                return MovieViewHolder(binding)
            }
        }
    }

    class MovieCallback : DiffUtil.ItemCallback<MovieItemUI>() {
        override fun areItemsTheSame(oldItem: MovieItemUI, newItem: MovieItemUI): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieItemUI, newItem: MovieItemUI): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), model)
    }
}