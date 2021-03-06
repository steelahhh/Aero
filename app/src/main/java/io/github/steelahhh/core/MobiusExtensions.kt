package io.github.steelahhh.core

import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

abstract class BaseEffectHandler<Effect, Event> {
    abstract fun create(): ObservableTransformer<Effect, Event>
}

inline fun <Effect, Event, HandleEffect : Effect> RxMobius.SubtypeEffectHandlerBuilder<Effect, Event>.action(
    effect: Class<HandleEffect>,
    scheduler: Scheduler = AndroidSchedulers.mainThread(),
    crossinline action: () -> Unit
): RxMobius.SubtypeEffectHandlerBuilder<Effect, Event> = addAction(effect, { action() }, scheduler)

inline fun <Effect, Event, HandleEffect : Effect> RxMobius.SubtypeEffectHandlerBuilder<Effect, Event>.consumer(
    effect: Class<HandleEffect>,
    scheduler: Scheduler = AndroidSchedulers.mainThread(),
    crossinline action: (effect: HandleEffect) -> Unit
): RxMobius.SubtypeEffectHandlerBuilder<Effect, Event> = addConsumer(effect, { action(it) }, scheduler)
