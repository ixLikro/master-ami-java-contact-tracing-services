package de.ami.team1.analyse.pojo;

public class MapPointPojo {

    private double latitude;
    private double longitude;

    public MapPointPojo() {
    }

    public MapPointPojo(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
