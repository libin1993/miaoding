package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;

/**
 * Author：Libin on 2016/11/29 17:31
 * Email：1993911441@qq.com
 * Describe：穿衣测试结果
 */
public class DressingResultActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.web_test_result)
    WebView webView;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;

    private String title;
    private String shareTitle;
    private String shareContent;
    private String url;
    private String shareUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dressing_result);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    /**
     * 加载webview
     */
    private void initView() {
        tvHeaderTitle.setText(title);
        imgHeaderShare.setVisibility(View.VISIBLE);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setBuiltInZoomControls(false);
        ws.setSupportZoom(false);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


             // WebView http拦截
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//                LogUtils.log(url);
//                if (!url.contains("cloudworkshop.cn")) {
//                    return new WebResourceResponse(null, null, null);
//                } else {
//                    return super.shouldInterceptRequest(view, url);
//                }
//            }

        });
        webView.loadUrl(url);
    }


    private void getData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        shareTitle = intent.getStringExtra("share_title");
        shareContent = intent.getStringExtra("share_content");
        url = intent.getStringExtra("url");
        shareUrl = intent.getStringExtra("share_url");
    }


    @OnClick({R.id.img_header_back, R.id.img_header_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                break;
            case R.id.img_header_share:
                ShareUtils.showShare(this, SharedPreferencesUtils.getStr(this, "avatar"), shareTitle, shareContent, shareUrl);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
