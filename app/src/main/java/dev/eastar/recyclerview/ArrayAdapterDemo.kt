package dev.eastar.recyclerview

import android.log.Log
import android.os.Bundle
import android.recycler.ArrayAdapter
import android.view.View
import android.view.ViewGroup
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
                Log.e(parent, view, position, data)
            }
        }
    }

    class DataArrayAdapter(items: List<Data>) :
        ArrayAdapter<Holder, Data>(
            R.layout.arrayadapter_demo_item,
            Holder::class.java,
            items,
        ) {
        override fun onBindViewHolder(h: Holder, d: Data, position: Int) {
            Glide.with(h.imageView).load(d.icon).into(h.imageView)
            h.textView.text = d.name
        }

        override fun onItemClick(parent: RecyclerView, itemView: View, position: Int, item: Data) {
            super.onItemClick(parent, itemView, position, item)
            Log.w(parent, itemView, position, item)
        }

        override fun getItemCount(): Int = super.getItemCount()
        override fun getHolder(holderClass: Class<*>?, itemView: View): Holder = super.getHolder(holderClass, itemView)
        //override fun getHolder(holderClass: Class<out Holder>?, itemView: View): Holder = super.getHolder(holderClass, itemView)
        override fun getItemView(layer: Int, parent: ViewGroup, viewType: Int): View = super.getItemView(layer, parent, viewType)
        override fun getItem(position: Int): Data = super.getItem(position)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
