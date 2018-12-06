package com.criss.kviews.commom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.chris.kviews.R
import com.chris.kviews.utils.DensityUtil
import kotlinx.android.synthetic.main.view_search.view.*

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/11/20
 * Description  自定义搜索View
 */
class SearchView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private var onChangeListener: OnChangeListener? = null
    private val DELETE = 1
    private val INPUT = 2
    private val LINE = 3
    private var onDeleteClickListener: OnDeleteClickListener? = null


    init {
        LayoutInflater.from(context).inflate(R.layout.view_search, this)
        initEdText()
        etListener()
        delete()
    }

    private fun initEdText() {
        etInput!!.requestFocus()
        etInput!!.setSelection(etInput!!.text.length)
        if (etInput.text != null && etInput!!.text.isNotEmpty()) {
            ivDelete.visibility = View.VISIBLE
        } else {
            ivDelete.visibility = View.GONE
        }
    }


    fun setCursorVisible(visible: Boolean) {
        etInput.isCursorVisible = visible
    }

    fun setCursorVisible(drawable: Int) {
        try {//修改光标的颜色（反射）
            val f = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            f.isAccessible = true
            f.set(etInput, drawable)
        } catch (ignored: Exception) {
        }

    }

    fun setHint(hint: String) {
        etInput.hint = hint
    }

    fun setHint(resid: Int) {
        etInput.setHint(resid)
    }

    fun iconToOutside(isOutside: Boolean) {
        if (isOutside) {
            val width = ivIcon.width
            val px = DensityUtil.dip2px(this.context, 16f)
            setLineMargin(width + px + 70, 0, 10, 0)
        } else {

        }
    }

    fun setIconResource(img: Int) {
        ivIcon.setImageResource(img)
    }

    fun setIconVisibility(visibility: Boolean) {
        ivIcon.visibility = View.GONE
        ivIcon.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    fun setHintColor(color: Int) {
        etInput.setHintTextColor(context.resources.getColor(color))
    }

    fun setEdTextColor(color: Int) {
        etInput.setTextColor(context.resources.getColor(color))
    }

    fun setTextSize(size: Int) {
        etInput.textSize = size.toFloat()
    }

    fun setLineColor(color: Int) {
        line!!.setBackgroundColor(context.resources.getColor(color))
    }

    fun setBackGroundColors(color: Int) {
        rootView.setBackgroundColor(context.resources.getColor(color))
    }

    fun setChildrenMargin(left: Int, top: Int, right: Int, bottom: Int) {
        val inputParams = etInput.layoutParams as RelativeLayout.LayoutParams
        val lpParams = line!!.layoutParams as RelativeLayout.LayoutParams
        val deleteParams = ivDelete.layoutParams as RelativeLayout.LayoutParams
        setParams(inputParams, left, top, right, bottom, INPUT)
        setParams(lpParams, left, top, right, bottom, LINE)
        setParams(deleteParams, left, top, right, bottom, DELETE)
    }

    private fun setParams(
        inputParams: RelativeLayout.LayoutParams,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        type: Int
    ) {
        inputParams.leftMargin = left
        inputParams.topMargin = top
        inputParams.rightMargin = right
        inputParams.bottomMargin = bottom
    }

    fun setLineMargin(left: Int, top: Int, right: Int, bottom: Int) {
        val lpParams = line!!.layoutParams as RelativeLayout.LayoutParams
        setParams(lpParams, left, top, right, bottom, LINE)
    }

    fun setEdtextMargin(left: Int, top: Int, right: Int, bottom: Int) {
        val lpParams = etInput.layoutParams as RelativeLayout.LayoutParams
        setParams(lpParams, left, top, right, bottom, INPUT)
    }

    fun setIconMargin(left: Int, top: Int, right: Int, bottom: Int) {
        val lpParams = ivIcon.layoutParams as RelativeLayout.LayoutParams
        setParams(lpParams, left, top, right, bottom, INPUT)
    }

    fun setDeleteImageResource(resource: Int) {
        ivDelete.setImageResource(resource)
    }

    private fun delete() {
        ivDelete.setOnClickListener {
            if (onDeleteClickListener != null) {
                onDeleteClickListener!!.onDeleteClick(etInput.text)
            }
            etInput.setText("")
        }
    }

    private fun etListener() {
        etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!(s == null || !s.isNotEmpty())) {
                    if (ivDelete.visibility != View.VISIBLE) {
                        ivDelete.visibility = View.VISIBLE
                    }
                } else {
                    if (ivDelete.visibility != View.GONE) {
                        ivDelete.visibility = View.GONE
                    }
                }
                if (onChangeListener != null) {
                    onChangeListener!!.onChangeText(s)
                }
            }
        })
    }

    interface OnChangeListener {
        fun onChangeText(text: Editable?)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(deleteText: Editable)
    }


    fun setOnChangeListener(onChangeListener: OnChangeListener) {
        this.onChangeListener = onChangeListener
    }

    fun setOnDeleteClickListener(onDeleteClickListener: OnDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener
    }
}