package cn.cloudworkshop.miaoding.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author：Libin on 2017-05-15 09:41
 * Email：1993911441@qq.com
 * Describe：
 */
public class CustomItemBean implements Serializable {
    private String id;
    private String shop_id;
    private String market_id;
    private String goods_name;
    private String img_url;
    private String price;
    private String price_type;
    private String spec_ids;
    private String spec_content;

    public int getClassify_id() {
        return classify_id;
    }

    public void setClassify_id(int classify_id) {
        this.classify_id = classify_id;
    }

    private int classify_id;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getMarket_id() {
        return market_id;
    }

    public void setMarket_id(String market_id) {
        this.market_id = market_id;
    }

    private String diy_contet;
    private String log_id;
    private long goods_time;
    private long dingzhi_time;
    private List<ItemBean> itemBean;
    private String banxing_id;
    private String default_img;
    private int is_scan;

    public String getDiy_contet() {
        return diy_contet;
    }

    public void setDiy_contet(String diy_contet) {
        this.diy_contet = diy_contet;
    }

    public int getIs_scan() {
        return is_scan;
    }

    public void setIs_scan(int is_scan) {
        this.is_scan = is_scan;
    }

    public String getDefault_img() {
        return default_img;
    }

    public void setDefault_img(String default_img) {
        this.default_img = default_img;
    }

    public String getBanxing_id() {
        return banxing_id;
    }

    public void setBanxing_id(String banxing_id) {
        this.banxing_id = banxing_id;
    }

    private String fabric_id;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public long getGoods_time() {
        return goods_time;
    }

    public void setGoods_time(long goods_time) {
        this.goods_time = goods_time;
    }

    public long getDingzhi_time() {
        return dingzhi_time;
    }

    public void setDingzhi_time(long dingzhi_time) {
        this.dingzhi_time = dingzhi_time;
    }


    public String getFabric_id() {
        return fabric_id;
    }

    public void setFabric_id(String fabric_id) {
        this.fabric_id = fabric_id;
    }

    public String getSpec_ids() {
        return spec_ids;
    }

    public void setSpec_ids(String spec_ids) {
        this.spec_ids = spec_ids;
    }

    public String getSpec_content() {
        return spec_content;
    }

    public void setSpec_content(String spec_content) {
        this.spec_content = spec_content;
    }


    public String getPrice_type() {
        return price_type;
    }

    public void setPrice_type(String price_type) {
        this.price_type = price_type;
    }

    public String getId() {
        return id;
    }

    public CustomItemBean() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public List<ItemBean> getItemBean() {
        return itemBean;
    }

    public void setItemBean(List<ItemBean> itemBean) {
        this.itemBean = itemBean;
    }


    public static class ItemBean implements Serializable {
        private String img;
        private int position_id;


        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getPosition_id() {
            return position_id;
        }

        public void setPosition_id(int position_id) {
            this.position_id = position_id;
        }


    }


}
