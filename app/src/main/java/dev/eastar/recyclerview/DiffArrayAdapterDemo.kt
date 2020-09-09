package dev.eastar.recyclerview

import android.os.Bundle
import android.recycler.ArrayAdapter
import android.recycler.DiffArrayAdapter
import android.recycler.DiffArrayAdapter.DiffInfo
import android.recycler.DiffArrayAdapter.NullHolder
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
            DiffInfo(
                R.layout.recycler_activity_item,
                HolderItem::class.java,
                Data::class.java
            ),
            DiffInfo(
                R.layout.recycler_activity_item_devider,
                NullHolder::class.java,
                DiffInfo::class.java
            ),
            items = items
        )
    }


    class HolderItem(itemView: View) : DiffHolder<Data>(itemView) {
        override fun bind(d: Data) {
            d.icon
        }
    }


    class DataArrayAdapter(vararg diffInfo: DiffInfo, var items: List<Any>) :
        DiffArrayAdapter(*diffInfo, items = items) {
    }
}



