package com.wildeyess.player.view.fragment;

import android.os.CountDownTimer;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.wildeyess.player.R;
import com.wildeyess.player.view.adapter.GridVideoAdapter;
import com.wildeyess.player.base.BaseFragment;
import com.wildeyess.player.model.bean.DataCreate;

import butterknife.BindView;

/**
 * create by wildeyess
 * create on 2020-10-19
 * description 附近的人fragment
 */
public class CurrentLocationFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private GridVideoAdapter adapter;

    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_current_location;
    }

    @Override
    protected void init() {
        new DataCreate().initData();
        
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        adapter = new GridVideoAdapter(getActivity(), DataCreate.datas);
        recyclerView.setAdapter(adapter);



        refreshLayout.setColorSchemeResources(R.color.color_link);
        refreshLayout.setOnRefreshListener(() -> new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                refreshLayout.setRefreshing(false);
            }
        }.start());
    }

}
