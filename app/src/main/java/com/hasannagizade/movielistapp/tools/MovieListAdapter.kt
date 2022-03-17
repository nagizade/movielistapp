package com.hasannagizade.movielistapp.tools

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.databinding.ListItemMovieBinding

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieVH>() {

    private val items = arrayListOf<MovieItem>()
    var listener: OnInteractionListener? = null

    fun setData(list: List<MovieItem>) {

        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class MovieVH(val binding: ListItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieItem) {
            Glide.with(binding.root.context)
                .load("http://image.tmdb.org/t/p/w154/${item.poster_path}")
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieBinding.inflate(inflater, parent, false)
        return MovieVH(binding)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bind(items[position])

        holder.binding.root.setOnClickListener {
            listener?.onClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnInteractionListener {
        fun onClick(item: MovieItem)
    }
}