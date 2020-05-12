package io.github.steelahhh.core

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.savedstate.SavedStateRegistryOwner
import kotlin.reflect.KClass

/*
 * Author: steelahhh
 * 12/5/20
 */

@MainThread
fun <VM : ViewModel> BaseController.createViewModelLazy(
    viewModelClass: KClass<VM>,
    storeProducer: () -> ViewModelStore,
    factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        getDefaultViewModelProviderFactory()
    }
    return ViewModelLazy(viewModelClass, storeProducer, factoryPromise)
}

inline fun <reified T : ViewModel> SavedStateRegistryOwner.createAbstractSavedStateViewModelFactory(
    arguments: Bundle,
    crossinline creator: (SavedStateHandle) -> T
): ViewModelProvider.Factory {
    return object : AbstractSavedStateViewModelFactory(this, arguments) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String, modelClass: Class<T>, handle: SavedStateHandle
        ): T = creator(handle) as T
    }
}

inline fun <reified T : ViewModel> BaseController.controllerSavedStateViewModels(
    crossinline creator: (SavedStateHandle) -> T
): Lazy<T> {
    return createViewModelLazy(T::class, storeProducer = {
        viewModelStore
    }, factoryProducer = {
        createAbstractSavedStateViewModelFactory(args, creator)
    })
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> BaseController.controllerViewModels(
    crossinline creator: () -> T
): Lazy<T> {
    return createViewModelLazy(T::class, storeProducer = {
        viewModelStore
    }, factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(
                modelClass: Class<T>
            ): T = creator.invoke() as T
        }
    })
}