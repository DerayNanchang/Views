package com.criss.kviews.commom

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.chris.kviews.R
import com.chris.kviews.utils.DensityUtil
import java.lang.Exception

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/11/20
 * Description EditText 一般样式的封装
 */

class CustomEditText(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private var ivIcon: ImageView
    private var edInput: EditText
    private var ivDelete: ImageView
    private var vLine: View
    private var onChangeListener: CustomEditText.OnChangeListener? = null
    private var onDeleteClickListener: OnDeleteClickListener? = null
    private val DELETE = 1
    private val INPUT = 2
    private val LINE = 3

    private var resLeft = 0
    private var cursorVisible = 0
    private var deleteIcon = 0
    private var hint : String ?
    private var type = 0

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
        resLeft = typedArray.getResourceId(R.styleable.CustomEditText_setLeftIcon, 0)
        hint = typedArray.getString(R.styleable.CustomEditText_setHint)
        cursorVisible = typedArray.getResourceId(R.styleable.CustomEditText_setCursorVisible, 0)
        deleteIcon = typedArray.getResourceId(R.styleable.CustomEditText_setDeleteIcon, 0)
        type = typedArray.getInteger(R.styleable.CustomEditText_setInputType, 0)
        typedArray.recycle()


        val view = LayoutInflater.from(context).inflate(R.layout.view_custom_simple_edit_text, this)
        ivIcon = view.findViewById(R.id.ivIcon)
        edInput = view.findViewById(R.id.etInput)
        ivDelete = view.findViewById(R.id.ivDelete)
        vLine = view.findViewById(R.id.line)
        initData()
        initEdText()
        addEtListener()
        delete()
        setOnFocusChangeListener()
    }

    private fun initData() {
        hint?.let {
            setHint(hint!!)
        }
        if (resLeft != 0) {
            setLeftIcon(resLeft)
        }
        if (cursorVisible != 0) {
            setCursorVisible(cursorVisible)
        }
        if (deleteIcon != 0) {
            setDeleteIcon(deleteIcon)
        }
        setInputType(type)
    }


    private fun initEdText() {
        edInput.requestFocus()
        edInput.setSelection(edInput.text.length)
        if (edInput.text != null && edInput.text.isNotEmpty()) {
            ivDelete.visibility = View.VISIBLE
        } else {
            ivDelete.visibility = View.GONE
        }
    }


    fun setInputType(type:Int){
        when(type){
            0 -> edInput.inputType = InputType.TYPE_CLASS_TEXT
            1 -> edInput.inputType = InputType.TYPE_CLASS_NUMBER
            2 -> edInput.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        }
    }


    fun getText(): Editable {
        return edInput.text
    }

    fun getTextString(): String {
        return edInput.text.toString()
    }


    fun setHintColor(color: Int) {
        edInput.setHintTextColor(color)
    }

    fun setCursorVisible(visible: Boolean) {
        edInput.isCursorVisible = visible
    }


    fun setCursorVisible(drawable: Int) {
        try {//修改光标的颜色（反射）
            val f = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            f.isAccessible = true
            f.set(edInput, drawable)
        } catch (ignored: Exception) {
            // TODO: handle exception
        }
    }

    fun setHint(hint: String) {
        edInput.hint = hint
    }

    fun setHint(resid: Int) {
        edInput.setHint(resid)
    }

    fun iconToOutside(isOutside: Boolean) {
        if (isOutside) {
            val width = ivIcon.width
            val px = DensityUtil.dip2px(this.context, 16f)
            setLineMargin(width + px + 70, 0, 10, 0)
        } else {

        }
    }

    fun setLeftIcon(img: Int) {
        ivIcon.setImageResource(img)
    }

    fun setIconVisibility(visibility: Boolean) {
        ivIcon.visibility = View.GONE
        ivIcon.visibility = if (visibility) View.VISIBLE else View.GONE
    }


    fun setEdTextColor(color: Int) {
        edInput.setTextColor(color)
    }

    fun setTextSize(size: Int) {
        edInput.textSize = size.toFloat()
    }

    fun setLineColor(color: Int) {
        vLine.setBackgroundColor(color)
    }

    fun setBackGroundColors(color: Int) {
        rootView.setBackgroundColor(color)
    }

    fun setChildrenMargin(left: Int, top: Int, right: Int, bottom: Int) {
        val inputParams = edInput.layoutParams as RelativeLayout.LayoutParams
        val lpParams = vLine.layoutParams as RelativeLayout.LayoutParams
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
        val lpParams = vLine.layoutParams as RelativeLayout.LayoutParams
        setParams(lpParams, left, top, right, bottom, LINE)
    }

    fun setEdtextMargin(left: Int, top: Int, right: Int, bottom: Int) {
        val lpParams = edInput.layoutParams as RelativeLayout.LayoutParams
        setParams(lpParams, left, top, right, bottom, INPUT)
    }

    fun setIconMargin(left: Int, top: Int, right: Int, bottom: Int) {
        val lpParams = ivIcon.layoutParams as RelativeLayout.LayoutParams
        setParams(lpParams, left, top, right, bottom, INPUT)
    }

    fun setDeleteIcon(resource: Int) {
        ivDelete.setImageResource(resource)
    }

    private fun delete() {
        ivDelete.setOnClickListener {
            onDeleteClickListener?.onDeleteClick(edInput.text)
            edInput.setText("")
        }
    }

    private fun setOnFocusChangeListener() {
        edInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && edInput.text.isNotEmpty()) {
                // 得到焦点
                ivDelete.visibility = View.VISIBLE
            } else {
                // 失去焦点
                ivDelete.visibility = View.GONE
            }
        }
    }


    private fun addEtListener() {
        edInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.isNotEmpty()) {
                    if (ivDelete.visibility != View.VISIBLE) {
                        ivDelete.visibility = View.VISIBLE
                    }
                } else {
                    if (ivDelete.visibility != View.GONE) {
                        ivDelete.visibility = View.GONE
                    }
                }
                onChangeListener?.onChangeText(s)
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