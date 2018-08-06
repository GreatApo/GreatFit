package com.dinodevs.greatfitwatchface.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dinodevs.greatfitwatchface.R;

import java.util.ArrayList;
import java.util.List;

public class Settings extends FragmentActivity {

    /*
        Activity to provide a settings list for choosing vibration and sub-settings
        Made by KieronQuinn for AmazfitStepNotify
        Modified by GreatApo
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView root = new RecyclerView(this);
        final SharedPreferences sharedPreferences = getSharedPreferences(getPackageName()+"_settings", Context.MODE_PRIVATE);

        //Add header to a list of settings
        List<BaseSetting> settings = new ArrayList<>();

        //Add IconSettings for each sub-setting. They contain an icon, title and subtitle, as well as a click action to launch the sub-setting's activity
        settings.add(new HeaderSetting(getString(R.string.settings)));

        settings.add(new IconSetting(getDrawable(R.drawable.palette), getString(R.string.main_color), getString(R.string.main_color_c), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, ColorActivity.class));
            }
        }, null));

        settings.add(new IconSetting(getDrawable(R.drawable.widgets), getString(R.string.elements), getString(R.string.elements_c), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, WidgetsActivity.class));
            }
        }, null));

        settings.add(new IconSetting(getDrawable(R.drawable.language), getString(R.string.language), getString(R.string.language_c), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, LanguageActivity.class));
            }
        }, null));

        //Add save button
        settings.add(new ButtonSetting(getString(R.string.save), getDrawable(R.drawable.green_button), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do settings stuff here

                // Set watchface
                //Intent intent = new Intent("com.dinodevs.greatfitwatchface.GreatFitSlpt");
                //finish();
                //Settings.this.sendBroadcast(new Intent("com.dinodevs.greatfitwatchface.GreatFitSlpt"));
                Settings.this.sendBroadcast(new Intent("com.huami.intent.action.WATCHFACE_CONFIG_CHANGED"));

                Settings.this.setResult(-1);
                Settings.this.finish();
            }
        }));

        //Add reset button
        settings.add(new ButtonSetting(getString(R.string.reset), getDrawable(R.drawable.grey_button), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().clear().apply();
                Toast.makeText(view.getContext(), "Settings reset", Toast.LENGTH_SHORT).show();
                Settings.this.sendBroadcast(new Intent("com.huami.intent.action.WATCHFACE_CONFIG_CHANGED"));
                Settings.this.setResult(-1);
                Settings.this.finish();
            }
        }));

        //Setup layout
        root.setBackgroundResource(R.drawable.settings_background);
        root.setLayoutManager(new LinearLayoutManager(this));
        root.setAdapter(new Adapter(this, settings));
        root.setPadding((int) getResources().getDimension(R.dimen.padding_round_small), 0, (int) getResources().getDimension(R.dimen.padding_round_small), (int) getResources().getDimension(R.dimen.padding_round_large));
        root.setClipToPadding(false);
        setContentView(root);
    }
}
