
package com.gmk.gmkandroid.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Mapbox implements Serializable {

    private Marker marker;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The marker
     */
    public Marker getMarker() {
        return marker;
    }

    /**
     * 
     * @param marker
     *     The marker
     */
    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
