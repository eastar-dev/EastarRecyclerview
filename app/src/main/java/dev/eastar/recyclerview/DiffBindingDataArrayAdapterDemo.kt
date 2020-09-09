package dev.eastar.recyclerview

import android.os.Bundle
import android.recycler.ArrayAdapter
import android.recycler.DiffBindingDataArrayAdapter
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.eastar.recyclerview.databinding.RecyclerActivityBinding

class DiffBindingDataArrayAdapterDemo : AppCompatActivity() {

    private lateinit var bb: RecyclerActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(this, R.layout.recycler_activity)
        bb.list.adapter = DiffBindingDataArrayAdapter()
    }
}
