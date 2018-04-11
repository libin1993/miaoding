package cn.cloudworkshop.miaoding.bean;

/**
 * Author：Libin on 2016/11/9 18:05
 * Email：1993911441@qq.com
 * Describe：
 */
public class QuestionDetailBean {

    /**
     * code : 1
     * data : {"id":1,"name":"标题","content":"内容内容内容内容内容内容内容\r\n\r\n内容\r\n内容内容\r\n内容"}
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
         * id : 1
         * name : 标题
         * content : 内容内容内容内容内容内容内容

         内容
         内容内容
         内容
         */

        private int id;
        private String name;
        private String content;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
