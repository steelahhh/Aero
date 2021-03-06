package io.github.steelahhh.feature.character.search.di

import io.github.steelahhh.feature.character.repository.CharacterRepository
import io.github.steelahhh.feature.character.repository.FilmRepository
import io.github.steelahhh.feature.character.repository.PlanetRepository
import io.github.steelahhh.feature.character.repository.SpeciesRepository
import io.github.steelahhh.feature.character.search.SearchCharacterFeature
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val SEARCH_SCOPE = "search_scope_key"

val searchModule = module {
    scope(named(SEARCH_SCOPE)) {
        scoped { CharacterRepository(get()) }
    }

    scope(named(SEARCH_SCOPE)) {
        scoped { PlanetRepository(get(), get()) }
    }

    scope(named(SEARCH_SCOPE)) {
        scoped { SpeciesRepository(get(), get()) }
    }

    scope(named(SEARCH_SCOPE)) {
        scoped { FilmRepository(get(), get()) }
    }

    scope(named(SEARCH_SCOPE)) {
        scoped { SearchCharacterFeature.SearchEffectHandler(get(), get()) }
    }
}
