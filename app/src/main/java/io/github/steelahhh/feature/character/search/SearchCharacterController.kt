package io.github.steelahhh.feature.character.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.android.MobiusAndroid.controller
import com.spotify.mobius.rx2.RxConnectables
import com.spotify.mobius.rx2.RxMobius
import io.github.steelahhh.core.ui.BaseController
import io.github.steelahhh.feature.character.search.SearchCharacterFeature.Event
import io.github.steelahhh.feature.character.search.SearchCharacterFeature.Model
import io.github.steelahhh.feature.character.search.di.SEARCH_SCOPE
import org.koin.androidx.scope.bindScope
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named

class SearchCharacterController : BaseController() {

    private val scope = get().koin.getOrCreateScope("searchScopeId", named(SEARCH_SCOPE))

    private val controller: MobiusLoop.Controller<Model, Event> = controller(
        RxMobius.loop(
            SearchCharacterFeature.SearchUpdater,
            scope.get<SearchCharacterFeature.SearchEffectHandler>().create()
        )
            .init(SearchCharacterFeature.SearchInitializer)
            .logger(AndroidLogger.tag("SearchCharacterFeature")),
        Model()
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = SearchCharacterView(inflater, container)
        bindScope(scope, Lifecycle.Event.ON_STOP)
        controller.connect(RxConnectables.fromTransformer(view::connect))
        controller.start()
        return view.containerView
    }

    override fun onDestroyView(view: View) {
        controller.stop()
        controller.disconnect()
        super.onDestroyView(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}