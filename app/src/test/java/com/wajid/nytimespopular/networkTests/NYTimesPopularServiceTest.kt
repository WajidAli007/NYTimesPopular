package com.wajid.nytimespopular.networkTests

import com.google.gson.Gson
import com.wajid.nytimespopular.di.appModule
import com.wajid.nytimespopular.di.networkServicesModule
import com.wajid.nytimespopular.mockData.NYTimesTrendingRepoResponses
import com.wajid.nytimespopular.services.ApiWrapper
import com.wajid.nytimespopular.services.NYTimesPopularService
import com.wajid.nytimespopular.services.networkModels.popularArticles.PopularItemsResponse
import com.wajid.nytimespopular.utils.ConnectivityUtil
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals

class NYTimesPopularServiceTest : KoinTest {

    private val server = MockWebServer()

    /**
     * init stuff
     */
    @Before
    fun before() {
        startKoin {
            printLogger()
            modules(appModule, networkServicesModule)
        }
    }

    /**
     * success call api test of the api to fetch trending repos
     * via a mock web server and mock response
     * to avoid api calls excessively.
     */
    @Test
    fun `test ny times popular api for successful call case`() = runBlocking {

        val mockResponse = MockResponse()
        mockResponse.setBody(NYTimesTrendingRepoResponses.getMockResponse())
        mockResponse.setResponseCode(200)
        server.enqueue(mockResponse)

        loadKoinModules(
            module {
                single<ConnectivityUtil> {
                    object : ConnectivityUtil {
                        override fun isConnectedToInternet(): Boolean {
                            return true
                        }
                    }
                }

                single<NYTimesPopularService> {
                    Retrofit.Builder()
                        .baseUrl(server.url("/"))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(NYTimesPopularService::class.java)
                }
            }
        )

        val gson: Gson by inject()
        val nYTimesPopularService: NYTimesPopularService by inject()
        val response = ApiWrapper {
            nYTimesPopularService.getPopularArticles(7)
        }.call()

        assertEquals(
            gson.fromJson(
                NYTimesTrendingRepoResponses.getMockResponse(),
                PopularItemsResponse::class.java
            ),
            response.payload
        )
    }


    /**
     * releasing resources
     */
    @After
    fun tearDown() {
        stopKoin()
        server.shutdown()
    }
}