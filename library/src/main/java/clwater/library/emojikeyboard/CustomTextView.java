package clwater.library.emojikeyboard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.text.emoji.widget.EmojiTextViewHelper;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputFilter;
import android.util.AttributeSet;


/**
 * 官方demo中自定义view 可以解决emoji展示颜色浅的问题
 * 详情见https://github.com/googlesamples/android-EmojiCompat/blob/master/app/src/main/java/com/example/android/emojicompat/CustomTextView.java
 */
public class CustomTextView extends AppCompatTextView {

    private EmojiTextViewHelper mEmojiTextViewHelper;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getEmojiTextViewHelper().updateTransformationMethod();
    }

    @Override
    public void setFilters(InputFilter[] filters) {
        super.setFilters(getEmojiTextViewHelper().getFilters(filters));
    }

    @Override
    public void setAllCaps(boolean allCaps) {
        super.setAllCaps(allCaps);
        getEmojiTextViewHelper().setAllCaps(allCaps);
    }

    /**
     * Returns the {@link EmojiTextViewHelper} for this TextView.
     *
     * <p>This method can be called from super constructors through {@link
     * #setFilters(InputFilter[])} or {@link #setAllCaps(boolean)}.</p>
     */
    private EmojiTextViewHelper getEmojiTextViewHelper() {
        if (mEmojiTextViewHelper == null) {
            mEmojiTextViewHelper = new EmojiTextViewHelper(this);
        }
        return mEmojiTextViewHelper;
    }

}
