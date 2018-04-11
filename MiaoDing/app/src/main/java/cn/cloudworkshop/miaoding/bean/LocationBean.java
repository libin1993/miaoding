package cn.cloudworkshop.miaoding.bean;

/**
 * Author：Libin on 2016/11/7 11:12
 * Email：1993911441@qq.com
 * Describe：
 */
public class LocationBean {
    private double longitude;//经度
    private double latitude;//纬度
    private String title;//信息标题
    private String text;//信息内容
    public LocationBean(double lon, double lat, String title, String text){
        this.longitude = lon;
        this.latitude = lat;
        this.title = title;
        this.text = text;
    }

    public LocationBean(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
    public String getTitle() {
        return title;
    }
    public String getText(){
        return text;
    }

}
