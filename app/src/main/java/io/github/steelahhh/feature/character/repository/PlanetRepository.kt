package io.github.steelahhh.feature.character.repository

import io.github.steelahhh.core.SchedulersProvider
import io.github.steelahhh.core.lastSegmentOrZero
import io.github.steelahhh.data.remote.SwapiService
import io.github.steelahhh.data.remote.model.PlanetResponse

class PlanetRepository(
    private val service: SwapiService,
    private val schedulers: SchedulersProvider
) {
    fun getPlanet(id: Int) = service.getPlanet(id)
        .map { it.toDomain() }
        .onErrorReturnItem(Planet())
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.main())
}

data class Planet(
    val id: Int = -1,
    val name: String = "unknown",
    val population: String = "unknown"
)

fun PlanetResponse.toDomain() = Planet(
    id = url.lastSegmentOrZero,
    name = name,
    population = population
)
