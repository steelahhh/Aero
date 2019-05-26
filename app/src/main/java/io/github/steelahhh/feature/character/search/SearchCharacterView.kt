package io.github.steelahhh.feature.character.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.widget.textChanges
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import com.mikepenz.fastadapter.ui.items.ProgressItem
import io.github.steelahhh.R
import io.github.steelahhh.core.hideSoftKeyboard
import io.github.steelahhh.core.ui.MobiusKotlinView
import io.github.steelahhh.feature.character.search.SearchCharacterFeature.Event
import io.github.steelahhh.feature.character.search.SearchCharacterFeature.Model
import io.github.steelahhh.feature.character.search.model.CharacterUi
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.search_view.*
import java.util.concurrent.TimeUnit

class SearchCharacterView(
    inflater: LayoutInflater,
    container: ViewGroup
) : MobiusKotlinView<Model, Event>(R.layout.search_view, inflater, container) {

    private val itemAdapter = FastItemAdapter<IItem<*>>()
    private val footerAdapter = ItemAdapter<IItem<*>>()
    private val insideEvents = PublishSubject.create<Event>()

    override fun connect(models: Observable<Model>): Observable<Event> {
        setupViews()
        val compositeDisposable = CompositeDisposable()

        compositeDisposable += models.distinctUntilChanged()
            .subscribe(::render)

        itemAdapter.onClickListener = { view, _, item, _ ->
            view?.hideSoftKeyboard()
            insideEvents.onNext(Event.OpenCharacterDetail(item as CharacterUi))
            true
        }

        return Observable.mergeArray<Event>(
            insideEvents.hide(),
            edit_query.textChanges()
                .debounce(250L, TimeUnit.MILLISECONDS)
                .map { Event.LoadCharacters(it.toString()) }
        ).doOnDispose(compositeDisposable::dispose)
    }

    private fun setupViews() {
        itemAdapter.addAdapter(1, footerAdapter)
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemAdapter
        }
    }

    private fun render(model: Model) {
        recycler.clearOnScrollListeners()
        recycler.addOnScrollListener(object : EndlessRecyclerOnScrollListener(footerAdapter) {
            override fun onLoadMore(currentPage: Int) {
                if (model.page != 0) {
                    insideEvents.onNext(Event.LoadNextCharacters(model.query, model.page))
                    footerAdapter.clear()
                    footerAdapter.add(ProgressItem().apply {
                        isEnabled = false
                    })
                }
            }
        })
        progress.isVisible = model.isLoading
        model.renderError()
        model.renderCharacters()
    }

    private fun Model.renderError() {
        val isError = errorRes != -1
        errorTv.isVisible = isError
        if (isError) errorTv.setText(errorRes)
    }

    private fun Model.renderCharacters() {
        footerAdapter.clear()
        if (characters.isEmpty()) itemAdapter.clear()
        else itemAdapter.add(characters)
    }
}