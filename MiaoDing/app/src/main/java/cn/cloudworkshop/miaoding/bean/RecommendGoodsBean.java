package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/2/9 15:26
 * Email：1993911441@qq.com
 * Describe：
 */
public class RecommendGoodsBean {

    /**
     * code : 1
     * data : {"total":3,"per_page":10,"current_page":1,"data":[{"id":1,"goods_id":43,"sort":1,"c_time":1486624258,"status":1,"name":"中国风","thumb":"/uploads/img/2016122812560351504852.png","classify_id":2,"content":1},{"id":2,"goods_id":47,"sort":1,"c_time":1486624264,"status":1,"name":"礼服系列","thumb":"/uploads/img/2016122814332048991024.png","classify_id":2,"content":1},{"id":3,"goods_id":40,"sort":1,"c_time":1486624278,"status":1,"name":"经典商务","thumb":"/uploads/img/2016122812095450995797.png","classify_id":1,"content":1}]}
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
         * total : 3
         * per_page : 10
         * current_page : 1
         * data : [{"id":1,"goods_id":43,"sort":1,"c_time":1486624258,"status":1,"name":"中国风","thumb":"/uploads/img/2016122812560351504852.png","classify_id":2,"content":1},{"id":2,"goods_id":47,"sort":1,"c_time":1486624264,"status":1,"name":"礼服系列","thumb":"/uploads/img/2016122814332048991024.png","classify_id":2,"content":1},{"id":3,"goods_id":40,"sort":1,"c_time":1486624278,"status":1,"name":"经典商务","thumb":"/uploads/img/2016122812095450995797.png","classify_id":1,"content":1}]
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
             * id : 1
             * goods_id : 43
             * sort : 1
             * c_time : 1486624258
             * status : 1
             * name : 中国风
             * thumb : /uploads/img/2016122812560351504852.png
             * classify_id : 2
             * content : 1
             */

            private int id;
            private int goods_id;
            private int sort;
            private int c_time;
            private int status;
            private String name;
            private String thumb;
            private int classify_id;
            private int type;
            private String sub_name;
            private String price;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSub_name() {
                return sub_name;
            }

            public void setSub_name(String sub_name) {
                this.sub_name = sub_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public int getClassify_id() {
                return classify_id;
            }

            public void setClassify_id(int classify_id) {
                this.classify_id = classify_id;
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
