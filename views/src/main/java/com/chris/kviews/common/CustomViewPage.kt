package com.chris.kviews.common

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import com.chris.kviews.R

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/11/19
 * Description ViewPage 优化类
 *
 *  P.S
 *      1. 加载大图 PhotoView 处理崩溃问题
 *      2. 是否禁止滑动
 */

class CustomViewPage(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var isCanScroll = true  // 默认可以滑动


    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomViewPage)
        isCanScroll = typedArray.getBoolean(R.styleable.CustomViewPage_isCanScroll, true)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return isCanScroll && super.onInterceptTouchEvent(ev)
        } catch (e: Exception) {
        }
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return isCanScroll && super.onTouchEvent(ev)
        } catch (e: Exception) {
        }
        return false
    }

    /**
     *  是否可以滑动 ViewPage
     */
    fun setScanScroll(isCanScroll: Boolean) {
        this.isCanScroll = isCanScroll
    }
}