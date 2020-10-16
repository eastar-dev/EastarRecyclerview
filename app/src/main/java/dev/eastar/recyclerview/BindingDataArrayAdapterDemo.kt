package dev.eastar.recyclerview

import android.log.Log
import android.os.Bundle
import android.recycler.BindingDataArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import dev.eastar.recyclerview.databinding.BindingdataarrayadapterDemoBinding
import dev.eastar.recyclerview.model.DATA_SOURCE
import dev.eastar.recyclerview.model.Data
import dev.eastar.recyclerview.model.ICON

class BindingDataArrayAdapterDemo : AppCompatActivity() {
    private lateinit var bb: BindingdataarrayadapterDemoBinding
    private val items = DATA_SOURCE.mapIndexed { index, text -> Data("$ICON$index", text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = BindingdataarrayadapterDemoBinding.inflate(layoutInflater)
        setContentView(bb.root)
        bb.list.adapter = BindingDataArrayAdapter(R.layout.bindingdataarrayadapter_demo_item, BR.data, items)
            .apply {
                setOnItemClickListener { parent, view, position, data ->
                    Log.e(parent, view, position, data)
                }
            }
    }
}
