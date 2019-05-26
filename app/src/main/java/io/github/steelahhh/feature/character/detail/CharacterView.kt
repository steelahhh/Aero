package io.github.steelahhh.feature.character.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import io.github.steelahhh.R
import io.github.steelahhh.core.ui.HeaderItem
import io.github.steelahhh.core.ui.MobiusKotlinView
import io.github.steelahhh.feature.character.detail.CharacterFeature.Event
import io.github.steelahhh.feature.character.detail.CharacterFeature.Model
import io.reactivex.Observable
import kotlinx.android.synthetic.main.character_view.*

class CharacterView(
    inflater: LayoutInflater,
    container: ViewGroup
) : MobiusKotlinView<Model, Event>(R.layout.character_view, inflater, container) {
    private val itemAdapter = FastItemAdapter<IItem<*>>()

    override fun connect(models: Observable<Model>): Observable<Event> {
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemAdapter
        }
        val subscription = models.distinctUntilChanged().subscribe(::render)
        return Observable.empty<Event>().doOnDispose(subscription::dispose)
    }

    fun setNavigationClickListener(action: () -> Boolean) {
        toolbar.setNavigationOnClickListener {
            action()
        }
    }

    private fun render(model: Model) {
        model.renderItems()
        model.renderError()
        progress.isVisible = model.isLoading
    }

    private fun Model.renderError() {
        val isError = errorRes != -1
        errorTv.isVisible = isError
        if (isError) errorTv.setText(errorRes)
    }

    private fun Model.renderItems() {
        character ?: return
        toolbar.title = character.name
        val items = mutableListOf<IItem<*>>().apply {
            add(HeaderItem(R.string.about))
            addAll(character.characteristics)
            add(HeaderItem(R.string.species))
            addAll(character.speciesInfo)
            addAll(character.planetInfo)
            add(HeaderItem(R.string.films))
            addAll(character.films)
        }
        itemAdapter.add(items)
    }
}