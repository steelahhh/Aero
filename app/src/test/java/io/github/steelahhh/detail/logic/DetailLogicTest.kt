@file:Suppress("IncorrectScope")

package io.github.steelahhh.detail.logic

import com.spotify.mobius.test.FirstMatchers
import com.spotify.mobius.test.InitSpec
import com.spotify.mobius.test.UpdateSpec
import io.github.steelahhh.feature.character.detail.CharacterFeature
import io.github.steelahhh.feature.character.detail.model.CharacterDetailUi
import org.junit.jupiter.api.Test

class DetailLogicTest {

    private val initSpec = InitSpec(CharacterFeature.CharacterInitializer)
    private val updateSpec = UpdateSpec(CharacterFeature.CharacterUpdater)

    @Test
    fun `Should fetch character info from network`() {
        initSpec
            .whenInit(CharacterFeature.Model(1))
            .then(
                InitSpec.assertThatFirst<CharacterFeature.Model, CharacterFeature.Effect>(
                    FirstMatchers.hasModel(CharacterFeature.Model(characterId = 1, character = null)),
                    FirstMatchers.hasEffects(
                        CharacterFeature.Effect.LoadCharacter(1)
                    )
                )
            )
    }

    @Test
    fun `Model should have character and not fetch anything`() {
        initSpec
            .whenInit(CharacterFeature.Model(characterId = 1, character = CharacterDetailUi()))
            .then(
                InitSpec.assertThatFirst<CharacterFeature.Model, CharacterFeature.Effect>(
                    FirstMatchers.hasModel(CharacterFeature.Model(characterId = 1, character = CharacterDetailUi())),
                    FirstMatchers.hasNoEffects()
                )
            )
    }
}
