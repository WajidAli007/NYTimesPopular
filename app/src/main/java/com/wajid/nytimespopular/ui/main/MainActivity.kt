package com.wajid.nytimespopular.ui.main

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.wajid.nytimespopular.R
import com.wajid.nytimespopular.base.BaseActivity
import com.wajid.nytimespopular.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    //by lazy view model will be provided
    private val mainViewModel: MainActivityViewModel by viewModel()

    //binding object of the main activity
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    //recycler view adapter
    private var popularRecyclerViewAdapter: PopularRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "NY Times Most Popular"

        popularRecyclerViewAdapter = PopularRecyclerViewAdapter(mainViewModel.listItems)
        binding.rvPopularItems.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvPopularItems.adapter = popularRecyclerViewAdapter

        setUpListeners()
        //fetch data, if cache is there, fetch from cache instead.
        mainViewModel.fetchTrendingRepos()
    }

    /**
     * listeners
     * pull to refresh, to update latest records
     * live data listeners for updating UI at the right time
     */
    private fun setUpListeners() {

        binding.pullToRefresh.setOnRefreshListener {
            mainViewModel.fetchTrendingRepos()
            binding.pullToRefresh.isRefreshing = false
        }

        mainViewModel.successLiveData.observe(this) {
            popularRecyclerViewAdapter?.notifyDataSetChanged()
            binding.rvPopularItems.visibility = View.VISIBLE
        }

        mainViewModel.failureLiveData.observe(this) {
            binding.errorAnimationView.visibility = View.VISIBLE
        }

        mainViewModel.loadingLiveData.observe(this) {
            if (it) {
                binding.rvPopularItems.visibility = View.GONE
                binding.shimmerFrame.visibility = View.VISIBLE
                binding.shimmerFrame.startShimmer()
                binding.pullToRefresh.isRefreshing = false
                binding.errorAnimationView.visibility = View.GONE
            } else {
                binding.shimmerFrame.stopShimmer()
                binding.shimmerFrame.visibility = View.GONE
                binding.pullToRefresh.isRefreshing = false
            }
        }
    }
}