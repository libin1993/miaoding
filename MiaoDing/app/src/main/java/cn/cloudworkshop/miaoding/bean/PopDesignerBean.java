package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017-04-08 17:22
 * Email：1993911441@qq.com
 * Describe：
 */
public class PopDesignerBean {


    /**
     * code : 1
     * data : [{"id":282,"name":"陈振富","phone":"13811111111","pwd":null,"nickname":"","avatar":"/uploads/img/2017052718290210157494.png","sex":1,"reg_time":null,"status":1,"type":2,"remark":"1","age":0,"c_time":1495880990,"a_id":0,"b_id":0,"group_id":1,"invite_ewm":null,"award_money":"0.00","tx_money":"0.00","reg_ip":null,"xf_money":"0.00","credit":"0.00","birthday":null,"tag":"方圆设计工作室","introduce":"1","story":null}]
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
         * id : 282
         * name : 陈振富
         * phone : 13811111111
         * pwd : null
         * nickname :
         * avatar : /uploads/img/2017052718290210157494.png
         * sex : 1
         * reg_time : null
         * status : 1
         * type : 2
         * remark : 1
         * age : 0
         * c_time : 1495880990
         * a_id : 0
         * b_id : 0
         * group_id : 1
         * invite_ewm : null
         * award_money : 0.00
         * tx_money : 0.00
         * reg_ip : null
         * xf_money : 0.00
         * credit : 0.00
         * birthday : null
         * tag : 方圆设计工作室
         * introduce : 1
         * story : null
         */

        private int id;
        private String name;
        private String phone;
        private Object pwd;
        private String nickname;
        private String avatar;
        private int sex;
        private Object reg_time;
        private int status;
        private int type;
        private String remark;
        private int age;
        private int c_time;
        private int a_id;
        private int b_id;
        private int group_id;
        private Object invite_ewm;
        private String award_money;
        private String tx_money;
        private Object reg_ip;
        private String xf_money;
        private String credit;
        private Object birthday;
        private String tag;
        private String introduce;
        private Object story;
        private String icon;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getPwd() {
            return pwd;
        }

        public void setPwd(Object pwd) {
            this.pwd = pwd;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public Object getReg_time() {
            return reg_time;
        }

        public void setReg_time(Object reg_time) {
            this.reg_time = reg_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getC_time() {
            return c_time;
        }

        public void setC_time(int c_time) {
            this.c_time = c_time;
        }

        public int getA_id() {
            return a_id;
        }

        public void setA_id(int a_id) {
            this.a_id = a_id;
        }

        public int getB_id() {
            return b_id;
        }

        public void setB_id(int b_id) {
            this.b_id = b_id;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public Object getInvite_ewm() {
            return invite_ewm;
        }

        public void setInvite_ewm(Object invite_ewm) {
            this.invite_ewm = invite_ewm;
        }

        public String getAward_money() {
            return award_money;
        }

        public void setAward_money(String award_money) {
            this.award_money = award_money;
        }

        public String getTx_money() {
            return tx_money;
        }

        public void setTx_money(String tx_money) {
            this.tx_money = tx_money;
        }

        public Object getReg_ip() {
            return reg_ip;
        }

        public void setReg_ip(Object reg_ip) {
            this.reg_ip = reg_ip;
        }

        public String getXf_money() {
            return xf_money;
        }

        public void setXf_money(String xf_money) {
            this.xf_money = xf_money;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public Object getStory() {
            return story;
        }

        public void setStory(Object story) {
            this.story = story;
        }
    }
}
