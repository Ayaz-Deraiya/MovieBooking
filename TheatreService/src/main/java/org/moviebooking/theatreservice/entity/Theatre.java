package org.moviebooking.theatreservice.entity;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "theatre")
public class Theatre {
    @Id
    private ObjectId id;

    private String theatreName;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    private int totalScreen;

    private List<ScreenInfo> screens; // {{screenNo,capacity}}

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    public List<ScreenInfo> getScreens() {
        return screens;
    }

    public void setScreens(List<ScreenInfo> screens) {
        this.screens = screens;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public int getTotalScreen() {
        return totalScreen;
    }

    public void setTotalScreen(int totalScreen) {
        this.totalScreen = totalScreen;
    }
}
