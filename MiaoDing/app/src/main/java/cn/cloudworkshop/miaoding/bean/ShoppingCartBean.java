package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/10/27 09:17
 * Email：1993911441@qq.com
 * Describe：
 */
public class ShoppingCartBean {


    /**
     * code : 1
     * data : {"total":2,"per_page":20,"current_page":"1","data":[{"id":5024,"mianliao_id":"16","goods_name":"格纹衬衫","goods_thumb":"/uploads/img/2017071315413310055995.jpg","num":1,"price":"999.00","c_time":1501221209,"goods_id":50,"goods_type":1,"size_content":""},{"id":5019,"mianliao_id":"15","goods_name":"始终如\u201c衣\u201d","goods_thumb":"/uploads/img/2017071315401198995799.jpg","num":3,"price":"799.00","c_time":1501221122,"goods_id":39,"goods_type":1,"size_content":""}]}
     * msg : 成功
     * token : uqeseln2s3fe13o18hovjj7bn2
     */

    private int code;
    private DataBeanX data;
    private String msg;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class DataBeanX {
        /**
         * total : 2
         * per_page : 20
         * current_page : 1
         * data : [{"id":5024,"mianliao_id":"16","goods_name":"格纹衬衫","goods_thumb":"/uploads/img/2017071315413310055995.jpg","num":1,"price":"999.00","c_time":1501221209,"goods_id":50,"goods_type":1,"size_content":""},{"id":5019,"mianliao_id":"15","goods_name":"始终如\u201c衣\u201d","goods_thumb":"/uploads/img/2017071315401198995799.jpg","num":3,"price":"799.00","c_time":1501221122,"goods_id":39,"goods_type":1,"size_content":""}]
         */

        private int total;
        private int per_page;
        private String current_page;
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

        public String getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(String current_page) {
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
             * id : 5024
             * mianliao_id : 16
             * goods_name : 格纹衬衫
             * goods_thumb : /uploads/img/2017071315413310055995.jpg
             * num : 1
             * price : 999.00
             * c_time : 1501221209
             * goods_id : 50
             * goods_type : 1
             * size_content :
             */

            private int id;
            private String mianliao_id;
            private String goods_name;
            private String goods_thumb;
            private int num;
            private String price;
            private int c_time;
            private int goods_id;
            private int goods_type;
            private String size_content;
            private boolean is_select = true;

            public boolean getIs_select() {
                return is_select;
            }

            public void setIs_select(boolean is_select) {
                this.is_select = is_select;
            }

            public int getId() {
                return id;

            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMianliao_id() {
                return mianliao_id;
            }

            public void setMianliao_id(String mianliao_id) {
                this.mianliao_id = mianliao_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_thumb() {
                return goods_thumb;
            }

            public void setGoods_thumb(String goods_thumb) {
                this.goods_thumb = goods_thumb;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getC_time() {
                return c_time;
            }

            public void setC_time(int c_time) {
                this.c_time = c_time;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(int goods_type) {
                this.goods_type = goods_type;
            }

            public String getSize_content() {
                return size_content;
            }

            public void setSize_content(String size_content) {
                this.size_content = size_content;
            }
        }
    }
}
