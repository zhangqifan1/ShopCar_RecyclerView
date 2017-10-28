package com.bawei.shopcar_recyclerview;

import java.util.Map;

/**
 * Created by 张祺钒
 * on2017/10/28.
 */

public interface IRefresh {
    void refreshPrice(Map<String, Boolean> pitchOnMap);
    void refreshIsCheckedAll(boolean b);
}
