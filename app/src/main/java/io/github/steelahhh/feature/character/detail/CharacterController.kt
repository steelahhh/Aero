package io.github.steelahhh.feature.character.detail

import android.os.Bundle
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
import io.github.steelahhh.feature.character.detail.CharacterFeature.Event
import io.github.steelahhh.feature.character.detail.CharacterFeature.Model
import io.github.steelahhh.feature.character.detail.di.CHARACTER_SCOPE
import org.koin.androidx.scope.bindScope
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named

class CharacterController(
    bundle: Bundle
) : BaseController() {
    private var id: Int

    init {
        id = bundle.getInt(CHARACTER_ID)
    }

    private val scope = get().koin.getOrCreateScope(
        "characterDetailScopeId",
        named(CHARACTER_SCOPE)
    )

    private val controller: MobiusLoop.Controller<Model, Event> =
        controller(
            RxMobius.loop(
                CharacterFeature.CharacterUpdater,
                scope.get<CharacterFeature.CharacterEffectHandler>().create()
            )
                .init(CharacterFeature.CharacterInitializer)
                .logger(AndroidLogger.tag("CharacterFeature")),
            Model(characterId = id)
        )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = CharacterView(inflater, container)
        bindScope(scope, Lifecycle.Event.ON_STOP)
        view.setNavigationClickListener {
            router.popCurrentController()
        }
        controller.connect(RxConnectables.fromTransformer(view::connect))
        controller.start()
        return view.containerView
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        controller.stop()
        controller.disconnect()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.apply {
            putParcelable(MODEL, controller.model)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        controller.replaceModel(savedInstanceState[MODEL] as Model)
    }

    companion object {
        private const val CHARACTER_ID = "character_id_key"
        private const val MODEL = "model_key"

        fun newInstance(id: Int) = CharacterController(
            Bundle().apply {
                putInt(CHARACTER_ID, id)
            }
        )
    }
}
