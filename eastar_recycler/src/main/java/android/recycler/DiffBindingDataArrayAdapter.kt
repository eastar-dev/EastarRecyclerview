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
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class DiffBindingDataArrayAdapter(
    private vararg var diffInfo: DiffInfo,
    items: List<Any> = listOf(),
) : DataAdapter<DiffBindingDataArrayAdapter.DiffBindingDataHolder, Any>(items) {
    override fun getItemViewType(position: Int): Int {
        val type = super.getItemViewType(position)
        return if (type > 0)
            type
        else
            getItem(position).runCatching {
                diffInfo.indexOfFirst {
                    it.dataClz == it.javaClass
                }.takeUnless { it < 0 } ?: 0
            }.onFailure {
                it.printStackTrace()
            }.getOrDefault(0)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffBindingDataHolder {
        val itemView = getItemView(parent, viewType)
        setOnItemClickListener(parent, itemView)
        return DiffBindingDataHolder(itemView)
    }
    //----------------------------------------------------------------------------------
    //ez bind holder
    override fun onBindViewHolder(holder: DiffBindingDataHolder, item: Any, position: Int) {
        holder.bind(diffInfo[holder.itemViewType].brId, item, position)

    }

    open fun getItemView(parent: ViewGroup, viewType: Int): View {
        return getItemView(diffInfo[viewType].layout, parent, viewType)
    }

    //-----------------------------------------------------------------------------
    data class DiffInfo(@LayoutRes var layout: Int, var brId: Int, var dataClz: Class<*>? = null)

    class DiffBindingDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bb: ViewDataBinding = DataBindingUtil.bind(itemView)!!
        fun bind(brId: Int, item: Any, @Suppress("UNUSED_PARAMETER") position: Int) {
            bb.setVariable(brId, item)
            bb.executePendingBindings()
        }
    }
}