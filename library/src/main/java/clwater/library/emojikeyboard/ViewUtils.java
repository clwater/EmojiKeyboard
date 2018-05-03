package clwater.library.emojikeyboard;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.TextView;

public class ViewUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
