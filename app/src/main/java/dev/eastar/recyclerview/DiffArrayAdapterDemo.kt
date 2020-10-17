package dev.eastar.recyclerview

import android.os.Bundle
import android.recycler.DiffArrayAdapter
import android.recycler.NullItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.eastar.recyclerview.databinding.DiffarrayadapterDemoBinding
import dev.eastar.recyclerview.model.DATA_SOURCE
import dev.eastar.recyclerview.model.Data
import dev.eastar.recyclerview.model.ICON

class DiffArrayAdapterDemo : AppCompatActivity() {
    private lateinit var bb: DiffarrayadapterDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DiffarrayadapterDemoBinding.inflate(layoutInflater)
        setContentView(bb.root)

        bb.list.adapter = DAdapter()
    }

    class DAdapter : DiffArrayAdapter(
        DiffArrayAdapter.DiffInfo(R.layout.diffarrayadapter_demo_item, HolderItem::class.java, Data::class.java),
        DiffArrayAdapter.DiffInfo(R.layout.recycler_activity_item_devider, NullHolder::class.java, NullItem::class.java),
        items = DATA_SOURCE
            .mapIndexed { index, text ->
                if (index % 5 == 0)
                    NullItem()
                else
                    Data("$ICON$index", text)
            }
    ) {
        override fun getItemCount(): Int = super.getItemCount()
        override fun getHolder(holderClass: Class<*>?, itemView: View): DiffHolder<Any> = super.getHolder(holderClass, itemView)
        override fun getItemView(parent: ViewGroup, viewType: Int): View = super.getItemView(parent, viewType)
        override fun getItem(position: Int): Any = super.getItem(position)
        override fun onItemClick(parent: RecyclerView, itemView: View, position: Int, item: Any) {
            super.onItemClick(parent, itemView, position, item)
        }

        inner class HolderItem(itemView: View) : DiffArrayAdapter.DiffHolder<Data>(itemView) {
            override fun bind(d: Data, position: Int) {
                    Glide.with(imageView).load(d.icon).into(imageView)
                    textView.text = d.name
            }
            private var textView: TextView = itemView.findViewById(R.id.textView)
            private var imageView: ImageView = itemView.findViewById(R.id.imageView)
        }
    }
}



