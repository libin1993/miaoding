package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.application.MyApplication;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.constant.Constant;
import cn.cloudworkshop.miaoding.utils.DateUtils;
import cn.cloudworkshop.miaoding.utils.ShareUtils;
import cn.cloudworkshop.miaoding.utils.SharedPreferencesUtils;
import okhttp3.Call;

/**
 * Author：Libin on 2016/10/12 09:59
 * Email：1993911441@qq.com
 * Describe：主界面详情页
 */

public class HomepageInfoActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.img_header_share)
    ImageView imgHeaderShare;

    //webView url
    private String url;
    //商品图片
    private String imgUrl;
    //标题
    private String title;
    //内容
    private String content;
    //分享 url
    private String shareUrl;
    //首页进入时间
    private long enterTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_info);
        ButterKnife.bind(this);
        enterTime = DateUtils.getCurrentTime();
        initData();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }

    /**
     * 商品参数
     */
    private void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        imgUrl = intent.getStringExtra("img_url");
        shareUrl = intent.getStringExtra("share_url");

        initView();
    }

    /**
     * 加载webView
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
        webView.addJavascriptInterface(this, "nativeMethod");

        webView.loadUrl(url + "&token=" + SharedPreferencesUtils.getStr(this, "token"));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }


    /**
     * @param str 跳转登录
     */
    @JavascriptInterface
    public void toActivity(String str) {

        if (!TextUtils.isEmpty(str)) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("page_name", str);
            startActivity(intent);
        }
    }

    /**
     * @param str 跳转商品
     */
    @JavascriptInterface
    public void toGoods(String str) {

        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(",");
            Intent intent;
            //1位定制商品，2为成品
            if (split[1].equals("1")) {
                intent = new Intent(this, CustomizedGoodsActivity.class);
            } else {
                intent = new Intent(this, WorksDetailActivity.class);
            }
            intent.putExtra("id", split[0]);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();// 返回前一个页面
            } else {
                homepageLog();
                MyApplication.homeEnterTime = DateUtils.getCurrentTime();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @OnClick({R.id.img_header_back, R.id.img_header_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    homepageLog();
                    MyApplication.homeEnterTime = DateUtils.getCurrentTime();
                    finish();
                }
                break;
            case R.id.img_header_share:
                ShareUtils.showShare(this, imgUrl, title, content, shareUrl);
                break;
        }
    }

    /**
     * 首页跟踪
     */
    private void homepageLog() {
        long time = DateUtils.getCurrentTime() - enterTime;
        OkHttpUtils.post()
                .url(Constant.HOMEPAGE_LOG)
                .addParams("token", SharedPreferencesUtils.getStr(this, "token"))
                .addParams("time", time + "")
                .addParams("p_module_name", "首页详情")
                .addParams("module_name", "首页")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                    }
                });

    }


}
