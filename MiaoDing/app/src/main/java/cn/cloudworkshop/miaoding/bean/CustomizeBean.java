package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2017-04-28 14:14
 * Email：1993911441@qq.com
 * Describe：
 */
public class CustomizeBean {


    /**
     * code : 1
     * data : {"goods_info":{"img_z":null,"img_f":null,"id":58,"banxin_ids":"4,5,6","mianliao_ids":"7,8,9","ziti_ids":"1,2,5","yanse_ids":"2,3,4,6","weizhi_ids":"3,4,5"},"banxin":[{"id":4,"classify_id":24,"name":"英伦风","img_max":null,"img_min":null,"status":2,"c_time":1493283560,"sort":1,"peijian":[{"id":100,"classify_id":24,"name":"正装领子","sort":1,"status":2,"position_id":1,"img":null,"c_time":1493283927,"display_type":1,"danwei":null,"liebiao":null,"banxin_id":"4,5","spec_list":[{"id":661,"name":"领子","spec_id":101,"has_child":0,"notmatch_spec_ids":null,"img_a":null,"img_b":null,"img_c":null}]},{"id":101,"classify_id":24,"name":"休闲领子","sort":2,"status":2,"position_id":1,"img":null,"c_time":1493286094,"display_type":1,"danwei":null,"liebiao":null,"banxin_id":"4,6","spec_list":[{"id":661,"name":"领子","spec_id":101,"has_child":0,"notmatch_spec_ids":null,"img_a":null,"img_b":null,"img_c":null}]}]},{"id":5,"classify_id":24,"name":"美国风","img_max":null,"img_min":null,"status":2,"c_time":1493283576,"sort":2,"peijian":[{"id":100,"classify_id":24,"name":"正装领子","sort":1,"status":2,"position_id":1,"img":null,"c_time":1493283927,"display_type":1,"danwei":null,"liebiao":null,"banxin_id":"4,5","spec_list":[]}]},{"id":6,"classify_id":24,"name":"法国风","img_max":null,"img_min":null,"status":2,"c_time":1493283614,"sort":2,"peijian":[{"id":101,"classify_id":24,"name":"休闲领子","sort":2,"status":2,"position_id":1,"img":null,"c_time":1493286094,"display_type":1,"danwei":null,"liebiao":null,"banxin_id":"4,6","spec_list":[{"id":662,"name":"袖子","spec_id":101,"has_child":0,"notmatch_spec_ids":null,"img_a":null,"img_b":null,"img_c":null},{"id":661,"name":"领子","spec_id":101,"has_child":0,"notmatch_spec_ids":null,"img_a":null,"img_b":null,"img_c":null}]}]}],"mianliao":[{"id":9,"classify_id":24,"name":"纯棉","sort":3,"status":2,"c_time":1493283464,"content":1,"img":"/uploads/img/2017042805334910049524.jpg","img_min":null,"price":899,"price_id":6,"introduce":"纯棉面料"}],"ziti":[{"id":1,"name":"英文","c_time":1476337998,"status":2,"sort":1,"img":"/uploads/img/2017042805340056491021.jpg","classify_id":24},{"id":2,"name":"中文","c_time":1476338011,"status":2,"sort":1,"img":"/uploads/img/2017042805340710255529.jpg","classify_id":24},{"id":5,"name":"1","c_time":1493347899,"status":2,"sort":1,"img":"/uploads/img/2017042805343054101975.jpg","classify_id":24}],"yanse":[{"id":2,"name":"红色","c_time":1,"status":2,"sort":1,"img":"/uploads/img/2016102811051054101101.png","classify_id":24},{"id":3,"name":"淡色","c_time":1,"status":2,"sort":5,"img":"/uploads/img/2016102811051810197511.png","classify_id":24},{"id":4,"name":"墨绿色","c_time":1477623488,"status":2,"sort":3,"img":"/uploads/img/2016102811053510254495.png","classify_id":24},{"id":6,"name":"1","c_time":1493347937,"status":1,"sort":1,"img":"/uploads/img/2017042805344149985455.jpg","classify_id":24}],"weizhi":[{"id":3,"name":"10221","img":"/uploads/img/2017042805351349515652.jpg","sort":4,"status":2,"c_time":1493270921,"classify_id":24},{"id":4,"name":"前","img":"/uploads/img/2017042805345749554955.jpg","sort":1,"status":2,"c_time":1493290970,"classify_id":24},{"id":5,"name":"1","img":"/uploads/img/2017042805350557535248.jpg","sort":1,"status":1,"c_time":1493347944,"classify_id":24}]}
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
         * goods_info : {"img_z":null,"img_f":null,"id":58,"banxin_ids":"4,5,6","mianliao_ids":"7,8,9","ziti_ids":"1,2,5","yanse_ids":"2,3,4,6","weizhi_ids":"3,4,5"}
         * banxin : [{"id":4,"classify_id":24,"name":"英伦风","img_max":null,"img_min":null,"status":2,"c_time":1493283560,"sort":1,"peijian":[{"id":100,"classify_id":24,"name":"正装领子","sort":1,"status":2,"position_id":1,"img":null,"c_time":1493283927,"display_type":1,"danwei":null,"liebiao":null,"banxin_id":"4,5","spec_list":[{"id":661,"name":"领子","spec_id":101,"has_child":0,"notmatch_spec_ids":null,"img_a":null,"img_b":null,"img_c":null}]},{"id":101,"classify_id":24,"name":"休闲领子","sort":2,"status":2,"position_id":1,"img":null,"c_time":1493286094,"display_type":1,"danwei":null,"liebiao":null,"banxin_id":"4,6","spec_list":[{"id":661,"name":"领子","spec_id":101,"has_child":0,"notmatch_spec_ids":null,"img_a":null,"img_b":null,"img_c":null}]}]},{"id":5,"classify_id":24,"name":"美国风","img_max":null,"img_min":null,"status":2,"c_time":1493283576,"sort":2,"peijian":[{"id":100,"classify_id":24,"name":"正装领子","sort":1,"status":2,"position_id":1,"img":null,"c_time":1493283927,"display_type":1,"danwei":null,"liebiao":null,"banxin_id":"4,5","spec_list":[]}]},{"id":6,"classify_id":24,"name":"法国风","img_max":null,"img_min":null,"status":2,"c_time":1493283614,"sort":2,"peijian":[{"id":101,"classify_id":24,"name":"休闲领子","sort":2,"status":2,"position_id":1,"img":null,"c_time":1493286094,"display_type":1,"danwei":null,"liebiao":null,"banxin_id":"4,6","spec_list":[{"id":662,"name":"袖子","spec_id":101,"has_child":0,"notmatch_spec_ids":null,"img_a":null,"img_b":null,"img_c":null},{"id":661,"name":"领子","spec_id":101,"has_child":0,"notmatch_spec_ids":null,"img_a":null,"img_b":null,"img_c":null}]}]}]
         * mianliao : [{"id":9,"classify_id":24,"name":"纯棉","sort":3,"status":2,"c_time":1493283464,"content":1,"img":"/uploads/img/2017042805334910049524.jpg","img_min":null,"price":899,"price_id":6,"introduce":"纯棉面料"}]
         * ziti : [{"id":1,"name":"英文","c_time":1476337998,"status":2,"sort":1,"img":"/uploads/img/2017042805340056491021.jpg","classify_id":24},{"id":2,"name":"中文","c_time":1476338011,"status":2,"sort":1,"img":"/uploads/img/2017042805340710255529.jpg","classify_id":24},{"id":5,"name":"1","c_time":1493347899,"status":2,"sort":1,"img":"/uploads/img/2017042805343054101975.jpg","classify_id":24}]
         * yanse : [{"id":2,"name":"红色","c_time":1,"status":2,"sort":1,"img":"/uploads/img/2016102811051054101101.png","classify_id":24},{"id":3,"name":"淡色","c_time":1,"status":2,"sort":5,"img":"/uploads/img/2016102811051810197511.png","classify_id":24},{"id":4,"name":"墨绿色","c_time":1477623488,"status":2,"sort":3,"img":"/uploads/img/2016102811053510254495.png","classify_id":24},{"id":6,"name":"1","c_time":1493347937,"status":1,"sort":1,"img":"/uploads/img/2017042805344149985455.jpg","classify_id":24}]
         * weizhi : [{"id":3,"name":"10221","img":"/uploads/img/2017042805351349515652.jpg","sort":4,"status":2,"c_time":1493270921,"classify_id":24},{"id":4,"name":"前","img":"/uploads/img/2017042805345749554955.jpg","sort":1,"status":2,"c_time":1493290970,"classify_id":24},{"id":5,"name":"1","img":"/uploads/img/2017042805350557535248.jpg","sort":1,"status":1,"c_time":1493347944,"classify_id":24}]
         */

        private GoodsInfoBean goods_info;
        private List<BanxinBean> banxin;
        private List<MianliaoBean> mianliao;
        private List<ZitiBean> ziti;
        private List<YanseBean> yanse;
        private List<WeizhiBean> weizhi;

        public GoodsInfoBean getGoods_info() {
            return goods_info;
        }

        public void setGoods_info(GoodsInfoBean goods_info) {
            this.goods_info = goods_info;
        }

        public List<BanxinBean> getBanxin() {
            return banxin;
        }

        public void setBanxin(List<BanxinBean> banxin) {
            this.banxin = banxin;
        }

        public List<MianliaoBean> getMianliao() {
            return mianliao;
        }

        public void setMianliao(List<MianliaoBean> mianliao) {
            this.mianliao = mianliao;
        }

        public List<ZitiBean> getZiti() {
            return ziti;
        }

        public void setZiti(List<ZitiBean> ziti) {
            this.ziti = ziti;
        }

        public List<YanseBean> getYanse() {
            return yanse;
        }

        public void setYanse(List<YanseBean> yanse) {
            this.yanse = yanse;
        }

        public List<WeizhiBean> getWeizhi() {
            return weizhi;
        }

        public void setWeizhi(List<WeizhiBean> weizhi) {
            this.weizhi = weizhi;
        }

        public static class GoodsInfoBean {
            /**
             * img_z : null
             * img_f : null
             * id : 58
             * banxin_ids : 4,5,6
             * mianliao_ids : 7,8,9
             * ziti_ids : 1,2,5
             * yanse_ids : 2,3,4,6
             * weizhi_ids : 3,4,5
             */

            private Object img_z;
            private Object img_f;
            private int id;
            private String banxin_ids;
            private String mianliao_ids;
            private String ziti_ids;
            private String yanse_ids;
            private String weizhi_ids;

            public Object getImg_z() {
                return img_z;
            }

            public void setImg_z(Object img_z) {
                this.img_z = img_z;
            }

            public Object getImg_f() {
                return img_f;
            }

            public void setImg_f(Object img_f) {
                this.img_f = img_f;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getBanxin_ids() {
                return banxin_ids;
            }

            public void setBanxin_ids(String banxin_ids) {
                this.banxin_ids = banxin_ids;
            }

            public String getMianliao_ids() {
                return mianliao_ids;
            }

            public void setMianliao_ids(String mianliao_ids) {
                this.mianliao_ids = mianliao_ids;
            }

            public String getZiti_ids() {
                return ziti_ids;
            }

            public void setZiti_ids(String ziti_ids) {
                this.ziti_ids = ziti_ids;
            }

            public String getYanse_ids() {
                return yanse_ids;
            }

            public void setYanse_ids(String yanse_ids) {
                this.yanse_ids = yanse_ids;
            }

            public String getWeizhi_ids() {
                return weizhi_ids;
            }

            public void setWeizhi_ids(String weizhi_ids) {
                this.weizhi_ids = weizhi_ids;
            }
        }

        public static class BanxinBean {
            /**
             * id : 4
             * classify_id : 24
             * name : 英伦风
             * img_max : null
             * img_min : null
             * status : 2
             * c_time : 1493283560
             * sort : 1
             * peijian : [{"id":100,"classify_id":24,"name":"正装领子","sort":1,"status":2,"position_id":1,"img":null,"c_time":1493283927,"display_type":1,"danwei":null,"liebiao":null,"banxin_id":"4,5","spec_list":[{"id":661,"name":"领子","spec_id":101,"has_child":0,"notmatch_spec_ids":null,"img_a":null,"img_b":null,"img_c":null}]},{"id":101,"classify_id":24,"name":"休闲领子","sort":2,"status":2,"position_id":1,"img":null,"c_time":1493286094,"display_type":1,"danwei":null,"liebiao":null,"banxin_id":"4,6","spec_list":[{"id":661,"name":"领子","spec_id":101,"has_child":0,"notmatch_spec_ids":null,"img_a":null,"img_b":null,"img_c":null}]}]
             */

            private int id;
            private int classify_id;
            private String name;
            private String img_a;
            private String img_b;
            private int status;
            private int c_time;
            private int sort;
            private String introduce;

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            private List<PeijianBean> peijian;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getClassify_id() {
                return classify_id;
            }

            public void setClassify_id(int classify_id) {
                this.classify_id = classify_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getC_time() {
                return c_time;
            }

            public void setC_time(int c_time) {
                this.c_time = c_time;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public List<PeijianBean> getPeijian() {
                return peijian;
            }

            public void setPeijian(List<PeijianBean> peijian) {
                this.peijian = peijian;
            }

            public static class PeijianBean {
                /**
                 * id : 100
                 * classify_id : 24
                 * name : 正装领子
                 * sort : 1
                 * status : 2
                 * position_id : 1
                 * img : null
                 * c_time : 1493283927
                 * display_type : 1
                 * danwei : null
                 * liebiao : null
                 * banxin_id : 4,5
                 * spec_list : [{"id":661,"name":"领子","spec_id":101,"has_child":0,"notmatch_spec_ids":null,"img_a":null,"img_b":null,"img_c":null}]
                 */

                private int id;
                private int classify_id;
                private String name;
                private int sort;
                private int status;
                private int position_id;
                private String img_a;
                private int c_time;
                private int display_type;
                private Object danwei;
                private Object liebiao;
                private String banxin_id;



                private List<SpecListBean> spec_list;

                public String getImg_a() {
                    return img_a;
                }

                public void setImg_a(String img_a) {
                    this.img_a = img_a;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getClassify_id() {
                    return classify_id;
                }

                public void setClassify_id(int classify_id) {
                    this.classify_id = classify_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getPosition_id() {
                    return position_id;
                }

                public void setPosition_id(int position_id) {
                    this.position_id = position_id;
                }



                public int getC_time() {
                    return c_time;
                }

                public void setC_time(int c_time) {
                    this.c_time = c_time;
                }

                public int getDisplay_type() {
                    return display_type;
                }

                public void setDisplay_type(int display_type) {
                    this.display_type = display_type;
                }

                public Object getDanwei() {
                    return danwei;
                }

                public void setDanwei(Object danwei) {
                    this.danwei = danwei;
                }

                public Object getLiebiao() {
                    return liebiao;
                }

                public void setLiebiao(Object liebiao) {
                    this.liebiao = liebiao;
                }

                public String getBanxin_id() {
                    return banxin_id;
                }

                public void setBanxin_id(String banxin_id) {
                    this.banxin_id = banxin_id;
                }

                public List<SpecListBean> getSpec_list() {
                    return spec_list;
                }

                public void setSpec_list(List<SpecListBean> spec_list) {
                    this.spec_list = spec_list;
                }

                public static class SpecListBean {
                    /**
                     * id : 661
                     * name : 领子
                     * spec_id : 101
                     * has_child : 0
                     * notmatch_spec_ids : null
                     * img_a : null
                     * img_b : null
                     * img_c : null
                     */

                    private int id;
                    private String name;
                    private int spec_id;
                    private int has_child;
                    private Object notmatch_spec_ids;
                    private String img_a;
                    private String img_b;
                    private String img_c;
                    private int mianliao_id;
                    private String introduce;

                    public String getIntroduce() {
                        return introduce;
                    }

                    public void setIntroduce(String introduce) {
                        this.introduce = introduce;
                    }

                    public int getMianliao_id() {
                        return mianliao_id;
                    }

                    public void setMianliao_id(int mianliao_id) {
                        this.mianliao_id = mianliao_id;
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

                    public int getSpec_id() {
                        return spec_id;
                    }

                    public void setSpec_id(int spec_id) {
                        this.spec_id = spec_id;
                    }

                    public int getHas_child() {
                        return has_child;
                    }

                    public void setHas_child(int has_child) {
                        this.has_child = has_child;
                    }

                    public Object getNotmatch_spec_ids() {
                        return notmatch_spec_ids;
                    }

                    public void setNotmatch_spec_ids(Object notmatch_spec_ids) {
                        this.notmatch_spec_ids = notmatch_spec_ids;
                    }


                }
            }
        }

        public static class MianliaoBean {
            /**
             * id : 9
             * classify_id : 24
             * name : 纯棉
             * sort : 3
             * status : 2
             * c_time : 1493283464
             * content : 1
             * img : /uploads/img/2017042805334910049524.jpg
             * img_min : null
             * price : 899
             * price_id : 6
             * introduce : 纯棉面料
             */

            private int id;
            private int classify_id;
            private String name;
            private int sort;
            private int status;
            private int c_time;
            private int type;
            private String img_a;
            private String img_b;
            private String goods_img;

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
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

            private double price;
            private int price_id;
            private String introduce;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getClassify_id() {
                return classify_id;
            }

            public void setClassify_id(int classify_id) {
                this.classify_id = classify_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getC_time() {
                return c_time;
            }

            public void setC_time(int c_time) {
                this.c_time = c_time;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getPrice_id() {
                return price_id;
            }

            public void setPrice_id(int price_id) {
                this.price_id = price_id;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }
        }

        public static class ZitiBean {
            /**
             * id : 1
             * name : 英文
             * c_time : 1476337998
             * status : 2
             * sort : 1
             * img : /uploads/img/2017042805340056491021.jpg
             * classify_id : 24
             */

            private int id;
            private String name;
            private int c_time;
            private int status;
            private int sort;
            private String img;
            private int classify_id;

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

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getClassify_id() {
                return classify_id;
            }

            public void setClassify_id(int classify_id) {
                this.classify_id = classify_id;
            }
        }

        public static class YanseBean {
            /**
             * id : 2
             * name : 红色
             * c_time : 1
             * status : 2
             * sort : 1
             * img : /uploads/img/2016102811051054101101.png
             * classify_id : 24
             */

            private int id;
            private String name;
            private int c_time;
            private int status;
            private int sort;
            private String img;
            private int classify_id;

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

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getClassify_id() {
                return classify_id;
            }

            public void setClassify_id(int classify_id) {
                this.classify_id = classify_id;
            }
        }

        public static class WeizhiBean {
            /**
             * id : 3
             * name : 10221
             * img : /uploads/img/2017042805351349515652.jpg
             * sort : 4
             * status : 2
             * c_time : 1493270921
             * classify_id : 24
             */

            private int id;
            private String name;
            private String img;
            private int sort;
            private int status;
            private int c_time;
            private int classify_id;

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

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getC_time() {
                return c_time;
            }

            public void setC_time(int c_time) {
                this.c_time = c_time;
            }

            public int getClassify_id() {
                return classify_id;
            }

            public void setClassify_id(int classify_id) {
                this.classify_id = classify_id;
            }
        }
    }
}
