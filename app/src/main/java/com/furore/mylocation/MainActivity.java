package com.furore.mylocation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.furore.intern.R;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    Context con;
    Button bt_lochist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_lochist = (Button)findViewById(R.id.bt_lochist);
        bt_lochist.setOnClickListener(this);
        con= this;
        // use this to start and trigger a service
        Intent i= new Intent(this, LocService.class);
        startService(i);

        Intent i2 = new Intent(this, ChatHead.class);

        startService(i2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_lochist)
        {
            Intent intent = new Intent(this, LocHistory.class);
            startActivity(intent);
        }
    }
}
