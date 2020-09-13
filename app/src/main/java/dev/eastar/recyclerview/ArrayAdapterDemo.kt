package dev.eastar.recyclerview

import android.os.Bundle
import android.recycler.ArrayAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.eastar.recyclerview.databinding.ArrayadapterDemoBinding
import dev.eastar.recyclerview.model.DATA_SOURCE
import dev.eastar.recyclerview.model.Data
import dev.eastar.recyclerview.model.ICON

class ArrayAdapterDemo : AppCompatActivity() {

    private lateinit var bb: ArrayadapterDemoBinding
    private val ITEMS = DATA_SOURCE.mapIndexed { index, text -> Data("$ICON$index", text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = ArrayadapterDemoBinding.inflate(layoutInflater)
        setContentView(bb.root)
        bb.list.adapter = DataArrayAdapter(ITEMS).apply {
            setOnItemClickListener { parent, view, position, data ->

            }
        }
    }

    class DataArrayAdapter(items: List<Data>) :
        ArrayAdapter<Holder, Data>(R.layout.arrayadapter_demo_item, items) {
        override fun getHolder(itemView: View, viewType: Int): Holder {
            return Holder(itemView)
        }

        override fun onBindViewHolder(h: Holder, d: Data, position: Int) {
            Glide.with(h.imageView).load(d.icon).into(h.imageView)
            h.textView.text = d.name
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

}
