package clwater.library.emojikeyboard;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.TextView;

public class ViewUtils {
    public static void setText(TextView textView, Object object) {
        if (object == null) {
            textView.setText(null);
        } else if (object instanceof Integer) {
            textView.setText((Integer) object);
        } else if (object instanceof String) {
            textView.setText(object.toString());
        }
    }

    public static String getText(Context context, Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof Integer) {
            return context.getString((Integer) object);
        } else {
            return object.toString();
        }
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp2pX
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static float getTextWidth(Paint paint, String string) {
        if (string == null || string.length() == 0) {
            return 0;
        }
        return paint.measureText(string);
    }
    public static float getTextHeight(Paint paint,String string) {
        if (string == null || string.length() == 0) {
            return 0;
        }
        Rect bonuds = new Rect();
        paint.getTextBounds(string, 0, string.length(),bonuds);
        return bonuds.height();
    }


    public static float getTextHeight(Paint paint) {
        Rect bonuds = new Rect();
        paint.getTextBounds("Q", 0, 1, bonuds);
        return bonuds.height();
    }
}
