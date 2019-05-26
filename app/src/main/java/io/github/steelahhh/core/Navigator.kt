package io.github.steelahhh.core

import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.bluelinelabs.conductor.changehandler.SimpleSwapChangeHandler
import io.github.steelahhh.core.ui.BaseController

class Navigator {
    private var _router: Router? = null

    private val router: Router
        get() = _router ?: error("Router not bound")

    fun bind(navController: Router) {
        _router = navController
    }

    fun setRoot(controller: BaseController) {
        if (!router.hasRootController()) {
            router.setRoot(
                RouterTransaction
                    .with(controller)
                    .popChangeHandler(SimpleSwapChangeHandler())
                    .pushChangeHandler(SimpleSwapChangeHandler())
                    .tag(controller::class.java.simpleName)
            )
        }
    }

    fun pushController(controller: BaseController) {
        router.pushController(
            RouterTransaction
                .with(controller)
                .popChangeHandler(HorizontalChangeHandler())
                .pushChangeHandler(HorizontalChangeHandler())
                .tag(controller::class.java.simpleName)
        )
    }

    fun unbind() {
        _router = null
    }
}
