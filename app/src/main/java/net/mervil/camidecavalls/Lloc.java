package net.mervil.camidecavalls;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Lloc {

    private String ID;
    private String tram;
    private String titol;
    private String text;
    private String sabies;

    public static Drawable getImage(String llocId, Context context) {
        return Utils.getDrawable("lloc" + llocId, context);
    }

    public String getID() {
        return ID;
    }
    public void setID(String ID) {
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
    public String getSabies() {
        return sabies;
    }
    public void setSabies(String sabies) {
        this.sabies = sabies;
    }
}