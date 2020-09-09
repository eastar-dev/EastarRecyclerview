package dev.eastar.recyclerview

import android.os.Bundle
import android.recycler.ArrayAdapter
import android.recycler.BindingDataArrayAdapter
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.eastar.recyclerview.BR
import dev.eastar.recyclerview.databinding.RecyclerActivityBinding

class BindingDataArrayAdapterDemo : AppCompatActivity() {
    private lateinit var bb: RecyclerActivityBinding
    private val items = DATA_SOURCE.mapIndexed { index, text -> Data("$ICON$index", text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(this, R.layout.recycler_activity)
        bb.list.adapter = BindingDataArrayAdapter(R.layout.recycler_activity_item, BR.data, items)
    }
}
