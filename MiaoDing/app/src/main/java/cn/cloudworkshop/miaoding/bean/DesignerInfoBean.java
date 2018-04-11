package cn.cloudworkshop.miaoding.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Author：Libin on 2017-04-26 10:39
 * Email：1993911441@qq.com
 * Describe：
 */
public class DesignerInfoBean {

    /**
     * code : 1
     * data : {"img":"/uploads/img/2017042411502549989751.jpg","content":"<p><span style=\"color: rgb(204, 0, 0); font-family: arial; font-size: 13px; background-color: rgb(255, 255, 255);\">杜嘉班纳<\/span><span style=\"color: rgb(51, 51, 51); font-family: arial; font-size: 13px; background-color: rgb(255, 255, 255);\">公司创立于1985年，总部位于意大利米兰。今天已成为在奢侈品领域中最主要的国际集团之一。<\/span><span style=\"color: rgb(204, 0, 0); font-family: arial; font-size: 13px; background-color: rgb(255, 255, 255);\">杜嘉班纳<\/span><span style=\"color: rgb(51, 51, 51); font-family: arial; font-size: 13px; background-color: rgb(255, 255, 255);\">公司创立于1985年，总部位于意大利米兰。今天已成为在奢侈品领域中最主要的国际集团之一。<\/span><\/p>","view_nums":1,"like_nums":1,"recommend_goods_ids":null,"name":"青衣","avatar":"/uploads/img/2017041314512157481021.jpg","goods_num":1,"collect_num":2,"sale_num":"6","goods_list":[{"name":"云工场&杜嘉班纳 2017春夏新款","sub_name":"东西方文化的碰撞","thumb":"/uploads/img/2017042411432510057545.jpg","id":49,"content":2,"c_time":"04-24 11:43"}]}
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
         * img : /uploads/img/2017042411502549989751.jpg
         * content : <p><span style="color: rgb(204, 0, 0); font-family: arial; font-size: 13px; background-color: rgb(255, 255, 255);">杜嘉班纳</span><span style="color: rgb(51, 51, 51); font-family: arial; font-size: 13px; background-color: rgb(255, 255, 255);">公司创立于1985年，总部位于意大利米兰。今天已成为在奢侈品领域中最主要的国际集团之一。</span><span style="color: rgb(204, 0, 0); font-family: arial; font-size: 13px; background-color: rgb(255, 255, 255);">杜嘉班纳</span><span style="color: rgb(51, 51, 51); font-family: arial; font-size: 13px; background-color: rgb(255, 255, 255);">公司创立于1985年，总部位于意大利米兰。今天已成为在奢侈品领域中最主要的国际集团之一。</span></p>
         * view_nums : 1
         * like_nums : 1
         * recommend_goods_ids : null
         * name : 青衣
         * avatar : /uploads/img/2017041314512157481021.jpg
         * goods_num : 1
         * collect_num : 2
         * sale_num : 6
         * goods_list : [{"name":"云工场&杜嘉班纳 2017春夏新款","sub_name":"东西方文化的碰撞","thumb":"/uploads/img/2017042411432510057545.jpg","id":49,"content":2,"c_time":"04-24 11:43"}]
         */

        private String img;
        private int view_nums;
        private int like_nums;
        private String name;
        private String avatar;
        private int goods_num;
        private int collect_num;
        private int sale_num;
        private String content;
        private String tag;
        private String story;
        private String introduce;
        private String bg_img;

        public String getBg_img() {
            return bg_img;
        }

        public void setBg_img(String bg_img) {
            this.bg_img = bg_img;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getStory() {
            return story;
        }

        public void setStory(String story) {
            this.story = story;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        private List<GoodsListBean> goods_list;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getView_nums() {
            return view_nums;
        }

        public void setView_nums(int view_nums) {
            this.view_nums = view_nums;
        }

        public int getLike_nums() {
            return like_nums;
        }

        public void setLike_nums(int like_nums) {
            this.like_nums = like_nums;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public int getCollect_num() {
            return collect_num;
        }

        public void setCollect_num(int collect_num) {
            this.collect_num = collect_num;
        }

        public int getSale_num() {
            return sale_num;
        }

        public void setSale_num(int sale_num) {
            this.sale_num = sale_num;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean implements Parcelable{
            /**
             * name : 云工场&杜嘉班纳 2017春夏新款
             * sub_name : 东西方文化的碰撞
             * thumb : /uploads/img/2017042411432510057545.jpg
             * id : 49
             * content : 2
             * c_time : 04-24 11:43
             */

            private String name;
            private String sub_name;
            private String thumb;
            private int id;
            private int type;
            private String c_time;
            private int is_love;
            private int is_collect;
            private int love_num;
            private String img_info;

            public String getImg_info() {
                return img_info;
            }

            public void setImg_info(String img_info) {
                this.img_info = img_info;
            }

            public int getIs_love() {
                return is_love;
            }

            public void setIs_love(int is_love) {
                this.is_love = is_love;
            }

            public int getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(int is_collect) {
                this.is_collect = is_collect;
            }

            public int getLove_num() {
                return love_num;
            }

            public void setLove_num(int love_num) {
                this.love_num = love_num;
            }

            protected GoodsListBean(Parcel in) {
                name = in.readString();
                sub_name = in.readString();
                thumb = in.readString();
                id = in.readInt();
                type = in.readInt();
                c_time = in.readString();
            }

            public static final Creator<GoodsListBean> CREATOR = new Creator<GoodsListBean>() {
                @Override
                public GoodsListBean createFromParcel(Parcel in) {
                    return new GoodsListBean(in);
                }

                @Override
                public GoodsListBean[] newArray(int size) {
                    return new GoodsListBean[size];
                }
            };

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

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getC_time() {
                return c_time;
            }

            public void setC_time(String c_time) {
                this.c_time = c_time;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(name);
                dest.writeString(sub_name);
                dest.writeString(thumb);
                dest.writeInt(id);
                dest.writeInt(type);
                dest.writeString(c_time);
            }
        }
    }
}
