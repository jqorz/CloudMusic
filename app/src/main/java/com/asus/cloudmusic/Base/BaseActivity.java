package com.asus.cloudmusic.Base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

public abstract class BaseActivity  extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initUtil();
        initData();
        initView();
        initEvent();
    }

    protected abstract void initEvent();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initUtil();
    protected abstract int getLayoutResId();
}
