package com.asus.cloudmusic.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.asus.cloudmusic.Constant.Text;

public class SearchBar extends View {
    private Paint paint = new Paint();
    private int choose = -1;
    private OnChooseChangeListener onChooseChangeListener;

    public void setOnChooseChangeListener(OnChooseChangeListener onChooseChangeListener) {
        this.onChooseChangeListener = onChooseChangeListener;
    }

    public SearchBar(Context context) {
        this(context, null);
    }

    public SearchBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.TRANSPARENT);
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / Text.letters.length;
        for (int i = 0; i < Text.letters.length; i++) {
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);//抗锯齿
            paint.setTextSize(30);
            float x = width / 2 - paint.measureText(Text.letters[i]) / 2;
            float y = singleHeight * i + singleHeight;
            canvas.drawText(Text.letters[i], x, y, paint);
            paint.reset();

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action=event.getAction();
        float y=event.getY();
        int oldChoose=choose;
        int pos= (int) (y/getHeight()*Text.letters.length);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if(oldChoose!=pos&&onChooseChangeListener!=null){
                    if (pos>-1&&pos<Text.letters.length){
                        onChooseChangeListener.onChooseLetter(Text.letters[pos]);
                        choose=pos;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != pos && onChooseChangeListener != null) {
                    if (pos > -1 && pos < Text.letters.length) {
                        onChooseChangeListener.onChooseLetter(Text.letters[pos]);
                        choose = pos;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                choose = -1;
                if (onChooseChangeListener != null) {
                    onChooseChangeListener.onNoChooseLetter();
                }
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public interface OnChooseChangeListener {
        void onChooseLetter(String letter);
        void onNoChooseLetter();
    }

}
