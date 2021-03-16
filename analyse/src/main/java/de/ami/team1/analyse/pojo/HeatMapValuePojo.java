package de.ami.team1.analyse.pojo;


public class HeatMapValuePojo extends MapPointPojo {

    private double intensity;

    public HeatMapValuePojo() {
    }

    public HeatMapValuePojo(double latitude, double longitude, double intensity) {
        super(latitude, longitude);
        this.intensity = intensity;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }
}
