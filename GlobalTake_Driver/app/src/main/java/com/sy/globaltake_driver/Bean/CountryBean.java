package com.sy.globaltake_driver.Bean;

import java.util.List;

/**
 * Created by sunnetdesign3 on 2017/3/15.
 */
public class CountryBean {

    /**
     * results : [{"address_components":[],"formatted_address":"Yothapol Khemarak Phoumin Blvd (271), Phnom Penh, 柬埔寨","geometry":{},"place_id":"ChIJpQXlXsFQCTERkBzFKlrlCnM","types":["route"]}]
     * status : OK
     */

    private String status;
    /**
     * address_components : []
     * formatted_address : Yothapol Khemarak Phoumin Blvd (271), Phnom Penh, 柬埔寨
     * geometry : {}
     * place_id : ChIJpQXlXsFQCTERkBzFKlrlCnM
     * types : ["route"]
     */

    private List<ResultsBean> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String formatted_address;
        private String place_id;
        private List<?> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<?> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<?> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }
}
