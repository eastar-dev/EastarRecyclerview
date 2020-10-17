package dev.eastar.recyclerview

import android.log.Log
import android.os.Bundle
import android.recycler.BindingDataArrayAdapter
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import dev.eastar.recyclerview.databinding.BindingdataarrayadapterDemoBinding
import dev.eastar.recyclerview.model.DATA_SOURCE
import dev.eastar.recyclerview.model.Data
import dev.eastar.recyclerview.model.ICON

class BindingDataArrayAdapterDemo : AppCompatActivity() {
    private lateinit var bb: BindingdataarrayadapterDemoBinding
    private val items = DATA_SOURCE.mapIndexed { index, text -> Data("$ICON$index", text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = BindingdataarrayadapterDemoBinding.inflate(layoutInflater)
        setContentView(bb.root)
        bb.list.adapter = BindingDataArrayAdapter(R.layout.bindingdataarrayadapter_demo_item, BR.data, items)
            .apply {
                setOnItemClickListener { parent, view, position, data ->
                    Log.e(parent, view, position, data)
                }
            }
        bb.list.adapter = Holder()
    }

    inner class Holder : BindingDataArrayAdapter<Data>(R.layout.bindingdataarrayadapter_demo_item, BR.data, items){
        override fun onBindViewHolder(bb: ViewDataBinding, item: Data, holder: RecyclerView.ViewHolder, position: Int) {
            super.onBindViewHolder(bb, item, holder, position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): android.recycler.Holder<ViewDataBinding> {
            return super.onCreateViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(holder: android.recycler.Holder<ViewDataBinding>, item: Data, position: Int) {
            super.onBindViewHolder(holder, item, position)
        }

        override fun onBindViewHolder(holder: android.recycler.Holder<ViewDataBinding>, position: Int) {
            super.onBindViewHolder(holder, position)
        }

        override fun onItemClick(parent: RecyclerView, itemView: View, position: Int, item: Data) {
            super.onItemClick(parent, itemView, position, item)
        }

        override fun getItemView(layer: Int, parent: ViewGroup, viewType: Int): View {
            return super.getItemView(layer, parent, viewType)
        }

        override fun getHolder(holderClass: Class<*>?, itemView: View): android.recycler.Holder<ViewDataBinding> {
            return super.getHolder(holderClass, itemView)
        }

        override fun getItemViewType(position: Int): Int {
            return super.getItemViewType(position)
        }

        override fun getItemCount(): Int {
            return super.getItemCount()
        }

        override fun getItem(position: Int): Data {
            return super.getItem(position)
        }
    }
}
