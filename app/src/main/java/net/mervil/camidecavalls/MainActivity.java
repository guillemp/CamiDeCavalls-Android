package net.mervil.camidecavalls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private List<Etapa> etapes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Camí de Cavalls");

        DatabaseHelper db = new DatabaseHelper(this);
        etapes = db.getAllEtapes();

        ArrayAdapter<Etapa> arrayAdapter = new EtapaArrayAdapter(this, 0, etapes);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Etapa etapa = etapes.get(position);
                openEtapaActivity(etapa);
            }
        });
    }

    private void openEtapaActivity(Etapa etapa) {
        Intent intent = new Intent(this, EtapaActivity.class);
        intent.putExtra("tram", etapa.getTram());
        startActivityForResult(intent, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuMapaMain) {
            openMapaActivity();
        } else if (id == R.id.menuPlatges) {
            openPlatgesActivity();
        } else if (id == R.id.menuInfo) {
            openInfosActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void openMapaActivity() {
        Intent intent = new Intent(this, MapaTotalActivity.class);
        startActivityForResult(intent, 100);
    }

    private void openPlatgesActivity() {
        Intent intent = new Intent(this, PlatgesActivity.class);
        startActivityForResult(intent, 200);
    }

    private void openInfosActivity() {
        Intent intent = new Intent(this, InfosActivity.class);
        startActivityForResult(intent, 300);
    }

    //
    // Etapa Array Adapter
    //
    public class EtapaArrayAdapter extends ArrayAdapter<Etapa> {

        private Context context;
        private List<Etapa> etapes;

        public EtapaArrayAdapter(Context context, int resource, List<Etapa> etapes) {
            super(context, resource, etapes);
            this.context = context;
            this.etapes = etapes;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Etapa etapa = etapes.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.adapter_etapa, null);

            TextView tram = (TextView) view.findViewById(R.id.etapaTram);
            tram.setText(getString(R.string.tram) + " " + etapa.getTram());

            TextView titol = (TextView) view.findViewById(R.id.etapaTitle);
            titol.setText(etapa.getTitol());

            return view;
        }
    }
}
