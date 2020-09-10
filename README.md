[![Release](https://jitpack.io/v/eastar-dev/EastarRecyclerview.svg)](https://jitpack.io/#eastar-dev/EastarRecyclerview)

# EastarRecyclerview
The Last weapon Recyclerview

## What different EastarRecyclerview
more simple helper
If used databinding and AAC MVVM, just one line in you'r kt file!!

So I made it.


## How...

### Gradle with jitpack

#### Add it in your root build.gradle at the end of repositories:
```javascript
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
#### Add the dependency
```javascript
dependencies {
    implementation 'com.github.eastar-dev:EastarRecyclerview:1.0.1'
}
```

#### source code
```javascript
class BindingDataArrayAdapterDemo : AppCompatActivity() {
    private lateinit var bb: BindingdataarrayadapterDemoBinding
    private val items = DATA_SOURCE.mapIndexed { index, text -> Data("$ICON$index", text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = BindingdataarrayadapterDemoBinding.inflate(layoutInflater)
        setContentView(bb.root)
        bb.list.adapter = BindingDataArrayAdapter(R.layout.bindingdataarrayadapter_demo_item, BR.data, items)
    }
}
```

#### item xml
```javascript
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="data"
            type="dev.eastar.recyclerview.model.Data" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <ImageView
            android:id="@+id/imageView"
            android:layout_width="60dp"
            android:layout_height="60dp"
            android:layout_margin="16dp"
            android:adjustViewBounds="true"
            android:scaleType="centerInside"
            app:glideLoad="@{data.icon}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toStartOf="@+id/textView"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
...
```
![Screenshot](https://github.com/eastar-dev/EastarRecyclerview/blob/master/release/clz.png?raw=true)

## License
 ```code
Copyright 2020 eastar Jeong

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
