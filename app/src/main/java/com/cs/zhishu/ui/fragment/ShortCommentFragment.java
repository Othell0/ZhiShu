package com.cs.zhishu.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cs.zhishu.R;
import com.cs.zhishu.adapter.CommentAdapter;
import com.cs.zhishu.base.LazyFragment;
import com.cs.zhishu.model.DailyComment;
import com.cs.zhishu.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Othell0 on 7/29/2016.
 */
public class ShortCommentFragment extends LazyFragment {
    private static final String EXTRA_ID = "short_comment_id";
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    private int id;

    private List<DailyComment.CommentInfo> shortCommentInfos = new ArrayList<>();

    public static ShortCommentFragment newInstance(int id) {
        ShortCommentFragment mShortCommentFragment = new ShortCommentFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt(EXTRA_ID, id);
        mShortCommentFragment.setArguments(mBundle);

        return mShortCommentFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_short_comment;
    }

    @Override
    public void initViews() {

        Bundle bundle = getArguments();
        id = bundle.getInt(EXTRA_ID);

        getShortComment();

    }

    private void getShortComment() {
        RetrofitHelper.builder().getDailyShortCommentById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailyComment>() {
                    @Override
                    public void onCompleted() {
                        Log.e("getShortComment", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("getShortComment", "onError: ");

                    }

                    @Override
                    public void onNext(DailyComment dailyComment) {
                        List<DailyComment.CommentInfo> comments = dailyComment.comments;
                        if (comments != null && comments.size() > 0)
                        {
                            shortCommentInfos.addAll(comments);
                            finishGetShortComment();
                        }

                    }
                });
    }

    private void finishGetShortComment() {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CommentAdapter mAdapter = new CommentAdapter( shortCommentInfos);
        mRecyclerView.setAdapter(mAdapter);

    }


}
