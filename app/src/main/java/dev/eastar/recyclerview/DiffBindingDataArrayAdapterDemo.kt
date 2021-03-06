package dev.eastar.recyclerview

import android.log.Log
import android.os.Bundle
import android.recycler.DiffBindingDataArrayAdapter
import android.recycler.DiffBindingDataArrayAdapter.DiffInfo
import android.recycler.NullItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.eastar.recyclerview.databinding.DiffbindingdataarrayadapterDemoBinding
import dev.eastar.recyclerview.model.Data

class DiffBindingDataArrayAdapterDemo : AppCompatActivity() {
    private lateinit var mAdapter: DiffBindingDataArrayAdapter
    private lateinit var bb: DiffbindingdataarrayadapterDemoBinding
    private val vm by viewModels<DiffBindingDataArrayAdapterDemoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(this, R.layout.diffbindingdataarrayadapter_demo)
        bb.vm = vm
        bb.lifecycleOwner = this

        //mAdapter = DiffBindingDataArrayAdapter(
        //    DiffInfo(R.layout.diffbindingdataarrayadapter_demo_item, BR.data, Data::class.java),
        //    DiffInfo(R.layout.recycler_activity_item_devider, DiffInfo.NoBr, NullItem::class.java),
        //    items = vm.items.value!!
        //)
        //bb.list.adapter = mAdapter
        //mAdapter.setOnItemClickListener { parent, view, position, data ->
        //    Log.e(parent, view, position, data)
        //}
    }
}

@BindingAdapter("adapterData")
fun setAdapterData(
    view: RecyclerView,
    data: List<Any>?,
) {
    Log.e(view, data)
    if (view.adapter !is DiffBindingDataArrayAdapter)
        view.adapter = DiffBindingDataArrayAdapter(
            DiffInfo(R.layout.diffbindingdataarrayadapter_demo_item, BR.data, Data::class.java),
            DiffInfo(R.layout.recycler_activity_item_devider, DiffInfo.NoBr, NullItem::class.java)
        )

    (view.adapter as DiffBindingDataArrayAdapter).run {
        set(data)
    }
}