package com.dinodevs.greatfitwatchface.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dinodevs.greatfitwatchface.R;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;

import java.util.ArrayList;
import java.util.List;

public class FontActivity extends FragmentActivity {

    /*
        Activity to provide a settings list for choosing days
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView root = new RecyclerView(this);
        //Add header to a list of settings
        List<BaseSetting> settings = new ArrayList<>();
        settings.add(new HeaderSetting(getString(R.string.set_font)));
        //Setup items for each day
        String[] fonts = ResourceManager.getNames(ResourceManager.Font.class);//getResources().getStringArray(R.array.colors);
        final SharedPreferences sharedPreferences = getSharedPreferences(getPackageName()+"_settings", Context.MODE_PRIVATE);
        int x = 0;
        int current = sharedPreferences.getInt("font", 0);
        for(String font : fonts){
            //Each item needs a Setting
            final int finalX = x;
            settings.add(new IconSetting( (current==x)?getDrawable(R.drawable.check):getDrawable(R.drawable.circle_icon), font, null, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharedPreferences.edit().putInt("font", finalX).apply();
                    FontActivity.this.finish();
                }
            }, x));
            x++;
        }
        //Setup layout
        root.setBackgroundResource(R.drawable.settings_background);
        root.setLayoutManager(new LinearLayoutManager(this));
        root.setAdapter(new Adapter(this, settings));
        root.setPadding((int) getResources().getDimension(R.dimen.padding_round_small), 0, (int) getResources().getDimension(R.dimen.padding_round_small), (int) getResources().getDimension(R.dimen.padding_round_large));
        root.setClipToPadding(false);
        setContentView(root);
    }
}
