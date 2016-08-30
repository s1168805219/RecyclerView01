## 1 RecycleView实现ListView的功能 ##
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
### **实现item的分割线** ###
	我们发现recyclerview没有分割线，需要调用mRecyclerView.addItemDecoration()添加分割线，
	但是ItemDecoration是抽象类，需要我们自己实现。



> 该类DividerItemDecoration源于：
	http://blog.csdn.net/lmj623565791/article/details/45059587

```
package com.cqc.recyclerview01;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * RecyclerView添加分割线（当使用LayoutManager为LinearLayoutManager时）
 * 该类源于：http://blog.csdn.net/lmj623565791/article/details/45059587
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    public DividerItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        Log.v("recyclerview - itemdecoration", "onDraw()");

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}

```

然后在mainActivity中调用：

```
recyclerView.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.VERTICAL));//添加分割线
```
#### **自定义分割线** ####
**item_divider.xml**
```
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
       android:shape="rectangle">
    <size android:height="4dp"/><!--不用设置宽度-->

    <gradient
        android:centerColor="#ff0000ff"
        android:endColor="#ffff0000"
        android:startColor="#ff00ff00"
        android:type="linear"/>
</shape>
<!--recyclerView分割线的设置-->
```
**如何使用自定义的分割线**


### **注意事项** ###


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

> 4 item的高度设为match_parent，父布局设置100dp，item的高无效

	父布局设置具体的高度，子view设match_parent，这是无效的。
	方法一：必须给子view（textview设置具体的数值），父布局math——parent
	方法二：item的view：使用    LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false); 而不是其他任何方法！（View.inflate(..)） 

> 5 item的子view（textview）宽度match_parent，父布局的宽度也是match_parent，但是仍然无法充满屏幕。

	方法：item的view：使用LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false); 而不是其他任何方法！（View.inflate(..)）




> 6 总结：结合4+5，使用View.inflate(context, R.layout.item_main, null);创建item的view，会导致item的view高度无效，子view（textview）的高宽无效。
	
	方法：item的view：使用LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false); 而不是其他任何方法！
	错误：（View.inflate(context, R.layout.item_main, null) 
	错误：LayoutInflater.from(context).inflate(R.layout.item_main,null)）