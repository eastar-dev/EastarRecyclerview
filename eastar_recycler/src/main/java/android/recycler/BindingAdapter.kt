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
package android.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindingRecyclerAdapter<VD>(private vararg val holderInfo: HolderInfo) : RecyclerView.Adapter<BindingRecyclerAdapter.BindingHolder<ViewDataBinding, VD>>() {
    var items: List<VD> = emptyList()
        set(value) {
            field = value
            notifyItemRangeChanged(0, value.size)
        }

    override fun getItemViewType(position: Int): Int {
        return holderInfo.indexOfFirst {
            it.dataType.isInstance(items[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<ViewDataBinding, VD> {
        return createViewHolder(holderInfo[viewType].holderType, getItemView(holderInfo[viewType].layoutRes, parent))
    }

    private fun createViewHolder(holderType: Class<*>, itemView: View): BindingHolder<ViewDataBinding, VD> {
        @Suppress("UNCHECKED_CAST")
        return runCatching {
            holderType.constructors[0].newInstance(itemView.context, itemView)
        }.recoverCatching {
            holderType.constructors[0].newInstance(this, itemView)
        }.recoverCatching {
            holderType.constructors[0].newInstance(itemView)
        }.recoverCatching {
            holderType.declaredConstructors[0].apply { isAccessible = true }.newInstance(itemView.context, itemView)
        }.recoverCatching {
            holderType.declaredConstructors[0].apply { isAccessible = true }.newInstance(this, itemView)
        }.recoverCatching {
            holderType.declaredConstructors[0].apply { isAccessible = true }.newInstance(itemView)
        }.getOrThrow() as BindingHolder<ViewDataBinding, VD>
    }

    private fun getItemView(layoutRes: Int, parent: ViewGroup): View =
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)

    override fun onBindViewHolder(holder: BindingHolder<ViewDataBinding, VD>, position: Int) =
        holder bind items[position]

    override fun getItemCount() =
        items.size

    open class BindingHolder<VH : ViewDataBinding, VD>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        protected val binding: VH = DataBindingUtil.bind(itemView)!!
        open infix fun bind(data: VD) {}
    }

    data class HolderInfo(
        val dataType: Class<*>,
        val holderType: Class<out BindingHolder<*, *>>,
        @LayoutRes val layoutRes: Int
    )
}