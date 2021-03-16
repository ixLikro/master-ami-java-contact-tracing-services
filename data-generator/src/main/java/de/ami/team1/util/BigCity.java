package de.ami.team1.util;


public enum BigCity {

    // lower saxony 10 biggest cities + hh, hb, wf
    // the population of the bigger cities are weighted down, because the population is used to choose a city.
    // with the weights the smaller cities are more likely to pick.
    HH("Hamburg", 53.55261, 9.99780, 1789954 * 0.19),
    H("Hannover", 52.380005, 9.74357, 1178968 * 0.25),
    HB("Bremen", 53.07820, 8.80727, 682986 * 0.36),
    BS("Braunschweig", 52.26645, 10.51978, 249406 * 0.8),
    OL("Oldenbug", 53.14359, 8.21607, 169077),
    OS("Osnabrück", 52.27574, 8.04777, 165251),
    WOB("Wolfsburg", 52.42473, 10.78614, 124371),
    GOE("Göttingen", 51.53556, 9.93364, 118911),
    SZ("Salzgitter",52.15882, 10.32059, 104291),
    HI("Hildesheim",52.15537, 9.95224, 101693),
    DEL("Delmenhorst",53.05042, 8.63154, 77559),
    WHV("Wilhelmshaven",53.53313, 8.08450, 76089),
    WF("Wolfenbüttel", 52.16353, 10.53624, 52165),


    ;
    private final Double latitude, longitude, population;
    private final String name;

    BigCity(String name, Double latitude, Double longitude, double population) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.population = population;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public double getPopulation() {
        return population;
    }
}
