package com.sy.globaltake_driver.Bean;

/**
 * Created by sunnetdesign3 on 2017/2/22.
 */
public class QiangDanBean {

    /**
     * code : 1
     * message : 抢单成功！
     * result : {"fdIs_driverscore":0,"fdOrderLastPrice":0,"fdId":"1487745530199C58VTH","fdOrderScore":5,"fdOrderTime":1487745530199,"fdOrderendTime":0,"fdOrderPrice":0,"fdOrderPreferentialPrice":0,"fdOrderStatus":2,"fdOrderNum":"201702221438504765"}
     */

    private String code;
    private String message;
    /**
     * fdIs_driverscore : 0
     * fdOrderLastPrice : 0
     * fdId : 1487745530199C58VTH
     * fdOrderScore : 5
     * fdOrderTime : 1487745530199
     * fdOrderendTime : 0
     * fdOrderPrice : 0
     * fdOrderPreferentialPrice : 0
     * fdOrderStatus : 2
     * fdOrderNum : 201702221438504765
     */

    private ResultBean result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private int fdIs_driverscore;
        private int fdOrderLastPrice;
        private String fdId;
        private int fdOrderScore;
        private long fdOrderTime;
        private int fdOrderendTime;
        private int fdOrderPrice;
        private int fdOrderPreferentialPrice;
        private int fdOrderStatus;
        private String fdOrderNum;

        public int getFdIs_driverscore() {
            return fdIs_driverscore;
        }

        public void setFdIs_driverscore(int fdIs_driverscore) {
            this.fdIs_driverscore = fdIs_driverscore;
        }

        public int getFdOrderLastPrice() {
            return fdOrderLastPrice;
        }

        public void setFdOrderLastPrice(int fdOrderLastPrice) {
            this.fdOrderLastPrice = fdOrderLastPrice;
        }

        public String getFdId() {
            return fdId;
        }

        public void setFdId(String fdId) {
            this.fdId = fdId;
        }

        public int getFdOrderScore() {
            return fdOrderScore;
        }

        public void setFdOrderScore(int fdOrderScore) {
            this.fdOrderScore = fdOrderScore;
        }

        public long getFdOrderTime() {
            return fdOrderTime;
        }

        public void setFdOrderTime(long fdOrderTime) {
            this.fdOrderTime = fdOrderTime;
        }

        public int getFdOrderendTime() {
            return fdOrderendTime;
        }

        public void setFdOrderendTime(int fdOrderendTime) {
            this.fdOrderendTime = fdOrderendTime;
        }

        public int getFdOrderPrice() {
            return fdOrderPrice;
        }

        public void setFdOrderPrice(int fdOrderPrice) {
            this.fdOrderPrice = fdOrderPrice;
        }

        public int getFdOrderPreferentialPrice() {
            return fdOrderPreferentialPrice;
        }

        public void setFdOrderPreferentialPrice(int fdOrderPreferentialPrice) {
            this.fdOrderPreferentialPrice = fdOrderPreferentialPrice;
        }

        public int getFdOrderStatus() {
            return fdOrderStatus;
        }

        public void setFdOrderStatus(int fdOrderStatus) {
            this.fdOrderStatus = fdOrderStatus;
        }

        public String getFdOrderNum() {
            return fdOrderNum;
        }

        public void setFdOrderNum(String fdOrderNum) {
            this.fdOrderNum = fdOrderNum;
        }
    }
}
