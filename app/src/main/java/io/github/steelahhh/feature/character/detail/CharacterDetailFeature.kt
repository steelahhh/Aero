package io.github.steelahhh.feature.character.detail

import android.os.Parcelable
import androidx.annotation.StringRes
import com.spotify.mobius.First
import com.spotify.mobius.First.first
import com.spotify.mobius.Init
import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import io.github.steelahhh.R
import io.github.steelahhh.core.BaseEffectHandler
import io.github.steelahhh.core.ui.CharacteristicItem
import io.github.steelahhh.feature.character.detail.model.CharacterDetailUi
import io.github.steelahhh.feature.character.detail.model.toUi
import io.github.steelahhh.feature.character.repository.Character
import io.github.steelahhh.feature.character.repository.CharacterRepository
import io.github.steelahhh.feature.character.repository.Film
import io.github.steelahhh.feature.character.repository.FilmRepository
import io.github.steelahhh.feature.character.repository.Planet
import io.github.steelahhh.feature.character.repository.PlanetRepository
import io.github.steelahhh.feature.character.repository.Species
import io.github.steelahhh.feature.character.repository.SpeciesRepository
import io.github.steelahhh.feature.character.repository.toDomain
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable
import io.reactivex.rxkotlin.zipWith
import kotlinx.android.parcel.Parcelize

object CharacterDetailFeature {
    @Parcelize
    data class Model(
        val characterId: Int,
        val character: CharacterDetailUi? = null,
        @Transient
        val isLoading: Boolean = false,
        @StringRes
        @Transient
        val errorRes: Int = -1
    ) : Parcelable

    sealed class Event {
        object StartedLoading : Event()
        data class ErrorLoading(@StringRes val message: Int) : Event()
        data class DisplayCharacter(val character: CharacterDetailUi) : Event()
    }

    sealed class Effect {
        data class LoadCharacter(val characterId: Int) : Effect()
    }

    object CharacterInitializer : Init<Model, Effect> {
        override fun init(model: Model): First<Model, Effect> = first(
            model,
            // if already has loaded character, don't load it again
            if (model.character == null) setOf(Effect.LoadCharacter(model.characterId))
            else setOf()
        )
    }

    object CharacterUpdater : Update<Model, Event, Effect> {
        override fun update(model: Model, event: Event): Next<Model, Effect> = when (event) {
            CharacterDetailFeature.Event.StartedLoading -> next(
                model.copy(
                    isLoading = true
                )
            )
            is Event.ErrorLoading -> next(
                model.copy(
                    isLoading = false,
                    errorRes = event.message
                )
            )
            is Event.DisplayCharacter -> next(
                model.copy(
                    isLoading = false,
                    character = event.character
                )
            )
        }
    }

    class CharacterEffectHandler(
        private val characterRepository: CharacterRepository,
        private val planetRepository: PlanetRepository,
        private val speciesRepository: SpeciesRepository,
        private val filmRepository: FilmRepository
    ) : BaseEffectHandler<Effect, Event>() {
        override fun create(): ObservableTransformer<Effect, Event> {
            return RxMobius.subtypeEffectHandler<Effect, Event>()
                .addTransformer(Effect.LoadCharacter::class.java) { effect ->
                    effect.flatMap { (characterId: Int) ->
                        characterRepository.getCharacter(characterId)
                            .map { it.toDomain() }
                            .flatMap(::getCharacterInfo)
                            .toObservable()
                            .map {
                                DetailedCharacterInfo(
                                    character = it.first.first,
                                    species = it.second.first,
                                    planet = it.second.second,
                                    films = it.first.second
                                )
                            }
                            .map<Event> { (character, species, planet, films) ->
                                val characterDetail = CharacterDetailUi(
                                    character.name,
                                    character.characteristics,
                                    species.characteristics,
                                    planet.characteristics,
                                    films.map { it.toUi() }
                                )
                                Event.DisplayCharacter(characterDetail)
                            }
                            .onErrorReturn { Event.ErrorLoading(R.string.server_error) }
                            .startWith(Event.StartedLoading)
                    }
                }
                .build()
        }

        // load all the needed information for the character
        // i.e. films in which it made appearance, species its planet and related information
        private fun getCharacterInfo(character: Character) =
            Single.just(character)
                .zipWith(
                    character.filmIds.toObservable().flatMapSingle {
                        filmRepository.getFilm(it)
                    }.toList()
                ).zipWith(
                    speciesRepository.getSpecies(character.speciesIds.first())
                        .flatMap { species ->
                            Single.just(species).zipWith(
                                planetRepository.getPlanet(species.planetId)
                            )
                        }

                )
    }

    private data class DetailedCharacterInfo(
        val character: Character,
        val species: Species,
        val planet: Planet,
        val films: List<Film>
    )

    private val Character.characteristics: List<CharacteristicItem>
        get() {
            val heightInches: Double = height.toDouble() / 2.54
            val feet = heightInches.toInt() / 12
            val inches = heightInches.toInt() % 12

            return listOf(
                CharacteristicItem(R.string.key_birth_year, birthYear),
                CharacteristicItem(
                    R.string.key_height_cm,
                    height.toString()
                ),
                CharacteristicItem(
                    R.string.key_height_feet,
                    "$feet′ $inches″"
                )
            )
        }

    private val Species.characteristics
        get() = listOf(
            CharacteristicItem(R.string.key_name, name),
            CharacteristicItem(R.string.key_species_language, language),
            CharacteristicItem(
                R.string.key_species_classification,
                classification
            ),
            CharacteristicItem(
                R.string.key_species_designation,
                designation
            )
        )

    private val Planet.characteristics
        get() = listOf(
            CharacteristicItem(R.string.key_planet_name, name),
            CharacteristicItem(R.string.key_planet_population, population)
        )
}
