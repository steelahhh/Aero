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
import io.github.steelahhh.feature.character.search.SearchFeature.Event
import io.github.steelahhh.feature.character.search.SearchFeature.Model
import io.github.steelahhh.feature.character.search.di.SEARCH_SCOPE
import org.koin.androidx.scope.bindScope
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named

class SearchController : BaseController() {

    private val scope = get().koin.getOrCreateScope("searchScopeId", named(SEARCH_SCOPE))

    private val controller: MobiusLoop.Controller<Model, Event> = controller(
        RxMobius.loop(
            SearchFeature.SearchUpdater,
            scope.get<SearchFeature.SearchEffectHandler>().create()
        )
            .init(SearchFeature.SearchInitializer)
            .logger(AndroidLogger.tag("SearchFeature")),
        Model()
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = SearchView(inflater, container)
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