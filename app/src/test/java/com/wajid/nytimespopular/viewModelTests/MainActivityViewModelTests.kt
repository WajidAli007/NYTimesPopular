package com.wajid.nytimespopular.viewModelTests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.wajid.nytimespopular.mockData.NYTimesTrendingRepoResponses
import com.wajid.nytimespopular.di.appModule
import com.wajid.nytimespopular.di.networkServicesModule
import com.wajid.nytimespopular.di.viewModelsModules
import com.wajid.nytimespopular.repos.NYPopularRepo
import com.wajid.nytimespopular.services.ApiWrapper
import com.wajid.nytimespopular.services.ErrorModel
import com.wajid.nytimespopular.services.NYTimesPopularService
import com.wajid.nytimespopular.services.networkModels.popularArticles.PopularItemsResponse
import com.wajid.nytimespopular.ui.main.MainActivityViewModel
import com.wajid.nytimespopular.utils.ConnectivityUtil
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.Cache
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Handler
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MainActivityViewModelTests : KoinTest {

    private val server = MockWebServer()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(dispatcher)
        startKoin {
            printLogger()
            modules(appModule, networkServicesModule, viewModelsModules)
        }
    }

    @Test
    fun `test view model success case`() = runBlocking {

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
        val nyTimesPopularService: NYTimesPopularService by inject()
        val response = ApiWrapper {
            nyTimesPopularService.getPopularArticles(7)
        }.call()

        assertEquals(
            gson.fromJson(
                NYTimesTrendingRepoResponses.getMockResponse(),
                PopularItemsResponse::class.java
            ),
            response.payload
        )
    }

    @Test
    fun `test view failure case`() = runTest {

        val gson: Gson by inject()

        val mockResponse = MockResponse()
        //with this empty body, failure case should be invoked
        mockResponse.setBody(gson.toJson(PopularItemsResponse()))
        mockResponse.setResponseCode(500)
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

        val nyTimesPopularService: NYTimesPopularService by inject()
        val viewModel = MainActivityViewModel(NYPopularRepo(nyTimesPopularService))
        val liveDataMock = mockk<MutableLiveData<ErrorModel?>>() {
            coEvery { value } returns ErrorModel()
        }
        viewModel.failureLiveData = liveDataMock
        viewModel.fetchTrendingRepos()
        assertEquals(ErrorModel(), viewModel.failureLiveData.value)
    }


    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

}