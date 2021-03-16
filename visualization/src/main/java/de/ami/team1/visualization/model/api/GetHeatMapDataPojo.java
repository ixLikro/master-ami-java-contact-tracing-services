package de.ami.team1.visualization.model.api;

import de.ami.team1.visualization.model.MapPoint;

public class GetHeatMapDataPojo {
    MapPoint p1, p2;

    public GetHeatMapDataPojo() {
    }

    public GetHeatMapDataPojo(MapPoint p1, MapPoint p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public MapPoint getP1() {
        return p1;
    }

    public void setP1(MapPoint p1) {
        this.p1 = p1;
    }

    public MapPoint getP2() {
        return p2;
    }

    public void setP2(MapPoint p2) {
        this.p2 = p2;
    }
}
