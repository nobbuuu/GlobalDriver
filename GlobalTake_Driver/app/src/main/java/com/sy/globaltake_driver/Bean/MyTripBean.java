package com.sy.globaltake_driver.Bean;

import java.util.List;

/**
 * Created by sunnetdesign3 on 2017/3/3.
 */
public class MyTripBean {

    /**
     * code : 1
     * message : 执行成功!
     * result : [{"trip_id":"148784291496734SO5M","trip":{"fdTripCreateTime":1487842914967,"fdTripStartCoordinates":22.536251174983,"passenger_fdPhone":"18277750576","fdVehicleTypeNum":"宝安出租车","fdTotalMileage":6172,"fdTriptype":1,"fdStatus":0,"fdTripNum":"201702231741541782","fdTripEndCoordinateslongitude":113.97423285414,"fdTripStartLocation":"南头","fdTripStartTime":0,"fdTripEndLocation":"世界之窗","fdTripPrice":0,"fdId":"148784291496734SO5M","fdTripEndCoordinates":22.536899510415,"passenger_fdIconUrl":"upload/userImg/dc187943-5630-45a8-801d-66a5d2420662.jpg","fdTripMileage":0,"fdTripEndTime":0,"fdName":"1","fdTripPeople":1,"fdTripStartCoordinateslongitude":113.91877999121},"order_id":"1487842934317RRSH2N"},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]
     */

    private String code;
    private String message;
    /**
     * trip_id : 148784291496734SO5M
     * trip : {"fdTripCreateTime":1487842914967,"fdTripStartCoordinates":22.536251174983,"passenger_fdPhone":"18277750576","fdVehicleTypeNum":"宝安出租车","fdTotalMileage":6172,"fdTriptype":1,"fdStatus":0,"fdTripNum":"201702231741541782","fdTripEndCoordinateslongitude":113.97423285414,"fdTripStartLocation":"南头","fdTripStartTime":0,"fdTripEndLocation":"世界之窗","fdTripPrice":0,"fdId":"148784291496734SO5M","fdTripEndCoordinates":22.536899510415,"passenger_fdIconUrl":"upload/userImg/dc187943-5630-45a8-801d-66a5d2420662.jpg","fdTripMileage":0,"fdTripEndTime":0,"fdName":"1","fdTripPeople":1,"fdTripStartCoordinateslongitude":113.91877999121}
     * order_id : 1487842934317RRSH2N
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
        private String trip_id;
        /**
         * fdTripCreateTime : 1487842914967
         * fdTripStartCoordinates : 22.536251174983
         * passenger_fdPhone : 18277750576
         * fdVehicleTypeNum : 宝安出租车
         * fdTotalMileage : 6172
         * fdTriptype : 1
         * fdStatus : 0
         * fdTripNum : 201702231741541782
         * fdTripEndCoordinateslongitude : 113.97423285414
         * fdTripStartLocation : 南头
         * fdTripStartTime : 0
         * fdTripEndLocation : 世界之窗
         * fdTripPrice : 0
         * fdId : 148784291496734SO5M
         * fdTripEndCoordinates : 22.536899510415
         * passenger_fdIconUrl : upload/userImg/dc187943-5630-45a8-801d-66a5d2420662.jpg
         * fdTripMileage : 0
         * fdTripEndTime : 0
         * fdName : 1
         * fdTripPeople : 1
         * fdTripStartCoordinateslongitude : 113.91877999121
         */

        private TripBean trip;
        private String order_id;

        public String getTrip_id() {
            return trip_id;
        }

        public void setTrip_id(String trip_id) {
            this.trip_id = trip_id;
        }

        public TripBean getTrip() {
            return trip;
        }

        public void setTrip(TripBean trip) {
            this.trip = trip;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public static class TripBean {
            private long fdTripCreateTime;
            private double fdTripStartCoordinates;
            private String passenger_fdPhone;
            private String fdVehicleTypeNum;
            private String fdTotalMileage;
            private String fdTriptype;
            private String fdStatus;
            private String fdTripNum;
            private double fdTripEndCoordinateslongitude;
            private String fdTripStartLocation;
            private String fdTripStartTime;
            private String fdTripEndLocation;
            private String fdTripPrice;
            private String fdId;
            private double fdTripEndCoordinates;
            private String passenger_fdIconUrl;
            private String fdTripMileage;
            private String fdTripEndTime;
            private String fdName;
            private String fdTripPeople;
            private double fdTripStartCoordinateslongitude;

            public long getFdTripCreateTime() {
                return fdTripCreateTime;
            }

            public void setFdTripCreateTime(long fdTripCreateTime) {
                this.fdTripCreateTime = fdTripCreateTime;
            }

            public double getFdTripStartCoordinates() {
                return fdTripStartCoordinates;
            }

            public void setFdTripStartCoordinates(double fdTripStartCoordinates) {
                this.fdTripStartCoordinates = fdTripStartCoordinates;
            }

            public String getPassenger_fdPhone() {
                return passenger_fdPhone;
            }

            public void setPassenger_fdPhone(String passenger_fdPhone) {
                this.passenger_fdPhone = passenger_fdPhone;
            }

            public String getFdVehicleTypeNum() {
                return fdVehicleTypeNum;
            }

            public void setFdVehicleTypeNum(String fdVehicleTypeNum) {
                this.fdVehicleTypeNum = fdVehicleTypeNum;
            }

            public String getFdTotalMileage() {
                return fdTotalMileage;
            }

            public void setFdTotalMileage(String fdTotalMileage) {
                this.fdTotalMileage = fdTotalMileage;
            }

            public String getFdTriptype() {
                return fdTriptype;
            }

            public void setFdTriptype(String fdTriptype) {
                this.fdTriptype = fdTriptype;
            }

            public String getFdStatus() {
                return fdStatus;
            }

            public void setFdStatus(String fdStatus) {
                this.fdStatus = fdStatus;
            }

            public String getFdTripNum() {
                return fdTripNum;
            }

            public void setFdTripNum(String fdTripNum) {
                this.fdTripNum = fdTripNum;
            }

            public double getFdTripEndCoordinateslongitude() {
                return fdTripEndCoordinateslongitude;
            }

            public void setFdTripEndCoordinateslongitude(double fdTripEndCoordinateslongitude) {
                this.fdTripEndCoordinateslongitude = fdTripEndCoordinateslongitude;
            }

            public String getFdTripStartLocation() {
                return fdTripStartLocation;
            }

            public void setFdTripStartLocation(String fdTripStartLocation) {
                this.fdTripStartLocation = fdTripStartLocation;
            }

            public String getFdTripStartTime() {
                return fdTripStartTime;
            }

            public void setFdTripStartTime(String fdTripStartTime) {
                this.fdTripStartTime = fdTripStartTime;
            }

            public String getFdTripEndLocation() {
                return fdTripEndLocation;
            }

            public void setFdTripEndLocation(String fdTripEndLocation) {
                this.fdTripEndLocation = fdTripEndLocation;
            }

            public String getFdTripPrice() {
                return fdTripPrice;
            }

            public void setFdTripPrice(String fdTripPrice) {
                this.fdTripPrice = fdTripPrice;
            }

            public String getFdId() {
                return fdId;
            }

            public void setFdId(String fdId) {
                this.fdId = fdId;
            }

            public double getFdTripEndCoordinates() {
                return fdTripEndCoordinates;
            }

            public void setFdTripEndCoordinates(double fdTripEndCoordinates) {
                this.fdTripEndCoordinates = fdTripEndCoordinates;
            }

            public String getPassenger_fdIconUrl() {
                return passenger_fdIconUrl;
            }

            public void setPassenger_fdIconUrl(String passenger_fdIconUrl) {
                this.passenger_fdIconUrl = passenger_fdIconUrl;
            }

            public String getFdTripMileage() {
                return fdTripMileage;
            }

            public void setFdTripMileage(String fdTripMileage) {
                this.fdTripMileage = fdTripMileage;
            }

            public String getFdTripEndTime() {
                return fdTripEndTime;
            }

            public void setFdTripEndTime(String fdTripEndTime) {
                this.fdTripEndTime = fdTripEndTime;
            }

            public String getFdName() {
                return fdName;
            }

            public void setFdName(String fdName) {
                this.fdName = fdName;
            }

            public String getFdTripPeople() {
                return fdTripPeople;
            }

            public void setFdTripPeople(String fdTripPeople) {
                this.fdTripPeople = fdTripPeople;
            }

            public double getFdTripStartCoordinateslongitude() {
                return fdTripStartCoordinateslongitude;
            }

            public void setFdTripStartCoordinateslongitude(double fdTripStartCoordinateslongitude) {
                this.fdTripStartCoordinateslongitude = fdTripStartCoordinateslongitude;
            }
        }
    }
}
