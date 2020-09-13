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

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingDataArrayAdapter<VD> @JvmOverloads constructor(
    @LayoutRes layoutResId: Int,
    var brId: Int,
    items: List<VD> = listOf()
) : BindingViewArrayAdapter<ViewDataBinding, VD>(layoutResId, items) {

    override fun onBindViewHolder(bb: ViewDataBinding, d: VD, holder: RecyclerView.ViewHolder, position: Int) {
        bb.setVariable(brId, d)
        bb.executePendingBindings()
    }
}