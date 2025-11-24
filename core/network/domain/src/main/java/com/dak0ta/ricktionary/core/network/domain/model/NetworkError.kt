package com.dak0ta.ricktionary.core.network.domain.model

sealed class NetworkError {
    object Network : NetworkError()
    object Timeout : NetworkError()
    data class Http(val code: Int, val body: String?, val message: String?) : NetworkError()
    data class Parse(val cause: Throwable) : NetworkError()
    data class Unknown(val cause: Throwable) : NetworkError()
}
