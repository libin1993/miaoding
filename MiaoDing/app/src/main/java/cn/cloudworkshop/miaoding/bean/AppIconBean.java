package cn.cloudworkshop.miaoding.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Libin on 2017-06-22 10:53
 * Email：1993911441@qq.com
 * Describe：
 */
public class AppIconBean {

    /**
     * code : 1
     * data : [{"id":5,"name":"12","img":"/uploads/img/2017062102234650975452.jpg","select_img":"/uploads/img/2017062109421998495153.jpg"},{"id":6,"name":"1","img":"/uploads/img/2017062103151955515610.jpg","select_img":null},{"id":23,"name":"liao","img":"/uploads/img/2017062107012553565098.jpg","select_img":null}]
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
         * id : 5
         * name : 12
         * img : /uploads/img/2017062102234650975452.jpg
         * select_img : /uploads/img/2017062109421998495153.jpg
         */

        private int id;
        private String name;
        private String img;
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

        public String getSelect_img() {
            return select_img;
        }

        public void setSelect_img(String select_img) {
            this.select_img = select_img;
        }
    }
}
