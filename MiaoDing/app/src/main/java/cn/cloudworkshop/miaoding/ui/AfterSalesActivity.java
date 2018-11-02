package cn.cloudworkshop.miaoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.cloudworkshop.miaoding.R;
import cn.cloudworkshop.miaoding.base.BaseActivity;
import cn.cloudworkshop.miaoding.utils.ContactService;

/**
 * Author：Libin on 2016/12/22 13:43
 * Email：1993911441@qq.com
 * Describe：售后
 */
public class AfterSalesActivity extends BaseActivity {
    @BindView(R.id.img_header_back)
    ImageView imgHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.ll_apply_change)
    LinearLayout llApplyChange;
    @BindView(R.id.ll_sale_progress)
    LinearLayout llSaleProgress;
    @BindView(R.id.ll_sale_consult)
    LinearLayout llSaleConsult;

    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sales);
        ButterKnife.bind(this);
        getData();
        initView();

    }

    private void getData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("order_id");
    }

    private void initView() {
        tvHeaderTitle.setText(R.string.after_sale);
    }

    @OnClick({R.id.img_header_back, R.id.ll_apply_change, R.id.ll_sale_progress, R.id.ll_sale_consult})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_header_back:
                finish();
                break;
            case R.id.ll_apply_change:
                Intent intent = new Intent(this, ChangeOrderActivity.class);
                intent.putExtra("order_id", orderId);
                startActivity(intent);
                break;
            case R.id.ll_sale_progress:
                break;
            case R.id.ll_sale_consult:
                ContactService.contactService(this);
                break;
        }
    }
}




