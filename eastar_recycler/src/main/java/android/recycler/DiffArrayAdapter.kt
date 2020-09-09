@file:Suppress("LocalVariableName")

package android.recycler

import android.log.Log
import android.recycler.DiffArrayAdapter.DiffHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class DiffArrayAdapter @JvmOverloads constructor(private vararg var diffInfo: DiffInfo, items: List<Any> = listOf()) : ArrayAdapter<DiffHolder<Any>, Any>(0, items) {
    override fun getItemViewType(position: Int): Int {
        val d = getItem(position)

        if (d is ItemViewType)
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

    override fun getHolder(itemView: View, viewType: Int): DiffHolder<Any> {
//        Log.e(holder_clz)
//        Log.e(parent.context.resources.getResourceName(layer))
//        Log.e(Arrays.toString(holder_clz.constructors))
//        Log.e(Arrays.toString(holder_clz.declaredConstructors))

        val holder_clz = diffInfo[viewType].holderClz
        //holder
        @Suppress("UNCHECKED_CAST")
        return runCatching {
            holder_clz.constructors[0].newInstance(itemView.context, itemView)
        }.recoverCatching {
            holder_clz.constructors[0].newInstance(this, itemView)
        }.recoverCatching {
            holder_clz.constructors[0].newInstance(itemView)
        }.recoverCatching {
            holder_clz.declaredConstructors[0].newInstance(itemView.context, itemView)
        }.recoverCatching {
            holder_clz.declaredConstructors[0].newInstance(this, itemView)
        }.recoverCatching {
            holder_clz.declaredConstructors[0].newInstance(itemView)
        }.getOrThrow() as DiffHolder<Any>
    }

    override fun onBindViewHolder(h: DiffHolder<Any>, d: Any, position: Int) {
        h.bind(d)
    }

    data class DiffInfo(@LayoutRes var layout: Int, var holderClz: Class<out DiffHolder<*>>, var dataClz: Class<*>? = null)

    abstract class DiffHolder<VD>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(d: VD)
    }

    interface ItemViewType {
        fun getItemViewType(): Int
    }

    open class NullItem

    class NullHolder(itemView: View) : DiffHolder<Any>(itemView) {
        override fun bind(d: Any) {}
    }
}