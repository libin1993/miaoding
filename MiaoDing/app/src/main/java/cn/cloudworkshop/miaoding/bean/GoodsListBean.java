package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/10/12 13:12
 * Email：1993911441@qq.com
 * Describe：
 */

public class GoodsListBean {


    /**
     * code : 1
     * data : {"total":5,"per_page":5,"current_page":1,"data":[{"id":1,"name":"衬衣A","sub_name":"衬衣A衬衣A","thumb":"/uploads/img/2016101210473555521019a2.jpg","content":1},{"id":2,"name":"衬衣B","sub_name":"衬衣B衬衣B衬衣B","thumb":"/uploads/img/2016101210474852994848a5.jpg","content":1},{"id":5,"name":"成品01","sub_name":"fb","thumb":"/uploads/img/2016101116064957521025a2.jpg","content":2},{"id":4,"name":"测试衬衣","sub_name":"11","thumb":"/uploads/img/2016101109124499501005a6.jpg","content":1},{"id":3,"name":"测试商品","sub_name":"测试试试","thumb":null,"content":1}]}
     * msg : 成功
     */

    private int code;
    /**
     * total : 5
     * per_page : 5
     * current_page : 1
     * data : [{"id":1,"name":"衬衣A","sub_name":"衬衣A衬衣A","thumb":"/uploads/img/2016101210473555521019a2.jpg","content":1},{"id":2,"name":"衬衣B","sub_name":"衬衣B衬衣B衬衣B","thumb":"/uploads/img/2016101210474852994848a5.jpg","content":1},{"id":5,"name":"成品01","sub_name":"fb","thumb":"/uploads/img/2016101116064957521025a2.jpg","content":2},{"id":4,"name":"测试衬衣","sub_name":"11","thumb":"/uploads/img/2016101109124499501005a6.jpg","content":1},{"id":3,"name":"测试商品","sub_name":"测试试试","thumb":null,"content":1}]
     */

    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private int total;
        private int per_page;
        private int current_page;
        /**
         * id : 1
         * name : 衬衣A
         * sub_name : 衬衣A衬衣A
         * thumb : /uploads/img/2016101210473555521019a2.jpg
         * content : 1
         */

        private List<itemDataBean> data;

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

        public List<itemDataBean> getData() {
            return data;
        }

        public void setData(List<itemDataBean> data) {
            this.data = data;
        }

        public static class itemDataBean {
            private int id;
            private String name;
            private String sub_name;
            private String thumb;
            private String price;
            private int type;
            private String img_new;
            private String img_info;

            public String getImg_new() {
                return img_new;
            }

            public void setImg_new(String img_new) {
                this.img_new = img_new;
            }

            public String getImg_info() {
                return img_info;
            }

            public void setImg_info(String img_info) {
                this.img_info = img_info;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
