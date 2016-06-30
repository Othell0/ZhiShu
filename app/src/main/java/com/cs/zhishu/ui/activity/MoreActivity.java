package com.cs.zhishu.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;

/**
 * Created by Othell0 on 2016/6/30.
 */
public class MoreActivity extends AbsBaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_more;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }
    @Override
    public void initToolBar() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
