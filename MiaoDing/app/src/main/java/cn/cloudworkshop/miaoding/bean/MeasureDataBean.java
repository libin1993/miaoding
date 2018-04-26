package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2018/4/25 12:49
 * Email：1993911441@qq.com
 * Describe：
 */
public class MeasureDataBean {

    /**
     * code : 1
     * data : {"total":1,"per_page":10,"current_page":1,"data":[{"id":302,"sh_name":"哈哈哈哈","height":"175","weight":"66","is_index":0,"img_list":""}]}
     */

    private int code;
    private DataBeanX data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 1
         * per_page : 10
         * current_page : 1
         * data : [{"id":302,"sh_name":"哈哈哈哈","height":"175","weight":"66","is_index":0,"img_list":""}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 302
             * sh_name : 哈哈哈哈
             * height : 175
             * weight : 66
             * is_index : 0
             * img_list :
             */

            private int id;
            private String name;
            private String height;
            private String weight;
            private int is_index;
            private String img_list;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }


            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public int getIs_index() {
                return is_index;
            }

            public void setIs_index(int is_index) {
                this.is_index = is_index;
            }

            public String getImg_list() {
                return img_list;
            }

            public void setImg_list(String img_list) {
                this.img_list = img_list;
            }
        }
    }
}
