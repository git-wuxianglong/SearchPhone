package app.wuxl.com.searchphone.model;

/**
 * Author:wuxianglong;
 * Time:2017/8/1.
 */
public class Phone {


    /**
     * status : 0
     * msg : ok
     * result : {"province":"浙江","city":"杭州","company":"中国移动","cardtype":"GSM"}
     */

    private String status;
    private String msg;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * province : 浙江
         * city : 杭州
         * company : 中国移动
         * cardtype : GSM
         */

        private String province;
        private String city;
        private String company;
        private String cardtype;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCardtype() {
            return cardtype;
        }

        public void setCardtype(String cardtype) {
            this.cardtype = cardtype;
        }
    }
}
