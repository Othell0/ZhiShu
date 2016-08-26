package com.cs.zhishu.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cs.zhishu.R;
import com.cs.zhishu.adapter.CommentAdapter;
import com.cs.zhishu.base.LazyFragment;
import com.cs.zhishu.model.DailyComment;
import com.cs.zhishu.network.RetrofitHelper;
import com.cs.zhishu.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Othell0 on 7/29/2016.
 */
public class LongCommentFragment extends LazyFragment {
    private static final String EXTRA_ID = "long_comment_id";
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty)
    EmptyView mEmptyView;

    private int id;
    private List<DailyComment.CommentInfo> longCommentinfos = new ArrayList<>();

    public static LongCommentFragment newInstance(int id) {
        LongCommentFragment mLongCommentFragment = new LongCommentFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt(EXTRA_ID, id);
        mLongCommentFragment.setArguments(mBundle);

        return mLongCommentFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_long_comment;
    }

    @Override
    public void initViews() {
        Bundle bundle = getArguments();
        id = bundle.getInt(EXTRA_ID);
        getLongComment();
    }

    private void getLongComment() {
        RetrofitHelper.builder().getDailyLongCommentById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailyComment>() {
                    @Override
                    public void onCompleted() {
                        Log.e("getLongComment", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("getLongComment", "onError: ");
                        mEmptyView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(DailyComment dailyComment) {
                        List<DailyComment.CommentInfo> comments = dailyComment.comments;
                        if (comments != null && comments.size() > 0) {
                            longCommentinfos.addAll(comments);
                            finishGetLongComment();
                        } else {
                            mEmptyView.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void finishGetLongComment() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CommentAdapter mAdapter = new CommentAdapter(longCommentinfos);
        mRecyclerView.setAdapter(mAdapter);
    }


}
