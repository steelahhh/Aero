package io.github.steelahhh.di

import io.github.steelahhh.core.Navigator
import io.github.steelahhh.core.SchedulersProvider
import io.github.steelahhh.core.SchedulersProviderImpl
import io.github.steelahhh.data.local.di.localModule
import io.github.steelahhh.data.remote.di.remoteModule
import io.github.steelahhh.feature.character.detail.di.characterModule
import io.github.steelahhh.feature.character.search.di.searchModule
import org.koin.dsl.module
import org.koin.experimental.builder.single

val appModule = module {
    single<Navigator>()
    single<SchedulersProvider> {
        SchedulersProviderImpl()
    }
}

val applicationModules = listOf(
    appModule,
    remoteModule,
    localModule
) + listOf(
    searchModule,
    characterModule
)