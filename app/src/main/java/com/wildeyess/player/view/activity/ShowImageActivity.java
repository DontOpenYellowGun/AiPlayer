package com.wildeyess.player.view.activity;

import android.widget.ImageView;
import com.wildeyess.player.R;
import com.wildeyess.player.base.BaseActivity;
import butterknife.BindView;

public class ShowImageActivity extends BaseActivity {
    @BindView(R.id.iv_head)
    ImageView ivHead;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_show_image;
    }

    @Override
    protected void init() {
        ivHead.setOnClickListener(v -> finish());

        int headRes = getIntent().getIntExtra("res", 0);
        ivHead.setImageResource(headRes);
    }
}
