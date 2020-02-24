package com.riocallos.itunes.views

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import com.riocallos.itunes.utils.ViewUtil

/**
 * Custom view for status and action bar.
 *
 */
class GradientStatusActionBar : View {

    private var statusBarHeight = 0

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            statusBarHeight = ViewUtil.dpToPx(this.context, 24)
            return insets.consumeSystemWindowInsets()
        }
        return insets
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), statusBarHeight)
    }

}