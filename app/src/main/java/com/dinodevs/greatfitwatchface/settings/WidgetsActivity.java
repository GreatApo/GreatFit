package com.dinodevs.greatfitwatchface.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CompoundButton;

import com.dinodevs.greatfitwatchface.R;

import java.util.ArrayList;
import java.util.List;

public class WidgetsActivity extends FragmentActivity {

    /*
        Activity to provide a settings list for choosing days
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView root = new RecyclerView(this);
        //Add header to a list of settings
        List<BaseSetting> settings = new ArrayList<>();
        settings.add(new HeaderSetting(getString(R.string.set_elements)));
        //Setup items for each day
        String[] elements = getResources().getStringArray(R.array.elements);
        final SharedPreferences sharedPreferences = getSharedPreferences(getPackageName()+"_settings", Context.MODE_PRIVATE);
        for(String element : elements){
            //Each item needs a SwitchSetting with a value

            if( !getDefaultSetting(element)){
                // Go to the next if it is disabled from resources
                continue;
            }

            final String tempElm = element;
            settings.add(new SwitchSetting(null, element, null, new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    sharedPreferences.edit().putBoolean( tempElm, b).apply();
                }
            }, getSetting(element, sharedPreferences)));
        }
        //Setup layout
        root.setBackgroundResource(R.drawable.settings_background);
        root.setLayoutManager(new LinearLayoutManager(this));
        root.setAdapter(new Adapter(this, settings));
        root.setPadding((int) getResources().getDimension(R.dimen.padding_round_small), 0, (int) getResources().getDimension(R.dimen.padding_round_small), (int) getResources().getDimension(R.dimen.padding_round_large));
        root.setClipToPadding(false);
        setContentView(root);
    }

    private boolean getSetting(String element, SharedPreferences sharedPreferences) {
        return sharedPreferences.getBoolean(element, true);
    }

    private boolean getDefaultSetting(String element) {
        return getResources().getBoolean(getResources().getIdentifier(element, "bool", getPackageName() ));
    }
}
