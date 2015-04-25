package io.karim;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {
    static int dpToPx(Resources resources, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }
}
