package com.wildeyess.player.view.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wildeyess.player.R;
import com.wildeyess.player.view.adapter.FansAdapter;
import com.wildeyess.player.base.BaseFragment;
import com.wildeyess.player.model.bean.DataCreate;
import butterknife.BindView;

public class FansFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private FansAdapter fansAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_fans;
    }

    @Override
    protected void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fansAdapter = new FansAdapter(getContext(), DataCreate.userList);
        recyclerView.setAdapter(fansAdapter);
    }

}
