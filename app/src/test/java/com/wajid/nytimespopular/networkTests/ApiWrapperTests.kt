package com.wajid.nytimespopular.networkTests

import com.google.gson.Gson
import com.wajid.nytimespopular.R
import com.wajid.nytimespopular.di.appModule
import com.wajid.nytimespopular.di.networkServicesModule
import com.wajid.nytimespopular.services.ApiWrapper
import com.wajid.nytimespopular.services.NYTimesPopularService
import com.wajid.nytimespopular.services.exceptions.NoInternetException
import com.wajid.nytimespopular.services.networkModels.popularArticles.PopularItemsResponse
import com.wajid.nytimespopular.utils.ConnectivityUtil
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.Cache
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


class ApiWrapperTests : KoinTest {

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
     * API wrapper should return error string in ApiResult when there is a 401
     */
    @Test
    fun `test parse error method response`() = runBlocking {

        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockResponse.setResponseCode(401)
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
        val apiWrapper: ApiWrapper<PopularItemsResponse> = ApiWrapper {
            nyTimesPopularService.getPopularArticles(7)
        }

        val apiResult = apiWrapper.call()
        assertEquals(R.string.unauthorized_error, apiResult.errorModel?.errorMessage?.messageId)

    }

    /**
     * when there is no internet, NoInternetException class instance should be return in error
     */
    @Test
    fun `test ny times popular api for no internet exception case`() = runBlocking {

        loadKoinModules(
            module {
                single<ConnectivityUtil> {
                    object : ConnectivityUtil {
                        override fun isConnectedToInternet(): Boolean {
                            return false
                        }
                    }
                }
                single<Cache> {
                    mockk<Cache>()
                }
            }
        )

        val gson: Gson by inject()
        val nyTimesPopularService: NYTimesPopularService by inject()
        val response = ApiWrapper {
            nyTimesPopularService.getPopularArticles(7)
        }.call()

        assertEquals(
            NoInternetException::class.java.canonicalName,
            response.errorModel?.exception?.javaClass?.canonicalName
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