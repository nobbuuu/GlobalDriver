package com.sy.globaltake_driver.Bean;

/**
 * Created by sunnetdesign3 on 2017/3/21.
 */
public class MsgitemBean {

    /**
     * code : 1
     * message : 执行成功!
     * result : {"fdAuthor":"dddsas","fdContent":"您的加盟时间还有2天到期","fdPushStatus":1,"fdId":"1490066186326PNQ3V1","fdStatus":1,"fdTitle":"系统消息","fdCreateTime":1490066186326}
     */

    private String code;
    private String message;
    /**
     * fdAuthor : dddsas
     * fdContent : 您的加盟时间还有2天到期
     * fdPushStatus : 1
     * fdId : 1490066186326PNQ3V1
     * fdStatus : 1
     * fdTitle : 系统消息
     * fdCreateTime : 1490066186326
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
        private String fdAuthor;
        private String fdContent;
        private int fdPushStatus;
        private String fdId;
        private int fdStatus;
        private String fdTitle;
        private long fdCreateTime;

        public String getFdAuthor() {
            return fdAuthor;
        }

        public void setFdAuthor(String fdAuthor) {
            this.fdAuthor = fdAuthor;
        }

        public String getFdContent() {
            return fdContent;
        }

        public void setFdContent(String fdContent) {
            this.fdContent = fdContent;
        }

        public int getFdPushStatus() {
            return fdPushStatus;
        }

        public void setFdPushStatus(int fdPushStatus) {
            this.fdPushStatus = fdPushStatus;
        }

        public String getFdId() {
            return fdId;
        }

        public void setFdId(String fdId) {
            this.fdId = fdId;
        }

        public int getFdStatus() {
            return fdStatus;
        }

        public void setFdStatus(int fdStatus) {
            this.fdStatus = fdStatus;
        }

        public String getFdTitle() {
            return fdTitle;
        }

        public void setFdTitle(String fdTitle) {
            this.fdTitle = fdTitle;
        }

        public long getFdCreateTime() {
            return fdCreateTime;
        }

        public void setFdCreateTime(long fdCreateTime) {
            this.fdCreateTime = fdCreateTime;
        }
    }
}
