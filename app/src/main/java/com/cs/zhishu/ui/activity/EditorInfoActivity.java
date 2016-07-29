package com.cs.zhishu.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import butterknife.BindView;

/**
 * Created by Othell0 on 2016/7/28.
 */
public class EditorInfoActivity extends AbsBaseActivity {

    private static final String EXTRA_ID = "extra_id";

    private static final String EXTRA_NAME = "extra_name";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.editor_web)
    WebView mWebView;
    @BindView(R.id.circle_progress)
    CircularProgressView mCircleProgressView;

    private int id;

    private String name;

    WebViewClientBase webViewClient = new WebViewClientBase();

    private String url;

    @Override
    public int getLayoutId() {

        return R.layout.activity_editor_info;
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(name);
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra(EXTRA_ID, -1);
            name = intent.getStringExtra(EXTRA_NAME);
        }

        url = "http://news-at.zhihu.com/api/4/editor/" + id + "/profile-page/android";
        showProgress();
        setupWebView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupWebView() {

        final WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setBlockNetworkImage(true);
        mWebView.setWebViewClient(webViewClient);
        mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

                AlertDialog.Builder b2 = new AlertDialog.Builder(EditorInfoActivity.this)
                        .setTitle(R.string.app_name)
                        .setMessage(message)
                        .setPositiveButton("确定", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                result.confirm();
                            }
                        });

                b2.setCancelable(false);
                b2.create();
                b2.show();
                return true;
            }
        });
        mWebView.loadUrl(url);

    }

    public static void luancher(Activity activity, int id, String name) {
        Intent mIntent = new Intent(activity, EditorInfoActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, id);
        mIntent.putExtra(EXTRA_NAME, name);
        activity.startActivity(mIntent);
    }

    public class WebViewClientBase extends WebViewClient {


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            hideProgress();
            mWebView.getSettings().setBlockNetworkImage(false);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);
            String errorHtml = "<html><body><h2>找不到网页</h2></body><ml>";
            view.loadDataWithBaseURL(null, errorHtml, "textml", "UTF-8", null);
        }

    }

    private void showProgress() {
        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.startAnimation();

    }

    private void hideProgress() {
        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopAnimation();

    }
}
