package com.wajid.nytimespopular.repos

import com.wajid.nytimespopular.services.ApiResult
import com.wajid.nytimespopular.services.ApiWrapper
import com.wajid.nytimespopular.services.NYTimesPopularService
import com.wajid.nytimespopular.services.networkModels.popularArticles.PopularItemsResponse

class NYPopularRepo(
    private val nyTimesPopularService: NYTimesPopularService
) {

    /**
     * entry point for fetching latest popular articles
     * this method only will be modified if we use custom cache or ROOM later on
     */
    suspend fun fetchTrendingRepos(): ApiResult<PopularItemsResponse> {
        return callApi()
    }

    /**
     * call REST for fetching latest popular items as of last 7 days
     */
    private suspend fun callApi(): ApiResult<PopularItemsResponse> {
        val response = ApiWrapper {
            nyTimesPopularService.getPopularArticles(7) //last 7 days as the requirement
        }.call()
        return response
    }

}