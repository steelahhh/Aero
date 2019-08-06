package io.github.steelahhh.feature.character.repository

import io.github.steelahhh.core.SchedulersProvider
import io.github.steelahhh.core.lastSegmentOrZero
import io.github.steelahhh.data.remote.SwapiService
import io.github.steelahhh.data.remote.model.SpeciesResponse

class SpeciesRepository(
    private val service: SwapiService,
    private val schedulers: SchedulersProvider
) {
    fun getSpecies(id: Int) = service.getSpecies(id)
        .map { it.toDomain() }
        .onErrorReturnItem(Species())
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.main())
}

data class Species(
    val id: Int = -1,
    val name: String = "unknown",
    val classification: String = "unknown",
    val designation: String = "unknown",
    val language: String = "unknown",
    val planetId: Int = -1
)

fun SpeciesResponse.toDomain() = Species(
    id = url.lastSegmentOrZero,
    name = name,
    classification = classification,
    designation = designation,
    language = language,
    planetId = homeworld?.lastSegmentOrZero ?: -1
)
