package io.github.steelahhh.feature.character.repository

import android.os.Parcelable
import androidx.core.net.toUri
import io.github.steelahhh.core.lastSegmentOrZero
import io.github.steelahhh.data.remote.SwapiService
import io.github.steelahhh.data.remote.model.CharacterResponse
import io.reactivex.Completable
import kotlinx.android.parcel.Parcelize

class CharacterRepository(private val service: SwapiService) {
    fun findCharacters(name: String, page: Int = 1) = service.getCharacterByName(name, page)
        .map { pageResponse ->
            Characters(
                (pageResponse.results ?: listOf()).map { it.toDomain() },
                pageResponse.next
                    ?.toUri()
                    ?.getQueryParameter("page")
                    ?.toIntOrNull() ?: 0
            )
        }

    fun getCharacter(id: Int) = service.getCharacter(id)

    fun saveCharacter(id: Int) = Completable.complete()
}

data class Characters(
    val characters: List<Character>,
    val nextPage: Int
)

@Parcelize
data class Character(
    val id: Int = 0,
    val name: String = "",
    val height: Int = 0,
    val birthYear: String = "",
    val planetId: Int = 0,
    val speciesIds: List<Int> = listOf(),
    val filmIds: List<Int> = listOf()
) : Parcelable

fun CharacterResponse.toDomain() = Character(
    id = url.lastSegmentOrZero,
    name = name,
    height = height.toInt(),
    birthYear = birthYear,
    planetId = homeworld.lastSegmentOrZero,
    speciesIds = species.map { it.lastSegmentOrZero },
    filmIds = films.map { it.lastSegmentOrZero }
)
