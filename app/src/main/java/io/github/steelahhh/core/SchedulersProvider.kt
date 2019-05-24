package io.github.steelahhh.core

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulersProvider {
    fun io(): Scheduler
    fun main(): Scheduler
    fun computation(): Scheduler
}

class SchedulersProviderImpl : SchedulersProvider {
    override fun io() = Schedulers.io()
    override fun computation() = Schedulers.computation()
    override fun main() = AndroidSchedulers.mainThread()
}
