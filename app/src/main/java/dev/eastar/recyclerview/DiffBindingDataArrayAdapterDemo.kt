package dev.eastar.recyclerview

import android.os.Bundle
import android.recycler.ArrayAdapter
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
        bb.list.adapter = DataArrayAdapter()
    }
}

//
//class Data {
//    val icon : String = "https://picsum.photos/100/100"
//    val icon : String = "https://picsum.photos/100/100"
//}
//
//class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//}
//
//
//class DataArrayAdapter : ArrayAdapter<Holder,Data>(R.layout.recycler_activity_item) {
//    override fun getHolder(itemView: View, viewType: Int): Holder {
//        return Holder(itemView)
//    }
//
//    override fun onBindViewHolder(h: Holder, d: Data, position: Int) {
//
//    }
//}
