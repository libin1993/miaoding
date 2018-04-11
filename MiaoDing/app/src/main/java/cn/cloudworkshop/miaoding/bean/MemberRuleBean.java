package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/2/16 11:05
 * Email：1993911441@qq.com
 * Describe：
 */
public class MemberRuleBean {

    /**
     * code : 1
     * data : [{"id":1,"name":"会员等级","img_list":["/uploads/img/2017021611582110048524.jpg"]},{"id":2,"name":"会员特权","img_list":["/uploads/img/2017021611584250515152.jpg"]},{"id":3,"name":"会员成长值","img_list":["/uploads/img/2017021611590355491015.jpg"]}]
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
         * id : 1
         * name : 会员等级
         * img_list : ["/uploads/img/2017021611582110048524.jpg"]
         */

        private int id;
        private String name;
        private List<String> img_list;

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

        public List<String> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<String> img_list) {
            this.img_list = img_list;
        }
    }
}
