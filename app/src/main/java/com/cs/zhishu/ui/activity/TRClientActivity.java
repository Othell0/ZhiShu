package com.cs.zhishu.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cs.zhishu.R;
import com.cs.zhishu.adapter.TRClientAdapter;
import com.cs.zhishu.base.AbsBaseActivity;
import com.cs.zhishu.model.ChatBean;
import com.cs.zhishu.model.TREntity;
import com.cs.zhishu.network.TRApi;
import com.cs.zhishu.network.TRService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Othell0 on 2016/7/7.
 */

public class TRClientActivity extends AbsBaseActivity implements View.OnClickListener {


    public static final String TRC_KEY = "6a4d7e19d51facae66ce7115ca8df792";
    public static final String TRC_USER_ID = "Othell0";
    public static final String TRC_ROBOT_REC = "开始一起快乐的玩耍吧(*^__^*)";
    public static final String TRC_ROBOT_REST = "小知已经休息，请明天再聊。";
    public static final String TRC_ROBOT_FAILED = "暂时无法回应";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.trc_list)
    ListView trcList;
    @BindView(R.id.trc_edit)
    EditText trcEdit;
    @BindView(R.id.item_trc_line)
    View itemTrcLine;
    @BindView(R.id.trc_btn_send)
    Button trcBtnSend;
    @BindView(R.id.trc_bottom)
    RelativeLayout trcBottom;


    private TRClientAdapter trClientAdapter;
    private TRApi service;


    @Override
    public int getLayoutId() {
        return R.layout.activity_trclient;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        trClientAdapter = new TRClientAdapter(this);
        trcList.setAdapter(trClientAdapter);
        initData();

    }

    @Override
    public void initToolBar() {
        toolbar.setTitle("小知");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

    }



    public void initData() {

        service = TRService.createTRService();
        trClientAdapter.addData(new ChatBean(TRClientAdapter.TYPE_ROBOT, TRC_ROBOT_REC));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.trc_btn_send)
    public void onClick(View v) {
        if (v.getId() == R.id.trc_btn_send){

            String str = trcEdit.getText().toString();
            if (TextUtils.isEmpty(str)) {
                return;
            }
            addData(new ChatBean(TRClientAdapter.TYPE_USER, str));
            trcEdit.setText("");
            gainChat(str);
        }
    }


    private void gainChat(String str) {
        Call<TREntity> call = service.getTRResponse(TRC_KEY, str, TRC_USER_ID);
        call.enqueue(new Callback<TREntity>() {
            @Override
            public void onResponse(Call<TREntity> call, Response<TREntity> response) {
                TREntity entity = response.body();
                if (entity != null) {
                    String str;
                    if (entity.getCode() == 40004) {
                        str = TRC_ROBOT_REST;
                    } else {
                        str = entity.getText();
                    }
                    addData(new ChatBean(TRClientAdapter.TYPE_ROBOT, str));
                }
            }

            @Override
            public void onFailure(Call<TREntity> call, Throwable t) {
                addData(new ChatBean(TRClientAdapter.TYPE_ROBOT, TRC_ROBOT_FAILED));
            }
        });
    }



    private void addData(ChatBean chatBean) {
        trClientAdapter.addData(chatBean);
        trcList.setSelection(trClientAdapter.getCount());
    }
}
