package com.ankit.astroankit.UI;

import android.content.Context;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class UIUtils {
    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
