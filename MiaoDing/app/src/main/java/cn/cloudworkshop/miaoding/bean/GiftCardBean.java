package cn.cloudworkshop.miaoding.bean;

/**
 * Author：Libin on 2017-09-20 17:23
 * Email：1993911441@qq.com
 * Describe：
 */
public class GiftCardBean {

    /**
     * code : 1
     * msg : 获取成功
     * info : {"gift_card":"0"}
     */

    private int code;
    private String msg;
    private InfoBean info;

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

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * gift_card : 0
         */

        private String gift_card;
        private String card_rule;

        public String getCard_rule() {
            return card_rule;
        }

        public void setCard_rule(String card_rule) {
            this.card_rule = card_rule;
        }

        public String getGift_card() {
            return gift_card;
        }

        public void setGift_card(String gift_card) {
            this.gift_card = gift_card;
        }
    }
}
