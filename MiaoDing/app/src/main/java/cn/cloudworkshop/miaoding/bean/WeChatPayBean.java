package cn.cloudworkshop.miaoding.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author：Libin on 2016/11/21 14:09
 * Email：1993911441@qq.com
 * Describe：
 */
public class WeChatPayBean implements Serializable{

    /**
     * code : 1
     * msg : 获取成功
     * data : {"appid":"wx07c2173e7686741e","noncestr":"W02IxI2X5a0z53C662IUMZgUbAaC82lv","package":"Sign=WXPay","partnerid":"1396426002","prepayid":"wx201611221141373d0c08b87b0054787997","timestamp":1479786097,"sign":"152787FDF1F14C65FB888B0700EFE105"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    private String pay_code;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appid : wx07c2173e7686741e
         * noncestr : W02IxI2X5a0z53C662IUMZgUbAaC82lv
         * package : Sign=WXPay
         * partnerid : 1396426002
         * prepayid : wx201611221141373d0c08b87b0054787997
         * timestamp : 1479786097
         * sign : 152787FDF1F14C65FB888B0700EFE105
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageValue;
        private String partnerid;
        private String prepayid;
        private int timestamp;
        private String sign;



        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageValue() {
            return packageValue;
        }

        public void setPackageValue(String packageValue) {
            this.packageValue = packageValue;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
