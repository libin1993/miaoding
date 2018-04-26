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
     * data : {"car_list":[{"goods_name":"白色商务经典Easycare系列","mianliao_id":"51","goods_id":65,"goods_thumb":"/uploads/img/2018041318050448579910.jpg","price":"399.00","num":1,"size_content":"","goods_type":1,"id":9178,"can_use_card":1}],"address_list":{"id":442,"uid":264,"name":"刚刚给","phone":"13133333333","province":"北京市","city":"北京市","area":"朝阳区","address":"古古怪怪","c_time":1524187565,"is_default":1,"zip_code":null,"status":1},"is_first":0,"first_money":100,"ticket_num":0,"gift_card":-50,"card_userable":1}
     * lt_arr : {"id":330,"sh_name":"15144","height":"5665","weight":"555","is_index":1,"img_list":""}
     * msg : 成功
     */

    private int code;
    private DataBean data;
    private LtArrBean lt_arr;
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

    public LtArrBean getLt_arr() {
        return lt_arr;
    }

    public void setLt_arr(LtArrBean lt_arr) {
        this.lt_arr = lt_arr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * car_list : [{"goods_name":"白色商务经典Easycare系列","mianliao_id":"51","goods_id":65,"goods_thumb":"/uploads/img/2018041318050448579910.jpg","price":"399.00","num":1,"size_content":"","goods_type":1,"id":9178,"can_use_card":1}]
         * address_list : {"id":442,"uid":264,"name":"刚刚给","phone":"13133333333","province":"北京市","city":"北京市","area":"朝阳区","address":"古古怪怪","c_time":1524187565,"is_default":1,"zip_code":null,"status":1}
         * is_first : 0
         * first_money : 100
         * ticket_num : 0
         * gift_card : -50
         * card_userable : 1
         */

        private AddressListBean address_list;
        private int is_first;
        private int first_money;
        private int ticket_num;
        private String gift_card;
        private int card_userable;
        private List<CarListBean> car_list;

        public AddressListBean getAddress_list() {
            return address_list;
        }

        public void setAddress_list(AddressListBean address_list) {
            this.address_list = address_list;
        }

        public int getIs_first() {
            return is_first;
        }

        public void setIs_first(int is_first) {
            this.is_first = is_first;
        }

        public int getFirst_money() {
            return first_money;
        }

        public void setFirst_money(int first_money) {
            this.first_money = first_money;
        }

        public int getTicket_num() {
            return ticket_num;
        }

        public void setTicket_num(int ticket_num) {
            this.ticket_num = ticket_num;
        }

        public String getGift_card() {
            return gift_card;
        }

        public void setGift_card(String gift_card) {
            this.gift_card = gift_card;
        }

        public int getCard_userable() {
            return card_userable;
        }

        public void setCard_userable(int card_userable) {
            this.card_userable = card_userable;
        }

        public List<CarListBean> getCar_list() {
            return car_list;
        }

        public void setCar_list(List<CarListBean> car_list) {
            this.car_list = car_list;
        }

        public static class AddressListBean implements Serializable {
            /**
             * id : 442
             * uid : 264
             * name : 刚刚给
             * phone : 13133333333
             * province : 北京市
             * city : 北京市
             * area : 朝阳区
             * address : 古古怪怪
             * c_time : 1524187565
             * is_default : 1
             * zip_code : null
             * status : 1
             */

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
            private Object zip_code;
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

            public Object getZip_code() {
                return zip_code;
            }

            public void setZip_code(Object zip_code) {
                this.zip_code = zip_code;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class CarListBean {
            /**
             * goods_name : 白色商务经典Easycare系列
             * mianliao_id : 51
             * goods_id : 65
             * goods_thumb : /uploads/img/2018041318050448579910.jpg
             * price : 399.00
             * num : 1
             * size_content :
             * goods_type : 1
             * id : 9178
             * can_use_card : 1
             */

            private String goods_name;
            private String mianliao_id;
            private int goods_id;
            private String goods_thumb;
            private String price;
            private int num;
            private String size_content;
            private int goods_type;
            private int id;
            private int can_use_card;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getMianliao_id() {
                return mianliao_id;
            }

            public void setMianliao_id(String mianliao_id) {
                this.mianliao_id = mianliao_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
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

            public String getSize_content() {
                return size_content;
            }

            public void setSize_content(String size_content) {
                this.size_content = size_content;
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

            public int getCan_use_card() {
                return can_use_card;
            }

            public void setCan_use_card(int can_use_card) {
                this.can_use_card = can_use_card;
            }
        }
    }

    public static class LtArrBean {
        /**
         * id : 330
         * sh_name : 15144
         * height : 5665
         * weight : 555
         * is_index : 1
         * img_list :
         */

        private int id;
        private String name;
        private String height;
        private String weight;
        private int is_index;
        private String img_list;

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

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public int getIs_index() {
            return is_index;
        }

        public void setIs_index(int is_index) {
            this.is_index = is_index;
        }

        public String getImg_list() {
            return img_list;
        }

        public void setImg_list(String img_list) {
            this.img_list = img_list;
        }
    }
}
