package clwater.library.emojikeyboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public BottomClassAdapter(Context context) {
        tips = new ArrayList<>();
        tips.add("1");
        tips.add("2");
        tips.add("3");
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BottomClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BottomClassViewHolder(mLayoutInflater.inflate(R.layout.emoji_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(BottomClassViewHolder holder, int position) {
        holder.imagview_bottom_icon.setImageDrawable(mContext.getDrawable(R.drawable.icon_emojikeyboard_emoji));
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    public static class BottomClassViewHolder extends RecyclerView.ViewHolder {
        ImageView imagview_bottom_icon;

        BottomClassViewHolder(View view) {
            super(view);
            imagview_bottom_icon = view.findViewById(R.id.imagview_bottom_icon);
        }
    }
}