/**
 * Author: Shikun Lin
 * CIS399 UO 2018SU
 *
 * SettingActivity can get setting fragments
 */
package com.example.shikunl.supersudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();

    }
}
