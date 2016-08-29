## 1 RecycleView的基本使用 ##
### 相关方法： ###
**RecyclerView的方法：**

|方法|含义|
|---|----|
|setLayoutManager(...)|设置布局管理者|
|setAdapter|设置适配器|

**Adapter中的方法：**

|方法|含义|
|---|----|
|onCreateViewHolder(...)|创建ViewHolder|
|onBindViewHolder(...)|给ViewHolder里面的view设置属性|
|getItemCount|item的数量|

### **步骤** ###
1. 找到RecycleView
2. 给RecycleView设置 LayoutManager
3. 给RecycleView设置 Adapter

    
### **下面通过一个demo显示RecycleView的基本使用。** ###
见图：

#### **MainActivity** ####

```
package com.cqc.recyclerview01;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Context context;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new MyAdapter());
    }

    private void initData() {
        list.clear();
        for (int i = 0; i < 100; i++) {
            list.add("item" + i);
        }
    }


    public class MyAdapter extends RecyclerView.Adapter {

        private MyHolder myHolder;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView tv = new TextView(context);
            tv.setHeight(50);
            myHolder = new MyHolder(tv);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            //  MyHolder myHolder = (MyHolder) holder;
            myHolder = (MyHolder) holder;
            myHolder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}

```
#### **注意事项** ####


> 1 onCreateViewHolder(...)不可以复用holder，必须new。

```
if (myHolder == null){
      myHolder = new MyHolder(tv);
}
```



> 2 onCreateViewHolder(...)中的holder和onBindViewHolder(RecyclerView.ViewHolder holder, int position) {。。}中的holder就是我们创建的MyHolder,可以直接进行格式转换，也可以声明成员变量。

```
    public class MyAdapter extends RecyclerView.Adapter {

        private MyHolder myHolder;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView tv = new TextView(context);
            tv.setHeight(50);
            myHolder = new MyHolder(tv);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            //  MyHolder myHolder = (MyHolder) holder;
            myHolder = (MyHolder) holder;
            myHolder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }
```

> 3 最好先创建类ViewHolder，再创建Adapter，这样可以使用直接指定泛型，就不需要再格式转换了。

```
public class MyAdapter extends RecyclerView.Adapter<MyHolder>
```
