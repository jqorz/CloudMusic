package com.asus.cloudmusic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.cloudmusic.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2018/1/21.
 */

public class ExpendedListAdapter extends BaseExpandableListAdapter{
    private Map<String,List<String>> data=new HashMap<>();
    private List<String> childrenList = new ArrayList<>();
    private String[] parentList = new String[]{"first"};
    private Context context;


    public ExpendedListAdapter(Context context){
        initData();
        this.context=context;
    }

    private void initData() {
        childrenList.add(parentList[0] + "-" + "first");
        childrenList.add(parentList[0] + "-" + "second");
        childrenList.add(parentList[0] + "-" + "third");
        childrenList.add(parentList[0] + "-" + "first");
        childrenList.add(parentList[0] + "-" + "second");
        childrenList.add(parentList[0] + "-" + "third");
        childrenList.add(parentList[0] + "-" + "first");
        childrenList.add(parentList[0] + "-" + "second");
        childrenList.add(parentList[0] + "-" + "third");
        childrenList.add(parentList[0] + "-" + "first");
        childrenList.add(parentList[0] + "-" + "second");
        childrenList.add(parentList[0] + "-" + "third");
        data.put(parentList[0], childrenList);
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return data.size();
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(parentList[groupPosition]).size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return data.get(parentList[groupPosition]);
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(parentList[groupPosition]).get(childPosition);
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.expand_parent,null);
        }
        convertView.setTag(R.layout.expand_parent,groupPosition);
        convertView.setTag(R.layout.expand_child,-1);
        ImageView ivIndicator= (ImageView) convertView.findViewById(R.id.ep_indicator);
        ImageView ivSet= (ImageView) convertView.findViewById(R.id.ep_set);

        if (isExpanded){
            ivIndicator.setImageResource(R.drawable.open);
        }else {
            ivIndicator.setImageResource(R.drawable.close);
        }
        return convertView;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.expand_child,null);
        }
        convertView.setTag(R.layout.expand_parent,groupPosition);
        convertView.setTag(R.layout.expand_child,childPosition);
        TextView title= (TextView) convertView.findViewById(R.id.ep_title);
        title.setText(data.get(parentList[groupPosition]).get(childPosition));
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "点到了内置的textview", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
