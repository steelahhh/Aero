package io.github.steelahhh.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.Observable

abstract class MobiusKotlinView<Model, Event>(
    layoutRes: Int,
    inflater: LayoutInflater,
    container: ViewGroup
) : KotlinView(layoutRes, inflater, container) {

    abstract fun connect(models: Observable<Model>): Observable<Event>
}