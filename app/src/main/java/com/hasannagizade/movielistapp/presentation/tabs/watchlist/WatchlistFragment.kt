package com.hasannagizade.movielistapp.presentation.tabs.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.databinding.FragmentWatchlistBinding
import com.hasannagizade.movielistapp.presentation.tabs.upcoming.UpcomingFragmentDirections
import com.hasannagizade.movielistapp.tools.BaseFragment
import com.hasannagizade.movielistapp.tools.GridSpacingItemDecoration
import com.hasannagizade.movielistapp.tools.MovieListAdapter
import kotlin.reflect.KClass

class WatchlistFragment : BaseFragment<WatchlistViewModel>() {

    override val vmClazz: KClass<WatchlistViewModel>
    get() = WatchlistViewModel::class
    override val screenName: String
    get() = "watchlist"

    lateinit var binding: FragmentWatchlistBinding

    private val movieListAdapter: MovieListAdapter by lazy { MovieListAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchlistBinding.inflate(inflater)

        binding.watchlistRecycler.layoutManager = GridLayoutManager(binding.root.context,3)
        binding.watchlistRecycler.addItemDecoration(
            GridSpacingItemDecoration(
                3,
                10,
                true)
        )

        movieListAdapter.listener = object : MovieListAdapter.OnInteractionListener {
            override fun onClick(item: MovieItem) {
                findNavController().navigate(UpcomingFragmentDirections.toMovieDetails(item))
            }

        }

        binding.watchlistRecycler.adapter = movieListAdapter


        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is WatchlistState.Show -> {
                    movieListAdapter.setData(state.data)
                }
            }
        }

        viewModel.loader.observe(viewLifecycleOwner) {
            binding.watchlistSwipe.isRefreshing = it
        }

        binding.watchlistSwipe.setOnRefreshListener {
            viewModel.reloadData()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadData()
    }
}