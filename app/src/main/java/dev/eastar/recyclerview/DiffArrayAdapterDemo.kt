package dev.eastar.recyclerview

import android.os.Bundle
import android.recycler.DiffArrayAdapter
import android.recycler.DiffArrayAdapter.*
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import dev.eastar.recyclerview.databinding.DiffarrayadapterDemoBinding
import dev.eastar.recyclerview.model.DATA_SOURCE
import dev.eastar.recyclerview.model.Data
import dev.eastar.recyclerview.model.ICON

class DiffArrayAdapterDemo : AppCompatActivity() {
    private val ITEMS = DATA_SOURCE
        .mapIndexed { index, text ->
            if(index % 5==0)
                NullItem()
            else
                Data("$ICON$index", text)
        }


    private lateinit var bb: DiffarrayadapterDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DiffarrayadapterDemoBinding.inflate(layoutInflater)
        setContentView(bb.root)

        bb.list.adapter = DataArrayAdapter(
            DiffInfo(
                R.layout.diffarrayadapter_demo_item,
                DataArrayAdapter.HolderItem::class.java,
                Data::class.java
            ),
            DiffInfo(
                R.layout.recycler_activity_item_devider,
                NullHolder::class.java,
                NullItem::class.java
            ),
            items = ITEMS
        )
    }

    class DataArrayAdapter(vararg diffInfo: DiffInfo, var items: List<Any>) :
        DiffArrayAdapter(*diffInfo, items = items) {
        inner class HolderItem(itemView: View) : DiffHolder<Data>(itemView) {
            private var textView: TextView = itemView.findViewById(R.id.textView)
            private var imageView: ImageView = itemView.findViewById(R.id.imageView)

            override fun bind(d: Data) {
                Glide.with(imageView).load(d.icon).into(imageView)
                textView.text = d.name
            }
        }
    }
}



