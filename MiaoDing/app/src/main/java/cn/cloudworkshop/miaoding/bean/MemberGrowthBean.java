package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/2/16 12:50
 * Email：1993911441@qq.com
 * Describe：
 */
public class MemberGrowthBean {


    /**
     * code : 1
     * data : [{"credit":"0.01","c_time":1483946450,"name":"购物"},{"credit":"0.01","c_time":1483948436,"name":"购物"},{"credit":"0.01","c_time":1484294630,"name":"购物"}]
     * msg : 成功
     */

    private int code;
    private String msg;
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

    public static class DataBean {
        /**
         * credit : 0.01
         * c_time : 1483946450
         * name : 购物
         */

        private String credit;
        private int c_time;
        private String name;

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public int getC_time() {
            return c_time;
        }

        public void setC_time(int c_time) {
            this.c_time = c_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
