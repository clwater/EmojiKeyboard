package clwater.library.emojikeyboard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

import clwater.library.R;

/**
 * Emoji输入选择器
 * 使用:
 * 1:在xml文件中引入(高度建议为263)
 * 2.绑定EditText(通过setEditText()方法)
 * 注意:
 * 1.当展示时应关闭系统键盘,离开时打开
 */
public class EmojiKeyboard extends LinearLayout {

    private Context context;
    private LinearLayout linearLayout_emoji;
    private ViewPager viewpager_emojikeyboard;
    private EmojiIndicatorLinearLayout emojiIndicatorLinearLayout_emoji;
    private EditText editText;
    private int maxViewWidth ;
    private EmojiAdapter emojiAdapter;
    public EmojiKeyboard(Context context) {
        super(context);
        this.context = context;
        initView();

    }

    public EmojiKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();

    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    boolean init = true;

    public void initView() {
        LayoutInflater.from(context).inflate(R.layout.emoji_keyobard, this);
        //获取根布局对象
        linearLayout_emoji = (LinearLayout) findViewById(R.id.linearLayout_emoji);
        viewpager_emojikeyboard = (ViewPager) findViewById(R.id.viewpager_emojikeyboard);
        emojiIndicatorLinearLayout_emoji = (EmojiIndicatorLinearLayout) findViewById(R.id.emojiIndicatorLinearLayout_emoji);

        viewpager_emojikeyboard.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                if (init) {
                    init = false;
                    maxViewWidth = linearLayout_emoji.getWidth();
                    List<String> list = initList();
                    emojiAdapter = new EmojiAdapter(context, list , maxViewWidth);
                    //通过构建后的EmojiAdapter获取底部指示器的范围
                    emojiIndicatorLinearLayout_emoji.setMaxCount(emojiAdapter.getCount());

                    viewpager_emojikeyboard.setAdapter(emojiAdapter);
                    viewpager_emojikeyboard.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        }

                        @Override
                        public void onPageSelected(int position) {
                            //更新底部指示器
                            emojiIndicatorLinearLayout_emoji.setChoose(position + 1);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    //设置emoji点击的回调
                    emojiAdapter.setEmojiOnClick(new EmojiAdapter.EmojiTextOnClick() {
                        @Override
                        public void OnClick(String text) {
                            //获取坐标位置及文本内容
                            int index = editText.getSelectionStart();
                            Editable edit = editText.getEditableText();

                            //当点击删除按钮时text为-1
                            if (text.equals("-1")) {
                                String str = editText.getText().toString();
                                if (!str.equals("")) {

                                    //只有一个字符
                                    if (str.length() < 2) {
                                        editText.getText().delete(index - 1, index);
                                    } else if (index > 0) {
                                        String lastText = str.substring(index - 2, index);
                                        //检测最后两个字符是否为一个emoji(emoji可能存在一个字符的情况 需要进行正则校验)
                                        if (EmojiRegexUtil.checkEmoji(lastText)) {
                                            editText.getText().delete(index - 2, index);
                                        } else {
                                            editText.getText().delete(index - 1, index);
                                        }
                                    }

                                }
                            } else {
                                //插入你内容
                                if (index < 0 || index >= edit.length()) {
                                    edit.append(text);
                                } else {
                                    edit.insert(index, text);
                                }
                            }
                        }
                    });

                }

                return true;
            }
        });


    }

    /**
     * @return 暂定的emoji列表
     * 可以通过https://apps.timwhitlock.info/emoji/tables/unicode 查看相关内容
     */
    private List<String> initList() {
//        int version = android.os.Build.VERSION.SDK_INT;
        int version = 24;
        List<String> list = new ArrayList<>();

        //7.0及以上系统
        if (version >= 24){
            list.add("\uD83D\uDE01");
            list.add("\uD83D\uDE11");
            list.add("\uD83D\uDE33");
            list.add("\uD83D\uDE42");
            list.add("\uD83D\uDE43");
            list.add("\uD83E\uDD23");
            list.add("\uD83D\uDE02");
            list.add("\uD83D\uDE0C");
            list.add("\uD83D\uDE0E");
            list.add("\uD83D\uDE44");
            list.add("\uD83E\uDD14");
            list.add("\uD83D\uDE37");
            list.add("\uD83D\uDE09");
            list.add("\uD83D\uDE18");
            list.add("\uD83D\uDE1D");
            list.add("\uD83D\uDE06");
            list.add("\uD83E\uDD13");
            list.add("\uD83D\uDE2D");
            list.add("\uD83D\uDE24");
            list.add("\uD83D\uDE08");
            list.add("\uD83D\uDE07");
            list.add("\uD83D\uDE0A");
            list.add("\uD83D\uDE29");
            list.add("\uD83D\uDE15");
            list.add("\uD83D\uDE12");
            list.add("\uD83D\uDE0D");
            list.add("\uD83D\uDE20");
            list.add("\uD83D\uDE34");
            list.add("\uD83D\uDE32");
            list.add("\uD83D\uDE31");
            list.add("\uD83D\uDE05");
            list.add("\uD83D\uDE21");
            list.add("\uD83D\uDE28");
            list.add("\uD83D\uDE13");
            list.add("\uD83E\uDD22");
            list.add("\uD83E\uDD24");
            list.add("\uD83E\uDD11");
            list.add("\uD83E\uDD21");
            list.add("\uD83E\uDD17");
            list.add("\uD83E\uDD20");
            list.add("\uD83D\uDE4F");
            list.add("\uD83D\uDC4D");
            list.add("\uD83D\uDD95");
            list.add("\uD83D\uDC4C");
            list.add("✌");
            list.add("\uD83E\uDD19");
            list.add("\uD83D\uDC8D");
            list.add("\uD83D\uDC8B");
            list.add("\uD83D\uDC40");
            list.add("\uD83D\uDC6F");
            list.add("\uD83D\uDC6C");
            list.add("\uD83D\uDC74");
            list.add("\uD83D\uDC75");
            list.add("\uD83D\uDC7C");
            list.add("\uD83D\uDC83");
            list.add("\uD83D\uDE48");
            list.add("\uD83D\uDE49");
            list.add("\uD83D\uDE4A");
            list.add("\uD83D\uDC36");
            list.add("\uD83E\uDD8A");
            list.add("\uD83D\uDC31");
            list.add("\uD83E\uDD84");
            list.add("\uD83C\uDF1A");
            list.add("\uD83C\uDF1D");
            list.add("\uD83C\uDF40");
            list.add("\uD83C\uDF38");
            list.add("\uD83D\uDC90");
            list.add("\uD83C\uDF08");
            list.add("✨");
            list.add("\uD83D\uDCA4");
            list.add("❤");
            list.add("\uD83D\uDC94");
            list.add("\uD83D\uDC9A");
            list.add("\uD83D\uDC95");
            list.add("\uD83D\uDC96");
            list.add("\uD83D\uDE06");
            list.add("\uD83D\uDE10");
            list.add("\uD83D\uDCA9");
            list.add("\uD83D\uDC4B");
            //6.0以上系统
        }else if (version >= 23){
            list.add("\uD83D\uDE01");
            list.add("\uD83D\uDE11");
            list.add("\uD83D\uDE33");
            list.add("\uD83D\uDE42");
            list.add("\uD83D\uDE43");
            list.add("\uD83D\uDE02");
            list.add("\uD83D\uDE0C");
            list.add("\uD83D\uDE0E");
            list.add("\uD83D\uDE44");
            list.add("\uD83E\uDD14");
            list.add("\uD83D\uDE37");
            list.add("\uD83D\uDE09");
            list.add("\uD83D\uDE18");
            list.add("\uD83D\uDE1D");
            list.add("\uD83D\uDE06");
            list.add("\uD83E\uDD13");
            list.add("\uD83D\uDE2D");
            list.add("\uD83D\uDE24");
            list.add("\uD83D\uDE08");
            list.add("\uD83D\uDE07");
            list.add("\uD83D\uDE0A");
            list.add("\uD83D\uDE29");
            list.add("\uD83D\uDE15");
            list.add("\uD83D\uDE12");
            list.add("\uD83D\uDE0D");
            list.add("\uD83D\uDE20");
            list.add("\uD83D\uDE34");
            list.add("\uD83D\uDE32");
            list.add("\uD83D\uDE31");
            list.add("\uD83D\uDE05");
            list.add("\uD83D\uDE21");
            list.add("\uD83D\uDE28");
            list.add("\uD83D\uDE13");
            list.add("\uD83E\uDD11");
            list.add("\uD83E\uDD17");
            list.add("\uD83D\uDE4F");
            list.add("\uD83D\uDC4D");
            list.add("\uD83D\uDD95");
            list.add("\uD83D\uDC4C");
            list.add("✌");
            list.add("\uD83D\uDC8D");
            list.add("\uD83D\uDC8B");
            list.add("\uD83D\uDC40");
            list.add("\uD83D\uDC6C");
            list.add("\uD83D\uDC74");
            list.add("\uD83D\uDC75");
            list.add("\uD83D\uDC7C");
            list.add("\uD83D\uDC83");
            list.add("\uD83D\uDE48");
            list.add("\uD83D\uDE49");
            list.add("\uD83D\uDE4A");
            list.add("\uD83D\uDC36");
            list.add("\uD83D\uDC31");
            list.add("\uD83E\uDD84");
            list.add("\uD83C\uDF1A");
            list.add("\uD83C\uDF1D");
            list.add("\uD83C\uDF40");
            list.add("\uD83C\uDF38");
            list.add("\uD83D\uDC90");
            list.add("\uD83C\uDF08");
            list.add("✨");
            list.add("\uD83D\uDCA4");
            list.add("❤");
            list.add("\uD83D\uDC94");
            list.add("\uD83D\uDC9A");
            //6.0以下系统
        }else {
            list.add("\uD83D\uDE01");
            list.add("\uD83D\uDE11");
            list.add("\uD83D\uDE33");
            list.add("\uD83D\uDE02");
            list.add("\uD83D\uDE0C");
            list.add("\uD83D\uDE0E");
            list.add("\uD83D\uDE37");
            list.add("\uD83D\uDE09");
            list.add("\uD83D\uDE18");
            list.add("\uD83D\uDE1D");
            list.add("\uD83D\uDE06");
            list.add("\uD83D\uDE2D");
            list.add("\uD83D\uDE24");
            list.add("\uD83D\uDE08");
            list.add("\uD83D\uDE07");
            list.add("\uD83D\uDE0A");
            list.add("\uD83D\uDE29");
            list.add("\uD83D\uDE15");
            list.add("\uD83D\uDE12");
            list.add("\uD83D\uDE0D");
            list.add("\uD83D\uDE20");
            list.add("\uD83D\uDE34");
            list.add("\uD83D\uDE32");
            list.add("\uD83D\uDE31");
            list.add("\uD83D\uDE05");
            list.add("\uD83D\uDE21");
            list.add("\uD83D\uDE28");
            list.add("\uD83D\uDE13");
            list.add("\uD83D\uDE4F");
            list.add("\uD83D\uDC4D");
            list.add("\uD83D\uDC4C");
            list.add("✌");
            list.add("\uD83D\uDC8D");
            list.add("\uD83D\uDC8B");
            list.add("\uD83D\uDC40");
            list.add("\uD83D\uDC6F");
            list.add("\uD83D\uDC83");
            list.add("\uD83D\uDE48");
            list.add("\uD83D\uDE49");
            list.add("\uD83D\uDE4A");
            list.add("\uD83D\uDC36");
            list.add("\uD83D\uDC31");
            list.add("\uD83C\uDF1A");
            list.add("\uD83C\uDF1D");
            list.add("\uD83C\uDF40");
            list.add("\uD83C\uDF38");
            list.add("\uD83D\uDC90");
            list.add("\uD83C\uDF08");
            list.add("✨");
            list.add("\uD83D\uDCA4");
            list.add("❤");
            list.add("\uD83D\uDC94");
            list.add("\uD83D\uDC9A");
            list.add("\uD83D\uDC95");
            list.add("\uD83D\uDC96");
            list.add("\uD83D\uDE06");
            list.add("\uD83D\uDE10");
            list.add("\uD83D\uDCA9");
            list.add("\uD83D\uDC4B");
        }


        return list;
    }
}
