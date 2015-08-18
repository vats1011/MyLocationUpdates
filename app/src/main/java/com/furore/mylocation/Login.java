package com.furore.mylocation;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.furore.intern.R;


public class Login extends ActionBarActivity {

    EditText name,username, emailId;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=(EditText)findViewById(R.id.etName);
        username=(EditText)findViewById(R.id.et_username);
        emailId =(EditText)findViewById(R.id.etPass);
        login=(Button)findViewById(R.id.bLogin);

        //setting shared pref

        SharedPreferences setPrefs = getSharedPreferences(
                "MYPREFS", 0);


        if(!(setPrefs.getBoolean("logged_in",false)) ){
            SharedPreferences.Editor editor = setPrefs.edit();
            editor.putString("name","");
            editor.putString("username", "");
            editor.putString("emailId","");
            editor.apply();
        }
        else
        {

            //intent to another activity
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // intent to next page if values

            }
        });
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
}