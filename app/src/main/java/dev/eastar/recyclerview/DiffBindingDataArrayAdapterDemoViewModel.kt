package dev.eastar.recyclerview

import android.log.Log
import android.recycler.DiffArrayAdapter
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import dev.eastar.recyclerview.model.DATA_SOURCE
import dev.eastar.recyclerview.model.Data
import dev.eastar.recyclerview.model.ICON

class DiffBindingDataArrayAdapterDemoViewModel : ViewModel() {
    val items by lazy {
        MutableLiveData(DATA_SOURCE.mapIndexed { index, text ->
            if (index % 5 == 0)
                DiffArrayAdapter.NullItem()
            else
                Data("$ICON$index", text)
        })
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















