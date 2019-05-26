package io.github.steelahhh.feature.character.detail.di

import io.github.steelahhh.feature.character.detail.CharacterFeature
import io.github.steelahhh.feature.character.repository.CharacterRepository
import io.github.steelahhh.feature.character.repository.FilmRepository
import io.github.steelahhh.feature.character.repository.PlanetRepository
import io.github.steelahhh.feature.character.repository.SpeciesRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val CHARACTER_SCOPE = "character_detail_key"

val characterModule = module {
    scope(named(CHARACTER_SCOPE)) {
        scoped { CharacterRepository(get()) }
    }

    scope(named(CHARACTER_SCOPE)) {
        scoped { PlanetRepository(get(), get()) }
    }

    scope(named(CHARACTER_SCOPE)) {
        scoped { SpeciesRepository(get(), get()) }
    }

    scope(named(CHARACTER_SCOPE)) {
        scoped { FilmRepository(get(), get()) }
    }

    scope(named(CHARACTER_SCOPE)) {
        scoped {
            CharacterFeature.CharacterEffectHandler(get(), get(), get(), get())
        }
    }
}
