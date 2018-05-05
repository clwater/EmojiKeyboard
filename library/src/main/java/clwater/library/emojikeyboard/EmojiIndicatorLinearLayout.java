package clwater.library.emojikeyboard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import clwater.library.R;


/**
 * emoji输入法中的指示器
 */

public class EmojiIndicatorLinearLayout extends LinearLayout {
    private Context context;
    private int chooseItem = 0;
    private int maxCount;

    public EmojiIndicatorLinearLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public EmojiIndicatorLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        updateView();
    }

    private void updateView() {
        removeAllViews();
        for (int i = 0; i < maxCount; i++) {
            ImageView imageView = new ImageView(context);
            int paddingHorizontal = ViewUtils.dip2px(getContext(), 2);
            imageView.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
            if (i == chooseItem) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.indicator_selected_black_bg));
            } else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.indicator_unselected_black_bg));
            }
            addView(imageView);
        }
    }

    public void setChoose(int chooseItem) {
        if (chooseItem < 0) {
            chooseItem = 0;
        } else if (chooseItem > maxCount) {
            chooseItem = maxCount;
        }
        this.chooseItem = chooseItem;

        updateView();
    }

    private void initView() {
        this.setOrientation(HORIZONTAL);
    }


}
