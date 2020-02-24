package com.riocallos.itunes.utils

import android.content.Context
import android.util.TypedValue

/**
 * Utility class for views.
 *
 */
object ViewUtil {

    /**
     * Convert density-independent pixels to pixels.
     *
     * @property context [Context] of the activity.
     * @property dp [Int] density-independent pixels to convert.
     * #return [Int] is the equivalent pixels.
     */
    fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
    
}