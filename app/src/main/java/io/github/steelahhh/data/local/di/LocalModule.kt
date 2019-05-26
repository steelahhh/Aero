package io.github.steelahhh.data.local.di

import com.google.gson.Gson
import org.koin.dsl.module

val localModule = module {
    single { Gson() }

    /*

    single {
        Room.databaseBuilder(
            androidContext(),
            SwapiDatabase::class.java, "SwapiDatabase"
        ).fallbackToDestructiveMigration().build()
    }

     */
}
