package com.dak0ta.ricktionary.core.network.data.mapper

import com.dak0ta.ricktionary.core.domain.Episode
import com.dak0ta.ricktionary.core.network.data.api.dto.EpisodeDto

internal fun EpisodeDto.toDomain(): Episode {
    val (season, episode) = parseEpisodeCode(episode)
    return Episode(
        id = id,
        name = name,
        season = season,
        episode = episode,
    )
}

private fun parseEpisodeCode(code: String): Pair<Int, Int> {
    val regex = Regex("""S(\d+)E(\d+)""", RegexOption.IGNORE_CASE)
    val match = regex.find(code)
    return if (match != null && match.groupValues.size >= 3) {
        val season = match.groupValues[1].toIntOrNull() ?: 0
        val episode = match.groupValues[2].toIntOrNull() ?: 0
        season to episode
    } else {
        0 to 0
    }
}
