package dev.eastar.recyclerview

import android.graphics.Color
import android.os.Bundle
import android.recycler.BindingRecyclerAdapter
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.eastar.recyclerview.databinding.ArrayadapterDemoBinding
import dev.eastar.recyclerview.databinding.ChildLayout1Binding
import dev.eastar.recyclerview.databinding.ChildLayout2Binding
import dev.eastar.recyclerview.databinding.ChildLayout3Binding
import dev.eastar.recyclerview.model.DATA_SOURCE
import dev.eastar.recyclerview.model.Data
import dev.eastar.recyclerview.model.ICON

sealed class Parent {
    data class Child1(
        val data: String = "Child1 String"
    ) : Parent()

    data class Child2(
        val data: Int = 1
    ) : Parent()

    object Child3 : Parent()
}

class BindingAdapterDemo : AppCompatActivity() {
    private lateinit var bb: ArrayadapterDemoBinding
    private val ITEMS = DATA_SOURCE.mapIndexed { index, text -> Data("$ICON$index", text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = ArrayadapterDemoBinding.inflate(layoutInflater)
        setContentView(bb.root)
        val adapter = TestBindingAdapter({
            Toast.makeText(this, "Child1BindingHolder imageView clicked", Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(this, "Child3BindingHolder root clicked", Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(this, "Child3BindingHolder imageView clicked", Toast.LENGTH_SHORT).show()
        })
        bb.list.adapter = adapter
        adapter.items = ITEMS.mapIndexed { index, data ->
            when (index % 3) {
                0 -> Parent.Child1(data.name)
                1 -> Parent.Child2(data.name.hashCode())
                else -> Parent.Child3
            }
        }
    }

    class TestBindingAdapter(
        val callback1: () -> Unit,
        val callback2: () -> Unit,
        val callback3: () -> Unit,
    ) : BindingRecyclerAdapter<Parent>(
        HolderInfo(Parent.Child1::class.java, Child1BindingHolder::class.java, R.layout.child_layout_1),
        HolderInfo(Parent.Child2::class.java, Child2BindingHolder::class.java, R.layout.child_layout_2),
        HolderInfo(Parent.Child3::class.java, Child3BindingHolder::class.java, R.layout.child_layout_3),
    ) {
        inner class Child1BindingHolder(itemView: View) :
            BindingHolder<ChildLayout1Binding, Parent.Child1>(itemView) {
            init {
                binding.root.setOnClickListener { items[bindingAdapterPosition] }
                binding.imageView.setOnClickListener { callback1() }
            }

            override fun bind(data: Parent.Child1) {
                binding.textView.text = data.data
            }
        }

        class Child2BindingHolder(itemView: View) :
            BindingHolder<ChildLayout2Binding, Parent.Child2>(itemView) {
            override fun bind(data: Parent.Child2) {
                binding.textView.text = "[${data.data}]"
            }
        }

        inner class Child3BindingHolder(itemView: View) :
            BindingHolder<ChildLayout3Binding, Parent.Child2>(itemView) {
            init {
                binding.root.setBackgroundColor(Color.BLUE)
                binding.root.alpha = .5F
                binding.imageView.setOnClickListener { callback3() }
                binding.root.setOnClickListener { callback2() }
            }
        }
    }
}
