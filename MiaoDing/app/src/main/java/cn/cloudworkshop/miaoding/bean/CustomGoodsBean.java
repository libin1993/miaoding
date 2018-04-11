package cn.cloudworkshop.miaoding.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Libin on 2016-10-18 13:56
 * Email：1993911441@qq.com
 * Describe：
 */

public class CustomGoodsBean {


    /**
     * code : 1
     * data : {"name":"始终如\u201c衣\u201d","sub_name":"三百七十一个小时的匠心大作","img_list":["/uploads/img/2017052508025648569752.png","/uploads/img/2017052508025648995210.jpg","/uploads/img/2017052508025749509948.jpg","/uploads/img/2017052508025749974998.jpg"],"view_num":0,"like_num":0,"price":[{"id":2,"price":799,"introduce":"白银"},{"id":3,"price":999,"introduce":"黄金"},{"id":4,"price":1888,"introduce":"钻石"}],"content":"选择你的衬衫\n选择你的生活态度\n是将就一份不适合\n还是去定制一份专属?\n你会如何选择?\n\n自带经纬度编织扎染效果的面料，赋予衬衫简约中\n的繁复感，清新怡人的色调，适合年轻时尚男士穿\n着，柔软亲肤面料，穿着更舒适，文艺风圆领设计，\n更时尚。\n\n面料成份:100%亚麻。","content2":"/uploads/img/2017052508053310049102.jpg","type":1,"heat":4047,"thumb":"/uploads/img/2017060517293951515557.jpg","classify_id":1,"uid":0,"default_spec_content":"领子: 带扣尖领;领子: 带扣尖领;面料:黑","default_spec_ids":"348,350","default_mianliao":1,"designer":null,"default_spec_list":[{"id":348,"gid":null,"spec_id":2,"mianliao_id":1,"name":"圆摆","type":0,"img_a":"/uploads/img/2016122916520957534954.png","img_b":"/uploads/img/2016122916523110297529.png","img_c":"/uploads/img/2017010216243248971019.png","introduce":null,"spec_name":"下摆","position_id":1},{"id":350,"gid":null,"spec_id":1,"mianliao_id":1,"name":" 带扣尖领","type":0,"img_a":"/uploads/img/2016111516165998519952.png","img_b":"/uploads/img/2016111516170751531001.png","img_c":"/uploads/img/2016111516152610149535.png","introduce":null,"spec_name":"领子","position_id":1}],"banxing_list":[{"id":1,"name":"修身"},{"id":2,"name":"宽松"},{"id":3,"name":"合适"}],"default_mianliao_name":"黑","default_price":399,"price_type":1,"new_comment":{"id":9,"uid":75,"content":"嗯这个商品的价值在于，独一无二，然后超级超级合身，好评好评好评好评好评。特别特别六六六","img_list":["/uploads/opencv/2017061211013712c10ebc0c4e7eb56676dd46fa2dc3ef.jpg","/uploads/opencv/2017061211013759b0245ebc61cef586111a3981d0a3ff.jpg","/uploads/opencv/20170612110137ee9603f73bfdf1dc6af642ae8d324f90.jpg",""],"order_id":1069,"c_time":1497236497,"status":1,"goods_id":39,"user_name":"黄哥哥","avatar":"/uploads/user/2017060619272.jpg"},"comment_num":6,"collect_user":[{"avatar":"/uploads/img/2016122211012553521024.png","id":132,"name":"云工场7558"},{"avatar":"/uploads/user/2016122882797.jpg","id":87,"name":"Recall.L"},{"avatar":"/uploads/img/2016122211012553521024.png","id":133,"name":"云工场7768"},{"avatar":"/uploads/user/2017011156485.jpg","id":78,"name":"白山黑水"},{"avatar":"/uploads/img/2016122211012553521024.png","id":123,"name":"云工场1427"},{"avatar":"/uploads/user/2017022204883.jpg","id":186,"name":"云工场5558"},{"avatar":"/uploads/img/2016122211012553521024.png","id":189,"name":"云工场5046"},{"avatar":"/uploads/user/2017060619272.jpg","id":75,"name":"黄哥哥"}],"is_collect":-1,"is_yuyue":0}
     * msg : 成功
     * id : 578
     */

    private int code;
    private DataBean data;
    private String msg;
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class DataBean {
        /**
         * name : 始终如“衣”
         * sub_name : 三百七十一个小时的匠心大作
         * img_list : ["/uploads/img/2017052508025648569752.png","/uploads/img/2017052508025648995210.jpg","/uploads/img/2017052508025749509948.jpg","/uploads/img/2017052508025749974998.jpg"]
         * view_num : 0
         * like_num : 0
         * price : [{"id":2,"price":799,"introduce":"白银"},{"id":3,"price":999,"introduce":"黄金"},{"id":4,"price":1888,"introduce":"钻石"}]
         * content : 选择你的衬衫
         * 选择你的生活态度
         * 是将就一份不适合
         * 还是去定制一份专属?
         * 你会如何选择?
         * <p>
         * 自带经纬度编织扎染效果的面料，赋予衬衫简约中
         * 的繁复感，清新怡人的色调，适合年轻时尚男士穿
         * 着，柔软亲肤面料，穿着更舒适，文艺风圆领设计，
         * 更时尚。
         * <p>
         * 面料成份:100%亚麻。
         * content2 : /uploads/img/2017052508053310049102.jpg
         * type : 1
         * heat : 4047
         * thumb : /uploads/img/2017060517293951515557.jpg
         * classify_id : 1
         * uid : 0
         * default_spec_content : 领子: 带扣尖领;领子: 带扣尖领;面料:黑
         * default_spec_ids : 348,350
         * default_mianliao : 1
         * designer : null
         * default_spec_list : [{"id":348,"gid":null,"spec_id":2,"mianliao_id":1,"name":"圆摆","type":0,"img_a":"/uploads/img/2016122916520957534954.png","img_b":"/uploads/img/2016122916523110297529.png","img_c":"/uploads/img/2017010216243248971019.png","introduce":null,"spec_name":"下摆","position_id":1},{"id":350,"gid":null,"spec_id":1,"mianliao_id":1,"name":" 带扣尖领","type":0,"img_a":"/uploads/img/2016111516165998519952.png","img_b":"/uploads/img/2016111516170751531001.png","img_c":"/uploads/img/2016111516152610149535.png","introduce":null,"spec_name":"领子","position_id":1}]
         * banxing_list : [{"id":1,"name":"修身"},{"id":2,"name":"宽松"},{"id":3,"name":"合适"}]
         * default_mianliao_name : 黑
         * default_price : 399
         * price_type : 1
         * new_comment : {"id":9,"uid":75,"content":"嗯这个商品的价值在于，独一无二，然后超级超级合身，好评好评好评好评好评。特别特别六六六","img_list":["/uploads/opencv/2017061211013712c10ebc0c4e7eb56676dd46fa2dc3ef.jpg","/uploads/opencv/2017061211013759b0245ebc61cef586111a3981d0a3ff.jpg","/uploads/opencv/20170612110137ee9603f73bfdf1dc6af642ae8d324f90.jpg",""],"order_id":1069,"c_time":1497236497,"status":1,"goods_id":39,"user_name":"黄哥哥","avatar":"/uploads/user/2017060619272.jpg"}
         * comment_num : 6
         * collect_user : [{"avatar":"/uploads/img/2016122211012553521024.png","id":132,"name":"云工场7558"},{"avatar":"/uploads/user/2016122882797.jpg","id":87,"name":"Recall.L"},{"avatar":"/uploads/img/2016122211012553521024.png","id":133,"name":"云工场7768"},{"avatar":"/uploads/user/2017011156485.jpg","id":78,"name":"白山黑水"},{"avatar":"/uploads/img/2016122211012553521024.png","id":123,"name":"云工场1427"},{"avatar":"/uploads/user/2017022204883.jpg","id":186,"name":"云工场5558"},{"avatar":"/uploads/img/2016122211012553521024.png","id":189,"name":"云工场5046"},{"avatar":"/uploads/user/2017060619272.jpg","id":75,"name":"黄哥哥"}]
         * is_collect : -1
         * is_yuyue : 0
         */

        private String name;
        private String sub_name;
        private int view_num;
        private int like_num;
        private String content;
        private String content2;
        private int type;
        private int heat;
        private String thumb;
        private int classify_id;
        private int uid;
        private String default_spec_content;
        private String default_spec_ids;
        private int default_mianliao;
        private Object designer;
        private String default_mianliao_name;
        private int default_price;
        private int price_type;
        private int is_collect;
        private int is_yuyue;
        private ArrayList<String> img_list;
        private List<PriceBean> price;
        private List<DefaultSpecListBean> default_spec_list;
        private List<BanxingListBean> banxing_list;
        private NewCommentBean new_comment;
        private int comment_num;
        private String default_img;

        public String getDefault_img() {
            return default_img;
        }

        public void setDefault_img(String default_img) {
            this.default_img = default_img;
        }

        public int getCollect_num() {
            return collect_num;
        }

        public void setCollect_num(int collect_num) {
            this.collect_num = collect_num;
        }

        private int collect_num;
        private List<CollectUserBean> collect_user;

        public int getPrice_type() {
            return price_type;
        }

        public void setPrice_type(int price_type) {
            this.price_type = price_type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSub_name() {
            return sub_name;
        }

        public void setSub_name(String sub_name) {
            this.sub_name = sub_name;
        }

        public int getView_num() {
            return view_num;
        }

        public void setView_num(int view_num) {
            this.view_num = view_num;
        }

        public int getLike_num() {
            return like_num;
        }

        public void setLike_num(int like_num) {
            this.like_num = like_num;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent2() {
            return content2;
        }

        public void setContent2(String content2) {
            this.content2 = content2;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getHeat() {
            return heat;
        }

        public void setHeat(int heat) {
            this.heat = heat;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getClassify_id() {
            return classify_id;
        }

        public void setClassify_id(int classify_id) {
            this.classify_id = classify_id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getDefault_spec_content() {
            return default_spec_content;
        }

        public void setDefault_spec_content(String default_spec_content) {
            this.default_spec_content = default_spec_content;
        }

        public String getDefault_spec_ids() {
            return default_spec_ids;
        }

        public void setDefault_spec_ids(String default_spec_ids) {
            this.default_spec_ids = default_spec_ids;
        }

        public int getDefault_mianliao() {
            return default_mianliao;
        }

        public void setDefault_mianliao(int default_mianliao) {
            this.default_mianliao = default_mianliao;
        }

        public Object getDesigner() {
            return designer;
        }

        public void setDesigner(Object designer) {
            this.designer = designer;
        }

        public String getDefault_mianliao_name() {
            return default_mianliao_name;
        }

        public void setDefault_mianliao_name(String default_mianliao_name) {
            this.default_mianliao_name = default_mianliao_name;
        }

        public int getDefault_price() {
            return default_price;
        }

        public void setDefault_price(int default_price) {
            this.default_price = default_price;
        }


        public NewCommentBean getNew_comment() {
            return new_comment;
        }

        public void setNew_comment(NewCommentBean new_comment) {
            this.new_comment = new_comment;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public int getIs_yuyue() {
            return is_yuyue;
        }

        public void setIs_yuyue(int is_yuyue) {
            this.is_yuyue = is_yuyue;
        }

        public ArrayList<String> getImg_list() {
            return img_list;
        }

        public void setImg_list(ArrayList<String> img_list) {
            this.img_list = img_list;
        }

        public List<PriceBean> getPrice() {
            return price;
        }

        public void setPrice(List<PriceBean> price) {
            this.price = price;
        }

        public List<DefaultSpecListBean> getDefault_spec_list() {
            return default_spec_list;
        }

        public void setDefault_spec_list(List<DefaultSpecListBean> default_spec_list) {
            this.default_spec_list = default_spec_list;
        }

        public List<BanxingListBean> getBanxing_list() {
            return banxing_list;
        }

        public void setBanxing_list(List<BanxingListBean> banxing_list) {
            this.banxing_list = banxing_list;
        }

        public List<CollectUserBean> getCollect_user() {
            return collect_user;
        }

        public void setCollect_user(List<CollectUserBean> collect_user) {
            this.collect_user = collect_user;
        }

        public static class NewCommentBean {
            /**
             * id : 9
             * uid : 75
             * content : 嗯这个商品的价值在于，独一无二，然后超级超级合身，好评好评好评好评好评。特别特别六六六
             * img_list : ["/uploads/opencv/2017061211013712c10ebc0c4e7eb56676dd46fa2dc3ef.jpg","/uploads/opencv/2017061211013759b0245ebc61cef586111a3981d0a3ff.jpg","/uploads/opencv/20170612110137ee9603f73bfdf1dc6af642ae8d324f90.jpg",""]
             * order_id : 1069
             * c_time : 1497236497
             * status : 1
             * goods_id : 39
             * user_name : 黄哥哥
             * avatar : /uploads/user/2017060619272.jpg
             */

            private int id;
            private int uid;
            private String content;
            private int order_id;
            private int c_time;
            private int status;
            private int goods_id;
            private String user_name;
            private String avatar;
            private List<String> img_list;
            private UserGradeBean user_grade;
            private String goods_intro;

            public UserGradeBean getUser_grade() {
                return user_grade;
            }

            public void setUser_grade(UserGradeBean user_grade) {
                this.user_grade = user_grade;
            }

            public String getGoods_intro() {
                return goods_intro;
            }

            public void setGoods_intro(String goods_intro) {
                this.goods_intro = goods_intro;
            }

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
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

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public List<String> getImg_list() {
                return img_list;
            }

            public void setImg_list(List<String> img_list) {
                this.img_list = img_list;
            }


            public static class UserGradeBean {
                /**
                 * name : V1
                 * img : /uploads/img/2017051614114953995651.png
                 * img2 : /uploads/img/2017051614115598575797.png
                 * id : 1
                 * min_credit : 0.00
                 * max_credit : 999.00
                 */

                private String name;
                private String img;
                private String img2;
                private int id;
                private String min_credit;
                private String max_credit;

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

                public String getImg2() {
                    return img2;
                }

                public void setImg2(String img2) {
                    this.img2 = img2;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getMin_credit() {
                    return min_credit;
                }

                public void setMin_credit(String min_credit) {
                    this.min_credit = min_credit;
                }

                public String getMax_credit() {
                    return max_credit;
                }

                public void setMax_credit(String max_credit) {
                    this.max_credit = max_credit;
                }
            }
        }

        public static class PriceBean {
            /**
             * id : 2
             * price : 799
             * introduce : 白银
             */

            private int id;
            private int price;
            private String introduce;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }
        }

        public static class DefaultSpecListBean {
            /**
             * id : 348
             * gid : null
             * spec_id : 2
             * mianliao_id : 1
             * name : 圆摆
             * type : 0
             * img_a : /uploads/img/2016122916520957534954.png
             * img_b : /uploads/img/2016122916523110297529.png
             * img_c : /uploads/img/2017010216243248971019.png
             * introduce : null
             * spec_name : 下摆
             * position_id : 1
             */

            private int id;
            private Object gid;
            private int spec_id;
            private int mianliao_id;
            private String name;
            private int type;
            private String img_a;
            private String img_b;
            private String img_c;
            private Object introduce;
            private String spec_name;
            private int position_id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getGid() {
                return gid;
            }

            public void setGid(Object gid) {
                this.gid = gid;
            }

            public int getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(int spec_id) {
                this.spec_id = spec_id;
            }

            public int getMianliao_id() {
                return mianliao_id;
            }

            public void setMianliao_id(int mianliao_id) {
                this.mianliao_id = mianliao_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getImg_a() {
                return img_a;
            }

            public void setImg_a(String img_a) {
                this.img_a = img_a;
            }

            public String getImg_b() {
                return img_b;
            }

            public void setImg_b(String img_b) {
                this.img_b = img_b;
            }

            public String getImg_c() {
                return img_c;
            }

            public void setImg_c(String img_c) {
                this.img_c = img_c;
            }

            public Object getIntroduce() {
                return introduce;
            }

            public void setIntroduce(Object introduce) {
                this.introduce = introduce;
            }

            public String getSpec_name() {
                return spec_name;
            }

            public void setSpec_name(String spec_name) {
                this.spec_name = spec_name;
            }

            public int getPosition_id() {
                return position_id;
            }

            public void setPosition_id(int position_id) {
                this.position_id = position_id;
            }
        }

        public static class BanxingListBean {
            /**
             * id : 1
             * name : 修身
             */

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

        public static class CollectUserBean {
            /**
             * avatar : /uploads/img/2016122211012553521024.png
             * id : 132
             * name : 云工场7558
             */

            private String avatar;
            private int id;
            private String name;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
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
        }
    }
}
