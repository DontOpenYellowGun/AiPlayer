package com.wildeyess.player.view.fragment;

import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.wildeyess.player.R;
import com.wildeyess.player.view.activity.MainActivity;
import com.wildeyess.player.view.activity.PlayListActivity;
import com.wildeyess.player.view.adapter.VideoAdapter;
import com.wildeyess.player.base.BaseFragment;
import com.wildeyess.player.model.bean.CurUserBean;
import com.wildeyess.player.model.bean.DataCreate;
import com.wildeyess.player.model.bean.MainPageChangeEvent;
import com.wildeyess.player.model.bean.PauseVideoEvent;
import com.wildeyess.player.utils.OnVideoControllerListener;
import com.wildeyess.player.utils.RxBus;
import com.wildeyess.player.view.CommentDialog;
import com.wildeyess.player.view.ControllerView;
import com.wildeyess.player.view.FullScreenVideoView;
import com.wildeyess.player.view.LikeView;
import com.wildeyess.player.view.ShareDialog;
import com.wildeyess.player.view.viewpagerlayoutmanager.OnViewPagerListener;
import com.wildeyess.player.view.viewpagerlayoutmanager.ViewPagerLayoutManager;
import butterknife.BindView;
import rx.functions.Action1;

/**
 * create by wildeyess
 * create on 2020-10-19
 * description 推荐播放页
 */
public class RecommendFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private VideoAdapter adapter;
    private ViewPagerLayoutManager viewPagerLayoutManager;
    /** 当前播放视频位置 */
    private int curPlayPos = -1;
    private FullScreenVideoView videoView;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshLayout;
    private ImageView ivCurCover;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void init() {

        adapter = new VideoAdapter(getActivity(), DataCreate.datas);
        recyclerView.setAdapter(adapter);

        videoView = new FullScreenVideoView(getActivity());

        setViewPagerLayoutManager();

        setRefreshEvent();

        //监听播放或暂停事件
        RxBus.getDefault().toObservable(PauseVideoEvent.class)
            .subscribe((Action1<PauseVideoEvent>) event -> {
                if (event.isPlayOrPause()) {
                    videoView.start();
                } else {
                    videoView.pause();
                }
            });

    }

    @Override
    public void onResume() {
        super.onResume();

        //返回时，推荐页面可见，则继续播放视频
        if (MainActivity.curMainPage == 0 && MainFragment.curPage == 1) {
            videoView.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        videoView.pause();
    }

    @Override
    public void onStop() {
        super.onStop();

        videoView.stopPlayback();
    }

    private void setViewPagerLayoutManager() {
        viewPagerLayoutManager = new ViewPagerLayoutManager(getActivity());
        recyclerView.setLayoutManager(viewPagerLayoutManager);
        recyclerView.scrollToPosition(PlayListActivity.initPos);

        viewPagerLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                playCurVideo(PlayListActivity.initPos);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                if (ivCurCover != null) {
                    ivCurCover.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                playCurVideo(position);
            }
        });
    }

    private void setRefreshEvent() {
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

    private void playCurVideo(int position) {
        if (position == curPlayPos) {
            return;
        }

        View itemView = viewPagerLayoutManager.findViewByPosition(position);
        if (itemView == null) {
            return;
        }

        ViewGroup rootView = itemView.findViewById(R.id.rl_container);
        LikeView likeView = rootView.findViewById(R.id.likeview);
        ControllerView controllerView = rootView.findViewById(R.id.controller);
        ImageView ivPlay = rootView.findViewById(R.id.iv_play);
        ImageView ivCover = rootView.findViewById(R.id.iv_cover);
        ivPlay.setAlpha(0.4f);

        //播放暂停事件
        likeView.setOnPlayPauseListener(() -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                ivPlay.setVisibility(View.VISIBLE);
            } else {
                videoView.start();
                ivPlay.setVisibility(View.GONE);
            }
        });

        //评论点赞事件
        likeShareEvent(controllerView);

        //切换播放视频的作者主页数据
        RxBus.getDefault().post(new CurUserBean(DataCreate.datas.get(position).getUserBean()));

        curPlayPos = position;

        //切换播放器位置
        dettachParentView(rootView);

        autoPlayVideo(curPlayPos, ivCover);
    }

    /**
     * 移除videoview父view
     */
    private void dettachParentView(ViewGroup rootView) {
        //1.添加videoview到当前需要播放的item中,添加进item之前，保证ijkVideoView没有父view
        ViewGroup parent = (ViewGroup) videoView.getParent();
        if (parent != null) {
            parent.removeView(videoView);
        }
        rootView.addView(videoView, 0);
    }

    /**
     * 自动播放视频
     */
    private void autoPlayVideo(int position, ImageView ivCover) {
        String bgVideoPath = "android.resource://" + getActivity().getPackageName() + "/" + DataCreate.datas.get(position).getVideoRes();
        videoView.setVideoPath(bgVideoPath);
        videoView.start();
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);

            //延迟取消封面，避免加载视频黑屏
            new CountDownTimer(200, 200) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    ivCover.setVisibility(View.GONE);
                    ivCurCover = ivCover;
                }
            }.start();
        });
    }

    /**
     * 用户操作事件
     */
    private void likeShareEvent(ControllerView controllerView) {
        controllerView.setListener(new OnVideoControllerListener() {
            @Override
            public void onHeadClick() {
                RxBus.getDefault().post(new MainPageChangeEvent(1));
            }

            @Override
            public void onLikeClick() {

            }

            @Override
            public void onCommentClick() {
                CommentDialog commentDialog = new CommentDialog();
                commentDialog.show(getChildFragmentManager(), "");
            }

            @Override
            public void onShareClick() {
                new ShareDialog().show(getChildFragmentManager(), "");
            }
        });
    }

}
