package com.chris.kviews.common

import android.content.Context
import android.support.annotation.DrawableRes
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.chris.kviews.R
import kotlinx.android.synthetic.main.view_item_custom.view.*


/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/11/27
 * Description  每个Item 的样式
 */
class CustomItemView(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs), View.OnClickListener {

    private var itemViewClick : IViewMeItem? = null


    init {

        val ta = context.obtainStyledAttributes(attrs, R.styleable.CustomItemView)
        val version = ta.getString(R.styleable.CustomItemView_setVersion)
        val bottomLine = ta.getBoolean(R.styleable.CustomItemView_bottomLine, false)
        val title = ta.getString(R.styleable.CustomItemView_setTitle)
        val newShow = ta.getBoolean(R.styleable.CustomItemView_newShow, false)
        val icon = ta.getResourceId(R.styleable.CustomItemView_setIcon, 0)
        ta.recycle()

        View.inflate(context,R.layout.view_item_custom,this)
        setTitle(title)
        setBottomLine(bottomLine,0)
        setVersion(version)
        isNewShow(newShow)
        if (icon != 0){
            ivMeIcon.setImageResource(icon)
        }
    }

    private fun setBottomLine(bottomLine: Boolean, homeSelectTypeBGCT: Int) {
        if (bottomLine) {
            if (homeSelectTypeBGCT == 0) {
                vMeBottomLine!!.setBackgroundColor(resources.getColor(R.color.homeSelectTypeBGCT))
            } else {
                vMeBottomLine!!.setBackgroundColor(homeSelectTypeBGCT)
            }

            vMeBottomLine!!.visibility = View.VISIBLE
        } else {
            if (homeSelectTypeBGCT == 0) {
                vMeBottomLine!!.setBackgroundColor(resources.getColor(R.color.homeSelectTypeBGCT))
            } else {
                vMeBottomLine!!.setBackgroundColor(homeSelectTypeBGCT)
            }
            vMeBottomLine!!.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_root -> if (itemViewClick != null) {
                itemViewClick!!.OnViewMeListRootListener(v)
            }
        }
    }

    fun isNewShow(isNewView: Boolean) {
        if (isNewView) {
            tvMeNew!!.visibility = View.VISIBLE
        } else {
            tvMeNew!!.visibility = View.GONE
        }
    }

    // 对外暴露接口 提供回调
    fun setOnClickListener(ItemViewClick: IViewMeItem) {
        this.itemViewClick = ItemViewClick
        rl_root!!.setOnClickListener(this)
    }

    fun setSrc(@DrawableRes resId: Int) {
        ivMeIcon!!.setImageResource(resId)
    }

    fun setTitle(title: String?) {
        tvMeTitle.text = title
    }

    fun getTitle(): String {
        return tvMeTitle!!.text.toString().trim { it <= ' ' }
    }

    fun setVersion(version: String?) {
        if (!TextUtils.isEmpty(version)) {
            tvMeVersion!!.text = version
        }
    }

    interface IViewMeItem {
        fun OnViewMeListRootListener(v: View)
    }
}