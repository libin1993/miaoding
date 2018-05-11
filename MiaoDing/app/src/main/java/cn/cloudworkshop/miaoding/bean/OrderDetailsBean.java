package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/10/27 18:07
 * Email：1993911441@qq.com
 * Describe：
 */
public class OrderDetailsBean {


    /**
     * code : 1
     * data : {"order_no":"YGC2017061343632","c_time":"2017-06-13 17:42:21","p_time":"1970-01-01 08:00:00","s_time":"1970-01-01 08:00:00","q_time":"1970-01-01 08:00:00","name":"ffggg","phone":"13966666666","province":"北京市","city":"北京市","area":"朝阳区","address":"ghhhhj","money":"399.00","car_ids":"2907","pay_type":0,"ems_com_id":0,"ems_no":null,"status":4,"ticket_money":"0.00","ems_com_name":null,"ems_com":null,"car_list":[{"id":2907,"goods_id":49,"goods_name":"小二西服","goods_thumb":"/uploads/img/2017051609544856491001.jpg","price":"399.00","num":1,"goods_type":2,"size_content":"颜色:黑;尺码:X"}],"last_time":225,"order_comment":{"id":23,"uid":264,"content":"刚好斤斤计较","img_list":"/uploads/comment/20170613174413e406e407e25a6a381d5c802ae3c2b98b.jpg,/uploads/comment/20170613174413c52b32e25096783a78e36ef092f976f4.jpg,/uploads/comment/20170613174413957fe3ed5d58773c9937eb080e1c1afd.jpg,","order_id":1092,"c_time":"2017-06-13 17:44","status":1,"goods_id":49,"car_id":2907}}
     * msg : 成功
     */

    private int code;
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
        /**
         * order_no : YGC2017061343632
         * c_time : 2017-06-13 17:42:21
         * p_time : 1970-01-01 08:00:00
         * s_time : 1970-01-01 08:00:00
         * q_time : 1970-01-01 08:00:00
         * name : ffggg
         * phone : 13966666666
         * province : 北京市
         * city : 北京市
         * area : 朝阳区
         * address : ghhhhj
         * money : 399.00
         * car_ids : 2907
         * pay_type : 0
         * ems_com_id : 0
         * ems_no : null
         * status : 4
         * ticket_money : 0.00
         * ems_com_name : null
         * ems_com : null
         * car_list : [{"id":2907,"goods_id":49,"goods_name":"小二西服","goods_thumb":"/uploads/img/2017051609544856491001.jpg","price":"399.00","num":1,"goods_type":2,"size_content":"颜色:黑;尺码:X"}]
         * last_time : 225
         * order_comment : {"id":23,"uid":264,"content":"刚好斤斤计较","img_list":"/uploads/comment/20170613174413e406e407e25a6a381d5c802ae3c2b98b.jpg,/uploads/comment/20170613174413c52b32e25096783a78e36ef092f976f4.jpg,/uploads/comment/20170613174413957fe3ed5d58773c9937eb080e1c1afd.jpg,","order_id":1092,"c_time":"2017-06-13 17:44","status":1,"goods_id":49,"car_id":2907}
         */

        private String order_no;
        private String c_time;
        private String p_time;
        private String s_time;
        private String q_time;
        private String name;
        private String phone;
        private String province;
        private String city;
        private String area;
        private String address;
        private String money;
        private String car_ids;
        private int pay_type;
        private int ems_com_id;
        private String ems_no;
        private int status;
        private String ticket_money;
        private String ems_com_name;
        private String ems_com;
        private int last_time;
        private OrderCommentBean order_comment;
        private List<CarListBean> car_list;
        private int quick_type;

        public int getQuick_type() {
            return quick_type;
        }

        public void setQuick_type(int quick_type) {
            this.quick_type = quick_type;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getC_time() {
            return c_time;
        }

        public void setC_time(String c_time) {
            this.c_time = c_time;
        }

        public String getP_time() {
            return p_time;
        }

        public void setP_time(String p_time) {
            this.p_time = p_time;
        }

        public String getS_time() {
            return s_time;
        }

        public void setS_time(String s_time) {
            this.s_time = s_time;
        }

        public String getQ_time() {
            return q_time;
        }

        public void setQ_time(String q_time) {
            this.q_time = q_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCar_ids() {
            return car_ids;
        }

        public void setCar_ids(String car_ids) {
            this.car_ids = car_ids;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTicket_money() {
            return ticket_money;
        }

        public void setTicket_money(String ticket_money) {
            this.ticket_money = ticket_money;
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

        public int getLast_time() {
            return last_time;
        }

        public void setLast_time(int last_time) {
            this.last_time = last_time;
        }

        public OrderCommentBean getOrder_comment() {
            return order_comment;
        }

        public void setOrder_comment(OrderCommentBean order_comment) {
            this.order_comment = order_comment;
        }

        public List<CarListBean> getCar_list() {
            return car_list;
        }

        public void setCar_list(List<CarListBean> car_list) {
            this.car_list = car_list;
        }

        public static class OrderCommentBean {
            /**
             * id : 23
             * uid : 264
             * content : 刚好斤斤计较
             * img_list : /uploads/comment/20170613174413e406e407e25a6a381d5c802ae3c2b98b.jpg,/uploads/comment/20170613174413c52b32e25096783a78e36ef092f976f4.jpg,/uploads/comment/20170613174413957fe3ed5d58773c9937eb080e1c1afd.jpg,
             * order_id : 1092
             * c_time : 2017-06-13 17:44
             * status : 1
             * goods_id : 49
             * car_id : 2907
             */

            private int id;
            private int uid;
            private String content;
            private String img_list;
            private int order_id;
            private String c_time;
            private int status;
            private int goods_id;
            private int car_id;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImg_list() {
                return img_list;
            }

            public void setImg_list(String img_list) {
                this.img_list = img_list;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public String getC_time() {
                return c_time;
            }

            public void setC_time(String c_time) {
                this.c_time = c_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getCar_id() {
                return car_id;
            }

            public void setCar_id(int car_id) {
                this.car_id = car_id;
            }
        }

        public static class CarListBean {
            /**
             * id : 2907
             * goods_id : 49
             * goods_name : 小二西服
             * goods_thumb : /uploads/img/2017051609544856491001.jpg
             * price : 399.00
             * num : 1
             * goods_type : 2
             * size_content : 颜色:黑;尺码:X
             */

            private int id;
            private int goods_id;
            private String goods_name;
            private String goods_thumb;
            private String price;
            private int num;
            private int goods_type;
            private String size_content;
            private String spec_content;
            private String diy_content;

            public String getSpec_content() {
                return spec_content;
            }

            public void setSpec_content(String spec_content) {
                this.spec_content = spec_content;
            }

            public String getDiy_content() {
                return diy_content;
            }

            public void setDiy_content(String diy_content) {
                this.diy_content = diy_content;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
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

