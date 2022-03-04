package dev.eastar.recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.recycler.BindingRecyclerAdapter;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import dev.eastar.recyclerview.databinding.ArrayadapterDemoBinding;
import dev.eastar.recyclerview.databinding.ChildLayout1Binding;
import dev.eastar.recyclerview.databinding.ChildLayout2Binding;
import dev.eastar.recyclerview.databinding.ChildLayout3Binding;
import dev.eastar.recyclerview.model.DataSourceKt;

public class BindingAdapterJavaDemo extends AppCompatActivity {

    private String[] datas;
    private ArrayadapterDemoBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas = DataSourceKt.getDATA_SOURCE();
        binding = ArrayadapterDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        View.OnClickListener child_1_ImageClickedListener = v -> Toast.makeText(v.getContext(), "Child1BindingHolder imageView clicked", Toast.LENGTH_SHORT).show();
        View.OnClickListener child_3_RootClickedListener = v -> Toast.makeText(v.getContext(), "Child3BindingHolder root clicked", Toast.LENGTH_SHORT).show();
        View.OnClickListener child_3_ImageClickedListener = v -> Toast.makeText(v.getContext(), "Child3BindingHolder imageView clicked", Toast.LENGTH_SHORT).show();
        TestBindingAdapter adapter = new TestBindingAdapter(child_1_ImageClickedListener, child_3_RootClickedListener, child_3_ImageClickedListener);
        binding.list.setAdapter(adapter);

        ArrayList<JavaParent> items = new ArrayList<JavaParent>();
        for (int i = 0; i < datas.length; i++) {
            String data = datas[i];
            if (i % 3 == 0) {
                JavaParent.Child1 child = new JavaParent.Child1();
                child.data = data;
                items.add(child);
            } else if (i % 3 == 1) {
                JavaParent.Child2 child = new JavaParent.Child2();
                child.data = data.hashCode();
                items.add(child);
            } else {
                items.add(new JavaParent.Child3());
            }
        }
        adapter.setItems(items);
    }


    public class TestBindingAdapter extends BindingRecyclerAdapter<JavaParent> {
        private View.OnClickListener child_1_imageClickedListener;
        private View.OnClickListener child_3_rootClickedListener;
        private View.OnClickListener child_3_imageClickedListener;

        public TestBindingAdapter(View.OnClickListener child_1_imageClickedListener, View.OnClickListener child_3_rootClickedListener, View.OnClickListener child_3_imageClickedListener) {
            super(
                new HolderInfo(JavaParent.Child1.class, Child1BindingHolder.class, R.layout.child_layout_1),
                new HolderInfo(JavaParent.Child2.class, Child2BindingHolder.class, R.layout.child_layout_2),
                new HolderInfo(JavaParent.Child3.class, Child3BindingHolder.class, R.layout.child_layout_3)
            );
            this.child_1_imageClickedListener = child_1_imageClickedListener;
            this.child_3_rootClickedListener = child_3_rootClickedListener;
            this.child_3_imageClickedListener = child_3_imageClickedListener;
        }

        private class Child1BindingHolder extends BindingHolder<ChildLayout1Binding, JavaParent.Child1> {
            public Child1BindingHolder(@NonNull View itemView) {
                super(itemView);
                getBinding().getRoot().setOnClickListener(v -> child_1_imageClickedListener.onClick(v));
            }

            @Override
            public void bind(JavaParent.Child1 data) {
                super.bind(data);
                getBinding().textView.setText(data.data);
            }
        }

        private class Child2BindingHolder extends BindingHolder<ChildLayout2Binding, JavaParent.Child2> {
            public Child2BindingHolder(@NonNull View itemView) {
                super(itemView);
            }

            @Override
            public void bind(JavaParent.Child2 data) {
                super.bind(data);
                getBinding().textView.setText("[" + data.data + "]");
            }
        }

        private class Child3BindingHolder extends BindingHolder<ChildLayout3Binding, JavaParent.Child3> {
            public Child3BindingHolder(@NonNull View itemView) {
                super(itemView);
                getBinding().getRoot().setBackgroundColor(Color.BLUE);
                getBinding().getRoot().setAlpha(.5F);
                getBinding().imageView.setOnClickListener(child_3_imageClickedListener);
                getBinding().getRoot().setOnClickListener(child_3_rootClickedListener);
            }
        }
    }
}

interface JavaParent {
    class Child1 implements JavaParent {
        String data = "Child1 String";
    }

    class Child2 implements JavaParent {
        int data = 1;
    }

    class Child3 implements JavaParent {

    }
}