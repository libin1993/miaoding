package cn.cloudworkshop.miaoding.bean;

import java.util.List;

/**
 * Author：Libin on 2016/11/18 08:27
 * Email：1993911441@qq.com
 * Describe：
 */
public class LogisticsBean {

    /**
     * message : ok
     * nu : 211304078908
     * ischeck : 0
     * condition : B00
     * com : huitongkuaidi
     * status : 200
     * state : 0
     * data : [{"time":"2016-11-18 01:44:31","ftime":"2016-11-18 01:44:31","context":"杭州市|发件|杭州市【杭州转运中心】，正发往【上海嘉定转运中心】"},{"time":"2016-11-18 01:40:18","ftime":"2016-11-18 01:40:18","context":"杭州市|到件|到杭州市【杭州转运中心】"},{"time":"2016-11-16 23:00:04","ftime":"2016-11-16 23:00:04","context":"杭州市|到件|到杭州市【康桥分部】"},{"time":"2016-11-16 20:16:59","ftime":"2016-11-16 20:16:59","context":"杭州市|收件|杭州市【康桥分部五分部】，【G代永/18969148089】已揽收"}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private String state;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2016-11-18 01:44:31
         * ftime : 2016-11-18 01:44:31
         * context : 杭州市|发件|杭州市【杭州转运中心】，正发往【上海嘉定转运中心】
         */

        private String time;
        private String ftime;
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }
}
