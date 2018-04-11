package cn.cloudworkshop.miaoding.bean;

/**
 * Author：Libin on 2016/12/12 14:09
 * Email：1993911441@qq.com
 * Describe：
 */
public class InviteBean {

    /**
     * code : 1
     * data : {"rate":"5%","ewm":"http://source9.oss-cn-shanghai.aliyuncs.com/api/erweima/images/20161212/962a819c374a14e9dcd013103ec132cc.png","invite_num":0,"money":100}
     * msg : 成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;

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
         * rate : 5%
         * ewm : http://source9.oss-cn-shanghai.aliyuncs.com/api/erweima/images/20161212/962a819c374a14e9dcd013103ec132cc.png
         * invite_num : 0
         * money : 100
         */

        private String rate;
        private String ewm;
        private int invite_num;
        private String money;
        private String up_uid;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getUp_uid() {
            return up_uid;
        }

        public void setUp_uid(String up_uid) {
            this.up_uid = up_uid;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getEwm() {
            return ewm;
        }

        public void setEwm(String ewm) {
            this.ewm = ewm;
        }

        public int getInvite_num() {
            return invite_num;
        }

        public void setInvite_num(int invite_num) {
            this.invite_num = invite_num;
        }
    }
}
