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

import android.recycler.DiffArrayAdapter.DiffHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class DiffArrayAdapter constructor(
    vararg var diffInfo: DiffInfo, items: List<Any> = listOf()
) : ArrayAdapter<DiffHolder<Any>, Any>(0, items) {

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

    override fun getItemView(@LayoutRes layer: Int, parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(parent.context).inflate(diffInfo[viewType].layout, parent, false)
    }

    override fun getHolder(itemView: View, viewType: Int): DiffHolder<Any> {
        val holderClz = diffInfo[viewType].holderClz

        @Suppress("UNCHECKED_CAST")
        return runCatching {
            holderClz.constructors[0].newInstance(itemView.context, itemView)
        }.recoverCatching {
            holderClz.constructors[0].newInstance(this, itemView)
        }.recoverCatching {
            holderClz.constructors[0].newInstance(itemView)
        }.recoverCatching {
            holderClz.declaredConstructors[0].newInstance(itemView.context, itemView)
        }.recoverCatching {
            holderClz.declaredConstructors[0].newInstance(this, itemView)
        }.recoverCatching {
            holderClz.declaredConstructors[0].newInstance(itemView)
        }.getOrThrow() as DiffHolder<Any>
    }

    override fun onBindViewHolder(h: DiffHolder<Any>, d: Any, position: Int) {
        h.bind(d, position)
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