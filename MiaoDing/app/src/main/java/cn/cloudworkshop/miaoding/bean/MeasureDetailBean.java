package cn.cloudworkshop.miaoding.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author：Libin on 2017/1/6 16:59
 * Email：1993911441@qq.com
 * Describe：
 */
public class MeasureDetailBean {

    /**
     * code : 1
     * data : [[{"name":"id","value":21},{"name":"姓名","value":"e"},{"name":"性别","value":0},{"name":"年龄","value":111},{"name":"身高","value":111},{"name":"体重","value":111},{"name":"收获人","value":"sdfsd"},{"name":"收货人手机号","value":"111111111"},{"name":"省","value":"北京市"},{"name":"市","value":"北京市"},{"name":"区","value":"西城区"},{"name":"详细地址","value":"dfsdfs"},{"name":"发货方式","value":"0"},{"name":"创建时间","value":1479374897},{"name":"状态","value":1},{"name":"肩","value":"偏前"},{"name":"背","value":"正常"},{"name":"臂","value":"偏前"},{"name":"胸","value":"正常"},{"name":"左肩","value":"偏前"},{"name":"右肩","value":"偏后"},{"name":"肚子","value":"正常"},{"name":"裤线","value":"正常"},{"name":"上衣类型","value":"正常"},{"name":"肩宽-净体","value":"111"},{"name":"肩宽-成品","value":"111"},{"name":"袖长左-净体","value":"111"},{"name":"袖长左-成品","value":"111"},{"name":"袖长右-净体","value":"111"},{"name":"袖长右-成品","value":"111"},{"name":"臂围-净体","value":"1"},{"name":"臂围-成品","value":"1"},{"name":"臂围-净体","value":"1"},{"name":"臂围-成品","value":"1"},{"name":"袖口-净体","value":"1"},{"name":"袖口-成品","value":"1"},{"name":"背宽-净体","value":"1"},{"name":"背宽-成品","value":null},{"name":"后中-净体","value":"1"},{"name":"后中-成品","value":"1"},{"name":"前衣长-净体","value":"1"},{"name":"前衣长-成品","value":"1"},{"name":"领围-净体","value":"1"},{"name":"领围-成品","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"腰围","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"sdfsdf"},{"name":"","value":"dsfsd"},{"name":"","value":"fdsfsd"},{"name":"","value":"衬衫,西装,风衣,"},{"name":"","value":"量体师"},{"name":"","value":80},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null}],[{"name":"id","value":22},{"name":"姓名","value":"e"},{"name":"性别","value":0},{"name":"年龄","value":111},{"name":"身高","value":111},{"name":"体重","value":111},{"name":"收获人","value":"sdfsd"},{"name":"收货人手机号","value":"111111111"},{"name":"省","value":"北京市"},{"name":"市","value":"北京市"},{"name":"区","value":"西城区"},{"name":"详细地址","value":"dfsdfs"},{"name":"发货方式","value":"0"},{"name":"创建时间","value":1479374985},{"name":"状态","value":1},{"name":"肩","value":"偏前"},{"name":"背","value":"正常"},{"name":"臂","value":"偏前"},{"name":"胸","value":"正常"},{"name":"左肩","value":"偏前"},{"name":"右肩","value":"偏后"},{"name":"肚子","value":"正常"},{"name":"裤线","value":"正常"},{"name":"上衣类型","value":"正常"},{"name":"肩宽-净体","value":"111"},{"name":"肩宽-成品","value":"111"},{"name":"袖长左-净体","value":"111"},{"name":"袖长左-成品","value":"111"},{"name":"袖长右-净体","value":"111"},{"name":"袖长右-成品","value":"111"},{"name":"臂围-净体","value":"1"},{"name":"臂围-成品","value":"1"},{"name":"臂围-净体","value":"1"},{"name":"臂围-成品","value":"1"},{"name":"袖口-净体","value":"1"},{"name":"袖口-成品","value":"1"},{"name":"背宽-净体","value":"1"},{"name":"背宽-成品","value":null},{"name":"后中-净体","value":"1"},{"name":"后中-成品","value":"1"},{"name":"前衣长-净体","value":"1"},{"name":"前衣长-成品","value":"1"},{"name":"领围-净体","value":"1"},{"name":"领围-成品","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"腰围","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"1"},{"name":"","value":"sdfsdf"},{"name":"","value":"dsfsd"},{"name":"","value":"fdsfsd"},{"name":"","value":"衬衫,西装,风衣,"},{"name":"","value":"量体师"},{"name":"","value":80},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null},{"name":"","value":null}]]
     * msg : 成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean{

        /**
         * name : id
         * value : 21
         */

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
