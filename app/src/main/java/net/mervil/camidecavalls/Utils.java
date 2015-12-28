package net.mervil.camidecavalls;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Guillem on 03/06/15.
 */
public class Utils {

    public static String loadJSONFromAsset(String fileName, Context context) {
        String json = null;
        try {
            AssetManager manager = context.getAssets();
            InputStream is = manager.open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static Drawable getDrawable(String name, Context context) {
        Drawable drawable = null;
        try {
            drawable = context.getResources().getDrawable(context.getResources().getIdentifier(name, "drawable", context.getPackageName()));
        } catch (Exception e) {
        }
        return drawable;
    }
}