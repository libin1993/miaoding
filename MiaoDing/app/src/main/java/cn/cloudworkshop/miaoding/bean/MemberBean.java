package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/2/15 16:50
 * Email：1993911441@qq.com
 * Describe：
 */
public class MemberBean {

    /**
     * code : 1
     * data : {"user_info":{"avatar":"/uploads/user/2017021412784.jpg","name":"测试","phone":"13967189485","sex":1,"age":24,"credit":"6.00","birthday":"747676800","user_grade":{"name":"青铜会员","img":"/uploads/img/2017021515055450485257.png","img2":"/uploads/img/2017022013413097975510.png","id":1,"min_credit":"0.00","max_credit":"999.00"}},"user_grade":[{"name":"青铜会员","img":"/uploads/img/2017021515055450485257.png","img2":"/uploads/img/2017022013413097975510.png","user_privilege_ids":"1,2,3","min_credit":"0.00","max_credit":"999.00"},{"name":"白银会员","img":"/uploads/img/2017021515062953515455.png","img2":"/uploads/img/2017022013414557549956.png","user_privilege_ids":"1,2,3,4,5","min_credit":"1000.00","max_credit":"4999.00"},{"name":"黄金会员","img":"/uploads/img/2017021515074099545599.png","img2":"/uploads/img/2017022013420157499852.png","user_privilege_ids":"1,2,3,4,5,6","min_credit":"5000.00","max_credit":"14999.00"},{"name":"钻石会员","img":"/uploads/img/2017021515084398989910.png","img2":"/uploads/img/2017022013421656985098.png","user_privilege_ids":"1,2,3,4,5,6,7","min_credit":"15000.00","max_credit":"50000.00"}],"user_privilege":[{"name":"免费上门量体","img":"/uploads/img/2017021614531497575098.png","desc":"每个等级的会员都可享受云工场提供的免费上门量体服务","is_get":1},{"name":"专属时尚顾问","img":"/uploads/img/2017021614513897551019.png","desc":"每个等级的会员都可享受云工场提供的1对1专属时尚顾问","is_get":1},{"name":"邀请好友返利","img":"/uploads/img/2017021614582099525610.png","desc":"邀请好友，可获得相应的现金返利，详情可在云工场APP-我的-邀请有礼页面查看","is_get":1},{"name":"会员升级礼包","img":"/uploads/img/2017021614540056521014.png","desc":"· 50点成长值\r\n· 1张全场通用券，满2000-500\r\n· 1张衬衫优惠券，满1000-200\r\n· 礼包领取有效期：\r\n         2016.09.28-2016.10.07","is_get":3},{"name":"急速售后服务","img":"/uploads/img/2017021614541757549799.png","desc":"享受云工场提供的7*24小时全年无休的售后服务","is_get":1},{"name":"多重生日惊喜","img":"/uploads/img/2017021614543357100511.png","desc":"· 100点成长值 \r\n· 1张全场通用券，满1500-500 \r\n· 1张衬衫优惠券，满1000-200 \r\n· 生日周期内购物享双倍积分\r\n· 礼包领取有效期：\r\n         2016.09.28-2016.10.07","is_get":2},{"name":"优先线下活动","img":"/uploads/img/2017021614545198971001.png","desc":"云工场会不定期组织、举办线下活动，邀请V4会员参加","is_get":1}]}
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
         * user_info : {"avatar":"/uploads/user/2017021412784.jpg","name":"测试","phone":"13967189485","sex":1,"age":24,"credit":"6.00","birthday":"747676800","user_grade":{"name":"青铜会员","img":"/uploads/img/2017021515055450485257.png","img2":"/uploads/img/2017022013413097975510.png","id":1,"min_credit":"0.00","max_credit":"999.00"}}
         * user_grade : [{"name":"青铜会员","img":"/uploads/img/2017021515055450485257.png","img2":"/uploads/img/2017022013413097975510.png","user_privilege_ids":"1,2,3","min_credit":"0.00","max_credit":"999.00"},{"name":"白银会员","img":"/uploads/img/2017021515062953515455.png","img2":"/uploads/img/2017022013414557549956.png","user_privilege_ids":"1,2,3,4,5","min_credit":"1000.00","max_credit":"4999.00"},{"name":"黄金会员","img":"/uploads/img/2017021515074099545599.png","img2":"/uploads/img/2017022013420157499852.png","user_privilege_ids":"1,2,3,4,5,6","min_credit":"5000.00","max_credit":"14999.00"},{"name":"钻石会员","img":"/uploads/img/2017021515084398989910.png","img2":"/uploads/img/2017022013421656985098.png","user_privilege_ids":"1,2,3,4,5,6,7","min_credit":"15000.00","max_credit":"50000.00"}]
         * user_privilege : [{"name":"免费上门量体","img":"/uploads/img/2017021614531497575098.png","desc":"每个等级的会员都可享受云工场提供的免费上门量体服务","is_get":1},{"name":"专属时尚顾问","img":"/uploads/img/2017021614513897551019.png","desc":"每个等级的会员都可享受云工场提供的1对1专属时尚顾问","is_get":1},{"name":"邀请好友返利","img":"/uploads/img/2017021614582099525610.png","desc":"邀请好友，可获得相应的现金返利，详情可在云工场APP-我的-邀请有礼页面查看","is_get":1},{"name":"会员升级礼包","img":"/uploads/img/2017021614540056521014.png","desc":"· 50点成长值\r\n· 1张全场通用券，满2000-500\r\n· 1张衬衫优惠券，满1000-200\r\n· 礼包领取有效期：\r\n         2016.09.28-2016.10.07","is_get":3},{"name":"急速售后服务","img":"/uploads/img/2017021614541757549799.png","desc":"享受云工场提供的7*24小时全年无休的售后服务","is_get":1},{"name":"多重生日惊喜","img":"/uploads/img/2017021614543357100511.png","desc":"· 100点成长值 \r\n· 1张全场通用券，满1500-500 \r\n· 1张衬衫优惠券，满1000-200 \r\n· 生日周期内购物享双倍积分\r\n· 礼包领取有效期：\r\n         2016.09.28-2016.10.07","is_get":2},{"name":"优先线下活动","img":"/uploads/img/2017021614545198971001.png","desc":"云工场会不定期组织、举办线下活动，邀请V4会员参加","is_get":1}]
         */

        private UserInfoBean user_info;
        private List<UserGradeBeanX> user_grade;
        private List<UserPrivilegeBean> user_privilege;

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public List<UserGradeBeanX> getUser_grade() {
            return user_grade;
        }

        public void setUser_grade(List<UserGradeBeanX> user_grade) {
            this.user_grade = user_grade;
        }

        public List<UserPrivilegeBean> getUser_privilege() {
            return user_privilege;
        }

        public void setUser_privilege(List<UserPrivilegeBean> user_privilege) {
            this.user_privilege = user_privilege;
        }

        public static class UserInfoBean {
            /**
             * avatar : /uploads/user/2017021412784.jpg
             * name : 测试
             * phone : 13967189485
             * sex : 1
             * age : 24
             * credit : 6.00
             * birthday : 747676800
             * user_grade : {"name":"青铜会员","img":"/uploads/img/2017021515055450485257.png","img2":"/uploads/img/2017022013413097975510.png","id":1,"min_credit":"0.00","max_credit":"999.00"}
             */

            private String avatar;
            private String name;
            private String phone;
            private int sex;
            private int age;
            private String credit;
            private String birthday;
            private UserGradeBean user_grade;

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

            public static class UserGradeBean {
                /**
                 * name : 青铜会员
                 * img : /uploads/img/2017021515055450485257.png
                 * img2 : /uploads/img/2017022013413097975510.png
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

        public static class UserGradeBeanX {
            /**
             * name : 青铜会员
             * img : /uploads/img/2017021515055450485257.png
             * img2 : /uploads/img/2017022013413097975510.png
             * user_privilege_ids : 1,2,3
             * min_credit : 0.00
             * max_credit : 999.00
             */

            private String name;
            private String img;
            private String img2;
            private String user_privilege_ids;
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

            public String getUser_privilege_ids() {
                return user_privilege_ids;
            }

            public void setUser_privilege_ids(String user_privilege_ids) {
                this.user_privilege_ids = user_privilege_ids;
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

        public static class UserPrivilegeBean {
            /**
             * name : 免费上门量体
             * img : /uploads/img/2017021614531497575098.png
             * desc : 每个等级的会员都可享受云工场提供的免费上门量体服务
             * is_get : 1
             */

            private int id;
            private String name;
            private String img;
            private String desc;
            private int is_get;

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

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getIs_get() {
                return is_get;
            }

            public void setIs_get(int is_get) {
                this.is_get = is_get;
            }
        }
    }
}
