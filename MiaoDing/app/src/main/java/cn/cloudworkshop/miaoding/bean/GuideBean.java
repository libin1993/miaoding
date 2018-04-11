package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/2/8 13:53
 * Email：1993911441@qq.com
 * Describe：
 */
public class GuideBean {

    /**
     * code : 1
     * data : {"name":"启动页","img_urls":["111","222"]}
     * msg : 登录成功
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
         * name : 启动页
         * img_urls : ["111","222"]
         */

        private String name;
        private List<String> img_urls;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getImg_urls() {
            return img_urls;
        }

        public void setImg_urls(List<String> img_urls) {
            this.img_urls = img_urls;
        }
    }
}
