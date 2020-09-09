package dev.eastar.recyclerview

import android.os.Bundle
import android.recycler.ArrayAdapter
import android.recycler.BindingViewArrayAdapter
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.eastar.recyclerview.databinding.RecyclerActivityBinding
import dev.eastar.recyclerview.databinding.RecyclerActivityItemBinding

class BindingViewArrayAdapterDemo : AppCompatActivity() {
    private val items = DATA_SOURCE.mapIndexed { index, text -> Data("$ICON$index", text) }

    private lateinit var bb: RecyclerActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(this, R.layout.recycler_activity)
        bb.list.adapter = DataArrayAdapter(items)
    }

    class DataArrayAdapter(items: List<Data>) : BindingViewArrayAdapter<RecyclerActivityItemBinding, Data>(R.layout.recycler_activity_item, items) {
        override fun onBindViewHolder(
            bb: RecyclerActivityItemBinding,
            d: Data,
            holder: RecyclerView.ViewHolder,
            position: Int
        ) {
            Glide.with(bb.imageView).load(d.icon).into(bb.imageView)
            bb.textView.text = d.name
        }
    }
}

