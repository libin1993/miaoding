package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/10/27 16:10
 * Email：1993911441@qq.com
 * Describe：
 */
public class OrderInfoBean {


    /**
     * code : 1
     * data : {"total":5,"per_page":10,"current_page":"1","data":[{"id":1058,"car_ids":"2851","order_no":"YGC2017052264122","status":2,"ems_com_id":0,"ems_no":null,"money":"0.01","c_time":1495447687,"ems_com_name":null,"ems_com":null,"list":[{"id":2851,"goods_name":"中国风","goods_thumb":"/uploads/img/2016122812013510210199.png","num":1,"price":"399.00","goods_type":1,"size_content":""}]},{"id":1056,"car_ids":"2776","order_no":"YGC2017051970812","status":2,"ems_com_id":0,"ems_no":null,"money":"0.01","c_time":1495189618,"ems_com_name":null,"ems_com":null,"list":[{"id":2776,"goods_name":"中国风","goods_thumb":"/uploads/img/2016122812013510210199.png","num":1,"price":"399.00","goods_type":1,"size_content":""}]},{"id":1047,"car_ids":"2783","order_no":"YGC2017051741337","status":-2,"ems_com_id":0,"ems_no":null,"money":"0.01","c_time":1494992354,"ems_com_name":null,"ems_com":null,"list":[{"id":2783,"goods_name":"中国风","goods_thumb":"/uploads/img/2016122812013510210199.png","num":1,"price":"399.00","goods_type":1,"size_content":""}]},{"id":1045,"car_ids":"2817","order_no":"YGC2017051794274","status":2,"ems_com_id":0,"ems_no":null,"money":"0.01","c_time":1494988880,"ems_com_name":null,"ems_com":null,"list":[{"id":2817,"goods_name":"中国风","goods_thumb":"/uploads/img/2016122812013510210199.png","num":1,"price":"399.00","goods_type":1,"size_content":""}]},{"id":1011,"car_ids":"2789","order_no":"YGC2017051675678","status":2,"ems_com_id":0,"ems_no":null,"money":"0.01","c_time":1494897575,"ems_com_name":null,"ems_com":null,"list":[{"id":2789,"goods_name":"中国风","goods_thumb":"/uploads/img/2016122812013510210199.png","num":1,"price":"399.00","goods_type":1,"size_content":""}]}]}
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
         * total : 5
         * per_page : 10
         * current_page : 1
         * data : [{"id":1058,"car_ids":"2851","order_no":"YGC2017052264122","status":2,"ems_com_id":0,"ems_no":null,"money":"0.01","c_time":1495447687,"ems_com_name":null,"ems_com":null,"list":[{"id":2851,"goods_name":"中国风","goods_thumb":"/uploads/img/2016122812013510210199.png","num":1,"price":"399.00","goods_type":1,"size_content":""}]},{"id":1056,"car_ids":"2776","order_no":"YGC2017051970812","status":2,"ems_com_id":0,"ems_no":null,"money":"0.01","c_time":1495189618,"ems_com_name":null,"ems_com":null,"list":[{"id":2776,"goods_name":"中国风","goods_thumb":"/uploads/img/2016122812013510210199.png","num":1,"price":"399.00","goods_type":1,"size_content":""}]},{"id":1047,"car_ids":"2783","order_no":"YGC2017051741337","status":-2,"ems_com_id":0,"ems_no":null,"money":"0.01","c_time":1494992354,"ems_com_name":null,"ems_com":null,"list":[{"id":2783,"goods_name":"中国风","goods_thumb":"/uploads/img/2016122812013510210199.png","num":1,"price":"399.00","goods_type":1,"size_content":""}]},{"id":1045,"car_ids":"2817","order_no":"YGC2017051794274","status":2,"ems_com_id":0,"ems_no":null,"money":"0.01","c_time":1494988880,"ems_com_name":null,"ems_com":null,"list":[{"id":2817,"goods_name":"中国风","goods_thumb":"/uploads/img/2016122812013510210199.png","num":1,"price":"399.00","goods_type":1,"size_content":""}]},{"id":1011,"car_ids":"2789","order_no":"YGC2017051675678","status":2,"ems_com_id":0,"ems_no":null,"money":"0.01","c_time":1494897575,"ems_com_name":null,"ems_com":null,"list":[{"id":2789,"goods_name":"中国风","goods_thumb":"/uploads/img/2016122812013510210199.png","num":1,"price":"399.00","goods_type":1,"size_content":""}]}]
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
             * id : 1058
             * car_ids : 2851
             * order_no : YGC2017052264122
             * status : 2
             * ems_com_id : 0
             * ems_no : null
             * money : 0.01
             * c_time : 1495447687
             * ems_com_name : null
             * ems_com : null
             * list : [{"id":2851,"goods_name":"中国风","goods_thumb":"/uploads/img/2016122812013510210199.png","num":1,"price":"399.00","goods_type":1,"size_content":""}]
             */

            private int id;
            private String car_ids;
            private String order_no;
            private int status;
            private int ems_com_id;
            private String ems_no;
            private String money;
            private int c_time;
            private String ems_com_name;
            private String ems_com;
            private int comment_id;

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            private List<ListBean> list;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCar_ids() {
                return car_ids;
            }

            public void setCar_ids(String car_ids) {
                this.car_ids = car_ids;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getEms_com_id() {
                return ems_com_id;
            }

            public void setEms_com_id(int ems_com_id) {
                this.ems_com_id = ems_com_id;
            }

            public String getEms_no() {
                return ems_no;
            }

            public void setEms_no(String ems_no) {
                this.ems_no = ems_no;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public int getC_time() {
                return c_time;
            }

            public void setC_time(int c_time) {
                this.c_time = c_time;
            }

            public String getEms_com_name() {
                return ems_com_name;
            }

            public void setEms_com_name(String ems_com_name) {
                this.ems_com_name = ems_com_name;
            }

            public String getEms_com() {
                return ems_com;
            }

            public void setEms_com(String ems_com) {
                this.ems_com = ems_com;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * id : 2851
                 * goods_name : 中国风
                 * goods_thumb : /uploads/img/2016122812013510210199.png
                 * num : 1
                 * price : 399.00
                 * goods_type : 1
                 * size_content :
                 */

                private int id;
                private int goods_id;
                private String goods_name;
                private String goods_thumb;
                private int num;
                private String price;
                private int goods_type;
                private String size_content;

                public int getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
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
}
