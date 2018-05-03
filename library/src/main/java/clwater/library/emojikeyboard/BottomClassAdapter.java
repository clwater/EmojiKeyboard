package clwater.library.emojikeyboard;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import clwater.library.R;

/**
 * Created by yszsyf on 2018/5/2.
 */


public class BottomClassAdapter extends RecyclerView.Adapter<BottomClassAdapter.BottomClassViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<String> tips;
    private ItemOnClick itemOnClick;
    private int itemIndex = 0;

    public BottomClassAdapter(Context context) {
        tips = new ArrayList<>();
        tips.add("1");
        tips.add("2");
        tips.add("3");
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setItemOnClick(ItemOnClick itemOnClick){
        this.itemOnClick = itemOnClick;
    }

    @Override
    public BottomClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BottomClassViewHolder(mLayoutInflater.inflate(R.layout.emoji_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(BottomClassViewHolder holder, int position) {
        if (itemIndex == position){
            holder.relative_bottom_bg.setBackgroundColor(Color.parseColor("#f9f9f9"));
        }else {
            holder.relative_bottom_bg.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.imagview_bottom_icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_emojikeyboard_emoji));
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    public void  changeBottomItem(int position){
        itemIndex = position;
        notifyDataSetChanged();
    }

    public  class BottomClassViewHolder extends RecyclerView.ViewHolder {
        ImageView imagview_bottom_icon;
        RelativeLayout relative_bottom_bg;

        BottomClassViewHolder(View view) {
            super(view);
            imagview_bottom_icon = view.findViewById(R.id.imagview_bottom_icon);
            relative_bottom_bg = view.findViewById(R.id.relative_bottom_bg);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemOnClick.itemOnClick(getAdapterPosition());
                }
            });
        }
    }

    public interface  ItemOnClick{
        public void itemOnClick(int position);
    }
}