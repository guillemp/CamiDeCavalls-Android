package net.mervil.camidecavalls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;


public class EtapaActivity extends ActionBarActivity {

    private Etapa etapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapa);

        String tram = getIntent().getStringExtra("tram");
        DatabaseHelper db = new DatabaseHelper(this);
        etapa = db.getEtapa(tram);

        setTitle(getString(R.string.tram) + " " + etapa.getTram());

        // Layout
        Drawable foto = Utils.getDrawable("etapa" + etapa.getTram(), this);
        ImageView iv = (ImageView) findViewById(R.id.etapaFoto);
        iv.setImageDrawable(foto);

        TextView titol = (TextView) findViewById(R.id.etapaTram);
        titol.setText(etapa.getTitol());

        TextView distancia = (TextView) findViewById(R.id.etapaDistancia);
        distancia.setText(etapa.getDistancia());

        TextView duracio = (TextView) findViewById(R.id.etapaDuracio);
        duracio.setText(etapa.getDuracio());

        TextView dificultat = (TextView) findViewById(R.id.etapaDificultat);
        dificultat.setText(etapa.getDificultatText(this));

        TextView text = (TextView) findViewById(R.id.etapaText);
        text.setText(etapa.getText());

        //
        // LLOCS
        //
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.etapaLinearLayout);

        List<Lloc> llocs = db.getLlocsByTram(tram);
        for (int i = 0; i < llocs.size(); i++) {
            Lloc lloc = llocs.get(i);

            View llocLayout = getLayoutInflater().inflate(R.layout.adapter_lloc, null);

            // row
            LinearLayout ll = (LinearLayout) llocLayout.findViewById(R.id.llocLinearLayout);
            ll.setId(Integer.parseInt(lloc.getID()));
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openLlocActivity(view);
                }
            });

            // lloc titol
            TextView llocTitol = (TextView) llocLayout.findViewById(R.id.llocTitleAdapter);
            llocTitol.setText(lloc.getTitol());

            // foto icon
                /*
                ImageView iv2 = (ImageView) llocLayout.findViewById(R.id.llocIconoAdapter);
                Drawable foto2 = Lloc.getImage(lloc.getID(), this);
                iv2.setImageDrawable(null);
                if (foto2 != null) {
                    iv2.setImageResource(R.drawable.rodo1);
                }
                */

            // add to parent layout
            linearLayout.addView(llocLayout);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_etapa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.etapaMapaIcon) {
            openRouteMap();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openLlocActivity(View view) {
        Intent intent = new Intent(this, LlocActivity.class);
        intent.putExtra("lloc", Integer.toString(view.getId()));
        intent.putExtra("tram", etapa.getTram());
        startActivityForResult(intent, 3);
    }

    private void openRouteMap() {
        Intent intent = new Intent(this, MapaActivity.class);
        intent.putExtra("tram", etapa.getTram());
        startActivityForResult(intent, 2);
    }

}