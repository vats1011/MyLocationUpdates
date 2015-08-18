package com.furore.mylocation;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.furore.intern.R;


public class UserDetails extends ActionBarActivity {

    TextView name,username,emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        name= (TextView) findViewById(R.id.tv_name);
        username=(TextView) findViewById(R.id.tv_username);
        emailid=(TextView) findViewById(R.id.tv_email);

        SharedPreferences prefs = getSharedPreferences(
                "MYPREFS", 0);
        name.setText(prefs.getString("name",""));
        username.setText(prefs.getString("username",""));
        emailid.setText(prefs.getString("emailId",""));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_details, menu);
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
