package dev.eastar.recyclerview

import android.log.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class EastarViewModel : ViewModel() {
    val items by lazy { MutableLiveData<List<Data>>() }

    init {
        items.value = DATA_SOURCE.mapIndexed { index, text -> Data("$ICON$index", text) }
    }
}


@BindingAdapter("glideLoad")
fun setGlideLoad(
    view: ImageView,
    data: String?,
) {
    Log.e(view, data)
    runCatching {
        Glide.with(view).load(data).into(view)
    }
}


@BindingAdapter("adapterData")
fun setAdapterData(
    view: RecyclerView,
    data: List<Data>?,
) {
    Log.e(view, data)
}
