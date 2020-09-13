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

import android.recycler.BindingViewArrayAdapter.Holder
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindingViewArrayAdapter<B : ViewDataBinding, VD> @JvmOverloads constructor(
    @LayoutRes layoutResId: Int,
    items: List<VD> = listOf()
) : ArrayAdapter<Holder<B>, VD>(layoutResId, items) {

    class Holder<B : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bb: B = DataBindingUtil.bind(itemView)!!
    }

    override fun getHolder(itemView: View, viewType: Int): Holder<B> = Holder(itemView)

    override fun onBindViewHolder(h: Holder<B>, d: VD, position: Int) {
        runCatching {
            onBindViewHolder(h.bb, d, h, position)
        }.onFailure {
            h.itemView.context.javaClass.name
        }
    }

    abstract fun onBindViewHolder(bb: B, d: VD, holder: RecyclerView.ViewHolder, position: Int)
}
