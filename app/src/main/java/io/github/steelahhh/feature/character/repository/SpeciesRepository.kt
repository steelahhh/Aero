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
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.main())
}

data class Species(
    val id: Int,
    val name: String,
    val classification: String,
    val designation: String,
    val language: String,
    val planetId: Int
)

fun SpeciesResponse.toDomain() = Species(
    id = url.lastSegmentOrZero,
    name = name,
    classification = classification,
    designation = designation,
    language = language,
    planetId = homeworld.lastSegmentOrZero
)
