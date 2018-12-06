package com.chris.kviews.im

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chris.kviews.R

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/11/21
 * Description 网络状态自定义 View
 */

class NetHintView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private lateinit var ivWarningIcon : ImageView
    private lateinit var tvWarningMsg : TextView
    private var icon : Int
    private var msg : String?

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.NetHintView)
        icon = array.getResourceId(R.styleable.NetHintView_setWarningIcon, 0)
        msg = array.getString(R.styleable.NetHintView_setWarningMsg)
        array.recycle()
        initView()
        initData()
    }

    private fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.view_net_hint, this)
        ivWarningIcon = view.findViewById(R.id.ivWarningIcon)
        tvWarningMsg = view.findViewById(R.id.tvWarningMsg)
    }

    private fun initData() {
        if (icon != 0){
           setIcon(icon)
        }

        msg?.let {
            setMsg(msg!!)
        }
    }

    public fun setIcon(icon : Int){
        ivWarningIcon.setImageResource(icon)
    }

    public fun setMsg(msg:String){
        tvWarningMsg.text = msg
    }

}