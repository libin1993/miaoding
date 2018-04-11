package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017-06-20 16:39
 * Email：1993911441@qq.com
 * Describe：
 */
public class DesignerWorksBean {


    /**
     * code : 1
     * data : {"total":1,"per_page":10,"current_page":1,"data":[{"id":"","name":"从你的全世界路过","des_uid":79,"title":"从你的全世界路过","content":"/uploads/img/2017061316102310210110.jpg","c_time":1494899535,"status":2,"recommend_goods_ids":"49","img":"/uploads/img/2017061318374254525399.jpg","p_time":"2017-05-19","sort":1,"uid":75,"thumb":"/uploads/img/2017061410583910210197.jpg","goods_name":"小二西服","goods_id":"49","tag":"","username":"","avatar":"","remark":"","introduce":""}]}
     * msg : 成功
     */

    private int code;
    private DataBeanX data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBeanX {
        /**
         * total : 1
         * per_page : 10
         * current_page : 1
         * data : [{"id":"","name":"从你的全世界路过","des_uid":79,"title":"从你的全世界路过","content":"/uploads/img/2017061316102310210110.jpg","c_time":1494899535,"status":2,"recommend_goods_ids":"49","img":"/uploads/img/2017061318374254525399.jpg","p_time":"2017-05-19","sort":1,"uid":75,"thumb":"/uploads/img/2017061410583910210197.jpg","goods_name":"小二西服","goods_id":"49","tag":"","username":"","avatar":"","remark":"","introduce":""}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id :
             * name : 从你的全世界路过
             * des_uid : 79
             * title : 从你的全世界路过
             * content : /uploads/img/2017061316102310210110.jpg
             * c_time : 1494899535
             * status : 2
             * recommend_goods_ids : 49
             * img : /uploads/img/2017061318374254525399.jpg
             * p_time : 2017-05-19
             * sort : 1
             * uid : 75
             * thumb : /uploads/img/2017061410583910210197.jpg
             * goods_name : 小二西服
             * goods_id : 49
             * tag :
             * username :
             * avatar :
             * remark :
             * introduce :
             */

            private String id;
            private String name;
            private int des_uid;
            private String title;
            private String content;
            private int c_time;
            private int status;
            private String recommend_goods_ids;
            private String img;
            private String p_time;
            private int sort;
            private int uid;
            private String thumb;
            private String goods_name;
            private String goods_id;
            private String tag;
            private String username;
            private String avatar;
            private String remark;
            private String introduce;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getDes_uid() {
                return des_uid;
            }

            public void setDes_uid(int des_uid) {
                this.des_uid = des_uid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getC_time() {
                return c_time;
            }

            public void setC_time(int c_time) {
                this.c_time = c_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getRecommend_goods_ids() {
                return recommend_goods_ids;
            }

            public void setRecommend_goods_ids(String recommend_goods_ids) {
                this.recommend_goods_ids = recommend_goods_ids;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getP_time() {
                return p_time;
            }

            public void setP_time(String p_time) {
                this.p_time = p_time;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }
        }
    }
}
