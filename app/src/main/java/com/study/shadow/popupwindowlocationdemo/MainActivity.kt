package com.study.shadow.popupwindowlocationdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow

class MainActivity : AppCompatActivity() {
    private var popupWindow: PopupWindow? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun showPopupWindow(view: View) {
        val displayMetrics = resources.displayMetrics
        val widthPixels = displayMetrics.widthPixels
        val heightPixels = displayMetrics.heightPixels
        if (popupWindow == null) {
            popupWindow = PopupWindow()
            val helpLayout = View.inflate(this, R.layout.bubble_item, null)
            helpLayout.setOnClickListener {
                if (popupWindow != null && popupWindow!!.isShowing()) {
                    popupWindow!!.dismiss()
                }
            }


            popupWindow!!.contentView = helpLayout
            popupWindow!!.width = (widthPixels * 0.4).toInt()
            popupWindow!!.height = WindowManager.LayoutParams.WRAP_CONTENT
            popupWindow!!.isTouchable = true
            popupWindow!!.isFocusable = true
            popupWindow!!.isOutsideTouchable = true
        }
        Log.e("zuobiao", "$widthPixels===$heightPixels===${displayMetrics.density}")
        Log.e("zuobiao", "${view.width}=宽高=${view.height}")
        Log.e("zuobiao", "${view.x}=坐标点xy=${view.y}")
        //相对于控件的缩放和旋转的中心点
        Log.e("zuobiao", "${view.pivotX}=坐标点=${view.pivotY}")
        //popupWindow!!.showAsDropDown(view)
        //popupWindow!!.showAsDropDown(view, -TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180f, displayMetrics).toInt(), -(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, displayMetrics) / 2).toInt())
        /**
         * 箭头的宽高都为16dp,箭头左边缘距离为15dp,圆形的按钮宽高为25dp
         * 为了让箭头的中心位置与圆形按钮的中心位置在垂直方向相对齐和箭头紧贴圆形按钮，则需要
         * 让PopupWindow左移(16/2+25-25/2=11),上移(16/2=8)
         */
        //popupWindow!!.showAsDropDown(view, -TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 11F, displayMetrics).toInt(),-TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8F, displayMetrics).toInt())
        /**
         *修正反方向挤压需要将PopupWindow布局向左移PopupWindow宽度减去圆形按钮左边缘距离屏幕右边缘的宽度，然后
         * 继续左移箭头中心位置和圆形按钮中心位置之间的距离（12.5+25-15-8）
         */
        val xoff = popupWindow!!.width - (widthPixels - view.x) + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14F, displayMetrics).toInt()
        popupWindow!!.showAsDropDown(view, -xoff.toInt(), -TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8F, displayMetrics).toInt())

    }
}
