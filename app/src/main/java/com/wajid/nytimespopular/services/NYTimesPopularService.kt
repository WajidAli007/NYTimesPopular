package com.wajid.nytimespopular.services

import com.wajid.nytimespopular.BuildConfig
import com.wajid.nytimespopular.services.networkModels.popularArticles.PopularItemsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NYTimesPopularService {

    @GET("svc/mostpopular/v2/viewed/{period}.json")
    fun getPopularArticles(@Path("period") period: Int, @Query("api-key") apiKey: String = BuildConfig.API_KEY): Call<PopularItemsResponse>
}


