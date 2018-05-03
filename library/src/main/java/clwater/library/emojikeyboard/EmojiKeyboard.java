package clwater.library.emojikeyboard;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 */
public class EmojiKeyboard extends LinearLayout {

    private Context context;

    private LinearLayout linearLayout_emoji;
    private ViewPager viewpager_emojikeyboard;
    private RecyclerView recycleview_emoji_class;
    private EmojiIndicatorLinearLayout emojiIndicatorLinearLayout_emoji;
    private BottomClassAdapter bottomClassAdapter;
    private EditText editText;
    private EmojiAdapter emojiAdapter;

    private List<List<String>> lists;           //数据源

    private List<Drawable> tips = new ArrayList<>();    //底部图标信息
    private List<Integer> listInfo = new ArrayList<>(); //输入器分页情况

    int maxLinex = 3;       //行数
    int maxColumns = 7;    //列数
    private int emojiSize = 26; //字体大小
    private int indicatorPadding = 10;  //底部指示器距离
    private int itemIndex = 0;          //当前选择页面
    private int minItemIndex;           //当前条目页面最小位置
    private int maxItemIndex;           //当前条目页面最大位置
    private int maxViewWidth;           //页面宽度


    public EmojiKeyboard(Context context) {
        super(context);
        this.context = context;
    }

    public EmojiKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setLists(List<List<String>> lists) {
        this.lists = lists;
    }

    public void setTips(List<Drawable> tips) {
        this.tips = tips;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    boolean init = true;

    public void init() {
        initView();
    }


    public void setEmojiSize(int emojiSize) {
        this.emojiSize = emojiSize;
    }

    public void setIndicatorPadding(int indicatorPadding) {
        this.indicatorPadding = indicatorPadding;
    }

    public void setMaxLines(int maxLinex) {
        this.maxLinex = maxLinex;
    }

    public void setMaxColumns(int maxColumns) {
        this.maxColumns = maxColumns;
    }

    public void initView() {
        LayoutInflater.from(context).inflate(R.layout.emoji_keyobard, this);
        //获取根布局对象
        linearLayout_emoji = (LinearLayout) findViewById(R.id.linearLayout_emoji);
        viewpager_emojikeyboard = (ViewPager) findViewById(R.id.viewpager_emojikeyboard);
        emojiIndicatorLinearLayout_emoji = (EmojiIndicatorLinearLayout) findViewById(R.id.emojiIndicatorLinearLayout_emoji);
        recycleview_emoji_class = (RecyclerView) findViewById(R.id.recycleview_emoji_class);

        viewpager_emojikeyboard.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                if (init) {
                    init = false;
                    maxViewWidth = linearLayout_emoji.getWidth();
                    emojiAdapter = new EmojiAdapter(context, lists, maxViewWidth, maxLinex, maxColumns, emojiSize);
                    //通过构建后的EmojiAdapter获取底部指示器的范围

                    listInfo = emojiAdapter.getListInfo();
                    viewpager_emojikeyboard.setAdapter(emojiAdapter);


                    minItemIndex = 0;
                    maxItemIndex = listInfo.get(itemIndex);

                    //初始化底部指示器信息
                    emojiIndicatorLinearLayout_emoji.setMaxCount(listInfo.get(itemIndex));
                    emojiIndicatorLinearLayout_emoji.setPadding(0, 0, 0, ViewUtils.dip2px(context, indicatorPadding));


                    //初始化底部icon
                    initBottomClass();
                    //为ViewPager添加滑动监听
                    initViewPageChangeListener();
                    //设置emoji点击的回调
                    initEmojiOnClick();
                }

                viewpager_emojikeyboard.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }

    private void initBottomClass() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleview_emoji_class.setLayoutManager(linearLayoutManager);

        if (tips.size() != 0 && listInfo.size() < tips.size()) {
            tips = tips.subList(0, listInfo.size());
        }

        bottomClassAdapter = new BottomClassAdapter(context, tips, listInfo.size());
        bottomClassAdapter.setItemOnClick(new BottomClassAdapter.ItemOnClick() {
            @Override
            public void itemOnClick(int position) {
                clickChangeBottomClass(position);
            }
        });
        recycleview_emoji_class.setAdapter(bottomClassAdapter);
    }

    private void initViewPageChangeListener() {
        viewpager_emojikeyboard.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //滑动后更新底部指示器
                touchChangeBottomClass(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * @param clickItemIndex 点击后更新指示器
     */
    private void clickChangeBottomClass(int clickItemIndex) {
        itemIndex = clickItemIndex;
        maxItemIndex = 0;
        minItemIndex = 0;
        for (int i = 0; i <= itemIndex; i++) {
            maxItemIndex += listInfo.get(i);
        }

        for (int i = 0; i < itemIndex; i++) {
            minItemIndex += listInfo.get(i);
        }


        viewpager_emojikeyboard.setCurrentItem(minItemIndex);
        changeBottomClassIcon();


        emojiIndicatorLinearLayout_emoji.setMaxCount(listInfo.get(itemIndex));
        emojiIndicatorLinearLayout_emoji.setChoose(0);
    }

    /**
     * @param position 滑动后更新底部指示器
     */
    private void touchChangeBottomClass(int position) {
        //判断滑动是否越界
        if (position >= maxItemIndex) {
            itemIndex++;

            maxItemIndex = 0;
            minItemIndex = 0;
            for (int i = 0; i <= itemIndex; i++) {
                maxItemIndex += listInfo.get(i);
            }

            for (int i = 0; i < itemIndex; i++) {
                minItemIndex += listInfo.get(i);
            }

            emojiIndicatorLinearLayout_emoji.setMaxCount(listInfo.get(itemIndex));
            emojiIndicatorLinearLayout_emoji.setChoose(0);
        } else if (position < minItemIndex) {
            itemIndex--;

            maxItemIndex = 0;
            minItemIndex = 0;
            for (int i = 0; i <= itemIndex; i++) {
                maxItemIndex += listInfo.get(i);
            }

            for (int i = 0; i < itemIndex; i++) {
                minItemIndex += listInfo.get(i);
            }

            emojiIndicatorLinearLayout_emoji.setMaxCount(listInfo.get(itemIndex));
            emojiIndicatorLinearLayout_emoji.setChoose(listInfo.get(itemIndex) - 1);
        } else {
            position -= minItemIndex;

            emojiIndicatorLinearLayout_emoji.setChoose(position);
        }

        changeBottomClassIcon();
    }

    private void changeBottomClassIcon() {
        bottomClassAdapter.changeBottomItem(itemIndex);


        int firstItem = recycleview_emoji_class.getChildLayoutPosition(recycleview_emoji_class.getChildAt(0));
        int lastItem = recycleview_emoji_class.getChildLayoutPosition(recycleview_emoji_class.getChildAt(recycleview_emoji_class.getChildCount() - 1));


        if (itemIndex <= firstItem || itemIndex >= lastItem) {
            recycleview_emoji_class.smoothScrollToPosition(itemIndex);
        }
    }


    private void initEmojiOnClick() {
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

}
