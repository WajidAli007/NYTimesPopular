package com.wajid.nytimespopular.services

import com.wajid.nytimespopular.R
import com.wajid.nytimespopular.services.exceptions.NoInternetException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ApiWrapper<T>(
    private val provideCall: () -> Call<T>
) {

    suspend fun call(): ApiResult<T> {
        return suspendCoroutine { continuation ->
            provideCall.invoke().enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    val errorId = parseError(t)
                    continuation.resume(
                        ApiResult(
                            errorModel = ErrorModel(
                                exception = t,
                                errorMessage = ErrorMessage(messageId = errorId),
                            ),
                            isSuccessful = false
                        )
                    )
                }

                override fun onResponse(
                    call: Call<T>,
                    response: Response<T>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        continuation.resume(
                            ApiResult(
                                statusCode = response.code(),
                                payload = response.body(),
                                isSuccessful = true,
                                errorModel = null
                            )
                        )
                    } else {
                        continuation.resume(
                            ApiResult(
                                statusCode = response.code(),
                                payload = null,
                                errorModel = ErrorModel(
                                    exception = Exception(),
                                    errorMessage = ErrorMessage(
                                        messageString = response.message(),
                                        messageId = parseError(response.code())
                                    ),
                                ),
                                isSuccessful = false
                            )
                        )
                    }
                }

            })
        }
    }


    private fun parseError(error: Any?): Int {
        return when (error) {
            is NoInternetException -> R.string.alert_internet
            is Int -> {
                //if int type, then its a status code
                when (error) {
                    401 -> R.string.unauthorized_error
                    500 -> R.string.internal_server_error
                    else -> R.string.unidentified_network_error
                }
            }
            else -> R.string.unidentified_network_error
        }
    }

}