package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017-04-26 11:13
 * Email：1993911441@qq.com
 * Describe：
 */
public class HomeClassifyBean {


    /**
     * code : 1
     * data : {"total":1,"per_page":5,"current_page":1,"data":[{"id":10,"sub_title":"黄新芸到此一游~","title":"男士衬衫如何穿 光是扣子就有大学问","tags_id":8,"img":"","uid":81,"content":"","recommend_goods_ids":"","c_time":1482835651,"status":2,"content":8,"p_time":"2016-12-28","sort":1,"img_list":"","link":null,"view_nums":0,"like_nums":33,"head_img":""}]}
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
         * per_page : 5
         * current_page : 1
         * data : [{"id":10,"sub_title":"黄新芸到此一游~","title":"男士衬衫如何穿 光是扣子就有大学问","tags_id":8,"img":"","uid":81,"content":"","recommend_goods_ids":"","c_time":1482835651,"status":2,"content":8,"p_time":"2016-12-28","sort":1,"img_list":"","link":null,"view_nums":0,"like_nums":33,"head_img":""}]
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
             * id : 10
             * sub_title : 黄新芸到此一游~
             * title : 男士衬衫如何穿 光是扣子就有大学问
             * tags_id : 8
             * img :
             * uid : 81
             * content :
             * recommend_goods_ids :
             * c_time : 1482835651
             * status : 2
             * content : 8
             * p_time : 2016-12-28
             * sort : 1
             * img_list :
             * link : null
             * view_nums : 0
             * like_nums : 33
             * head_img :
             */

            private int id;
            private String sub_title;
            private String title;
            private int tags_id;
            private String img;
            private int uid;
            private String content;
            private String recommend_goods_ids;
            private int c_time;
            private int status;
            private int type;
            private String p_time;
            private int sort;
            private String img_list;
            private Object link;
            private int view_nums;
            private int like_nums;
            private String head_img;
            private String tags_name;

            public String getTags_name() {
                return tags_name;
            }

            public void setTags_name(String tags_name) {
                this.tags_name = tags_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSub_title() {
                return sub_title;
            }

            public void setSub_title(String sub_title) {
                this.sub_title = sub_title;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getTags_id() {
                return tags_id;
            }

            public void setTags_id(int tags_id) {
                this.tags_id = tags_id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getRecommend_goods_ids() {
                return recommend_goods_ids;
            }

            public void setRecommend_goods_ids(String recommend_goods_ids) {
                this.recommend_goods_ids = recommend_goods_ids;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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

            public String getImg_list() {
                return img_list;
            }

            public void setImg_list(String img_list) {
                this.img_list = img_list;
            }

            public Object getLink() {
                return link;
            }

            public void setLink(Object link) {
                this.link = link;
            }

            public int getView_nums() {
                return view_nums;
            }

            public void setView_nums(int view_nums) {
                this.view_nums = view_nums;
            }

            public int getLike_nums() {
                return like_nums;
            }

            public void setLike_nums(int like_nums) {
                this.like_nums = like_nums;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }
        }
    }
}
