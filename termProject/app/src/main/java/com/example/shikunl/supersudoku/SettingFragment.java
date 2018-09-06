/**
 * Author: Shikun Lin
 * CIS399 UO 2018SU
 * This is fragment for setting. Adding preference form preferences.xml
 *
 */
package com.example.shikunl.supersudoku;

import android.os.Bundle;
import android.preference.PreferenceFragment;


public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
