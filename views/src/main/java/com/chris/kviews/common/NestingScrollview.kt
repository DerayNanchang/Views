package com.chris.kviews.common

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.ScrollView

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/11/27
 * Description  解决EditText 弹出软键盘时，不会将RecyclerView 指向底部
 */
class NestingScrollview : ScrollView {



    private var downX: Int = 0
    private var downY: Int = 0
    private var mTouchSlop: Int = 0
    private var autoScroll = true

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 多层嵌套时的自动滚动
     * @param autoScroll
     */
    fun setAutoScroll(autoScroll: Boolean) {
        this.autoScroll = autoScroll
    }

    /**
     * 防止多层嵌套时候的自动滚动
     * @param rect
     * @return
     */
    override fun computeScrollDeltaToGetChildRectOnScreen(rect: Rect): Int {
        return if (autoScroll) super.computeScrollDeltaToGetChildRectOnScreen(rect) else 0
    }

    init {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        val action = e.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                downX = e.rawX.toInt()
                downY = e.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val moveY = e.rawY.toInt()
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(e)
    }
}