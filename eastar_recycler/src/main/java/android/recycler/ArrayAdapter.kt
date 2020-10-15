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
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

abstract class ArrayAdapter<VH : RecyclerView.ViewHolder, VD> @JvmOverloads constructor(
    @LayoutRes val layoutId: Int,
    items: List<VD> = listOf()
) : RecyclerView.Adapter<VH>() {

    private var objects: MutableList<VD> = items.toMutableList()
    abstract fun getHolder(itemView: View, viewType: Int): VH
    abstract fun onBindViewHolder(h: VH, d: VD, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = getItemView(layoutId, parent, viewType)
        setOnItemClickListener(parent, itemView)
        return getHolder(itemView, viewType)
    }

    private fun setOnItemClickListener(parent: ViewGroup, itemView: View) {
        itemView.setOnClickListener { v ->
            val position = (parent as RecyclerView).getChildLayoutPosition(v)
            onItemClick(parent, itemView, position, getItem(position))
            onItemClickListener?.invoke(parent, itemView, position, getItem(position))
        }
    }

    open fun onItemClick(parent: RecyclerView, itemView: View, position: Int, item: VD) {
    }

    protected open fun getItemView(@LayoutRes layer: Int, parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(parent.context).inflate(layer, parent, false)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, objects[position], position)
    }


    override fun getItemCount(): Int {
        return objects.size
    }

    fun getItem(position: Int): VD {
        return objects[position]
    }

    //----------------------------------------------------------------------------------
//    fun itemChange(collection: Collection<VD>?) = set(collection)
//    fun itemInsert(item: VD) = add(item)
//    fun itemInsert(position: Int, item: VD) = add(position, item)
//    fun itemMove(fromPosition: Int, toPosition: Int) = move(fromPosition, toPosition)
//    fun itemRangeInserte(collection: Collection<VD>?) = addAll(collection)
//    fun itemRangeInserte(position: Int, collection: Collection<VD>?) = addAll(position, collection)
//    fun itemRangeRemove(position: Int, itemCount: Int) = remove(position, itemCount)
//    fun itemRemove(position: Int) = remove(position)


    private val lock = Any()
    fun set(collection: Collection<VD>?) {
        synchronized(lock) {
            objects.clear()
            if (collection == null)
                return
            objects.addAll(collection)
        }
        notifyDataSetChanged()
    }

    fun add(data: VD) {
        synchronized(lock) {
            objects.add(data)
        }
        notifyItemInserted(objects.size)
    }

    fun add(position: Int, item: VD) {
        if (position !in 0..objects.size) {
            Log.i("EastarRecyclerView", "!position is must in 0 until objects.size  Current index is [$position]")
            return
        }

        synchronized(lock) {
            objects.add(position, item)
        }
        notifyItemInserted(position)
    }

    fun addAll(collection: Collection<VD>?) {
        if (collection == null)
            return
        val position = objects.size
        synchronized(lock) {
            objects.addAll(collection)
        }
        notifyItemRangeInserted(position, collection.size)
    }

    fun addAll(position: Int, collection: Collection<VD>?) {
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

    fun sort(comparator: Comparator<in VD>) {
        synchronized(lock) {
            Collections.sort(objects, comparator)
        }
        notifyDataSetChanged()
    }

    fun get(): List<VD> {
        return objects
    }

    //----------------------------------------------------------------------------------
    private var onItemClickListener: ((parent: RecyclerView, view: View, position: Int, data: VD) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: (parent: RecyclerView, view: View, position: Int, data: VD) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

}