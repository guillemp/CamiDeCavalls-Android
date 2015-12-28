package net.mervil.camidecavalls;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_PATH = "/data/data/net.mervil.camidecavalls/databases/";
    private static final String DATABASE_NAME = "cdc.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public SQLiteDatabase getDatabase() {
        String path = DATABASE_PATH + DATABASE_NAME;
        if (!checkDatabase()) {
            copyDatabase();
        }
        return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    private boolean checkDatabase() {
        SQLiteDatabase database = null;
        try {
            String path = DATABASE_PATH + DATABASE_NAME;
            database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (database != null) {
            database.close();
            return true;
        }
        return false;
    }

    private void copyDatabase() {
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);
            File databaseFile = new File(DATABASE_PATH);
            if (!databaseFile.exists()){
                databaseFile.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(DATABASE_PATH + DATABASE_NAME);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // ========================================================================

    public Etapa getEtapa(String tram) {

        SQLiteDatabase db = getDatabase();
        String query = "SELECT * FROM etapes WHERE id = " + tram;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
            cursor.moveToFirst();

        Etapa etapa = new Etapa();
        etapa.setTram(cursor.getString(0));
        etapa.setTitol(cursor.getString(1));

        switch (Locale.getDefault().getLanguage()) {
            case "ca":
                etapa.setText(cursor.getString(4));
                break;
            case "es":
                etapa.setText(cursor.getString(5));
                break;
            default:
                etapa.setText(cursor.getString(6));
                break;
        }

        etapa.setDuracio(cursor.getString(7));
        etapa.setDistancia(cursor.getString(8));
        etapa.setDificultat(cursor.getString(9));

        db.close();
        return etapa;
    }

    public List<Etapa> getAllEtapes() {
        List<Etapa> etapes = new ArrayList<Etapa>();

        SQLiteDatabase db = getDatabase();
        String query = "SELECT * FROM etapes";
        Cursor cursor = db.rawQuery(query, null);

        Etapa etapa = null;
        if (cursor.moveToFirst()) {
            do {
                etapa = new Etapa();
                etapa.setTram(cursor.getString(0));
                etapa.setTitol(cursor.getString(1));

                switch (Locale.getDefault().getLanguage()) {
                    case "ca":
                        etapa.setText(cursor.getString(4));
                        break;
                    case "es":
                        etapa.setText(cursor.getString(5));
                        break;
                    default:
                        etapa.setText(cursor.getString(6));
                        break;
                }

                etapa.setDuracio(cursor.getString(7));
                etapa.setDistancia(cursor.getString(8));
                etapa.setDificultat(cursor.getString(9));
                etapes.add(etapa);

            } while (cursor.moveToNext());
        }

        db.close();
        return etapes;
    }

    public Lloc getLloc(String id) {

        SQLiteDatabase db = getDatabase();
        String query = "SELECT * FROM llocs WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
            cursor.moveToFirst();

        Lloc lloc = new Lloc();
        lloc.setID(cursor.getString(0));

        switch (Locale.getDefault().getLanguage()) {
            case "ca":
                lloc.setTitol(cursor.getString(2));
                break;
            case "es":
                lloc.setTitol(cursor.getString(3));
                break;
            default:
                lloc.setTitol(cursor.getString(4));
                break;
        }

        switch (Locale.getDefault().getLanguage()) {
            case "ca":
                lloc.setText(cursor.getString(5));
                break;
            case "es":
                lloc.setText(cursor.getString(6));
                break;
            default:
                lloc.setText(cursor.getString(7));
                break;
        }

        switch (Locale.getDefault().getLanguage()) {
            case "ca":
                lloc.setSabies(cursor.getString(8));
                break;
            case "es":
                lloc.setSabies(cursor.getString(9));
                break;
            default:
                lloc.setSabies(cursor.getString(10));
                break;
        }

        db.close();
        return lloc;
    }

    public List<Lloc> getLlocsByTram(String tram) {
        List<Lloc> llocs = new ArrayList<Lloc>();

        SQLiteDatabase db = getDatabase();
        String query = "SELECT * FROM llocs WHERE etapa = " + tram;
        Cursor cursor = db.rawQuery(query, null);

        Lloc lloc = null;
        if (cursor.moveToFirst()) {
            do {
                lloc = new Lloc();
                lloc.setID(cursor.getString(0));

                switch (Locale.getDefault().getLanguage()) {
                    case "ca":
                        lloc.setTitol(cursor.getString(2));
                        break;
                    case "es":
                        lloc.setTitol(cursor.getString(3));
                        break;
                    default:
                        lloc.setTitol(cursor.getString(4));
                        break;
                }

                switch (Locale.getDefault().getLanguage()) {
                    case "ca":
                        lloc.setText(cursor.getString(5));
                        break;
                    case "es":
                        lloc.setText(cursor.getString(6));
                        break;
                    default:
                        lloc.setText(cursor.getString(7));
                        break;
                }

                switch (Locale.getDefault().getLanguage()) {
                    case "ca":
                        lloc.setSabies(cursor.getString(8));
                        break;
                    case "es":
                        lloc.setSabies(cursor.getString(9));
                        break;
                    default:
                        lloc.setSabies(cursor.getString(10));
                        break;
                }

                llocs.add(lloc);

            } while (cursor.moveToNext());
        }

        db.close();
        return llocs;
    }

    public List<Coord> getCoordsByTram(String tram) {
        List<Coord> coords = new ArrayList<Coord>();

        SQLiteDatabase db = getDatabase();
        String query = "SELECT * FROM coords WHERE tram = " + tram;
        Cursor cursor = db.rawQuery(query, null);

        Coord coord = null;
        if (cursor.moveToFirst()) {
            do {
                String lat = cursor.getString(3);
                String lng = cursor.getString(4);
                coord = new Coord(tram, lat, lng);
                coords.add(coord);
            } while (cursor.moveToNext());
        }

        db.close();
        return coords;
    }

    public List<Coord> getAllCoords(GoogleMap mMap) {
        List<Coord> coords = new ArrayList<Coord>();

        SQLiteDatabase db = getDatabase();
        String query = "SELECT * FROM coords";
        Cursor cursor = db.rawQuery(query, null);

        Coord coord = null;
        if (cursor.moveToFirst()) {
            do {
                String tram = cursor.getString(1);
                String lat = cursor.getString(3);
                String lng = cursor.getString(4);
                coord = new Coord(tram, lat, lng);
                coords.add(coord);
            } while (cursor.moveToNext());
        }

        db.close();
        return coords;
    }

    public PolylineOptions getMapPath() {
        PolylineOptions options = new PolylineOptions();
        options.color(Color.RED);
        options.width(5);
        options.visible(true);

        SQLiteDatabase db = getDatabase();
        String query = "SELECT * FROM coords";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                double lat = cursor.getDouble(3);
                double lng = cursor.getDouble(4);

                LatLng latLng = new LatLng(lat, lng);
                options.add(latLng);

            } while (cursor.moveToNext());
        }

        db.close();
        return options;
    }

    public List<MarkerOptions> getMapMarkers() {
        List<MarkerOptions> markers = new ArrayList<MarkerOptions>();

        SQLiteDatabase db = getDatabase();
        String query = "SELECT * FROM coords WHERE inici > 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String tram = cursor.getString(1);
                double lat = cursor.getDouble(3);
                double lng = cursor.getDouble(4);

                int iconId = context.getResources().getIdentifier("rodo" + tram, "drawable", context.getPackageName());
                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng)).title(context.getString(R.string.tram) + " " + tram);
                marker.icon(BitmapDescriptorFactory.fromResource(iconId));
                markers.add(marker);

            } while (cursor.moveToNext());
        }

        db.close();
        return markers;
    }

    public List<Info> getAllInfo() {
        List<Info> infos = new ArrayList<Info>();

        SQLiteDatabase db = getDatabase();
        String query = "SELECT * FROM info WHERE id < 6";
        Cursor cursor = db.rawQuery(query, null);

        Info info = null;
        if (cursor.moveToFirst()) {
            do {
                info = new Info();
                info.setId(cursor.getString(0));

                switch (Locale.getDefault().getLanguage()) {
                    case "ca":
                        info.setTitol(cursor.getString(1));
                        break;
                    case "es":
                        info.setTitol(cursor.getString(2));
                        break;
                    default:
                        info.setTitol(cursor.getString(3));
                        break;
                }

                switch (Locale.getDefault().getLanguage()) {
                    case "ca":
                        info.setText(cursor.getString(4));
                        break;
                    case "es":
                        info.setText(cursor.getString(5));
                        break;
                    default:
                        info.setText(cursor.getString(6));
                        break;
                }

                infos.add(info);

            } while (cursor.moveToNext());
        }

        db.close();
        return infos;
    }

    public Info getInfo(String id) {

        SQLiteDatabase db = getDatabase();
        String query = "SELECT * FROM info WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
            cursor.moveToFirst();

        Info info = new Info();
        info.setId(cursor.getString(0));

        switch (Locale.getDefault().getLanguage()) {
            case "ca":
                info.setTitol(cursor.getString(1));
                break;
            case "es":
                info.setTitol(cursor.getString(2));
                break;
            default:
                info.setTitol(cursor.getString(3));
                break;
        }

        switch (Locale.getDefault().getLanguage()) {
            case "ca":
                info.setText(cursor.getString(4));
                break;
            case "es":
                info.setText(cursor.getString(5));
                break;
            default:
                info.setText(cursor.getString(6));
                break;
        }

        db.close();
        return info;
    }

    public List<Platja> getAllPlatges() {
        List<Platja> platges = new ArrayList<Platja>();

        SQLiteDatabase db = getDatabase();
        String query = "SELECT * FROM platges ORDER BY nom ASC";
        Cursor cursor = db.rawQuery(query, null);

        Platja platja = null;
        if (cursor.moveToFirst()) {
            do {
                platja = new Platja();
                platja.setId(cursor.getString(0));
                platja.setNom(cursor.getString(1));
                platja.setTipus(cursor.getString(2));
                platja.setLat(cursor.getString(3));
                platja.setLng(cursor.getString(4));
                platja.setMunicipi(cursor.getString(5));
                platges.add(platja);

            } while (cursor.moveToNext());
        }

        db.close();
        return platges;
    }

    public Platja getPlatja(String id) {

        SQLiteDatabase db = getDatabase();
        String query = "SELECT * FROM platges WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
            cursor.moveToFirst();

        Platja platja = new Platja();
        platja.setId(cursor.getString(0));
        platja.setNom(cursor.getString(1));
        platja.setTipus(cursor.getString(2));
        platja.setLat(cursor.getString(3));
        platja.setLng(cursor.getString(4));
        platja.setMunicipi(cursor.getString(5));

        db.close();
        return platja;
    }
}