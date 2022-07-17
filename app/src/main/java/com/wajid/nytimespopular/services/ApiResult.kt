package com.wajid.nytimespopular.services

data class ApiResult<T>(
    val statusCode: Int? = null,
    val payload: T? = null,
    val errorModel: ErrorModel?,
    val isSuccessful: Boolean
)

data class ErrorModel(
    val exception: Throwable? = null,
    val errorMessage: ErrorMessage? = null,
)
