package net.mervil.camidecavalls;

public class Coord {

    private String tram;
    private String lat;
    private String lng;

    public Coord() {
    }

    public Coord(String tram, String lat, String lng) {
        this.tram = tram;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return Double.parseDouble(lat);
    }

    public double getLng() {
        return Double.parseDouble(lng);
    }

    public String getTram() {
        return tram;
    }

    @Override
    public String toString() {
        return "Coord { lat=" + lat + ", lng=" + lng + " }";
    }
}