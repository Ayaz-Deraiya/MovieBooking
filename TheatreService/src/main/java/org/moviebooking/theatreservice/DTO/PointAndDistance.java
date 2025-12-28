package org.moviebooking.theatreservice.DTO;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class PointAndDistance {
    private GeoJsonPoint point;
    private int distance;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public GeoJsonPoint getPoint() {
        return point;
    }

    public void setPoint(GeoJsonPoint point) {
        this.point = point;
    }
}
