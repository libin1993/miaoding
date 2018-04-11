package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/11/2 17:05
 * Email：1993911441@qq.com
 * Describe：
 */
public class CartDetailsBean {

    /**
     * code : 1
     * data : {"id":456,"uid":18,"goods_id":4,"goods_type":1,"price":"3000.00","spec_ids":"39,28,22,42,36,33","gxh_id":null,"font_id":null,"words":null,"size_ids":"","c_time":1478002149,"status":1,"content":null,"goods_name":"衬衣01","goods_thumb":"/uploads/img/20161014172554505257560072370400_2_1_1.jpg","goods_no":"","num":1,"spec_content":"门襟:里三褶门襟;下摆:圆摆大身前;领子:方开领;袖口:单袖头六角一粒扣;后背:后背无褶;口袋:圆角口袋;","size_content":"","diy_content":"","content":1,"img_list":[{"img_c":"/uploads/img/2016102411214910098489.png"},{"img_c":"/uploads/img/2016102717352510053481.png"},{"img_c":"/uploads/img/2016102412150899100100.png"},{"img_c":"/uploads/img/2016102414591753575154.png"}]}
     * msg : 成功
     */

    private int code;
    /**
     * id : 456
     * uid : 18
     * goods_id : 4
     * goods_type : 1
     * price : 3000.00
     * spec_ids : 39,28,22,42,36,33
     * gxh_id : null
     * font_id : null
     * words : null
     * size_ids :
     * c_time : 1478002149
     * status : 1
     * content : null
     * goods_name : 衬衣01
     * goods_thumb : /uploads/img/20161014172554505257560072370400_2_1_1.jpg
     * goods_no :
     * num : 1
     * spec_content : 门襟:里三褶门襟;下摆:圆摆大身前;领子:方开领;袖口:单袖头六角一粒扣;后背:后背无褶;口袋:圆角口袋;
     * size_content :
     * diy_content :
     * content : 1
     * img_list : [{"img_c":"/uploads/img/2016102411214910098489.png"},{"img_c":"/uploads/img/2016102717352510053481.png"},{"img_c":"/uploads/img/2016102412150899100100.png"},{"img_c":"/uploads/img/2016102414591753575154.png"}]
     */

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
        private int id;
        private int uid;
        private int goods_id;
        private int goods_type;
        private double price;
        private String spec_ids;
        private int gxh_id;
        private int font_id;
        private String words;
        private String size_ids;
        private int c_time;
        private int status;
        private String content;
        private String goods_name;
        private String goods_thumb;
        private String goods_no;
        private int num;
        private String spec_content;
        private String size_content;
        private String diy_content;
        private String mianliao_id;
        private String banxing_id;
        private int price_id;
        private String default_img;
        private int is_scan;

        public int getIs_scan() {
            return is_scan;
        }

        public void setIs_scan(int is_scan) {
            this.is_scan = is_scan;
        }

        public String getDefault_img() {
            return default_img;
        }

        public void setDefault_img(String default_img) {
            this.default_img = default_img;
        }

        public int getPrice_id() {
            return price_id;
        }

        public void setPrice_id(int price_id) {
            this.price_id = price_id;
        }

        public String getBanxing_id() {
            return banxing_id;
        }

        public void setBanxing_id(String banxing_id) {
            this.banxing_id = banxing_id;
        }

        public String getMianliao_id() {
            return mianliao_id;
        }

        public void setMianliao_id(String mianliao_id) {
            this.mianliao_id = mianliao_id;
        }

        private int type;
        /**
         * img_c : /uploads/img/2016102411214910098489.png
         */

        private List<ImgListBean> img_list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSpec_ids() {
            return spec_ids;
        }

        public void setSpec_ids(String spec_ids) {
            this.spec_ids = spec_ids;
        }

        public int getGxh_id() {
            return gxh_id;
        }

        public void setGxh_id(int gxh_id) {
            this.gxh_id = gxh_id;
        }

        public int getFont_id() {
            return font_id;
        }

        public void setFont_id(int font_id) {
            this.font_id = font_id;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public String getSize_ids() {
            return size_ids;
        }

        public void setSize_ids(String size_ids) {
            this.size_ids = size_ids;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public String getGoods_no() {
            return goods_no;
        }

        public void setGoods_no(String goods_no) {
            this.goods_no = goods_no;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getSpec_content() {
            return spec_content;
        }

        public void setSpec_content(String spec_content) {
            this.spec_content = spec_content;
        }

        public String getSize_content() {
            return size_content;
        }

        public void setSize_content(String size_content) {
            this.size_content = size_content;
        }

        public String getDiy_content() {
            return diy_content;
        }

        public void setDiy_content(String diy_content) {
            this.diy_content = diy_content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<ImgListBean> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<ImgListBean> img_list) {
            this.img_list = img_list;
        }

        public static class ImgListBean {
            private int spec_id;
            private String img_c;
            private String name;
            private int position_id;


            public int getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(int spec_id) {
                this.spec_id = spec_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPosition_id() {
                return position_id;
            }

            public void setPosition_id(int position_id) {
                this.position_id = position_id;
            }

            public String getImg_c() {
                return img_c;
            }

            public void setImg_c(String img_c) {
                this.img_c = img_c;
            }
        }
    }
}
