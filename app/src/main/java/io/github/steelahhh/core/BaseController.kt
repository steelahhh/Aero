package io.github.steelahhh.core

import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.ViewModelStore
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.bluelinelabs.conductor.archlifecycle.LifecycleController
import leakcanary.LeakSentry

abstract class BaseController : LifecycleController(), SavedStateRegistryOwner {
    private var defaultFactory: ViewModelProvider.Factory? = null
    private var hasExited: Boolean = false
    private var savedStateRegistryController = SavedStateRegistryController.create(this)

    val viewModelStore = ViewModelStore()

    override fun getSavedStateRegistry(): SavedStateRegistry =
        savedStateRegistryController.savedStateRegistry

    override fun onChangeEnded(
        changeHandler: ControllerChangeHandler,
        changeType: ControllerChangeType
    ) {
        super.onChangeEnded(changeHandler, changeType)
        hasExited = !changeType.isEnter
        if (isDestroyed) LeakSentry.refWatcher.watch(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (hasExited) LeakSentry.refWatcher.watch(this)
        viewModelStore.clear()
    }

    fun viewModelProvider(): ViewModelProvider? {
        return viewModelProvider(getDefaultViewModelProviderFactory())
    }

    open fun viewModelProvider(factory: ViewModelProvider.Factory): ViewModelProvider {
        return ViewModelProvider(viewModelStore, factory)
    }

    open fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        if (defaultFactory == null) {
            defaultFactory = SavedStateViewModelFactory(
                activity!!.application,
                this,
                args
            )
        }
        return defaultFactory!!
    }
}
