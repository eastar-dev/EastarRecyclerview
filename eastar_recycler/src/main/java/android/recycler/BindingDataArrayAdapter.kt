@file:Suppress("unused")

package android.recycler

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingDataArrayAdapter @JvmOverloads constructor(
    @LayoutRes layoutResId: Int,
    var brId: Int,
    items: List<Any> = listOf()
) : BindingViewArrayAdapter<ViewDataBinding, Any>(layoutResId, items) {
    override fun onBindViewHolder(
        bb: ViewDataBinding,
        d: Any,
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        bb.setVariable(brId, d)
        bb.executePendingBindings()
    }
}