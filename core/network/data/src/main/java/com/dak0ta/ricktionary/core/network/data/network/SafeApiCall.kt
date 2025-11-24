package com.dak0ta.ricktionary.core.network.data.network

import com.dak0ta.ricktionary.core.coroutine.CoroutineDispatchers
import com.dak0ta.ricktionary.core.network.domain.model.ApiResult
import com.dak0ta.ricktionary.core.network.domain.model.NetworkError
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

internal class SafeApiCall @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
) {

    @Suppress("TooGenericExceptionCaught")
    suspend operator fun <T> invoke(
        operationName: String = "API_CALL",
        block: suspend () -> T,
    ): ApiResult<T> = withContext(dispatchers.io) {
        try {
            val result = block()
            ApiResult.Success(result)
        } catch (e: Exception) {
            val networkError = handleException(e)
            ApiResult.Failure(networkError)
        }
    }

    private fun handleException(e: Exception): NetworkError {
        return when (e) {
            is JsonEncodingException, is JsonDataException -> {
                NetworkError.Parse(e)
            }

            is SocketTimeoutException -> {
                NetworkError.Timeout
            }

            is IOException -> {
                NetworkError.Network
            }

            is HttpException -> {
                val code = e.code()
                val body = try {
                    e.response()?.errorBody()?.string()
                } catch (_: Exception) {
                    null
                }
                NetworkError.Http(code = code, body = body, message = e.message())
            }

            else -> {
                NetworkError.Unknown(e)
            }
        }
    }
}
