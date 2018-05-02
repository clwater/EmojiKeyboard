package clwater.library.emojikeyboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import clwater.library.R;

public class EmojiAdapter extends PagerAdapter {
    private Context context;
    private List<String> list;
    private List<List<String>> lists = new ArrayList<>();
    private EmojiTextOnClick emojiOnClick;


    private int maxIndex = 0;           //展示的页数
    int showMaxLines ;
    int showMaxColumns ;
    private int pageMaxCount = showMaxLines * showMaxColumns - 1;      //每个页面最多展示的emoji数量 此处不包括最后一个预留的删除
    private int maxViewWidth;



    public void setMaxLines(int showMaxLines) {
        this.showMaxLines = showMaxLines;
    }

    public void setMaxColumns(int showMaxColumns) {
        this.showMaxColumns = showMaxColumns;
    }

    public EmojiAdapter(Context context, List<String> list , int maxViewWidth , int showMaxLines  , int showMaxColumns) {
        this.context = context;
        this.list = list;
        this.maxViewWidth = maxViewWidth;
        this.showMaxColumns = maxViewWidth;
        this.showMaxLines = showMaxLines;
        this.showMaxColumns = showMaxColumns;
        this.pageMaxCount = showMaxLines * showMaxColumns - 1;
        initList();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 根据每个页面展示效果,序列化lists
     */
    private void initList() {
        int listSize = list.size();
        maxIndex = listSize / pageMaxCount + 1;
        for (int i = 0; i < maxIndex; i++) {
            List<String> tempList = new ArrayList<>();

            for (int j = 0; j < pageMaxCount; j++) {
                int index = i * pageMaxCount + j;
                if (index < listSize) {
                    tempList.add(list.get(index));
                } else {
                    j = pageMaxCount;
                }
            }
            lists.add(tempList);
        }

    }

    @Override
    public int getCount() {
        return maxIndex;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setEmojiOnClick(EmojiTextOnClick emojiOnClick) {
        this.emojiOnClick = emojiOnClick;
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        List<String> list = lists.get(position);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setPadding(ViewUtils.dip2px(context, 2), ViewUtils.dip2px(context, 0),
                ViewUtils.dip2px(context, 2), ViewUtils.dip2px(context, 18));
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        //分成三行展示
        for (int index = 0; index < showMaxLines; index++) {
            LinearLayout linearLayoutIndex = new LinearLayout(context);
            linearLayout.addView(linearLayoutIndex);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayoutIndex.getLayoutParams();
            layoutParams.weight = 1;

            linearLayoutIndex.setOrientation(LinearLayout.HORIZONTAL);
            linearLayoutIndex.setGravity(Gravity.CENTER);

            for (int i = index * showMaxColumns; i < (index + 1) * showMaxColumns; i++) {
                 CustomTextView textView = new CustomTextView(context);
                textView.setBackground(context.getDrawable(R.drawable.user_select_bg));
                linearLayoutIndex.addView(textView);
                textView.setTextSize(28);
                textView.getLayoutParams().width = maxViewWidth / showMaxColumns;
                textView.setGravity(Gravity.CENTER);

                //即使没有内容,也要将空view填充占位,防止最后的删除按钮错位
                if (i < list.size()) {
                    textView.setText(list.get(i));
                    final String finaltext = list.get(i);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            emojiOnClick.OnClick(finaltext);
                        }
                    });
                } else {
                    if (i == pageMaxCount) {
                        linearLayoutIndex.removeView(textView);
                        LinearLayout backLinearLayout = new LinearLayout(context);
                        linearLayoutIndex.addView(backLinearLayout);

                        backLinearLayout.setBackground(context.getDrawable(R.drawable.user_select_bg));
                        backLinearLayout.getLayoutParams().width = maxViewWidth / showMaxColumns;
                        backLinearLayout.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;


                        backLinearLayout.setGravity(Gravity.CENTER);


                        TextView backTextView = new TextView(context);
                        backLinearLayout.addView(backTextView);

                        LinearLayout.LayoutParams textViewLayout = (LinearLayout.LayoutParams) backTextView.getLayoutParams();
                        textViewLayout.width = ViewUtils.dip2px(context, 28);
                        textViewLayout.height = ViewUtils.dip2px(context, 23);
                        backTextView.setBackground(context.getResources().getDrawable(R.drawable.icon_emoji_delete));

                        backLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                emojiOnClick.OnClick("-1");
                            }
                        });
                    }
                }
            }

        }


        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(linearLayout);

        return linearLayout;
    }

    /**
     * emoji点击回调按钮
     */
    interface EmojiTextOnClick {
        void OnClick(String text);
    }
}