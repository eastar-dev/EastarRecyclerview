package dev.eastar.recyclerview

import android.os.Bundle
import android.recycler.BindingViewArrayAdapter
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
            setOnItemClickListener { parent, view, position, data ->  }
        }
    }

    class DataArrayAdapter(items: List<Data>) :
        BindingViewArrayAdapter<BindingviewarrayadapterDemoItemBinding, Data>(
            R.layout.bindingviewarrayadapter_demo_item,
            items
        ) {
        override fun onBindViewHolder(
            bb: BindingviewarrayadapterDemoItemBinding,
            d: Data,
            holder: RecyclerView.ViewHolder,
            position: Int
        ) {
            Glide.with(bb.imageView).load(d.icon).into(bb.imageView)
            bb.textView.text = d.name
        }
    }
}

