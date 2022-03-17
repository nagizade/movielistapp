package com.hasannagizade.movielistapp.presentation.tabs.toprated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.databinding.FragmentTopRatedBinding
import com.hasannagizade.movielistapp.tools.BaseFragment
import com.hasannagizade.movielistapp.tools.GridSpacingItemDecoration
import com.hasannagizade.movielistapp.tools.MovieListAdapter
import com.hasannagizade.movielistapp.tools.PaginationListener
import kotlin.reflect.KClass

class TopRatedFragment : BaseFragment<TopRatedViewModel>() {

    override val vmClazz: KClass<TopRatedViewModel>
        get() = TopRatedViewModel::class
    override val screenName: String
        get() = "top_rated"

    private lateinit var binding : FragmentTopRatedBinding

    private val movieListAdapter: MovieListAdapter by lazy { MovieListAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopRatedBinding.inflate(inflater)

        binding.topRatedRecycler.layoutManager = GridLayoutManager(binding.root.context,3)

        binding.topRatedRecycler.addItemDecoration(GridSpacingItemDecoration(
            3,
            10,
            true))

        binding.topRatedRecycler.addOnScrollListener(object :
            PaginationListener(binding.topRatedRecycler.layoutManager as GridLayoutManager) {
            override fun loadMoreItems() {
                viewModel.loadData()
            }

            override fun isLastPage(): Boolean {
                return !viewModel.pagination.hasNext
            }

            override fun isLoading(): Boolean {
                return viewModel.pagination.isLoading
            }
        })

        movieListAdapter.listener = object : MovieListAdapter.OnInteractionListener {
            override fun onClick(item: MovieItem) {
            }

        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is TopRatedState.Show -> {
                    movieListAdapter.setData(state.data)
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.topRatedSwipeRefresh.isRefreshing = it
        }

        binding.topRatedSwipeRefresh.setOnRefreshListener {
            viewModel.reloadData()
        }

        if (viewModel.state.value == null) {
            viewModel.reloadData()
        }

        binding.topRatedRecycler.adapter = movieListAdapter

        return binding.root
    }
}