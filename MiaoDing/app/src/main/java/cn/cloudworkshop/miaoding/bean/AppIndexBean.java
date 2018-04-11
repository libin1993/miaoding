package cn.cloudworkshop.miaoding.bean;

/**
 * Author：Libin on 2016/11/10 15:25
 * Email：1993911441@qq.com
 * Describe：默认配置
 */
public class AppIndexBean {

    /**
     * code : 1
     * data : {"id":1,"host":"http://www.cloudworkshop.cn/","news_detail_url":"http://192.168.1.50:4000/web/designer/designer.html","version":{"android":{"version":"1.0.3","download_url":"http://139.196.113.61/index.php/admin/admin/index.html","remark":"阿萨达是"},"ios":{"version":"1.0.1","download_url":"http://139.196.113.61/index.php/admin/admin/index.html","remark":"萨达"}},"version_remark":"1.asdsa 2.asdsa 3.sad ","download_url":"http://139.196.113.61/cloudshop.apk","appid":"2016111502847323","privateKey":"MIICXQIBAAKBgQC9j84tVtBuIUDGx4y8O22neT62Un4wg+Bkg0RyVsez69IAHOB/sA4h1f/YLJVvqkMuWXeoO8bAnqiItuSFA1+7ErCeb3Z9NoAeYlf81CQ1/y1XKF5x/AqZdSZD5g/8v20wy/MRDhHciTMmhosJJc7LgefpWz/wHT5bitie4NSMBQIDAQABAoGBAJlarIa7c/WUi04FA9MIFObmzJg9Vn8zQSavl4EUuYzSG9shgXFrYZN4B/m/38ktqhnmIUqFyX3tHRU1ONxu5VMP/Zh0yRnFujGOWOsBnglFldi0prGDGiRdqVfmjEeXpsdHej+f9j/sq0x1p2crSbCyfuXPF2xH0GW9M2AyTSABAkEA+0zCyDd+Bjr33ZhkpNFudjdZZNx4b1ZEKOOxNx8c6hUZiEpYfP4bJ9USBOCvWkjQoehUeKlbXAefPHRGORUUBQJBAMEbcEuoPq8AuF6BNsNoOSBVHYSf8bp/yuBpu+AeLdjbgKTm0ve7FNEFCbNdi5tcNEjSQWCCKOQDmXsBoaOTGAECQFO21oxsMAdTTY78XUqqmem3tofrChPM4RfeAgMi0jcIoGDOo3vACrvSNxZ44vFooFfPVZR8lnqp71nsQhhx9SECQEPzOMeWk0AXW8j9ZNzLztY+vR1O/stmbmgbLJ4HYJCfZJwiEPhVH1URlcFE2Hw5rLg3LAQeqO7bCWXMRCl/+AECQQDkUfrj4DmnqAFwBNz1S/22QfvmGSqQ5akS4YL6CVmcUuafnq5E0OxXz6mCn9w7kF5Nxs/2XuqPeW75DD+HyRjL","web_host":"http://www.cloudworkshop.cn/web/jquery-obj/static","login_img":"/uploads/img/2016120114104452515010.jpg","price_remark":"性价比高/个性轻奢/顶级品质","order_expire_time":30}
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
         * host : http://www.cloudworkshop.cn/
         * news_detail_url : http://192.168.1.50:4000/web/designer/designer.html
         * version : {"android":{"version":"1.0.3","download_url":"http://139.196.113.61/index.php/admin/admin/index.html","remark":"阿萨达是"},"ios":{"version":"1.0.1","download_url":"http://139.196.113.61/index.php/admin/admin/index.html","remark":"萨达"}}
         * version_remark : 1.asdsa 2.asdsa 3.sad
         * download_url : http://139.196.113.61/cloudshop.apk
         * appid : 2016111502847323
         * privateKey : MIICXQIBAAKBgQC9j84tVtBuIUDGx4y8O22neT62Un4wg+Bkg0RyVsez69IAHOB/sA4h1f/YLJVvqkMuWXeoO8bAnqiItuSFA1+7ErCeb3Z9NoAeYlf81CQ1/y1XKF5x/AqZdSZD5g/8v20wy/MRDhHciTMmhosJJc7LgefpWz/wHT5bitie4NSMBQIDAQABAoGBAJlarIa7c/WUi04FA9MIFObmzJg9Vn8zQSavl4EUuYzSG9shgXFrYZN4B/m/38ktqhnmIUqFyX3tHRU1ONxu5VMP/Zh0yRnFujGOWOsBnglFldi0prGDGiRdqVfmjEeXpsdHej+f9j/sq0x1p2crSbCyfuXPF2xH0GW9M2AyTSABAkEA+0zCyDd+Bjr33ZhkpNFudjdZZNx4b1ZEKOOxNx8c6hUZiEpYfP4bJ9USBOCvWkjQoehUeKlbXAefPHRGORUUBQJBAMEbcEuoPq8AuF6BNsNoOSBVHYSf8bp/yuBpu+AeLdjbgKTm0ve7FNEFCbNdi5tcNEjSQWCCKOQDmXsBoaOTGAECQFO21oxsMAdTTY78XUqqmem3tofrChPM4RfeAgMi0jcIoGDOo3vACrvSNxZ44vFooFfPVZR8lnqp71nsQhhx9SECQEPzOMeWk0AXW8j9ZNzLztY+vR1O/stmbmgbLJ4HYJCfZJwiEPhVH1URlcFE2Hw5rLg3LAQeqO7bCWXMRCl/+AECQQDkUfrj4DmnqAFwBNz1S/22QfvmGSqQ5akS4YL6CVmcUuafnq5E0OxXz6mCn9w7kF5Nxs/2XuqPeW75DD+HyRjL
         * web_host : http://www.cloudworkshop.cn/web/jquery-obj/static
         * login_img : /uploads/img/2016120114104452515010.jpg
         * price_remark : 性价比高/个性轻奢/顶级品质
         * order_expire_time : 30
         */

        private int id;
        private String host;
        private String news_detail_url;
        private VersionBean version;
        private String version_remark;
        private String download_url;
        private String appid;
        private String privateKey;
        private String web_host;
        private String login_img;
        private String price_remark;
        private int order_expire_time;
        private String kf_tel;
        private String reg_agreement;
        private String lt_agreement;
        private String user_manual;
        private String cobbler_banner;

        public String getCobbler_banner() {
            return cobbler_banner;
        }

        public void setCobbler_banner(String cobbler_banner) {
            this.cobbler_banner = cobbler_banner;
        }

        public String getUser_manual() {
            return user_manual;
        }

        public void setUser_manual(String user_manual) {
            this.user_manual = user_manual;
        }

        public String getLt_agreement() {
            return lt_agreement;
        }

        public void setLt_agreement(String lt_agreement) {
            this.lt_agreement = lt_agreement;
        }

        public String getReg_agreement() {
            return reg_agreement;
        }

        public void setReg_agreement(String reg_agreement) {
            this.reg_agreement = reg_agreement;
        }

        public String getKf_tel() {
            return kf_tel;
        }

        public void setKf_tel(String kf_tel) {
            this.kf_tel = kf_tel;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getNews_detail_url() {
            return news_detail_url;
        }

        public void setNews_detail_url(String news_detail_url) {
            this.news_detail_url = news_detail_url;
        }

        public VersionBean getVersion() {
            return version;
        }

        public void setVersion(VersionBean version) {
            this.version = version;
        }

        public String getVersion_remark() {
            return version_remark;
        }

        public void setVersion_remark(String version_remark) {
            this.version_remark = version_remark;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getWeb_host() {
            return web_host;
        }

        public void setWeb_host(String web_host) {
            this.web_host = web_host;
        }

        public String getLogin_img() {
            return login_img;
        }

        public void setLogin_img(String login_img) {
            this.login_img = login_img;
        }

        public String getPrice_remark() {
            return price_remark;
        }

        public void setPrice_remark(String price_remark) {
            this.price_remark = price_remark;
        }

        public int getOrder_expire_time() {
            return order_expire_time;
        }

        public void setOrder_expire_time(int order_expire_time) {
            this.order_expire_time = order_expire_time;
        }

        public static class VersionBean {
            /**
             * android : {"version":"1.0.3","download_url":"http://139.196.113.61/index.php/admin/admin/index.html","remark":"阿萨达是"}
             * ios : {"version":"1.0.1","download_url":"http://139.196.113.61/index.php/admin/admin/index.html","remark":"萨达"}
             */

            private AndroidBean android;
            private IosBean ios;

            public AndroidBean getAndroid() {
                return android;
            }

            public void setAndroid(AndroidBean android) {
                this.android = android;
            }

            public IosBean getIos() {
                return ios;
            }

            public void setIos(IosBean ios) {
                this.ios = ios;
            }

            public static class AndroidBean {
                /**
                 * version : 1.0.3
                 * download_url : http://139.196.113.61/index.php/admin/admin/index.html
                 * remark : 阿萨达是
                 */

                private String version;
                private String download_url;
                private String remark;

                public String getVersion() {
                    return version;
                }

                public void setVersion(String version) {
                    this.version = version;
                }

                public String getDownload_url() {
                    return download_url;
                }

                public void setDownload_url(String download_url) {
                    this.download_url = download_url;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }
            }

            public static class IosBean {
                /**
                 * version : 1.0.1
                 * download_url : http://139.196.113.61/index.php/admin/admin/index.html
                 * remark : 萨达
                 */

                private String version;
                private String download_url;
                private String remark;

                public String getVersion() {
                    return version;
                }

                public void setVersion(String version) {
                    this.version = version;
                }

                public String getDownload_url() {
                    return download_url;
                }

                public void setDownload_url(String download_url) {
                    this.download_url = download_url;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }
            }
        }
    }
}
