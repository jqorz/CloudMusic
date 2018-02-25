package com.asus.cloudmusic.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment{
    private Context context;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(getLayoutResId(),container,false);
        context=view.getContext();
        initUtil();
        initData();
        initView();
        initEvent();
        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public Context getFragContext() {
        return context;
    }

    protected abstract void initEvent();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initUtil();
    protected abstract int getLayoutResId();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
