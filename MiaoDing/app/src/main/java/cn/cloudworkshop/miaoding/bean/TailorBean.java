package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016-10-19 14:33
 * Email：1993911441@qq.com
 * Describe：
 */

public class TailorBean {

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

        private List<SpecListBean> spec_list;


        private List<SpecTempletsRecommendBean> spec_templets_recommend;

        private List<SpecTempletsNotmatchBean> spec_templets_notmatch;

        public List<SpecListBean> getSpec_list() {
            return spec_list;
        }

        public void setSpec_list(List<SpecListBean> spec_list) {
            this.spec_list = spec_list;
        }

        public List<SpecTempletsRecommendBean> getSpec_templets_recommend() {
            return spec_templets_recommend;
        }

        public void setSpec_templets_recommend(List<SpecTempletsRecommendBean> spec_templets_recommend) {
            this.spec_templets_recommend = spec_templets_recommend;
        }

        public List<SpecTempletsNotmatchBean> getSpec_templets_notmatch() {
            return spec_templets_notmatch;
        }

        public void setSpec_templets_notmatch(List<SpecTempletsNotmatchBean> spec_templets_notmatch) {
            this.spec_templets_notmatch = spec_templets_notmatch;
        }

        public static class SpecListBean {
            private int spec_id;
            private String img;
            private String spec_name;
            private int position_id;

            private List<ListBean> list;

            public int getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(int spec_id) {
                this.spec_id = spec_id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
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

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                private int id;
                private String name;
                private String img_a;
                private String img_b;
                private String img_c;
                private String mianliao_img;

                public String getMianliao_img() {
                    return mianliao_img;
                }

                public void setMianliao_img(String mianliao_img) {
                    this.mianliao_img = mianliao_img;
                }

                private String spec_name;
                private int position_id;
                private int spec_id;
                private int has_child;
                private String notmatch_spec_ids;
                private List<ChildBean> child_list;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getNotmatch_spec_ids() {
                    return notmatch_spec_ids;
                }

                public void setNotmatch_spec_ids(String notmatch_spec_ids) {
                    this.notmatch_spec_ids = notmatch_spec_ids;
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

                public String getImg_c() {
                    return img_c;
                }

                public void setImg_c(String img_c) {
                    this.img_c = img_c;
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

                public List<ChildBean> getChild_list() {
                    return child_list;
                }

                public void setChild_list(List<ChildBean> child_list) {
                    this.child_list = child_list;
                }

                public static class ChildBean {

                    private String name;
                    private String img_a;
                    private String img_b;
                    private String img_c;

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

                    public String getImg_c() {
                        return img_c;
                    }

                    public void setImg_c(String img_c) {
                        this.img_c = img_c;
                    }
                }
            }
        }

        public static class SpecTempletsRecommendBean {
            private String name;
            private String spec_ids;
            private int type;
            /**
             * id : 22
             * name : 方开领
             * img_a : /uploads/img/2016102411171256541025.png
             * img_b : /uploads/img/2016102411180848485350.png
             * img_c : /uploads/img/2016102411190910049549.png
             * position_id : 1
             * spec_id : 1
             * spec_name : 领子
             */

            private List<ListBean> list;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSpec_ids() {
                return spec_ids;
            }

            public void setSpec_ids(String spec_ids) {
                this.spec_ids = spec_ids;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                private int id;
                private String name;
                private String img_a;
                private String img_b;
                private String img_c;
                private int position_id;
                private int spec_id;
                private String spec_name;

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

                public int getPosition_id() {
                    return position_id;
                }

                public void setPosition_id(int position_id) {
                    this.position_id = position_id;
                }

                public int getSpec_id() {
                    return spec_id;
                }

                public void setSpec_id(int spec_id) {
                    this.spec_id = spec_id;
                }

                public String getSpec_name() {
                    return spec_name;
                }

                public void setSpec_name(String spec_name) {
                    this.spec_name = spec_name;
                }
            }
        }

        public static class SpecTempletsNotmatchBean {
            private String name;
            private String spec_ids;
            private int type;
            /**
             * id : 20
             * name : 小八字领
             * img_a : /uploads/img/2016102410530099555253.png
             * img_b : /uploads/img/2016102410554310248519.png
             * img_c : /uploads/img/2016102410584452991019.png
             * position_id : 1
             * spec_id : 1
             * spec_name : 领子
             */

            private List<ItemBean> list;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSpec_ids() {
                return spec_ids;
            }

            public void setSpec_ids(String spec_ids) {
                this.spec_ids = spec_ids;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public List<ItemBean> getList() {
                return list;
            }

            public void setList(List<ItemBean> list) {
                this.list = list;
            }

            public static class ItemBean {
                private int id;
                private String name;
                private String img_a;
                private String img_b;
                private String img_c;
                private int position_id;
                private int spec_id;
                private String spec_name;

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

                public int getPosition_id() {
                    return position_id;
                }

                public void setPosition_id(int position_id) {
                    this.position_id = position_id;
                }

                public int getSpec_id() {
                    return spec_id;
                }

                public void setSpec_id(int spec_id) {
                    this.spec_id = spec_id;
                }

                public String getSpec_name() {
                    return spec_name;
                }

                public void setSpec_name(String spec_name) {
                    this.spec_name = spec_name;
                }
            }
        }
    }
}
