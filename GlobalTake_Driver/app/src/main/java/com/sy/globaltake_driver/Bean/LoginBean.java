package com.sy.globaltake_driver.Bean;

import java.util.List;

/**
 * Created by sunnetdesign3 on 2017/2/10.
 */
public class LoginBean {

    /**
     * code : 1
     * message : 登录成功！
     * result : [{"driver":{"fdDriverCode":"16595","fdEmail":"1184824553@qq.com","fdVehicleTypeNum":"宝安出租车","fdUserName":"李显友","fdIconUrl":"upload/userImg/607a55a4-2903-4271-8331-872cf531652d.jpg","fdPhone":"18277750576","fdAccount":"是滴是滴","fdDriverCheck":"1","fdDriverAvgSocre":4.9,"fdNickName":"的士速递","fdExpireDate":1546300800000,"fdDriverlatitude":22.499012,"fdDriveStatus":1,"vehicleType_fdName":"1","fdDriverSex":1,"fdId":"1486710028198CQGA7X","fdPassowrd":"e10adc3949ba59abbe56e057f20f883e","fdTelephone":"18277750576","identity":"36078114","fdDriverlongitude":113.915082,"fdAddress":"","vehicleType_fdId":"14841158408490A9RJ3"},"vehicle":{"vehicleType_fdName":"1","fdEnVehicleName":"sss","fdEnVehiclecolor":"bai","fdId":"14866343354088HAMW9","fdVehiclecolor":"bai","fdLastTime":1489226218000,"fdCreateTime":1486634335408,"fdVehicleCompanyCode":"sss","fdVehicleNumber":"asss","fdVehicleName":"s","fdVehicleStatus":1}}]
     */

    private String code;
    private String message;
    /**
     * driver : {"fdDriverCode":"16595","fdEmail":"1184824553@qq.com","fdVehicleTypeNum":"宝安出租车","fdUserName":"李显友","fdIconUrl":"upload/userImg/607a55a4-2903-4271-8331-872cf531652d.jpg","fdPhone":"18277750576","fdAccount":"是滴是滴","fdDriverCheck":"1","fdDriverAvgSocre":4.9,"fdNickName":"的士速递","fdExpireDate":1546300800000,"fdDriverlatitude":22.499012,"fdDriveStatus":1,"vehicleType_fdName":"1","fdDriverSex":1,"fdId":"1486710028198CQGA7X","fdPassowrd":"e10adc3949ba59abbe56e057f20f883e","fdTelephone":"18277750576","identity":"36078114","fdDriverlongitude":113.915082,"fdAddress":"","vehicleType_fdId":"14841158408490A9RJ3"}
     * vehicle : {"vehicleType_fdName":"1","fdEnVehicleName":"sss","fdEnVehiclecolor":"bai","fdId":"14866343354088HAMW9","fdVehiclecolor":"bai","fdLastTime":1489226218000,"fdCreateTime":1486634335408,"fdVehicleCompanyCode":"sss","fdVehicleNumber":"asss","fdVehicleName":"s","fdVehicleStatus":1}
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
        /**
         * fdDriverCode : 16595
         * fdEmail : 1184824553@qq.com
         * fdVehicleTypeNum : 宝安出租车
         * fdUserName : 李显友
         * fdIconUrl : upload/userImg/607a55a4-2903-4271-8331-872cf531652d.jpg
         * fdPhone : 18277750576
         * fdAccount : 是滴是滴
         * fdDriverCheck : 1
         * fdDriverAvgSocre : 4.9
         * fdNickName : 的士速递
         * fdExpireDate : 1546300800000
         * fdDriverlatitude : 22.499012
         * fdDriveStatus : 1
         * vehicleType_fdName : 1
         * fdDriverSex : 1
         * fdId : 1486710028198CQGA7X
         * fdPassowrd : e10adc3949ba59abbe56e057f20f883e
         * fdTelephone : 18277750576
         * identity : 36078114
         * fdDriverlongitude : 113.915082
         * fdAddress :
         * vehicleType_fdId : 14841158408490A9RJ3
         */

        private DriverBean driver;
        /**
         * vehicleType_fdName : 1
         * fdEnVehicleName : sss
         * fdEnVehiclecolor : bai
         * fdId : 14866343354088HAMW9
         * fdVehiclecolor : bai
         * fdLastTime : 1489226218000
         * fdCreateTime : 1486634335408
         * fdVehicleCompanyCode : sss
         * fdVehicleNumber : asss
         * fdVehicleName : s
         * fdVehicleStatus : 1
         */

        private VehicleBean vehicle;

        public DriverBean getDriver() {
            return driver;
        }

        public void setDriver(DriverBean driver) {
            this.driver = driver;
        }

        public VehicleBean getVehicle() {
            return vehicle;
        }

        public void setVehicle(VehicleBean vehicle) {
            this.vehicle = vehicle;
        }

        public static class DriverBean {
            private String fdDriverCode;
            private String fdEmail;
            private String fdVehicleTypeNum;
            private String fdUserName;
            private String fdIconUrl;
            private String fdPhone;
            private String fdAccount;
            private String fdDriverCheck;
            private double fdDriverAvgSocre;
            private String fdNickName;
            private long fdExpireDate;
            private double fdDriverlatitude;
            private String fdDriveStatus;
            private String vehicleType_fdName;
            private String fdDriverSex;
            private String fdId;
            private String fdPassowrd;
            private String fdTelephone;
            private String identity;
            private double fdDriverlongitude;
            private String fdAddress;
            private String vehicleType_fdId;

            public String getFdDriverCode() {
                return fdDriverCode;
            }

            public void setFdDriverCode(String fdDriverCode) {
                this.fdDriverCode = fdDriverCode;
            }

            public String getFdEmail() {
                return fdEmail;
            }

            public void setFdEmail(String fdEmail) {
                this.fdEmail = fdEmail;
            }

            public String getFdVehicleTypeNum() {
                return fdVehicleTypeNum;
            }

            public void setFdVehicleTypeNum(String fdVehicleTypeNum) {
                this.fdVehicleTypeNum = fdVehicleTypeNum;
            }

            public String getFdUserName() {
                return fdUserName;
            }

            public void setFdUserName(String fdUserName) {
                this.fdUserName = fdUserName;
            }

            public String getFdIconUrl() {
                return fdIconUrl;
            }

            public void setFdIconUrl(String fdIconUrl) {
                this.fdIconUrl = fdIconUrl;
            }

            public String getFdPhone() {
                return fdPhone;
            }

            public void setFdPhone(String fdPhone) {
                this.fdPhone = fdPhone;
            }

            public String getFdAccount() {
                return fdAccount;
            }

            public void setFdAccount(String fdAccount) {
                this.fdAccount = fdAccount;
            }

            public String getFdDriverCheck() {
                return fdDriverCheck;
            }

            public void setFdDriverCheck(String fdDriverCheck) {
                this.fdDriverCheck = fdDriverCheck;
            }

            public double getFdDriverAvgSocre() {
                return fdDriverAvgSocre;
            }

            public void setFdDriverAvgSocre(double fdDriverAvgSocre) {
                this.fdDriverAvgSocre = fdDriverAvgSocre;
            }

            public String getFdNickName() {
                return fdNickName;
            }

            public void setFdNickName(String fdNickName) {
                this.fdNickName = fdNickName;
            }

            public long getFdExpireDate() {
                return fdExpireDate;
            }

            public void setFdExpireDate(long fdExpireDate) {
                this.fdExpireDate = fdExpireDate;
            }

            public double getFdDriverlatitude() {
                return fdDriverlatitude;
            }

            public void setFdDriverlatitude(double fdDriverlatitude) {
                this.fdDriverlatitude = fdDriverlatitude;
            }

            public String getFdDriveStatus() {
                return fdDriveStatus;
            }

            public void setFdDriveStatus(String fdDriveStatus) {
                this.fdDriveStatus = fdDriveStatus;
            }

            public String getVehicleType_fdName() {
                return vehicleType_fdName;
            }

            public void setVehicleType_fdName(String vehicleType_fdName) {
                this.vehicleType_fdName = vehicleType_fdName;
            }

            public String getFdDriverSex() {
                return fdDriverSex;
            }

            public void setFdDriverSex(String fdDriverSex) {
                this.fdDriverSex = fdDriverSex;
            }

            public String getFdId() {
                return fdId;
            }

            public void setFdId(String fdId) {
                this.fdId = fdId;
            }

            public String getFdPassowrd() {
                return fdPassowrd;
            }

            public void setFdPassowrd(String fdPassowrd) {
                this.fdPassowrd = fdPassowrd;
            }

            public String getFdTelephone() {
                return fdTelephone;
            }

            public void setFdTelephone(String fdTelephone) {
                this.fdTelephone = fdTelephone;
            }

            public String getIdentity() {
                return identity;
            }

            public void setIdentity(String identity) {
                this.identity = identity;
            }

            public double getFdDriverlongitude() {
                return fdDriverlongitude;
            }

            public void setFdDriverlongitude(double fdDriverlongitude) {
                this.fdDriverlongitude = fdDriverlongitude;
            }

            public String getFdAddress() {
                return fdAddress;
            }

            public void setFdAddress(String fdAddress) {
                this.fdAddress = fdAddress;
            }

            public String getVehicleType_fdId() {
                return vehicleType_fdId;
            }

            public void setVehicleType_fdId(String vehicleType_fdId) {
                this.vehicleType_fdId = vehicleType_fdId;
            }
        }

        public static class VehicleBean {
            private String vehicleType_fdName;
            private String fdEnVehicleName;
            private String fdEnVehiclecolor;
            private String fdId;
            private String fdVehiclecolor;
            private long fdLastTime;
            private long fdCreateTime;
            private String fdVehicleCompanyCode;
            private String fdVehicleNumber;
            private String fdVehicleName;
            private String fdVehicleStatus;

            public String getVehicleType_fdName() {
                return vehicleType_fdName;
            }

            public void setVehicleType_fdName(String vehicleType_fdName) {
                this.vehicleType_fdName = vehicleType_fdName;
            }

            public String getFdEnVehicleName() {
                return fdEnVehicleName;
            }

            public void setFdEnVehicleName(String fdEnVehicleName) {
                this.fdEnVehicleName = fdEnVehicleName;
            }

            public String getFdEnVehiclecolor() {
                return fdEnVehiclecolor;
            }

            public void setFdEnVehiclecolor(String fdEnVehiclecolor) {
                this.fdEnVehiclecolor = fdEnVehiclecolor;
            }

            public String getFdId() {
                return fdId;
            }

            public void setFdId(String fdId) {
                this.fdId = fdId;
            }

            public String getFdVehiclecolor() {
                return fdVehiclecolor;
            }

            public void setFdVehiclecolor(String fdVehiclecolor) {
                this.fdVehiclecolor = fdVehiclecolor;
            }

            public long getFdLastTime() {
                return fdLastTime;
            }

            public void setFdLastTime(long fdLastTime) {
                this.fdLastTime = fdLastTime;
            }

            public long getFdCreateTime() {
                return fdCreateTime;
            }

            public void setFdCreateTime(long fdCreateTime) {
                this.fdCreateTime = fdCreateTime;
            }

            public String getFdVehicleCompanyCode() {
                return fdVehicleCompanyCode;
            }

            public void setFdVehicleCompanyCode(String fdVehicleCompanyCode) {
                this.fdVehicleCompanyCode = fdVehicleCompanyCode;
            }

            public String getFdVehicleNumber() {
                return fdVehicleNumber;
            }

            public void setFdVehicleNumber(String fdVehicleNumber) {
                this.fdVehicleNumber = fdVehicleNumber;
            }

            public String getFdVehicleName() {
                return fdVehicleName;
            }

            public void setFdVehicleName(String fdVehicleName) {
                this.fdVehicleName = fdVehicleName;
            }

            public String getFdVehicleStatus() {
                return fdVehicleStatus;
            }

            public void setFdVehicleStatus(String fdVehicleStatus) {
                this.fdVehicleStatus = fdVehicleStatus;
            }
        }
    }
}
