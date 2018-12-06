package com.chris.kviews.utils

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/11/26
 * Description recyclerView 工具类
 */

object RecyclerViewUtil {


    /**
     * 滑动到指定位置
     */
    fun smoothMoveToPosition(mRecyclerView: RecyclerView, position: Int) {
        // 第一个可见位置
        val firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0))
        // 最后一个可见位置
        val lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.childCount - 1))
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position)
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            val movePosition = position - firstItem
            if (movePosition >= 0 && movePosition < mRecyclerView.childCount) {
                val top = mRecyclerView.getChildAt(movePosition).top
                mRecyclerView.smoothScrollBy(0, top)
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position)
        }
    }

    fun moveToPosition(linearLayoutManager: LinearLayoutManager, rv: RecyclerView, index: Int) {
        //获取当前recycleView屏幕可见的第一项和最后一项的Position
        val firstItem = linearLayoutManager.findFirstVisibleItemPosition()
        val lastItem = linearLayoutManager.findLastVisibleItemPosition()
        //然后区分情况
        if (index <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            rv.scrollToPosition(index)
        } else if (index <= lastItem) {
            //当要置顶的项已经在屏幕上显示时，计算它离屏幕原点的距离
            val top = rv.getChildAt(index - firstItem).getTop()
            rv.scrollBy(0, top)
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            rv.scrollToPosition(index)
            //记录当前需要在RecyclerView滚动监听里面继续第二次滚动
            //move = true
        }
    }

}