package dev.eastar.recyclerview

import android.log.Log
import android.os.Bundle
import android.recycler.BindingViewArrayAdapter
import android.recycler.Holder
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.eastar.recyclerview.databinding.BindingviewarrayadapterDemoBinding
import dev.eastar.recyclerview.databinding.BindingviewarrayadapterDemoItemBinding
import dev.eastar.recyclerview.model.DATA_SOURCE
import dev.eastar.recyclerview.model.Data
import dev.eastar.recyclerview.model.ICON

class BindingViewArrayAdapterDemo : AppCompatActivity() {
    private val items = DATA_SOURCE.mapIndexed { index, text -> Data("$ICON$index", text) }

    private lateinit var bb: BindingviewarrayadapterDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = BindingviewarrayadapterDemoBinding.inflate(layoutInflater)
        setContentView(bb.root)
        bb.list.adapter = DataArrayAdapter(items).apply {
            setOnItemClickListener { parent, view, position, data ->
                Log.e(parent, view, position, data)
            }
        }
    }

    class DataArrayAdapter(items: List<Data>) : BindingViewArrayAdapter<BindingviewarrayadapterDemoItemBinding, Data>(
        R.layout.bindingviewarrayadapter_demo_item,
        items
    ) {
        override fun onBindViewHolder(
            bb: BindingviewarrayadapterDemoItemBinding,
            item: Data,
            holder: RecyclerView.ViewHolder,
            position: Int
        ) {
            Glide.with(bb.imageView).load(item.icon).into(bb.imageView)
            bb.textView.text = item.name
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<BindingviewarrayadapterDemoItemBinding> {
            return super.onCreateViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(holder: Holder<BindingviewarrayadapterDemoItemBinding>, item: Data, position: Int) {
            super.onBindViewHolder(holder, item, position)
        }

        override fun onBindViewHolder(holder: Holder<BindingviewarrayadapterDemoItemBinding>, position: Int) {
            super.onBindViewHolder(holder, position)
        }

        override fun onItemClick(parent: RecyclerView, itemView: View, position: Int, item: Data) {
            super.onItemClick(parent, itemView, position, item)
        }

        override fun getItemView(layer: Int, parent: ViewGroup, viewType: Int): View {
            return super.getItemView(layer, parent, viewType)
        }

        override fun getHolder(holderClass: Class<*>?, itemView: View): Holder<BindingviewarrayadapterDemoItemBinding> {
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

