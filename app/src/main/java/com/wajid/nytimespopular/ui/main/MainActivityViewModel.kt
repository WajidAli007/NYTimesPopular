package com.wajid.nytimespopular.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wajid.nytimespopular.base.BaseViewModel
import com.wajid.nytimespopular.repos.NYPopularRepo
import com.wajid.nytimespopular.services.ErrorModel
import com.wajid.nytimespopular.services.networkModels.popularArticles.NyPopularResultsItem
import com.wajid.nytimespopular.services.networkModels.popularArticles.PopularItemsResponse
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class MainActivityViewModel(
    private val nyPopularRepo: NYPopularRepo
) : BaseViewModel() {

    /**
     * live data objects
     * for live notifications to UI
     */
    val successLiveData = MutableLiveData<PopularItemsResponse>()
    val loadingLiveData = MutableLiveData<Boolean>()
    var failureLiveData = MutableLiveData<ErrorModel?>()

    /**
     * list of already fetched items.
     */
    val listItems = ArrayList<NyPopularResultsItem>()

    /**
     * date formatter, for sorting articles
     */
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd")

    /**
     * starting to fetch popular items from repo
     * will start loading on UI via live data
     */
    fun fetchTrendingRepos() {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            val response = nyPopularRepo.fetchTrendingRepos()
            loadingLiveData.postValue(false)
            if (response.isSuccessful && !response.payload?.results.isNullOrEmpty())
                onSuccess(response.payload)
            else
                onError(response.errorModel)
        }
    }

    /**
     * in case of error, via live data, UI will be updated to show relative contents
     */
    private fun onError(errorModel: ErrorModel?) {
        failureLiveData.postValue(errorModel)
    }

    /**
     * in case of success, UI will be updated to show relative contents
     */
    private fun onSuccess(response: PopularItemsResponse?) {
        listItems.clear()
        listItems.addAll(response?.results ?: ArrayList())
        sortListItems()
        successLiveData.postValue(response)
    }

    /**
     * for sorting articles after successfully fetched
     */
    private fun sortListItems() {
        listItems.sortByDescending { nyItem ->
            dateFormatter.parse(nyItem.publishedDate ?: "2000-01-01")
        }
    }

}