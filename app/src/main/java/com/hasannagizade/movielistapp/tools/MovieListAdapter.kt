package com.hasannagizade.movielistapp.tools

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.databinding.ListItemMovieBinding

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieVH>() {

    private var items : List<MovieItem> = listOf()
    var listener: OnInteractionListener? = null

    fun setData(newMovieList: List<MovieItem>) {
        val categoryDiff = CategoryListDiff(items,newMovieList)
        val diffResult = DiffUtil.calculateDiff(categoryDiff)
        diffResult.dispatchUpdatesTo(this)
        this.items = newMovieList
    }

    class MovieVH(val binding: ListItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieItem) {

            Glide.with(binding.root.context)
                .load("http://image.tmdb.org/t/p/w154/${item.poster_path}")
                .thumbnail(0.1f)
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

open class CategoryListDiff(
    private val oldList: List<MovieItem>,
    private val newList: List<MovieItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

}