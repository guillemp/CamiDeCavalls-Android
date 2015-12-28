package net.mervil.camidecavalls;

import android.content.Context;
import java.util.List;

public class Etapa {

    private int ID;
    private String tram;
    private String titol;
    private String text;
    private String duracio;
    private String distancia;
    private String dificultat;
    private List<Lloc> llocs;
    private List<Coord> coords;

    public Etapa() {}

    public String getDificultatText(Context context) {
        switch (dificultat) {
            case "1":
                return context.getString(R.string.facil);
            case "2":
                return context.getString(R.string.mitja);
            case "3":
                return context.getString(R.string.dificil);
        }
        return dificultat;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getTram() {
        return tram;
    }
    public void setTram(String tram) {
        this.tram = tram;
    }
    public String getTitol() {
        return titol;
    }
    public void setTitol(String titol) {
        this.titol = titol;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getDuracio() {
        return duracio;
    }
    public void setDuracio(String duracio) {
        this.duracio = duracio;
    }
    public String getDistancia() {
        return distancia;
    }
    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }
    public String getDificultat() {
        return dificultat;
    }
    public void setDificultat(String dificultat) {
        this.dificultat = dificultat;
    }
    public List<Lloc> getLlocs() {
        return llocs;
    }
    public void setLlocs(List<Lloc> llocs) {
        this.llocs = llocs;
    }
    public List<Coord> getCoords() {
        return coords;
    }
    public void setCoords(List<Coord> coords) {
        this.coords = coords;
    }
}