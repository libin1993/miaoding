package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/1/3 14:13
 * Email：1993911441@qq.com
 * Describe：
 */
public class MsgDetailBean {

    /**
     * code : 1
     * data : [{"title":"消息01","img":"/uploads/img/2017010211364499541021.png","content":"<p>消息内容<img src=\"http://139.196.113.61/ueditor/php/upload/image/20170102/1483328216670776.jpg\" title=\"1483328216670776.jpg\" alt=\"2347909729_1883729209.310x310.jpg\"/><\/p>","c_time":1483339033,"is_read":1}]
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
         * title : 消息01
         * img : /uploads/img/2017010211364499541021.png
         * content : <p>消息内容<img src="http://139.196.113.61/ueditor/php/upload/image/20170102/1483328216670776.jpg" title="1483328216670776.jpg" alt="2347909729_1883729209.310x310.jpg"/></p>
         * c_time : 1483339033
         * is_read : 1
         */

        private String title;
        private String img;
        private String content;
        private int c_time;
        private int is_read;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getC_time() {
            return c_time;
        }

        public void setC_time(int c_time) {
            this.c_time = c_time;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }
    }
}
