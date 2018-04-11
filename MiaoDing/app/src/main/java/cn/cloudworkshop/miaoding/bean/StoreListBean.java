package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2018/3/13 14:58
 * Email：1993911441@qq.com
 * Describe：
 */
public class StoreListBean {


    /**
     * code : 1
     * data : [{"id":13,"name":"上海维度服装有限公司","phone":"18621111659","pwd":"18621111659","c_time":1499917358,"status":1,"uname":"张","account":"18621111659","email":"","link":"","login_ip":"","type":2,"pid":0,"factory_id":0,"img":"","address":"","is_love":0,"is_collect":0,"lovenum":0},{"id":14,"name":"广州迪敖贸易","phone":"13711772175","pwd":"13711772175","c_time":1500953360,"status":1,"uname":"庄佳冰","account":"13711772175","email":"","link":"","login_ip":"","type":2,"pid":0,"factory_id":0,"img":"","address":"","is_love":0,"is_collect":0,"lovenum":0},{"id":18,"name":"商店1","phone":"17130044546","pwd":"17130044546","c_time":1507543838,"status":1,"uname":"商店1","account":"17130044546","email":"","link":"","login_ip":"","type":2,"pid":0,"factory_id":17,"img":"","address":"","is_love":0,"is_collect":0,"lovenum":2},{"id":20,"name":"商店2","phone":"17130044548","pwd":"17130044548","c_time":1507777683,"status":1,"uname":"商店2","account":"17130044548","email":"","link":"","login_ip":"","type":2,"pid":0,"factory_id":19,"img":"","address":"","is_love":0,"is_collect":0,"lovenum":0},{"id":21,"name":"123","phone":"123123123","pwd":"123123","c_time":1520828831,"status":1,"uname":"123","account":"123123","email":"","link":"123132123","login_ip":"","type":2,"pid":0,"factory_id":4,"img":"","address":"","is_love":0,"is_collect":0,"lovenum":0},{"id":22,"name":"1231231","phone":"12312312","pwd":"123","c_time":1520828946,"status":1,"uname":"123123123","account":"123123123123","email":"","link":"123","login_ip":"141.123.123.12","type":2,"pid":0,"factory_id":17,"img":"","address":"","is_love":0,"is_collect":0,"lovenum":0}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 13
         * name : 上海维度服装有限公司
         * phone : 18621111659
         * pwd : 18621111659
         * c_time : 1499917358
         * status : 1
         * uname : 张
         * account : 18621111659
         * email :
         * link :
         * login_ip :
         * type : 2
         * pid : 0
         * factory_id : 0
         * img :
         * address :
         * is_love : 0
         * is_collect : 0
         * lovenum : 0
         */

        private int id;
        private String name;
        private String phone;
        private String pwd;
        private int c_time;
        private int status;
        private String uname;
        private String account;
        private String email;
        private String link;
        private String login_ip;
        private int type;
        private int pid;
        private int factory_id;
        private String img;
        private String address;
        private int is_love;
        private int is_collect;
        private int lovenum;

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

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public int getC_time() {
            return c_time;
        }

        public void setC_time(int c_time) {
            this.c_time = c_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLogin_ip() {
            return login_ip;
        }

        public void setLogin_ip(String login_ip) {
            this.login_ip = login_ip;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getFactory_id() {
            return factory_id;
        }

        public void setFactory_id(int factory_id) {
            this.factory_id = factory_id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getIs_love() {
            return is_love;
        }

        public void setIs_love(int is_love) {
            this.is_love = is_love;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public int getLovenum() {
            return lovenum;
        }

        public void setLovenum(int lovenum) {
            this.lovenum = lovenum;
        }
    }
}
