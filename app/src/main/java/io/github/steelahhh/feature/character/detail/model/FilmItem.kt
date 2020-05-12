package io.github.steelahhh.feature.character.detail.model

import android.os.Parcelable
import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.github.steelahhh.R
import io.github.steelahhh.feature.character.repository.Film
import java.text.SimpleDateFormat
import java.util.Locale
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.item_film.view.*

@Parcelize
data class FilmItem(
    val title: String,
    val releaseDate: String,
    val openingCrawl: String
) : Parcelable, AbstractItem<FilmItem.VH>() {
    override val layoutRes: Int get() = R.layout.item_film
    override val type: Int get() = R.id.item_film_id
    override fun getViewHolder(v: View) = VH(v)

    class VH(view: View) : FastAdapter.ViewHolder<FilmItem>(view) {
        override fun bindView(item: FilmItem, payloads: MutableList<Any>) = with(itemView) {
            titleTv.text = item.title
            releaseDateTv.text = String.format(
                context.getString(
                    R.string.release_date_pattern,
                    item.releaseDate
                )
            )
            openingCrawlTv.text = item.openingCrawl
        }

        override fun unbindView(item: FilmItem) = with(itemView) {
            titleTv.text = null
            releaseDateTv.text = null
            openingCrawlTv.text = null
        }
    }
}

fun Film.toUi() = FilmItem(
    title = title,
    releaseDate = releaseDate.formatDate(),
    openingCrawl = openingCrawl
)

private fun String.formatDate(pattern: String = "dd MMMM, yyyy"): String {
    val parsed = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.ENGLISH
    ).parse(this)

    val formatted = SimpleDateFormat(pattern, Locale.ENGLISH).format(parsed)
    return formatted
}
