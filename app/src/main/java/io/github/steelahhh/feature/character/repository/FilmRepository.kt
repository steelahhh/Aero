package io.github.steelahhh.feature.character.repository

import io.github.steelahhh.core.SchedulersProvider
import io.github.steelahhh.core.lastSegmentOrZero
import io.github.steelahhh.data.remote.SwapiService
import io.github.steelahhh.data.remote.model.FilmResponse

class FilmRepository(
    private val service: SwapiService,
    private val schedulers: SchedulersProvider
) {
    fun getFilm(id: Int) = service.getFilm(id)
        .map { it.toDomain() }
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.main())
}

data class Film(
    val id: Int,
    val title: String,
    val openingCrawl: String,
    val releaseDate: String
)

fun FilmResponse.toDomain() = Film(
    id = url.lastSegmentOrZero,
    title = title,
    openingCrawl = openingCrawl,
    releaseDate = releaseDate
)
