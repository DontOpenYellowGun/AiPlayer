package com.wildeyess.player.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

/**
 * create by wildeyess
 * create on 2020-10-19
 * description
 */
public abstract class BaseFragment extends Fragment {
    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(setLayoutId(), container, false);
        ButterKnife.bind(this, rootView);

        init();
        return rootView;
    }

    protected abstract int setLayoutId();

    protected abstract void init();
}
