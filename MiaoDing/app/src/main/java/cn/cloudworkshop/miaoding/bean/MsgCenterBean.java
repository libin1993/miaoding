package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017/1/2 17:53
 * Email：1993911441@qq.com
 * Describe：
 */
public class MsgCenterBean {

    /**
     * code : 1
     * data : [{"id":1,"name":"系统消息","img":"/uploads/img/2017010217040957524810.png","last_msg":{"title":"消息01","is_read":0,"c_time":1483339033}},{"id":2,"name":"活动信息","img":"/uploads/img/2017010217043450489810.png","last_msg":null},{"id":3,"name":"其它信息","img":"/uploads/img/2017010217044949545797.png","last_msg":null}]
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
         * name : 系统消息
         * img : /uploads/img/2017010217040957524810.png
         * last_msg : {"title":"消息01","is_read":0,"c_time":1483339033}
         */

        private int id;
        private String name;
        private String img;
        private LastMsgBean last_msg;
        private int num;
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public LastMsgBean getLast_msg() {
            return last_msg;
        }

        public void setLast_msg(LastMsgBean last_msg) {
            this.last_msg = last_msg;
        }

        public static class LastMsgBean {
            /**
             * title : 消息01
             * is_read : 0
             * c_time : 1483339033
             */

            private String title;
            private int is_read;
            private int c_time;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getIs_read() {
                return is_read;
            }

            public void setIs_read(int is_read) {
                this.is_read = is_read;
            }

            public int getC_time() {
                return c_time;
            }

            public void setC_time(int c_time) {
                this.c_time = c_time;
            }
        }
    }
}
