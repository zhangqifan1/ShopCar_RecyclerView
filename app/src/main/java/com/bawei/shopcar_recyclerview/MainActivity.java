package com.bawei.shopcar_recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CheckBox mCbMain;
    /**
     * ￥
     */
    private TextView mPriceMain;
    /**
     * 结算()件
     */
    private TextView mCountMain;
    private RecyclerView mRecyclerView;
    private List<HashMap<String, String>> list;
    private int totalCount;
    private int totalPrice;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //准备数据
        initData();
        //设置数据
        initAdapter();
        mCbMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Boolean> pitchOnMap = myRecyclerViewAdapter.getPitchOnMap();
                if(mCbMain.isChecked()){
                    for (Map.Entry<String,Boolean> entry:pitchOnMap.entrySet()){
                        entry.setValue(mCbMain.isChecked());
                    }
                }else{
                    for (Map.Entry<String,Boolean> entry:pitchOnMap.entrySet()){
                        entry.setValue(mCbMain.isChecked());
                    }
                }
                priceControl(pitchOnMap);
                myRecyclerViewAdapter.setPitchOnMap(pitchOnMap);
                myRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initAdapter() {
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(list, this);
        myRecyclerViewAdapter.setiRefresh(new IRefresh() {
            @Override
            public void refreshPrice(Map<String, Boolean> pitchOnMap) {
                priceControl(pitchOnMap);
            }

            @Override
            public void refreshIsCheckedAll(boolean b) {
                mCbMain.setChecked(b);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
    }

    //控制价格展示
    private void priceControl(Map<String, Boolean> pitchOnMap) {
        totalCount = 0;
        totalPrice = 0;
        for (int i = 0; i < list.size(); i++) {
            if (pitchOnMap.get(list.get(i).get("id"))) {
                totalCount += Integer.parseInt(list.get(i).get("count"));
                totalPrice += Integer.valueOf(list.get(i).get("count")) * Integer.valueOf(list.get(i).get("price"));
            }
        }
        mPriceMain.setText("￥" + totalPrice + "元");
        mCountMain.setText("结算(" + totalCount + ")");
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", (new Random().nextInt(10000)) + "");
            map.put("name", "购物车里的第" + (i + 1) + "件商品");
            map.put("price", 50 + "");
            map.put("count", 1 + "");
            list.add(map);
        }
    }

    private void initView() {
        mCbMain = (CheckBox) findViewById(R.id.cbMain);
        mPriceMain = (TextView) findViewById(R.id.priceMain);
        mCountMain = (TextView) findViewById(R.id.countMain);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }
}
