/**
 * Author: Shikun Lin
 * CIS399 UO 2018SU
 * This is MainActivity which allows player input their name
 * and intent the data to second activity
 */

package com.example.shikunl.supersudoku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private final String PLAYER_NAME = "player";
    private final String PREF_TIME = "time";
    private final String PREF_LEVEL = "level";
    private String playerName;
    private String prefTime = "300";
    private String prefLevel = "1";
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText nameEdit = (EditText) findViewById(R.id.playerEditText);
        Button newGame = (Button) findViewById(R.id.newGameButton);

        //NewGame Button, will intent data to SecondActivity
        newGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                playerName = nameEdit.getText().toString();

                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(PLAYER_NAME,playerName);
                bundle.putString(PREF_LEVEL,prefLevel);
                bundle.putString(PREF_TIME,prefTime);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        //retrieve data
        if(savedInstanceState != null){
            playerName = savedInstanceState.getString(PLAYER_NAME);
            prefLevel = savedInstanceState.getString(PREF_LEVEL);
            prefTime = savedInstanceState.getString(PREF_TIME);
        }
        // set the default values for the preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // get default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

    }
    @Override
    public void onResume() {
        super.onResume();
        // get preferences
        prefTime = prefs.getString("prefTime","0");
        prefLevel = prefs.getString("prefLevel","0");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //saving data
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(PLAYER_NAME,playerName);
        outState.putString(PREF_LEVEL, prefLevel);
        outState.putString(PREF_TIME, prefTime);
        super.onSaveInstanceState(outState);
    }
}
