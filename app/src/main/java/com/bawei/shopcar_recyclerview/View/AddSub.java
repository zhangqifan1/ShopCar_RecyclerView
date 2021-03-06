package com.bawei.shopcar_recyclerview.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bawei.shopcar_recyclerview.R;

/**
 * Created by 张祺钒
 * on2017/10/28.
 */

public class AddSub extends RelativeLayout {

    private TextView add;
    private TextView sub;
    private TextView count;

    public AddSub(Context context) {
        this(context, null);
    }

    public AddSub(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddSub(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.addsub, this);
        add = inflate.findViewById(R.id.add);
        sub = inflate.findViewById(R.id.sub);
        count = inflate.findViewById(R.id.count);
    }

    /**
     * 加
     */
    public  void setAddListener(OnClickListener clickListener){
        add.setOnClickListener(clickListener);
    }
    /**
     * 减
     */
    public  void setSubListener(OnClickListener clickListener){
        sub.setOnClickListener(clickListener);
    }
    /**
     * 数量
     */
    public  void setCountListener(OnClickListener clickListener){
        count.setOnClickListener(clickListener);
    }

    public void setCount(String string) {
        count.setText(string);
    }
}
