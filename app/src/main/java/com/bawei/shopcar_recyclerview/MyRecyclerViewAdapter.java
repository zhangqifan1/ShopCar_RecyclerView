package com.bawei.shopcar_recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shopcar_recyclerview.View.AddSub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 张祺钒
 * on2017/10/28.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    //一个大集合  用以存放商品基本信息
    private List<HashMap<String, String>> dataList = new ArrayList<>();
    //用于存放标识
    private Map<String, Boolean> pitchOnMap = new HashMap<>();
    private Context context;
    private IRefresh iRefresh;

    public void setiRefresh(IRefresh iRefresh) {
        this.iRefresh = iRefresh;
    }

    public MyRecyclerViewAdapter(List<HashMap<String, String>> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
        //如果dataList不为空,遍历dataList,
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                //为pitchOnMap保存标识  赋予初值 false
                pitchOnMap.put(dataList.get(i).get("id"), false);
            }
        }
    }

    /**
     * 返回条目
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.item, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    /**
     * 返回数据
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //首先判空
        if (dataList.size() > 0) {
            //根据标识集合的对应值确定checkbox的选中情况
            if (pitchOnMap.get(dataList.get(position).get("id"))) {
                holder.cb.setChecked(true);
            } else {
                holder.cb.setChecked(false);
            }
            iRefresh.refreshIsCheckedAll(selectAll());
            iRefresh.refreshPrice(pitchOnMap);
        }

        //商品信息
        final HashMap<String, String> GoodsInfoHashMap = dataList.get(position);
        //赋值
        holder.goodsName.setText(GoodsInfoHashMap.get("name"));
        holder.goodsPrice.setText(GoodsInfoHashMap.get("price"));
        holder.GoodsInfo.setText(GoodsInfoHashMap.get("count") + "件商品,共计" + Integer.parseInt(GoodsInfoHashMap.get("count")) * Integer.parseInt(GoodsInfoHashMap.get("price")) + "元");
        holder.addsub.setCount(GoodsInfoHashMap.get("count"));
        //对自定义加减号进行逻辑处理
        //加的
        holder.addsub.setAddListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsInfoHashMap.put("count", Integer.parseInt(GoodsInfoHashMap.get("count")) + 1 + "");
                holder.GoodsInfo.setText(GoodsInfoHashMap.get("count") + "件商品,共计" + Integer.parseInt(GoodsInfoHashMap.get("count")) * Integer.parseInt(GoodsInfoHashMap.get("price")) + "元");
                notifyDataSetChanged();
                iRefresh.refreshPrice(pitchOnMap);

            }
        });

        //减的
        holder.addsub.setSubListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(dataList.get(position).get("count")) > 1) {
                    dataList.get(position).put("count", Integer.parseInt(dataList.get(position).get("count")) - 1 + "");
                } else {
                    Toast.makeText(context, "不能再少了", Toast.LENGTH_SHORT).show();
                    dataList.get(position).put("count", 1 + "");
                }
                GoodsInfoHashMap.put("count", Integer.parseInt(GoodsInfoHashMap.get("count")) - 1 + "");
                holder.GoodsInfo.setText(GoodsInfoHashMap.get("count") + "件商品,共计" + Integer.parseInt(GoodsInfoHashMap.get("count")) * Integer.parseInt(GoodsInfoHashMap.get("price")) + "元");
                notifyDataSetChanged();
                iRefresh.refreshPrice(pitchOnMap);
            }
        });

        //点击删除删除条目  价格 数量对应
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //标识集合和 数据集合 都要移除
                pitchOnMap.remove(dataList.get(position).get("id"));
                dataList.remove(position);
                notifyDataSetChanged();
                iRefresh.refreshPrice(pitchOnMap);
            }
        });
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int index = position;
                if (holder.cb.isChecked()) {
                    pitchOnMap.put(dataList.get(index).get("id"), true);
                } else {
                    pitchOnMap.put(dataList.get(index).get("id"), false);
                }
                iRefresh.refreshIsCheckedAll(selectAll());
                iRefresh.refreshPrice(pitchOnMap);
            }
        });
    }

    /**
     * 全选方法
     */
    public boolean selectAll(){
        boolean isChecked=true;
        for(Map.Entry<String,Boolean> entry:pitchOnMap.entrySet()){
            //遍历集合 如果为false 就不是全选了
            if(!entry.getValue()){
                isChecked=false;
                break;
            }
        }

        return isChecked;
    }

    public Map<String, Boolean> getPitchOnMap() {
        return pitchOnMap;
    }

    public void setPitchOnMap(Map<String, Boolean> pitchOnMap) {
        this.pitchOnMap.putAll(pitchOnMap);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final AddSub addsub;
        private final CheckBox cb;
        private final TextView goodsPrice;
        private final TextView delete;
        private final TextView goodsName;
        private final TextView GoodsInfo;

        public MyViewHolder(View itemView) {
            super(itemView);
            addsub = itemView.findViewById(R.id.addsub);
            cb = itemView.findViewById(R.id.cb);
            goodsPrice = itemView.findViewById(R.id.GoodsPrice);
            delete = itemView.findViewById(R.id.Delete);
            goodsName = itemView.findViewById(R.id.GoodsName);
            GoodsInfo = itemView.findViewById(R.id.GoodsInfo);
        }
    }
}
