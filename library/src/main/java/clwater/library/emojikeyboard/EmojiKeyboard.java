package clwater.library.emojikeyboard;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
    private RecyclerView recycleview_emoji_class;
    private EmojiIndicatorLinearLayout emojiIndicatorLinearLayout_emoji;
    private BottomClassAdapter bottomClassAdapter;
    private EditText editText;
    private int maxViewWidth ;
    private EmojiAdapter emojiAdapter;

    List<Drawable> tips = new ArrayList<>();
    int maxLinex = 3;
    int maxColumns = 7 ;
    private int emojiSize = 28;
    private int indicatorPadding = 10;
    private List<Integer> listInfo = new ArrayList<>();
    private int itemIndex = 0;
    private int minItemIndex ;
    private int maxItemIndex ;
    private List<List<String>> lists;
    private int bottomOffset = 0;



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

    public void  init(){
        initView();
    }



    public void setEmojiSize(int emojiSize) {
        this.emojiSize = emojiSize;
    }

    public void setIndicatorPadding(int indicatorPadding) {
        this.indicatorPadding = indicatorPadding;
    }

    public void setMaxLines(int maxLinex){
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
                    emojiAdapter = new EmojiAdapter(context, lists , maxViewWidth , maxLinex , maxColumns ,  emojiSize);
                    //通过构建后的EmojiAdapter获取底部指示器的范围

                    listInfo = emojiAdapter.getListInfo();
                    minItemIndex = 0;
                    maxItemIndex = listInfo.get(itemIndex);

                    emojiIndicatorLinearLayout_emoji.setMaxCount(listInfo.get(itemIndex));
                    emojiIndicatorLinearLayout_emoji.setPadding(0 , 0 , 0 , ViewUtils.dip2px(context , indicatorPadding));

                    viewpager_emojikeyboard.setAdapter(emojiAdapter);

                    initBottomClass();
                    initViewPageChangeListener();
                    //设置emoji点击的回调
                    initEmojiOnClick();
                }

                return true;
            }
        });
    }

    private void initBottomClass() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleview_emoji_class.setLayoutManager(linearLayoutManager);

        if (tips.size() != 0 && listInfo.size() <  tips.size()) {
            tips = tips.subList(0, listInfo.size());
        }

        bottomClassAdapter = new BottomClassAdapter(context , tips , listInfo.size());
        bottomClassAdapter.setItemOnClick(new BottomClassAdapter.ItemOnClick() {
            @Override
            public void itemOnClick(int position) {
                clickChangeBottomClass(position);
            }
        });
        recycleview_emoji_class.setAdapter(bottomClassAdapter);
        recycleview_emoji_class.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                bottomOffset += dx;
            }
        });
    }

    private void initViewPageChangeListener() {
        viewpager_emojikeyboard.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                touchChangeBottomClass(position);
                //更新底部指示器
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void clickChangeBottomClass(int clickItemIndex){
        itemIndex = clickItemIndex;
        maxItemIndex = 0;
        minItemIndex = 0;
        for (int i = 0 ; i <= itemIndex ; i++){
            maxItemIndex += listInfo.get(i);
        }

        for (int i = 0 ; i < itemIndex ; i++){
            minItemIndex += listInfo.get(i);
        }


        viewpager_emojikeyboard.setCurrentItem(minItemIndex);
        changeBottomClassIcon();


        emojiIndicatorLinearLayout_emoji.setMaxCount(listInfo.get(itemIndex));
        emojiIndicatorLinearLayout_emoji.setChoose(0);
    }

    private void touchChangeBottomClass(int position) {
        if (position >= maxItemIndex ){
            itemIndex ++;

            maxItemIndex = 0;
            minItemIndex = 0;
            for (int i = 0 ; i <= itemIndex ; i++){
                maxItemIndex += listInfo.get(i);
            }

            for (int i = 0 ; i < itemIndex ; i++){
                minItemIndex += listInfo.get(i);
            }

            emojiIndicatorLinearLayout_emoji.setMaxCount(listInfo.get(itemIndex));
            emojiIndicatorLinearLayout_emoji.setChoose(0);
        }else if (position < minItemIndex){
            itemIndex --;

            maxItemIndex = 0;
            minItemIndex = 0;
            for (int i = 0 ; i <= itemIndex ; i++){
                maxItemIndex += listInfo.get(i);
            }

            for (int i = 0 ; i < itemIndex ; i++){
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
//        if (lastItemChoose == itemIndex){
//            return;
//        }
//        lastItemChoose = itemIndex;
        bottomClassAdapter.changeBottomItem(itemIndex);


        int firstItem = recycleview_emoji_class.getChildLayoutPosition(recycleview_emoji_class.getChildAt(0));
        int lastItem = recycleview_emoji_class.getChildLayoutPosition(recycleview_emoji_class.getChildAt(recycleview_emoji_class.getChildCount() - 1));


        if (itemIndex <= firstItem || itemIndex >= lastItem) {
            recycleview_emoji_class.scrollToPosition(itemIndex);
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
