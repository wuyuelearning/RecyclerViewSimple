package com.example.admin.recyclesimple;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MultipleItemAdapter multipleItemAdapter;
    private List<MultipleItemModel> itemModelList = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;
    private android.os.Handler handler = new android.os.Handler();
    private boolean isLoading;

    private int count = 0;
    private boolean isFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//     initData();
        initData2();
    }

    private void initData() {

        itemModelList.add(new MultipleItemModel("饼干", 0));
        itemModelList.add(new MultipleItemModel("奶油饼干", 0));
        itemModelList.add(new MultipleItemModel("威化", 0));
        itemModelList.add(new MultipleItemModel("传统糕点", 0));
        itemModelList.add(new MultipleItemModel("泡芙", "蛋挞", 1));
        itemModelList.add(new MultipleItemModel("凤梨酥", 0));
        itemModelList.add(new MultipleItemModel("杏仁饼", "烧饼", 1));
        itemModelList.add(new MultipleItemModel("花生酥", "西式糕点", 1));
        itemModelList.add(new MultipleItemModel("曲奇", 0));
        itemModelList.add(new MultipleItemModel("曲奇", 0));
        itemModelList.add(new MultipleItemModel("巧克力派", "酥心卷", 1));
        itemModelList.add(new MultipleItemModel("面包", "泡芙", 1));
        itemModelList.add(new MultipleItemModel("威化", 0));
        itemModelList.add(new MultipleItemModel("蛋挞", "巧克力派", 1));
        itemModelList.add(new MultipleItemModel("酥心卷", "面包", 1));
    }

    private void initData2() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 1500);

    }

    private void getData() {
        for (int i = 0; i < 20 && count < 100; i++) {
            itemModelList.add(i % 2 == 0 ? new MultipleItemModel("type1-- " + count, 0) : new MultipleItemModel("type2-- " + count, "type2-- " + count, 1));
            count++;
        }
        multipleItemAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        multipleItemAdapter.notifyItemRemoved(multipleItemAdapter.getItemCount());
    }


    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        multipleItemAdapter = new MultipleItemAdapter(this, itemModelList);


        //GridLayoutManager
//        GridLayoutManager gridLayout = new GridLayoutManager(this, 4);
//
//        gridLayout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                switch (multipleItemAdapter.getItemViewType(position)) {
//                    case MultipleItemModel.SECOND_TYPE:
//                        return 4;
//                    case MultipleItemModel.THIRD_TYPE:
//                        return 1;
//                    default:
//                        return 1;
//
//                }
//            }
//        });
//        recyclerView.setLayoutManager(gridLayout);
//        recyclerView.setAdapter(multipleItemAdapter);

        //  LinearLayout

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // 设置布局的样式，线性布局
        recyclerView.setLayoutManager(linearLayoutManager);   // recyclerview 与LinearLayout布局相关联
        recyclerView.setAdapter(multipleItemAdapter);     //  将recyclerView与adapter相关联

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshlayout);  // 刷新加载
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {  // 监听刷新加载
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "loading item");
                        itemModelList.clear();
                        count = 0;
                        isFirstLoad = true;
                        getData();
                    }
                }, 2000);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();  // 最后一个Item的位置
                if (lastVisibleItemPosition + 1 == multipleItemAdapter.getItemCount() && isFirstLoad) {
                    Toast.makeText(MainActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //  adapter Item点击事件监听
        multipleItemAdapter.setItemClickLisenter(new MultipleItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (view.getId() == R.id.itemText11) {
                    Toast.makeText(MainActivity.this, " position: ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                }
            }
        });

        multipleItemAdapter.setItemChildClickListener(new MultipleItemAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(View view, int position) {
                if (view.getId() == R.id.itemText21) {
                    Toast.makeText(MainActivity.this, "点击了左边， position++: " + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                    startActivity(intent);
                } else if (view.getId() == R.id.itemText22) {
                    Toast.makeText(MainActivity.this, "点击了右边， position++: " + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                    startActivity(intent);
                }
            }
        });


        // recyclerview 滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

                if (lastVisibleItemPosition + 1 < multipleItemAdapter.getItemCount()) {
                    isFirstLoad = false;
                }
                if (lastVisibleItemPosition + 1 == multipleItemAdapter.getItemCount() && !isFirstLoad) {
                    Log.d("test", "loading executed");
                    //  在这里可以请求网络加载

                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    isFirstLoad = false;
                    if (isRefreshing) {
                        multipleItemAdapter.notifyItemRemoved(multipleItemAdapter.getItemCount());
                        return;
                    }

                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1000);
                    }

                } else if (lastVisibleItemPosition + 1 == multipleItemAdapter.getItemCount() && isFirstLoad) {
                    Toast.makeText(MainActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
                }

            }
        });


//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int lastVisibleItem =(linearLayoutManager).findLastVisibleItemPosition();
//                int totalItemCount = linearLayoutManager.getItemCount();
//            }
//        });

//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
//        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager1);
//        recyclerView.setAdapter(multipleItemAdapter);
//
//
//        multipleItemAdapter.setItemClickLisenter(new MultipleItemAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//            }
//        });
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });









    }


}

