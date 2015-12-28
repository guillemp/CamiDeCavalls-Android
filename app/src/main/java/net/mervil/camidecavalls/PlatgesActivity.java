package net.mervil.camidecavalls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class PlatgesActivity extends ActionBarActivity {

    ArrayAdapter<Platja> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platges);

        setTitle(getString(R.string.platges));

        DatabaseHelper db = new DatabaseHelper(this);
        final List<Platja> platges = db.getAllPlatges();

        arrayAdapter = new PlatgesArrayAdapter(this, 0, platges);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Platja platja = platges.get(position);
                openPlatjaActivity(platja);
            }
        });
    }

    private void openPlatjaActivity(Platja platja) {
        Intent intent = new Intent(this, MapaPlatjaActivity.class);
        intent.putExtra("id", platja.getId());
        startActivityForResult(intent, 1);
    }

    public class PlatgesArrayAdapter extends ArrayAdapter<Platja> {

        private Context context;
        private List<Platja> platges;

        public PlatgesArrayAdapter(Context context, int resource, List<Platja> platges) {
            super(context, resource, platges);
            this.context = context;
            this.platges = platges;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Platja platja = platges.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.adapter_platja, null);

            TextView nom = (TextView) view.findViewById(R.id.platjaTitleAdapter);
            nom.setText(platja.getNom());

            return view;
        }
    }
}