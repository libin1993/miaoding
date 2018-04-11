package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/10/25 17:32
 * Email：1993911441@qq.com
 * Describe：
 */
public class JoinUsBean {


    /**
     * code : 1
     * data : {"img_list":[{"img":"/uploads/img/2016102616520250555755.jpg","link":"设计师入住","title":"123","share_link":null}],"is_apply":0}
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
         * img_list : [{"img":"/uploads/img/2016102616520250555755.jpg","link":"设计师入住","title":"123","share_link":null}]
         * is_apply : 0
         */

        private int is_apply;
        private List<ImgListBean> img_list;

        public int getIs_apply() {
            return is_apply;
        }

        public void setIs_apply(int is_apply) {
            this.is_apply = is_apply;
        }

        public List<ImgListBean> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<ImgListBean> img_list) {
            this.img_list = img_list;
        }

        public static class ImgListBean {
            /**
             * img : /uploads/img/2016102616520250555755.jpg
             * link : 设计师入住
             * title : 123
             * share_link : null
             */

            private String img;
            private String link;
            private String title;
            private Object share_link;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getShare_link() {
                return share_link;
            }

            public void setShare_link(Object share_link) {
                this.share_link = share_link;
            }
        }
    }
}
