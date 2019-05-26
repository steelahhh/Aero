package io.github.steelahhh.search.effecthandlers

import io.github.steelahhh.core.Navigator
import io.github.steelahhh.di.appModule
import io.github.steelahhh.feature.character.search.SearchCharacterFeature
import io.github.steelahhh.feature.character.search.SearchCharacterFeature.Effect.LoadCharacters
import io.github.steelahhh.feature.character.search.SearchCharacterFeature.Effect.LoadNextCharacters
import io.github.steelahhh.feature.character.search.SearchCharacterFeature.Effect.NavigateToCharacterDetail
import io.github.steelahhh.feature.character.search.di.searchModule
import io.github.steelahhh.feature.character.search.model.CharacterUi
import io.github.steelahhh.feature.character.repository.Character
import io.github.steelahhh.feature.character.repository.CharacterRepository
import io.github.steelahhh.feature.character.repository.Characters
import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import java.util.concurrent.Executor

class EffectHandlersTest {

    private val repository: CharacterRepository = mockk()
    private val navigator: Navigator = mockk()
    private val effectHandler = SearchCharacterFeature.SearchEffectHandler(repository, navigator).create()

    @Before
    fun before() {
        startKoin {
            modules(searchModule, appModule)
        }
    }

    @Test
    fun `Calls repository to fetch characters from network`() {
        every { repository.findCharacters("luke", any()) } returns Single.just(Characters(listOf(Character()), 0))

        val testCase = TestCase(effectHandler)

        testCase.dispatchEffect(LoadCharacters("luke"))

        verify { repository.findCharacters("luke", any()) }
    }

    @Test
    fun `Calls repository to fetch more characters from network`() {
        every { repository.findCharacters("luke", 1) } returns Single.just(Characters(listOf(Character()), 2))

        val testCase = TestCase(effectHandler)

        testCase.dispatchEffect(LoadNextCharacters("luke", 2))

        verify { repository.findCharacters("luke", 2) }
    }

    @Test
    fun `Navigates to the detail`() {
        val testCase = TestCase(effectHandler)

        testCase.dispatchEffect(
            NavigateToCharacterDetail(
                CharacterUi(1, "Luke Skywalker", "")
            )
        )

        verify { repository wasNot called }
    }

    @After
    fun after() {
        stopKoin()
    }

    internal class TestCase<F, E>(underTest: ObservableTransformer<F, E>) {
        private val upstream = PublishSubject.create<F>()
        private val observer = TestObserver<E>()

        init {
            upstream.compose(underTest).subscribe(observer)
        }

        fun dispatchEffect(effect: F) {
            upstream.onNext(effect)
        }

        @SafeVarargs
        fun assertEvents(vararg events: E) {
            observer.assertValues(*events)
        }
    }

    companion object {

        @JvmField
        @ClassRule
        val schedulers = RxImmediateSchedulerRule()

        class RxImmediateSchedulerRule : TestRule {
            private val immediate = object : Scheduler() {
                override fun createWorker(): Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
                }
            }

            override fun apply(base: Statement, description: Description): Statement {
                return object : Statement() {
                    @Throws(Throwable::class)
                    override fun evaluate() {
                        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
                        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
                        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
                        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
                        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }

                        try {
                            base.evaluate()
                        } finally {
                            RxJavaPlugins.reset()
                            RxAndroidPlugins.reset()
                        }
                    }
                }
            }
        }
    }
}