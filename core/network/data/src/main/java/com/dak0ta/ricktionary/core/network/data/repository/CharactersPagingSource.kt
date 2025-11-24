package com.dak0ta.ricktionary.core.network.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dak0ta.ricktionary.core.domain.CharacterSummary
import com.dak0ta.ricktionary.core.network.data.api.service.CharactersService
import com.dak0ta.ricktionary.core.network.data.mapper.toDomain
import com.dak0ta.ricktionary.core.network.data.network.SafeApiCall
import com.dak0ta.ricktionary.core.network.domain.model.ApiResult
import com.dak0ta.ricktionary.core.network.domain.model.NetworkError

@Suppress("TooGenericExceptionCaught")
internal class CharactersPagingSource(
    private val service: CharactersService,
    private val safeApiCall: SafeApiCall,
) : PagingSource<Int, CharacterSummary>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterSummary> {
        val page = params.key ?: 1
        try {
            val apiResult = safeApiCall("GET_CHARACTERS_PAGE") { service.getCharacters(page) }

            return when (apiResult) {
                is ApiResult.Success -> {
                    val response = apiResult.data
                    val items = response.results.map { it.toDomain() }
                    val next = response.info.next?.substringAfterLast("page=")?.toIntOrNull()
                    LoadResult.Page(
                        data = items,
                        prevKey = if (page > 1) page - 1 else null,
                        nextKey = next,
                    )
                }

                is ApiResult.Failure -> {
                    val errorMessage = when (val err = apiResult.error) {
                        is NetworkError.Network -> "Network error"
                        is NetworkError.Timeout -> "Timeout error"
                        is NetworkError.Http -> "HTTP error ${err.code}: ${err.message ?: err.body}"
                        is NetworkError.Parse -> "Parse error: ${err.cause.message}"
                        is NetworkError.Unknown -> "Unknown error: ${err.cause.message}"
                    }
                    LoadResult.Error(Exception(errorMessage))
                }
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterSummary>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
