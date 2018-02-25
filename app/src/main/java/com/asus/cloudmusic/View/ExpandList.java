package com.asus.cloudmusic.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;

/**
 * Created by asus on 2018/1/20.
 */

public class ExpandList extends ExpandableListView {

    public ExpandList(Context context) {
        super(context);
    }

    public ExpandList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
