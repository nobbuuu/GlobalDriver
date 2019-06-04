package com.sy.globaltake_driver.Bean;

/**
 * Created by sunnetdesign3 on 2017/2/22.
 */
public class JpushBean {

    /**
     * carType : 1
     * trip_id : 1487734691799T6OAO7
     * tag : 1
     */

    private String carType;
    private String trip_id;
    private String tag;
    private String eventStr;

    public String getEventStr() {
        return eventStr;
    }

    public void setEventStr(String eventStr) {
        this.eventStr = eventStr;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
