/*
 * Copyright 2020 eastar Jeong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("unused")

package android.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

abstract class DiffBindingViewArrayAdapter(
    private vararg var diffInfo: DiffInfo,
    items: List<Any> = listOf()
) : RecyclerView.Adapter<DiffBindingViewArrayAdapter.DiffHolder<out ViewDataBinding, Any>>() {
    override fun getItemViewType(position: Int): Int {
        val d = getItem(position)

        if (d is DiffItemViewType)
            return d.getItemViewType()

        return runCatching {
            diffInfo.indexOfFirst {
                it.dataClz == d.javaClass
            }.takeUnless { it < 0 } ?: 0
        }.onFailure {
            it.printStackTrace()
        }.getOrDefault(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffHolder<out ViewDataBinding, Any> {
        val itemView = getItemView(parent, viewType)
        setOnItemClickListener(parent, itemView)
        return getHolder(diffInfo[viewType].holderClz, itemView)
    }

    override fun onBindViewHolder(holder: DiffHolder<out ViewDataBinding, Any>, position: Int) {
        onBindViewHolder(holder, getItem(position), position)
    }

    //----------------------------------------------------------------------------------
    open fun onItemClick(parent: RecyclerView, itemView: View, position: Int, item: Any) {}

    open fun onBindViewHolder(holder: DiffHolder<out ViewDataBinding, Any>, item: Any, position: Int) {
        holder.bind(item, position)
    }

    open fun getItemView(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(parent.context).inflate(diffInfo[viewType].layout, parent, false)

    open fun getHolder(holderClass: Class<*>, itemView: View): DiffHolder<out ViewDataBinding, Any> {
        @Suppress("UNCHECKED_CAST")
        return runCatching {
            holderClass.constructors[0].newInstance(itemView.context, itemView)
        }.recoverCatching {
            holderClass.constructors[0].newInstance(this, itemView)
        }.recoverCatching {
            holderClass.constructors[0].newInstance(itemView)
        }.recoverCatching {
            holderClass.declaredConstructors[0].newInstance(itemView.context, itemView)
        }.recoverCatching {
            holderClass.declaredConstructors[0].newInstance(this, itemView)
        }.recoverCatching {
            holderClass.declaredConstructors[0].newInstance(itemView)
        }.getOrThrow() as DiffHolder<out ViewDataBinding, Any>
    }

    private fun setOnItemClickListener(parent: ViewGroup, itemView: View) {
        itemView.setOnClickListener {
            val position = (parent as RecyclerView).getChildLayoutPosition(it)
            onItemClick(parent, itemView, position, getItem(position))
            onItemClickListener?.invoke(parent, itemView, position, getItem(position))
        }
    }

    //----------------------------------------------------------------------------------
    data class DiffInfo(
        @LayoutRes var layout: Int,
        var holderClz: Class<out DiffHolder<out ViewDataBinding, *>>,
        var dataClz: Class<*>? = null
    )

    abstract class DiffHolder<B : ViewDataBinding, VD>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bb: B = DataBindingUtil.bind(itemView)!!
        fun bind(d: VD, position: Int) = bind(bb, d, position)

        abstract fun bind(bb: B, d: VD, position: Int)
    }

    class NullHolder(itemView: View) : DiffHolder<ViewDataBinding, Any>(itemView) {
        override fun bind(bb: ViewDataBinding, d: Any, position: Int) {}
    }

    //----------------------------------------------------------------------------------
//    fun itemChange(collection: Collection<Any>?) = set(collection)
//    fun itemInsert(item: Any) = add(item)
//    fun itemInsert(position: Int, item: Any) = add(position, item)
//    fun itemMove(fromPosition: Int, toPosition: Int) = move(fromPosition, toPosition)
//    fun itemRangeInserte(collection: Collection<Any>?) = addAll(collection)
//    fun itemRangeInserte(position: Int, collection: Collection<Any>?) = addAll(position, collection)
//    fun itemRangeRemove(position: Int, itemCount: Int) = remove(position, itemCount)
//    fun itemRemove(position: Int) = remove(position)
    private var objects: MutableList<Any> = items.toMutableList()
    override fun getItemCount(): Int = objects.size
    open fun getItem(position: Int) = objects[position]


    private val lock = Any()
    fun set(collection: Collection<Any>?) {
        synchronized(lock) {
            objects.clear()
            if (collection == null)
                return
            objects.addAll(collection)
        }
        notifyDataSetChanged()
    }

    fun add(data: Any) {
        synchronized(lock) {
            objects.add(data)
        }
        notifyItemInserted(objects.size)
    }

    fun add(position: Int, item: Any) {
        if (position !in 0..objects.size) {
            Log.i("EastarRecyclerView", "!position is must in 0 until objects.size  Current index is [$position]")
            return
        }

        synchronized(lock) {
            objects.add(position, item)
        }
        notifyItemInserted(position)
    }

    fun addAll(collection: Collection<Any>?) {
        if (collection == null)
            return
        val position = objects.size
        synchronized(lock) {
            objects.addAll(collection)
        }
        notifyItemRangeInserted(position, collection.size)
    }

    fun addAll(position: Int, collection: Collection<Any>?) {
        if (position !in 0..objects.size) {
            Log.i("EastarRecyclerView", "!position is must in 0 until objects.size  Current index is [$position]")
            return
        }

        if (collection == null)
            return
        synchronized(lock) {
            objects.addAll(position, collection)
        }
        notifyItemRangeInserted(position, collection.size)
    }

    fun remove(position: Int) {
        if (position !in 0 until objects.size)
            return
        synchronized(lock) {
            objects.removeAt(position)
        }
        notifyItemRemoved(position)
    }

    fun remove(position: Int, itemCount: Int) {
        if (position !in 0 until objects.size) {
            Log.i("EastarRecyclerView", "!position is must in 0 until objects.size  Current index is [$position]")
            return
        }
        if (itemCount > objects.size) {
            Log.i("EastarRecyclerView", "!itemCount is must itemCount <= objects.size  Current objects.size is [objects.size] Current index is [$position]")
            return
        }
        synchronized(lock) {
            repeat(itemCount) {
                objects.removeAt(position)
            }
        }
        notifyItemRangeRemoved(position, itemCount)
    }

    fun move(fromPosition: Int, toPosition: Int, notifyItemChange: Boolean = false) {
        if (fromPosition !in 0 until objects.size) {
            Log.i("EastarRecyclerView", "!fromPosition is must in 0..objects.size  Current fromPosition is [$fromPosition]")
            return
        }
        if (toPosition !in 0 until objects.size) {
            Log.i("EastarRecyclerView", "!toPosition is must in 0..objects.size  Current toPosition is [$toPosition]")
            return
        }
        repeat(max(fromPosition, toPosition) - min(fromPosition, toPosition)) {
            val sign = (toPosition - fromPosition).sign
            val start = fromPosition + sign * it
            val end = start + sign
            synchronized(lock) {
                Collections.swap(objects, start, end)
            }
            notifyItemMoved(start, end)
        }
        if (notifyItemChange)
            notifyItemChanged(toPosition)
    }

    fun move(fromPosition: Int, toPosition: Int) {
        if (fromPosition !in 0 until objects.size) {
            Log.i("EastarRecyclerView", "!fromPosition is must in 0..objects.size  Current fromPosition is [$fromPosition]")
            return
        }
        if (toPosition !in 0 until objects.size) {
            Log.i("EastarRecyclerView", "!toPosition is must in 0..objects.size  Current toPosition is [$toPosition]")
            return
        }

        Log.w("EastarRecyclerView", "=============================================================================")
        Log.w("EastarRecyclerView", "!toPosition is must in 0..objects.size  Current toPosition is [$toPosition]")
        Log.w("EastarRecyclerView", "=============================================================================")
        synchronized(lock) {
            objects.add(toPosition, objects.removeAt(fromPosition))
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun clear() {
        synchronized(lock) {
            objects.clear()
        }
        notifyDataSetChanged()
    }

    fun sort(comparator: Comparator<Any>) {
        synchronized(lock) {
            Collections.sort(objects, comparator)
        }
        notifyDataSetChanged()
    }

    fun get() = objects


    //----------------------------------------------------------------------------------
    private var onItemClickListener: ((parent: RecyclerView, view: View, position: Int, data: Any) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: (parent: RecyclerView, view: View, position: Int, data: Any) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }


}