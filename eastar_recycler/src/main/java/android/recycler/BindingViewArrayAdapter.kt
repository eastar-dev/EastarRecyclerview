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


//import android.recycler.BindingViewArrayAdapter.Holder
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
abstract class BindingViewArrayAdapter<B : ViewDataBinding, VD>(
    @LayoutRes val layoutResId: Int,
    items: List<VD> = listOf()
) : DataAdapter<BindingViewArrayAdapter.Holder<B>, VD>(items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<B> {
        val itemView = getItemView(layoutResId, parent, viewType)
        setOnItemClickListener(parent, itemView)
        return getHolder(Holder::class.java, itemView)
    }

    override fun onBindViewHolder(holder: Holder<B>, item: VD, position: Int) {
        onBindViewHolder(holder.bb, item, holder, position)
    }

    abstract fun onBindViewHolder(bb: B, item: VD, holder: RecyclerView.ViewHolder, position: Int)

    class Holder<B : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bb: B = DataBindingUtil.bind(itemView)!!
    }
}

