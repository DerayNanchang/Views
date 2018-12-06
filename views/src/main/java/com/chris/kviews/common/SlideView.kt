package com.chris.kviews.common

import android.content.Context
import android.graphics.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.chris.kviews.R
import com.chris.kviews.utils.Config
import com.chris.kviews.utils.RecyclerViewUtil
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sp

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/11/30
 * Description 通讯录右侧滑动自定义View
 *
 * 绘制思路：
 *  1. 获取每个字母的区域高度
 *  2. 遍历循环，绘制每个字母的区域
 *  3. 绘制文本,注意 baseLine = (Rect.top + Rect.bottom - fontMetrics.bottom - fontMetrics.top)/2
 *  4. 复写 onTouchEvent 方法,注意 performClick
 */

class SlideView(context: Context, attrs: AttributeSet?) : View(context, attrs) {


    private var textColor: Int = Config.DEFAULT_INT
    private var textSize: Float = Config.DEFAULT_FLOAT


    enum class Schema {
        PROMPT, SMOOTH
    }

    private var paint = Paint()
    private var areaHeight: Float = Config.DEFAULT_FLOAT
    private var areaTop: Float = Config.DEFAULT_FLOAT
    private var areaBottom: Float = Config.DEFAULT_FLOAT
    private var onTouchEventListener: OnTouchEventListener? = null

    companion object {
        //   A - Z
        val LIST = listOf(
            "↑", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"
            , "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"
        )
    }

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlideView)
        textColor = typedArray.getColor(R.styleable.SlideView_setTextColor, resources.getColor(android.R.color.black))
        textSize = typedArray.getDimension(R.styleable.SlideView_setTextSize, sp(12).toFloat())
        typedArray.recycle()


        paint.apply {

            textAlign = Paint.Align.CENTER

            color = if (this@SlideView.textColor == Config.DEFAULT_INT)
                resources.getColor(android.R.color.black)
            else
                this@SlideView.textColor

            textSize = if (this@SlideView.textSize == Config.DEFAULT_FLOAT)
                sp(12).toFloat()
            else
                this@SlideView.textSize

            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 1. 获取每个字母的区域高度
        areaHeight = h * 1.0f / LIST.size
        areaBottom = areaHeight
    }

    override fun onDraw(canvas: Canvas) {
        // 绘制每行文本
        LIST.forEachIndexed { index, s ->
            // 每行文本居中位置  =  (外部图形的底部的高度 + 顶部的高度 - fontMetrics.bottom - fontMetrics.top)/2
            areaTop = index * areaHeight
            areaBottom = (index + 1) * areaHeight
            val baseLine = (areaTop + areaBottom - paint.fontMetrics.bottom - paint.fontMetrics.top) / 2
            // 每行文本X轴 = (总宽度 - 字母的宽度)/2 p.s 前面设置了Align Center 中间对齐，所以直接除以2就可以了
            canvas.drawText(s, width * 1.0f / 2, baseLine, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 找到当前所在的位置
                performClick()
                background = resources.getDrawable(R.drawable.select_slide_color)
                onChange(event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                onChange(event.y)
            }
            MotionEvent.ACTION_UP -> {
                backgroundColor = resources.getColor(R.color.gone)
                onTouchEventListener?.onTouchFinish()
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        return true
    }

    private fun onChange(y: Float) {
        var position = (y / areaHeight).toInt()
        if (position <= 0) position = 0
        if (position >= LIST.size - 1) position = LIST.size - 1
        val chat = LIST[position]
        onTouchEventListener?.onTouchChange(chat)
    }

    interface OnTouchEventListener {
        fun onTouchChange(chat: String)
        fun onTouchFinish()
    }

    fun setOnTouchEventListener(onTouchEventListener: OnTouchEventListener) {
        this.onTouchEventListener = onTouchEventListener
    }

    fun setMoreToPosition(linearLayoutManager: LinearLayoutManager, rv: RecyclerView, index: Int, schema: Schema) {
        when (schema) {
            Schema.PROMPT -> RecyclerViewUtil.moveToPosition(linearLayoutManager, rv, index)
            Schema.SMOOTH -> RecyclerViewUtil.smoothMoveToPosition(rv, index)
        }
    }

    fun setTextSize(size: Float) {
        paint.textSize = size
    }

    fun setTextColor(color: Int) {
        paint.color = color
    }
}