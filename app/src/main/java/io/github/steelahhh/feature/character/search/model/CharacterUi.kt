package io.github.steelahhh.feature.character.search.model

import android.os.Parcelable
import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.github.steelahhh.R
import io.github.steelahhh.feature.character.repository.Character
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.item_character.view.*

@Parcelize
data class CharacterUi(
    val id: Int,
    val name: String,
    val birthYear: String
) : Parcelable, AbstractItem<CharacterUi.VH>() {
    override val type: Int get() = R.id.item_character_id
    override val layoutRes get() = R.layout.item_character

    override fun getViewHolder(v: View) = VH(v)

    class VH(view: View) : FastAdapter.ViewHolder<CharacterUi>(view) {
        override fun bindView(item: CharacterUi, payloads: MutableList<Any>) = with(itemView) {
            nameTv.text = item.name
            birthYearTv.text = item.birthYear
        }

        override fun unbindView(item: CharacterUi) = with(itemView) {
            nameTv.text = null
            birthYearTv.text = null
        }
    }
}

fun Character.toUi() = CharacterUi(
    id = id,
    name = name,
    birthYear = birthYear
)
