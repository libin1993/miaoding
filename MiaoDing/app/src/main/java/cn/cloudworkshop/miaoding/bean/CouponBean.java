package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/12/19 09:58
 * Email：1993911441@qq.com
 * Describe：
 */
public class CouponBean {

    /**
     * code : 1
     * data : [{"id":3,"uid":18,"title":"满50减30","sub_title":"满50减30","ticket_no":"2016121946531","money":null,"ticket_id":1,"status":1,"c_time":1482111896,"s_time":1482111073,"e_time":1483113600}]
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
         * id : 3
         * uid : 18
         * title : 满50减30
         * sub_title : 满50减30
         * ticket_no : 2016121946531
         * money : null
         * ticket_id : 1
         * status : 1
         * c_time : 1482111896
         * s_time : 1482111073
         * e_time : 1483113600
         */

        private int id;
        private int uid;
        private String title;
        private String sub_title;
        private String ticket_no;
        private String money;
        private int ticket_id;
        private int status;
        private int c_time;
        private int s_time;
        private int e_time;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getTicket_no() {
            return ticket_no;
        }

        public void setTicket_no(String ticket_no) {
            this.ticket_no = ticket_no;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getTicket_id() {
            return ticket_id;
        }

        public void setTicket_id(int ticket_id) {
            this.ticket_id = ticket_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getC_time() {
            return c_time;
        }

        public void setC_time(int c_time) {
            this.c_time = c_time;
        }

        public int getS_time() {
            return s_time;
        }

        public void setS_time(int s_time) {
            this.s_time = s_time;
        }

        public int getE_time() {
            return e_time;
        }

        public void setE_time(int e_time) {
            this.e_time = e_time;
        }
    }
}
