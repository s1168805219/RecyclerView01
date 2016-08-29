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
