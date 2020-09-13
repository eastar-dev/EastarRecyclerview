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
@file:Suppress("LocalVariableName", "unused")

package android.recycler

//import android.log.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class DiffBindingDataArrayAdapter @JvmOverloads constructor(
    private vararg var diffInfo: DiffInfo,
    items: List<Any> = listOf()
) : BindingViewArrayAdapter<ViewDataBinding, Any>(0, items) {

    override fun getItemViewType(position: Int): Int {
        val d = getItem(position)

        if (d is DiffItemViewType)
            return d.getItemViewType()

        return runCatching {
            diffInfo.indexOfFirst {
                it.dataClz == d.javaClass
            }.takeUnless { it < 0 } ?: 0
        }.onFailure {
//            Log.e(javaClass)
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