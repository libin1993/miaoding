package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/10/12 13:12
 * Email：1993911441@qq.com
 * Describe：
 */

public class GoodsTitleBean {

    /**
     * code : 1
     * data : [{"id":5,"name":"领带"},{"id":4,"name":"风衣"},{"id":3,"name":"裤子"},{"id":1,"name":"衬衫"},{"id":2,"name":"西服"},{"id":11,"name":"分类3"}]
     * msg : 成功
     */

    private int code;
    private String msg;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**

     * id : 5
     * name : 领带
     */

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
        private int id;
        private String name;

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
    }
}
