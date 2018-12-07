package com.chris.kviews.popup

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow


/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/11/28
 * Description  PopupWindow 优化类，增加点击动画，以及背景颜色
 */
class BasePopupWindow(context: Context) : PopupWindow(context) {

    private var mContext: Context? = null
    private val mShowAlpha = 0.88f
    private var mBackgroundDrawable: Drawable? = null


    init {
        this@BasePopupWindow.mContext = context
        initBasePopupWindow()
    }


    override fun setOutsideTouchable(touchable: Boolean) {
        super.setOutsideTouchable(touchable)
        if (touchable) {
            if (mBackgroundDrawable == null) {
                mBackgroundDrawable = ColorDrawable(0x00000000)
            }
            super.setBackgroundDrawable(mBackgroundDrawable)
        } else {
            super.setBackgroundDrawable(null)
        }
    }

    override fun setBackgroundDrawable(background: Drawable) {
        mBackgroundDrawable = background
        isOutsideTouchable = isOutsideTouchable
    }

    /**
     * 初始化BasePopupWindow的一些信息
     */
    private fun initBasePopupWindow() {
        animationStyle = android.R.style.Animation_Dialog
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true  //默认设置outside点击无响应
        isFocusable = true
    }

    override fun setContentView(contentView: View?) {
        if (contentView != null) {
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            super.setContentView(contentView)
            addKeyListener(contentView)
        }
    }

    fun getContext(): Context? {
        return mContext
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y)
        showAnimator().start()
    }

    override fun showAsDropDown(anchor: View) {
        super.showAsDropDown(anchor)
        showAnimator().start()
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int) {
        super.showAsDropDown(anchor, xoff, yoff)
        showAnimator().start()
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        showAnimator().start()
    }

    override fun dismiss() {
        super.dismiss()
        dismissAnimator().start()
    }

    /**
     * 窗口显示，窗口背景透明度渐变动画
     */
    private fun showAnimator(): ValueAnimator {
        val animator = ValueAnimator.ofFloat(1.0f, mShowAlpha)
        animator.addUpdateListener { animation ->
            val alpha = animation.animatedValue as Float
            setWindowBackgroundAlpha(alpha)
        }
        animator.duration = 360
        return animator
    }

    /**
     * 窗口隐藏，窗口背景透明度渐变动画
     */
    private fun dismissAnimator(): ValueAnimator {
        val animator = ValueAnimator.ofFloat(mShowAlpha, 1.0f)
        animator.addUpdateListener { animation ->
            val alpha = animation.animatedValue as Float
            setWindowBackgroundAlpha(alpha)
        }
        animator.duration = 320
        return animator
    }

    /**
     * 为窗体添加outside点击事件
     */
    private fun addKeyListener(contentView: View?) {
        if (contentView != null) {
            contentView.isFocusable = true
            contentView.isFocusableInTouchMode = true
            contentView.setOnKeyListener(View.OnKeyListener { view, keyCode, event ->
                when (keyCode) {
                    KeyEvent.KEYCODE_BACK -> {
                        dismiss()
                        return@OnKeyListener true
                    }
                    else -> {
                    }
                }
                false
            })
        }
    }

    /**
     * 控制窗口背景的不透明度
     */
    private fun setWindowBackgroundAlpha(alpha: Float) {
        val window = (getContext() as Activity).window
        val layoutParams = window.attributes
        layoutParams.alpha = alpha
        window.attributes = layoutParams
    }


}