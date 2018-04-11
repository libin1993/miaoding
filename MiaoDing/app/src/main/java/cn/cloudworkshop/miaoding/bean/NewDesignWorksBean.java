package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016-10-17 15:54
 * Email：1993911441@qq.com
 * Describe：
 */

public class NewDesignWorksBean {


    /**
     * code : 1
     * data : {"total":2,"per_page":10,"current_page":1,"data":[{"id":15,"name":"博物馆","des_uid":282,"title":null,"content":"/uploads/img/2017090708315155555253.png","c_time":1504773151,"status":2,"recommend_goods_ids":"49","img":"/uploads/img/2017090708533310057565.png","p_time":"1504773156","sort":1},{"id":8,"name":"","des_uid":282,"title":"从你的全世界路过","content":"/uploads/img/2017073117561510210154.png","c_time":1494899535,"status":2,"recommend_goods_ids":"","img":"/uploads/img/2017073117561951102505.png","p_time":"1495868320","sort":1}]}
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
         * total : 2
         * per_page : 10
         * current_page : 1
         * data : [{"id":15,"name":"博物馆","des_uid":282,"title":null,"content":"/uploads/img/2017090708315155555253.png","c_time":1504773151,"status":2,"recommend_goods_ids":"49","img":"/uploads/img/2017090708533310057565.png","p_time":"1504773156","sort":1},{"id":8,"name":"","des_uid":282,"title":"从你的全世界路过","content":"/uploads/img/2017073117561510210154.png","c_time":1494899535,"status":2,"recommend_goods_ids":"","img":"/uploads/img/2017073117561951102505.png","p_time":"1495868320","sort":1}]
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
             * id : 15
             * name : 博物馆
             * des_uid : 282
             * title : null
             * content : /uploads/img/2017090708315155555253.png
             * c_time : 1504773151
             * status : 2
             * recommend_goods_ids : 49
             * img : /uploads/img/2017090708533310057565.png
             * p_time : 1504773156
             * sort : 1
             */

            private int id;
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
            private String c_time_format;
            private String avatar;
            private String uname;
            private String tag;
            private String introduce;
            private int is_love;
            private int is_collect;
            private int love_num;
            private int commentnum;
            private String goods_id;
            private String img_info;

            public String getImg_info() {
                return img_info;
            }

            public void setImg_info(String img_info) {
                this.img_info = img_info;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public int getIs_love() {
                return is_love;
            }

            public void setIs_love(int is_love) {
                this.is_love = is_love;
            }

            public int getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(int is_collect) {
                this.is_collect = is_collect;
            }

            public int getLove_num() {
                return love_num;
            }

            public void setLove_num(int love_num) {
                this.love_num = love_num;
            }

            public int getCommentnum() {
                return commentnum;
            }

            public void setCommentnum(int commentnum) {
                this.commentnum = commentnum;
            }

            public String getC_time_format() {
                return c_time_format;
            }

            public void setC_time_format(String c_time_format) {
                this.c_time_format = c_time_format;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
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
        }
    }
}
