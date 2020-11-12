package com.wildeyess.player.view.fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wildeyess.player.R;
import com.wildeyess.player.view.adapter.WorkAdapter;
import com.wildeyess.player.base.BaseFragment;
import com.wildeyess.player.model.bean.DataCreate;
import butterknife.BindView;

/**
 * create by wildeyess
 * create on 2020-10-19
 * description 个人作品fragment
 */
public class WorkFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private WorkAdapter workAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_work;
    }

    @Override
    protected void init() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        workAdapter = new WorkAdapter(getActivity(), DataCreate.datas);
        recyclerView.setAdapter(workAdapter);
    }

}
