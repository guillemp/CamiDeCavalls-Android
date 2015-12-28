package net.mervil.camidecavalls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class InfosActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        setTitle(getString(R.string.info));

        DatabaseHelper db = new DatabaseHelper(this);
        final List<Info> infos = db.getAllInfo();

        ArrayAdapter<Info> arrayAdapter = new InfoArrayAdapter(this, 0, infos);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Info info = infos.get(position);
                openInfoActivity(info);
            }
        });
    }

    private void openInfoActivity(Info info) {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("id", info.getId());
        startActivityForResult(intent, 1);
    }

    public class InfoArrayAdapter extends ArrayAdapter<Info> {

        private Context context;
        private List<Info> infos;

        public InfoArrayAdapter(Context context, int resource, List<Info> infos) {
            super(context, resource, infos);
            this.context = context;
            this.infos = infos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Info info = infos.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.adapter_platja, null);

            TextView nom = (TextView) view.findViewById(R.id.platjaTitleAdapter);
            nom.setText(info.getTitol());

            return view;
        }
    }
}
