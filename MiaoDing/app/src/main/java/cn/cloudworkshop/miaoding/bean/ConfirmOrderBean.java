package cn.cloudworkshop.miaoding.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author：Libin on 2016/10/26 14:51
 * Email：1993911441@qq.com
 * Describe：
 */
public class ConfirmOrderBean {

    /**
     * code : 1
     * data : {"car_list":[{"goods_name":"山沟02","goods_thumb":"1","price":"1.00","num":1}],"address_list":{"id":7,"uid":2,"name":"Mr.h","phone":"17012348908","province":"浙江省","city":"杭州市","area":"下城区","address":"Wanghexihuan","c_time":1476959362,"is_default":1,"status":1}}
     * msg : 成功
     */

    private int code;
    /**
     * car_list : [{"goods_name":"山沟02","goods_thumb":"1","price":"1.00","num":1}]
     * address_list : {"id":7,"uid":2,"name":"Mr.h","phone":"17012348908","province":"浙江省","city":"杭州市","area":"下城区","address":"Wanghexihuan","c_time":1476959362,"is_default":1,"status":1}
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
        /**
         * id : 7
         * uid : 2
         * name : Mr.h
         * phone : 17012348908
         * province : 浙江省
         * city : 杭州市
         * area : 下城区
         * address : Wanghexihuan
         * c_time : 1476959362
         * is_default : 1
         * status : 1
         */

        private AddressListBean address_list;
        /**
         * goods_name : 山沟02
         * goods_thumb : 1
         * price : 1.00
         * num : 1
         */

        private List<CarListBean> car_list;

        private int is_first;
        private String first_money;
        private int ticket_num;
        private String gift_card;
        private int card_userable;

        public int getCard_userable() {
            return card_userable;
        }

        public void setCard_userable(int card_userable) {
            this.card_userable = card_userable;
        }

        public String getGift_card() {
            return gift_card;
        }

        public void setGift_card(String gift_card) {
            this.gift_card = gift_card;
        }

        public int getTicket_num() {
            return ticket_num;
        }

        public void setTicket_num(int ticket_num) {
            this.ticket_num = ticket_num;
        }

        public int getIs_first() {
            return is_first;
        }

        public void setIs_first(int is_first) {
            this.is_first = is_first;
        }

        public String getFirst_money() {
            return first_money;
        }

        public void setFirst_money(String first_money) {
            this.first_money = first_money;
        }

        public AddressListBean getAddress_list() {
            return address_list;
        }

        public void setAddress_list(AddressListBean address_list) {
            this.address_list = address_list;
        }

        public List<CarListBean> getCar_list() {
            return car_list;
        }

        public void setCar_list(List<CarListBean> car_list) {
            this.car_list = car_list;
        }

        public static class AddressListBean implements Serializable{
            private int id;
            private int uid;
            private String name;
            private String phone;
            private String province;
            private String city;
            private String area;
            private String address;
            private int c_time;
            private int is_default;
            private int status;

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

            public int getC_time() {
                return c_time;
            }

            public void setC_time(int c_time) {
                this.c_time = c_time;
            }

            public int getIs_default() {
                return is_default;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class CarListBean {
            private String goods_name;
            private String goods_thumb;
            private String price;
            private int num;
            private int goods_type;
            private String size_content;
            private int id;
            private int goods_id;
            private int can_use_card;

            public int getCan_use_card() {
                return can_use_card;
            }

            public void setCan_use_card(int can_use_card) {
                this.can_use_card = can_use_card;
            }

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
        }
    }
}
