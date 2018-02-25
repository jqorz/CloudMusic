package com.asus.cloudmusic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asus.cloudmusic.Bean.LocalSong;
import com.asus.cloudmusic.R;
import com.asus.cloudmusic.ViewHolder.MusicListItemView;

import java.util.List;

/**
 * Created by asus on 2018/1/23.
 */

public class LocalMusicAdapter extends RecyclerView.Adapter<MusicListItemView> {
    private List<LocalSong> localSongs;
    private Context context;
    private ItemClickListener itemClickListener;

    public LocalMusicAdapter(List<LocalSong> data, Context context) {
        localSongs = data;
        this.context = context;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MusicListItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MusicListItemView(LayoutInflater.from(context).inflate(R.layout.musiclist_child,parent,false));
    }

    @Override
    public void onBindViewHolder(MusicListItemView holder, int position) {
        final int pos=holder.getAdapterPosition();
        holder.title.setText(localSongs.get(pos).getTitle());
        holder.album.setText(localSongs.get(pos).getAlbum());
        if(itemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(pos);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return localSongs.size();
    }

    public  interface ItemClickListener{
        void onItemClick(int pos);
    }

}
