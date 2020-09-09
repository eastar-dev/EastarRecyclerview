package dev.eastar.recyclerview

import android.os.Bundle
import android.recycler.ArrayAdapter
import android.recycler.DiffArrayAdapter
import android.recycler.DiffArrayAdapter.DiffHolder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.eastar.recyclerview.databinding.RecyclerActivityBinding

class DiffArrayAdapterDemo : AppCompatActivity() {
    private val items = DATA_SOURCE.mapIndexed { index, text -> Data("$ICON$index", text) }

    private lateinit var bb: RecyclerActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(this, R.layout.recycler_activity)
        bb.list.adapter = DataArrayAdapter(
            items,
            DiffArrayAdapter.DiffInfo(R.layout.recycler_activity_item, HolderItem::class.java, Data::class.java),
            DiffArrayAdapter.DiffInfo(R.layout.recycler_activity_item_devider, DiffArrayAdapter.NullHolder::class.java, DiffArrayAdapter.DiffInfo::class.java),
        )
    }


    class HolderItem(itemView: View) : DiffHolder<Data>(itemView) {
        override fun bind(d: Data) {

        }
    }


    class DataArrayAdapter(var items: List<*>, vararg diffInfo: DiffInfo) : DiffArrayAdapter(items,*diffInfo) {

    }
}



