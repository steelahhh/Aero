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
import io.github.steelahhh.feature.character.search.SearchFeature
import io.github.steelahhh.feature.character.search.SearchFeature.Event.LoadCharacters
import io.github.steelahhh.feature.character.search.SearchFeature.Model
import io.github.steelahhh.feature.character.search.model.CharacterUi
import org.junit.jupiter.api.Test

class SearchLogicTest {

    private val initSpec = InitSpec(SearchFeature.SearchInitializer)

    private val updateSpec = UpdateSpec(SearchFeature.SearchUpdater)

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
                assertThatNext<Model, SearchFeature.Effect>(
                    hasModel(model.copy(query = query)),
                    hasEffects(SearchFeature.Effect.LoadCharacters(query))
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
            .whenEvent(SearchFeature.Event.DisplayCharacters(characters))
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
            .whenEvent(SearchFeature.Event.LoadNextCharacters(model.query, page))
            .then(
                assertThatNext<Model, SearchFeature.Effect>(
                    hasModel(model),
                    hasEffects(SearchFeature.Effect.LoadNextCharacters(model.query, page))
                )
            )
    }
}