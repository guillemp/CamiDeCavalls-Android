package net.mervil.camidecavalls;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class InfoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        String id = getIntent().getStringExtra("id");
        DatabaseHelper db = new DatabaseHelper(this);
        Info info = db.getInfo(id);

        setTitle(getString(R.string.info));

        TextView titol = (TextView) findViewById(R.id.infoTitol);
        titol.setText(info.getTitol());

        TextView text = (TextView) findViewById(R.id.infoText);
        text.setText(info.getText());
    }
}
