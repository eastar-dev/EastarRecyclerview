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

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class DiffArrayAdapter(
    private vararg var diffInfo: DiffInfo,
    items: List<Any> = listOf()
) : DataAdapter<DiffArrayAdapter.DiffHolder<Any>, Any>(items) {

    override fun getItemViewType(position: Int): Int {
        val d = getItem(position)
        if (d is DiffItemViewType)
            return d.getItemViewType()

        val type = diffInfo.map { it.dataClz }
            .indexOf(d.javaClass)
        if (type < 0)
            throw IndexOutOfBoundsException("getItemViewType not found in diffInfo")
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffHolder<Any> {
        //override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffHolder<Any> {
        val itemView = getItemView(parent, viewType)
        setOnItemClickListener(parent, itemView)
        return getHolder(diffInfo[viewType].holderClz, itemView)
    }

    override fun onBindViewHolder(holder: DiffHolder<Any>, item: Any, position: Int) {
        holder.bind(item, position)
    }


    open fun getItemView(parent: ViewGroup, viewType: Int): View {
        return getItemView(diffInfo[viewType].layout, parent, viewType)
    }

    //----------------------------------------------------------------------------------
    data class DiffInfo(
        @LayoutRes var layout: Int,
        var holderClz: Class<out DiffHolder<*>>,
        var dataClz: Class<*>? = null
    )

    abstract class DiffHolder<VD>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(d: VD, position: Int)
    }

    class NullHolder(itemView: View) : DiffHolder<Any>(itemView) {
        override fun bind(d: Any, position: Int) {}
    }
}