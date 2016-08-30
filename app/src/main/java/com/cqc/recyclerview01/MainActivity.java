package com.cqc.recyclerview01;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Context context;
    private List<String> list = new ArrayList<>();
    private Button btn_add;
    private Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_delete = (Button) findViewById(R.id.btn_delete);

        initData();

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.VERTICAL));//添加分割线
        recyclerView.setItemAnimator(new DefaultItemAnimator());//item添加移出动画

        final MyAdapter adapter = new MyAdapter(context, list);
        recyclerView.setAdapter(adapter);


        //点击事件（用adapter调用点击方法）
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListerner(View view, int position) {
                Toast.makeText(context,"短点击",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

                Toast.makeText(context,"长点击",Toast.LENGTH_SHORT).show();
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(0,"btn_add");
//                adapter.notifyDataSetChanged();//也可以,但是没有动画效果
                adapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);//滑动到第一个item，不加不会滑动到顶部。
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(0);
                adapter.notifyDataSetChanged();//也可以,但是没有动画效果
//                adapter.notifyItemRemoved(0);
            }
        });

    }

    private void initData() {
        list.clear();
        for (int i = 0; i < 100; i++) {
            list.add("item" + i);
        }
    }



}
