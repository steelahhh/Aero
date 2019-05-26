package io.github.steelahhh.core.ui

import android.os.Parcelable
import android.view.View
import androidx.annotation.StringRes
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.github.steelahhh.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.item_characteristic.view.*

@Parcelize
data class CharacteristicItem(
    @StringRes val key: Int,
    val value: String
) : Parcelable, AbstractItem<CharacteristicItem.VH>() {
    override val layoutRes: Int get() = R.layout.item_characteristic
    override val type: Int get() = R.id.id_char
    override fun getViewHolder(v: View) = VH(v)

    override fun bindView(holder: VH, payloads: MutableList<Any>) {
        super.bindView(holder, payloads)
    }

    class VH(view: View) : FastAdapter.ViewHolder<CharacteristicItem>(view) {
        override fun bindView(item: CharacteristicItem, payloads: MutableList<Any>) = with(itemView) {
            keyTv.text = String.format(
                context.getString(R.string.key_template),
                context.getString(item.key)
            )
            valueTv.text = item.value
        }

        override fun unbindView(item: CharacteristicItem) = with(itemView) {
            keyTv.text = null
            valueTv.text = null
        }
    }
}
