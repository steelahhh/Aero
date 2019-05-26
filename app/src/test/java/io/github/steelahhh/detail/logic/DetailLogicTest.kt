@file:Suppress("IncorrectScope")

package io.github.steelahhh.detail.logic

import com.spotify.mobius.test.FirstMatchers
import com.spotify.mobius.test.InitSpec
import com.spotify.mobius.test.UpdateSpec
import io.github.steelahhh.feature.character.detail.CharacterDetailFeature
import io.github.steelahhh.feature.character.detail.model.CharacterDetailUi
import org.junit.jupiter.api.Test

class DetailLogicTest {

    private val initSpec = InitSpec(CharacterDetailFeature.CharacterInitializer)
    private val updateSpec = UpdateSpec(CharacterDetailFeature.CharacterUpdater)

    @Test
    fun `Should fetch character info from network`() {
        initSpec
            .whenInit(CharacterDetailFeature.Model(1))
            .then(
                InitSpec.assertThatFirst<CharacterDetailFeature.Model, CharacterDetailFeature.Effect>(
                    FirstMatchers.hasModel(CharacterDetailFeature.Model(characterId = 1, character = null)),
                    FirstMatchers.hasEffects(
                        CharacterDetailFeature.Effect.LoadCharacter(1)
                    )
                )
            )
    }

    @Test
    fun `Model should have character and not fetch anything`() {
        initSpec
            .whenInit(CharacterDetailFeature.Model(characterId = 1, character = CharacterDetailUi()))
            .then(
                InitSpec.assertThatFirst<CharacterDetailFeature.Model, CharacterDetailFeature.Effect>(
                    FirstMatchers.hasModel(CharacterDetailFeature.Model(characterId = 1, character = CharacterDetailUi())),
                    FirstMatchers.hasNoEffects()
                )
            )
    }
}
