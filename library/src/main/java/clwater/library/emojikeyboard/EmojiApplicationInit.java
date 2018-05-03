package clwater.library.emojikeyboard;

import android.content.Context;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;

/**
 * 初始化时调用
 */
public class EmojiApplicationInit {
    public static void init(Context context) {
        EmojiCompat.Config config = new BundledEmojiCompatConfig(context);
        EmojiCompat.init(config);
    }
}
