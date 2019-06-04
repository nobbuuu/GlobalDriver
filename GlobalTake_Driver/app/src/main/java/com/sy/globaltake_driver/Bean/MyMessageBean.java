package com.sy.globaltake_driver.Bean;

import java.util.List;

/**
 * Created by sunnetdesign3 on 2017/3/2.
 */
public class MyMessageBean {

    /**
     * code : 1
     * message : 执行成功!
     * result : [{"fdEnContent":"Good news,good news,big news!","fdAuthor":"刘廉俊","fdContent":"好消息，好消息，特大好消息！","fdPushStatus":1,"fdId":"1487661589313DTOW1R","fdTitle":"消息","fdCreateTime":1487661589313,"fdEnTitle":"news"},{"fdEnContent":"Are you kidding me？？？","fdAuthor":"LLJ","fdContent":"你逗我呢？？？","fdPushStatus":1,"fdId":"1487670206540PWTAO2","fdTitle":"消息","fdCreateTime":1487670206540,"fdEnTitle":"news"},{"fdEnContent":"This is new massage！","fdAuthor":"LLJ","fdContent":"这是一条新消息","fdPushStatus":1,"fdId":"1487670904963MRLK9Z","fdTitle":"注意","fdCreateTime":1487670904963,"fdEnTitle":"Attention please"},{"fdEnContent":"news","fdAuthor":"LLJ","fdContent":"消息","fdPushStatus":1,"fdId":"1487671760342L0BSC3","fdTitle":"推送通知","fdCreateTime":1487671760342,"fdEnTitle":"Push Notification"},{"fdEnContent":"hhhh","fdAuthor":"LLJ","fdContent":"好说好说","fdPushStatus":1,"fdId":"1487674039451L6MUXK","fdTitle":"告诉你一个秘密","fdCreateTime":1487674039451,"fdEnTitle":"Tell you a secret"}]
     */

    private String code;
    private String message;
    /**
     * fdEnContent : Good news,good news,big news!
     * fdAuthor : 刘廉俊
     * fdContent : 好消息，好消息，特大好消息！
     * fdPushStatus : 1
     * fdId : 1487661589313DTOW1R
     * fdTitle : 消息
     * fdCreateTime : 1487661589313
     * fdEnTitle : news
     */

    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String fdEnContent;
        private String fdAuthor;
        private String fdContent;
        private int fdPushStatus;
        private String fdId;
        private String fdTitle;
        private long fdCreateTime;
        private String fdEnTitle;

        public String getFdEnContent() {
            return fdEnContent;
        }

        public void setFdEnContent(String fdEnContent) {
            this.fdEnContent = fdEnContent;
        }

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

        public String getFdEnTitle() {
            return fdEnTitle;
        }

        public void setFdEnTitle(String fdEnTitle) {
            this.fdEnTitle = fdEnTitle;
        }
    }
}
