package io.github.steelahhh.data.remote

import io.github.steelahhh.data.remote.model.CharacterResponse
import io.github.steelahhh.data.remote.model.FilmResponse
import io.github.steelahhh.data.remote.model.Page
import io.github.steelahhh.data.remote.model.PlanetResponse
import io.github.steelahhh.data.remote.model.SpeciesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SwapiService {
    @GET("people/")
    fun getCharacterByName(
        @Query(value = "search") query: String
    ): Single<Page<CharacterResponse>>

    @GET("people/{id}/")
    fun getCharacter(
        @Path(value = "id") characterId: Int
    ): Single<CharacterResponse>

    @GET("planets/{id}/")
    fun getPlanet(
        @Path(value = "id") planetId: Int
    ): Single<PlanetResponse>

    @GET("films/{id}/")
    fun getFilm(
        @Path(value = "id") filmId: Int
    ): Single<FilmResponse>

    @GET("species/{id}/")
    fun getSpecies(
        @Path(value = "id") speciesId: Int
    ): Single<SpeciesResponse>
}
