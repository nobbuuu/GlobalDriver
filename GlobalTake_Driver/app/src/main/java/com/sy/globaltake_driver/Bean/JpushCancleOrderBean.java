package com.sy.globaltake_driver.Bean;

/**
 * Created by sunnetdesign3 on 2017/2/23.
 */
public class JpushCancleOrderBean {

    /**
     * trip_id : 1487841751304PK5UTQ
     * order_id : 1487841770859C9UYOH
     * tag : 2
     */

    private String trip_id;
    private String order_id;
    private String tag;
    private String eventStr;

    public String getEventStr() {
        return eventStr;
    }

    public void setEventStr(String eventStr) {
        this.eventStr = eventStr;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
