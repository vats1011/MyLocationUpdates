package com.furore.mylocation;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;

import com.furore.intern.R;

import java.util.ArrayList;
import java.util.List;

public class LocHistory extends ActionBarActivity {

    private RecyclerView rv_loc;
    List<LocPojo> data = new ArrayList<>();
    LocHistAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_history);

        DBLocHistory getData = new DBLocHistory(this);
        getData.open();
        data = getData.getGroups();
        getData.close();


        rv_loc=(RecyclerView) findViewById(R.id.rv_lochist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_loc.setLayoutManager(layoutManager);
       

        adapter = new LocHistAdapter(this, data);
        rv_loc.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loc_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
