package com.chris.kviews.manager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/12/4
 * Description  LinearLayoutManager 优化类
 *
 *  p.s
 *      1. 可禁止左右，上下滑动
 */
public class CustomLinearLayoutManager extends LinearLayoutManager {


    private boolean isScrollEnabled = false;


    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    /**
     * 禁止滑动
     * canScrollHorizontally（禁止横向滑动）
     * @return
     */
    @Override
    public boolean canScrollHorizontally() {
        return isScrollEnabled && super.canScrollVertically();
    }
    /**
     * 禁止滑动
     * canScrollVertically（禁止竖向滑动）
     * @return
     */
    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
