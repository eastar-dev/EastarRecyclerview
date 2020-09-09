@file:Suppress("LocalVariableName", "unused")

package android.recycler

import android.log.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class DiffBindingDataArrayAdapter @JvmOverloads constructor(private vararg var diffInfo: DiffInfo, items: List<Any> = listOf()) :
    BindingViewArrayAdapter<ViewDataBinding, Any>(0, items) {

    override fun getItemViewType(position: Int): Int {
        val d = getItem(position)

        if (d is DiffArrayAdapter.ItemViewType)
            return d.getItemViewType()

        return runCatching {
            diffInfo.indexOfFirst {
                it.dataClz == d.javaClass
            }.takeUnless { it < 0 } ?: 0
        }.onFailure {
            Log.e(javaClass)
            it.printStackTrace()
        }.getOrDefault(0)

    }

    override fun getItemView(@LayoutRes layer: Int, parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(parent.context).inflate(diffInfo[viewType].layout, parent, false)
    }

    override fun onBindViewHolder(bb: ViewDataBinding, d: Any, holder: RecyclerView.ViewHolder, position: Int) {
        val brId: Int = diffInfo[holder.itemViewType].brId
        bb.setVariable(brId, d)
        bb.executePendingBindings()
    }

    //-----------------------------------------------------------------------------
    data class DiffInfo(@LayoutRes var layout: Int, var brId: Int, var dataClz: Class<*>? = null)
}