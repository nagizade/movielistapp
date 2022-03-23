package com.hasannagizade.movielistapp.presentation.tabs.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.databinding.FragmentUpcomingBinding
import com.hasannagizade.movielistapp.tools.BaseFragment
import com.hasannagizade.movielistapp.tools.GridSpacingItemDecoration
import com.hasannagizade.movielistapp.tools.MovieListAdapter
import com.hasannagizade.movielistapp.tools.PaginationListener
import kotlin.reflect.KClass

class UpcomingFragment : BaseFragment<UpcomingViewModel>() {

    override val vmClazz: KClass<UpcomingViewModel>
        get() = UpcomingViewModel::class
    override val screenName: String
        get() = "upcoming"

    lateinit var binding: FragmentUpcomingBinding

    private val movieListAdapter: MovieListAdapter by lazy { MovieListAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingBinding.inflate(inflater)

        binding.upcomingrecycler.layoutManager = GridLayoutManager(binding.root.context,3)
        binding.upcomingrecycler.addItemDecoration(
            GridSpacingItemDecoration(
            3,
            10,
            true)
        )

        binding.upcomingrecycler.addOnScrollListener(object :
            PaginationListener(binding.upcomingrecycler.layoutManager as GridLayoutManager) {
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
                findNavController().navigate(UpcomingFragmentDirections.toMovieDetails(item))
            }

        }

        binding.upcomingrecycler.adapter = movieListAdapter


        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UpcomingState.Show -> {
                    movieListAdapter.setData(state.data)
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.upcomingSwipeRefresh.isRefreshing = it
        }

        binding.upcomingSwipeRefresh.setOnRefreshListener {
            viewModel.reloadData()
        }

        if (viewModel.state.value == null) {
            viewModel.reloadData()
        }

        return binding.root
    }
}