@file:Suppress("IncorrectScope")

package io.github.steelahhh.search.logic

import com.spotify.mobius.test.FirstMatchers
import com.spotify.mobius.test.InitSpec
import com.spotify.mobius.test.InitSpec.assertThatFirst
import com.spotify.mobius.test.NextMatchers.hasEffects
import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import io.github.steelahhh.feature.character.search.SearchCharacterFeature
import io.github.steelahhh.feature.character.search.SearchCharacterFeature.Event.LoadCharacters
import io.github.steelahhh.feature.character.search.SearchCharacterFeature.Model
import io.github.steelahhh.feature.character.search.model.CharacterUi
import org.junit.jupiter.api.Test

class SearchLogicTest {

    private val initSpec = InitSpec(SearchCharacterFeature.SearchInitializer)

    private val updateSpec = UpdateSpec(SearchCharacterFeature.SearchUpdater)

    @Test
    fun `Init should have no effects and contain empty model`() {
        initSpec
            .whenInit(Model())
            .then(
                assertThatFirst(
                    FirstMatchers.hasModel(Model()),
                    FirstMatchers.hasNoEffects()
                )
            )
    }

    @Test
    fun `Should fetch characters from network`() {
        val query = "luke"
        val model = Model()
        updateSpec.given(model)
            .whenEvent(LoadCharacters(query))
            .then(
                assertThatNext<Model, SearchCharacterFeature.Effect>(
                    hasModel(model.copy(query = query)),
                    hasEffects(SearchCharacterFeature.Effect.LoadCharacters(query))
                )
            )
    }

    @Test
    fun `Should put characters into model`() {
        val characters = listOf(
            CharacterUi(1, "Luke", "1923"),
            CharacterUi(2, "test", "1235BBY")
        )
        val model = Model(page = 0)
        updateSpec.given(model)
            .whenEvent(SearchCharacterFeature.Event.DisplayCharacters(characters))
            .then(
                assertThatNext(
                    hasModel(model.copy(characters = characters)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `Should load next page`() {
        val query = "luke"
        val page = 2
        val model = Model(query = query, page = page)
        updateSpec.given(model)
            .whenEvent(SearchCharacterFeature.Event.LoadNextCharacters(model.query, page))
            .then(
                assertThatNext<Model, SearchCharacterFeature.Effect>(
                    hasModel(model),
                    hasEffects(SearchCharacterFeature.Effect.LoadNextCharacters(model.query, page))
                )
            )
    }
}