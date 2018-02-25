package com.asus.cloudmusic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asus.cloudmusic.Constant.Text;
import com.asus.cloudmusic.R;
import com.asus.cloudmusic.ViewHolder.GuideItemView;

/**
 * Created by asus on 2018/1/21.
 */

public class GuideRvAdapter extends RecyclerView.Adapter<GuideItemView> {
    private GuideRvAdapter.ItemClickListener itemClickListener;
    private Context context;
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public GuideRvAdapter(Context context){
        this.context=context;
    }

    @Override
    public GuideItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        GuideItemView view= new GuideItemView(LayoutInflater.from(context).inflate(R.layout.guide_child,parent,false));
        return view;
    }

    @Override
    public void onBindViewHolder(GuideItemView holder, int position) {
        final int pos=holder.getAdapterPosition();
        holder.tvTitle.setText(Text.guideTitles[pos]);
        if (itemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(pos);
                }
            });
        }
        holder.tvTitle.setText(Text.guideTitles[pos]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public interface ItemClickListener{
        void onItemClick(int pos);
    }
}
