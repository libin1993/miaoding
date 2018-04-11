package cn.cloudworkshop.miaoding.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author：Libin on 2016-10-21 17:10
 * Email：1993911441@qq.com
 * Describe：
 */

public class DeliveryAddressBean implements Serializable{

    /**
     * code : 1
     * data : [{"id":7,"uid":22,"name":"Mr.h","phone":"17012348908","province":"浙江省","city":"杭州市","area":"下城区","address":"Wanghexihuan","c_time":1476959362,"is_default":1,"status":1}]
     * msg : 成功
     */

    private static final long serialVersionUID = -1466479389299512377L;

    private int code;
    private String msg;
    /**
     * id : 7
     * uid : 22
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

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private static final long serialVersionUID = -146647999512377L;
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
}
