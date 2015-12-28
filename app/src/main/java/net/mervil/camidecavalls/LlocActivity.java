package net.mervil.camidecavalls;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class LlocActivity extends ActionBarActivity {

    private Lloc lloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lloc);

        String ID = getIntent().getStringExtra("lloc");
        String tram = getIntent().getStringExtra("tram");

        DatabaseHelper db = new DatabaseHelper(this);
        lloc = db.getLloc(ID);

        setTitle("Tram " + tram);

        TextView titol = (TextView) findViewById(R.id.llocTitol);
        titol.setText(lloc.getTitol());

        TextView text = (TextView) findViewById(R.id.llocText);
        text.setText(lloc.getText());

        TextView sabies = (TextView) findViewById(R.id.llocSabies);
        sabies.setText(lloc.getSabies());

        // foto
        ImageView iv = (ImageView) findViewById(R.id.llocFoto);
        Drawable foto = Lloc.getImage(lloc.getID(), this);
        iv.setImageDrawable(null);
        if (foto != null) {
            iv.setImageDrawable(foto);
        }
    }
}
