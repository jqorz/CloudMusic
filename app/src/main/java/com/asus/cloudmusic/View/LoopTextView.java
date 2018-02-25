package com.asus.cloudmusic.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by asus on 2018/1/23.
 */

public class LoopTextView extends TextView {
    public LoopTextView(Context context) {
        super(context);
    }

    public LoopTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoopTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
