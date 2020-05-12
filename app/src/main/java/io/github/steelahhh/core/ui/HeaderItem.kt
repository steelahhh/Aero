package io.github.steelahhh.core.ui

import android.view.View
import androidx.annotation.StringRes
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import io.github.steelahhh.R
import kotlinx.android.synthetic.main.item_header.view.*

class HeaderItem(@StringRes private val textRes: Int) : AbstractItem<HeaderItem.VH>() {
    override val layoutRes: Int get() = R.layout.item_header
    override val type: Int get() = R.id.id_header
    override fun getViewHolder(v: View) = VH(v)

    class VH(view: View) : FastAdapter.ViewHolder<HeaderItem>(view) {
        override fun bindView(item: HeaderItem, payloads: MutableList<Any>) = with(itemView) {
            title.text = context.getString(item.textRes)
        }

        override fun unbindView(item: HeaderItem) = with(itemView) {
            title.text = null
        }
    }
}
