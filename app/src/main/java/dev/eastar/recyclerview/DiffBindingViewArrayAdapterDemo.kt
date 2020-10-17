package dev.eastar.recyclerview

import android.os.Bundle
import android.recycler.DiffBindingViewArrayAdapter
import android.recycler.NullItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dev.eastar.recyclerview.databinding.DiffbindingdataarrayadapterDemoBinding
import dev.eastar.recyclerview.databinding.DiffbindingviewarrayadapterDemoItemBinding
import dev.eastar.recyclerview.model.Data

class DiffBindingViewArrayAdapterDemo : AppCompatActivity() {
    private lateinit var bb: DiffbindingdataarrayadapterDemoBinding
    private val vm by viewModels<DiffBindingDataArrayAdapterDemoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(this, R.layout.diffbindingdataarrayadapter_demo)
        bb.vm = vm
        bb.lifecycleOwner = this

        bb.list.adapter = DAdapter()
    }

    inner class DAdapter : DiffBindingViewArrayAdapter(
        DiffBindingViewArrayAdapter.DiffInfo(R.layout.diffbindingviewarrayadapter_demo_item, Holder::class.java, Data::class.java),
        DiffBindingViewArrayAdapter.DiffInfo(R.layout.recycler_activity_item_devider, NullHolder::class.java, NullItem::class.java)
    ) {

        inner class Holder(itemView: View) : DiffBindingViewArrayAdapter.DiffHolder<DiffbindingviewarrayadapterDemoItemBinding, Data>(itemView){
            override fun bind(bb: DiffbindingviewarrayadapterDemoItemBinding, d: Data, position: Int) {
                TODO("Not yet implemented")
            }
        }
    }
}
