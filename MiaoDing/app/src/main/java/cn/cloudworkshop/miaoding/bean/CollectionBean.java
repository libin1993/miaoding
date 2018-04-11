package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016-10-21 15:06
 * Email：1993911441@qq.com
 * Describe：
 */

public class CollectionBean {


    /**
     * code : 1
     * data : [{"id":15,"uid":264,"type":1,"cid":15,"c_time":1500449104,"status":1,"img":"/uploads/img/2017060112393454999899.jpg","link":"","title":"知道你会选表，但是会搭配吗？","sub_title":"","tags_name":"首页"},{"id":24,"uid":264,"type":1,"cid":24,"c_time":1499923283,"status":1,"img":"/uploads/img/2017071313194751489754.jpg","link":"","title":"大叔，修炼多年的皇冠","sub_title":"这个杀手不太冷","tags_name":"时尚"},{"id":14,"uid":264,"type":1,"cid":14,"c_time":1499910083,"status":1,"img":"/uploads/img/2017053120083753975199.png","link":"","title":"这样简单，地球男人都想和你撞衫！","sub_title":"","tags_name":"首页"},{"id":16,"uid":264,"type":1,"cid":16,"c_time":1498541849,"status":1,"img":"/uploads/img/2017060116300157995748.jpg","link":"","title":"这个特工很时尚","sub_title":"","tags_name":"首页"},{"id":13,"uid":264,"type":1,"cid":13,"c_time":1495903483,"status":1,"img":"/uploads/img/2017051614155598102505.jpg","link":"","title":"就是这么帅","sub_title":"","tags_name":"首页"}]
     * msg : 成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 15
         * uid : 264
         * type : 1
         * cid : 15
         * c_time : 1500449104
         * status : 1
         * img : /uploads/img/2017060112393454999899.jpg
         * link :
         * title : 知道你会选表，但是会搭配吗？
         * sub_title :
         * tags_name : 首页
         */

        private int id;
        private int uid;
        private int type;
        private int cid;
        private String img;
        private String link;
        private String title;
        private String sub_title;
        private String tags_name;
        private String name;
        private String sub_name;
        private String thumb;
        private int goods_type;
        private String price2;
        private String factory_name;
        private String factory_img;
        private String address;
        private int lovenum;
        private String img_info;

        public String getImg_info() {
            return img_info;
        }

        public void setImg_info(String img_info) {
            this.img_info = img_info;
        }

        public String getFactory_name() {
            return factory_name;
        }

        public void setFactory_name(String factory_name) {
            this.factory_name = factory_name;
        }

        public String getFactory_img() {
            return factory_img;
        }

        public void setFactory_img(String factory_img) {
            this.factory_img = factory_img;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getLovenum() {
            return lovenum;
        }

        public void setLovenum(int lovenum) {
            this.lovenum = lovenum;
        }

        public String getPrice2() {
            return price2;
        }

        public void setPrice2(String price2) {
            this.price2 = price2;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSub_name() {
            return sub_name;
        }

        public void setSub_name(String sub_name) {
            this.sub_name = sub_name;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getTags_name() {
            return tags_name;
        }

        public void setTags_name(String tags_name) {
            this.tags_name = tags_name;
        }
    }
}
