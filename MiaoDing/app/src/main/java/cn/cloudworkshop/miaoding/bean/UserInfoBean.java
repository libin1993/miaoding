package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017-06-22 11:26
 * Email：1993911441@qq.com
 * Describe：
 */
public class UserInfoBean {


    /**
     * code : 1
     * data : {"avatar":"/uploads/user/2017061393059.jpg","name":"广告广告","phone":"15167168495","sex":0,"age":0,"credit":"143.04","birthday":"1980-01-01","user_grade":{"name":"V1","img":"/uploads/img/2017051614114953995651.png","img2":"/uploads/img/2017051614115598575797.png","id":1,"min_credit":"0.00","max_credit":"999.00"},"unread_message_num":1,"is_yuyue":0,"uid":264}
     * icon_list : [{"id":24,"name":"我的订单","img":"/uploads/img/2017062211093198100565.png","sort":1,"status":2,"c_time":1498100973,"type":1,"select_img":""},{"id":25,"name":"购物袋","img":"/uploads/img/2017062211100297979757.png","sort":2,"status":2,"c_time":1498101027,"type":1,"select_img":""},{"id":26,"name":"优惠券","img":"/uploads/img/2017062211103910210099.png","sort":3,"status":2,"c_time":1498101041,"type":1,"select_img":""},{"id":27,"name":"我的收藏","img":"/uploads/img/2017062211105850995699.png","sort":4,"status":2,"c_time":1498101061,"type":1,"select_img":""},{"id":28,"name":"预约量体","img":"/uploads/img/2017062211111450525457.png","sort":5,"status":2,"c_time":1498101076,"type":1,"select_img":""},{"id":29,"name":"穿衣测试","img":"/uploads/img/2017062211112710251505.png","sort":6,"status":2,"c_time":1498101089,"type":1,"select_img":""},{"id":30,"name":"拍照量体","img":"/uploads/img/2017062211114110056995.png","sort":7,"status":2,"c_time":1498101103,"type":1,"select_img":""},{"id":31,"name":"私人顾问","img":"/uploads/img/2017062211115497499799.png","sort":8,"status":2,"c_time":1498101116,"type":1,"select_img":""},{"id":32,"name":"邀请好友","img":"/uploads/img/2017062211130110056499.png","sort":9,"status":2,"c_time":1498101184,"type":1,"select_img":""}]
     * msg : 成功
     */

    private int code;
    private DataBean data;
    private String msg;
    private List<IconListBean> icon_list;
    private int is_opencv;

    public int getIs_opencv() {
        return is_opencv;
    }

    public void setIs_opencv(int is_opencv) {
        this.is_opencv = is_opencv;
    }

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

    public List<IconListBean> getIcon_list() {
        return icon_list;
    }

    public void setIcon_list(List<IconListBean> icon_list) {
        this.icon_list = icon_list;
    }

    public static class DataBean {
        /**
         * avatar : /uploads/user/2017061393059.jpg
         * name : 广告广告
         * phone : 15167168495
         * sex : 0
         * age : 0
         * credit : 143.04
         * birthday : 1980-01-01
         * user_grade : {"name":"V1","img":"/uploads/img/2017051614114953995651.png","img2":"/uploads/img/2017051614115598575797.png","id":1,"min_credit":"0.00","max_credit":"999.00"}
         * unread_message_num : 1
         * is_yuyue : 0
         * uid : 264
         */

        private String avatar;
        private String name;
        private String phone;
        private int sex;
        private int age;
        private String credit;
        private String birthday;
        private UserGradeBean user_grade;
        private int unread_message_num;
        private int is_yuyue;
        private int uid;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public UserGradeBean getUser_grade() {
            return user_grade;
        }

        public void setUser_grade(UserGradeBean user_grade) {
            this.user_grade = user_grade;
        }

        public int getUnread_message_num() {
            return unread_message_num;
        }

        public void setUnread_message_num(int unread_message_num) {
            this.unread_message_num = unread_message_num;
        }

        public int getIs_yuyue() {
            return is_yuyue;
        }

        public void setIs_yuyue(int is_yuyue) {
            this.is_yuyue = is_yuyue;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public static class UserGradeBean {
            /**
             * name : V1
             * img : /uploads/img/2017051614114953995651.png
             * img2 : /uploads/img/2017051614115598575797.png
             * id : 1
             * min_credit : 0.00
             * max_credit : 999.00
             */

            private String name;
            private String img;
            private String img2;
            private int id;
            private String min_credit;
            private String max_credit;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getImg2() {
                return img2;
            }

            public void setImg2(String img2) {
                this.img2 = img2;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMin_credit() {
                return min_credit;
            }

            public void setMin_credit(String min_credit) {
                this.min_credit = min_credit;
            }

            public String getMax_credit() {
                return max_credit;
            }

            public void setMax_credit(String max_credit) {
                this.max_credit = max_credit;
            }
        }
    }

    public static class IconListBean {
        /**
         * id : 24
         * name : 我的订单
         * img : /uploads/img/2017062211093198100565.png
         * sort : 1
         * status : 2
         * c_time : 1498100973
         * type : 1
         * select_img :
         */

        private int id;
        private String name;
        private String img;
        private int sort;
        private int status;
        private int c_time;
        private int type;
        private String select_img;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getSelect_img() {
            return select_img;
        }

        public void setSelect_img(String select_img) {
            this.select_img = select_img;
        }
    }
}
