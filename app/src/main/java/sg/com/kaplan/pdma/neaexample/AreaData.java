package sg.com.kaplan.pdma.neaexample;

/**
 * Created by wku on 7/4/2016.
 */
public class AreaData {
    public String getArea() {
        return area;
    }

    public String getForecast() {
        return forecast;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    private String area;
    private String forecast;
    private String lon;
    private String lat;

    public AreaData(String area, String forecast, String lon, String lat) {
        this.area = area;
        this.forecast = forecast;
        this.lon = lon;
        this.lat = lat;
    }
}
