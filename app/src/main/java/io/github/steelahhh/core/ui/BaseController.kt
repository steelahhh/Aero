package io.github.steelahhh.core.ui

import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.bluelinelabs.conductor.archlifecycle.LifecycleController
import leakcanary.LeakSentry

abstract class BaseController : LifecycleController() {
    private var hasExited: Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        if (hasExited) LeakSentry.refWatcher.watch(this)
    }

    override fun onChangeEnded(
        changeHandler: ControllerChangeHandler,
        changeType: ControllerChangeType
    ) {
        super.onChangeEnded(changeHandler, changeType)
        hasExited = !changeType.isEnter
        if (isDestroyed) LeakSentry.refWatcher.watch(this)
    }
}
