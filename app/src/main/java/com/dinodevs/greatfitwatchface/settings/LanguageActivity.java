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

import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends FragmentActivity {

    /*
        Activity to provide a settings list for choosing days
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView root = new RecyclerView(this);
        //Add header to a list of settings
        List<BaseSetting> settings = new ArrayList<>();
        settings.add(new HeaderSetting(getString(R.string.set_language)));
        //Setup items for each day
        String[] languages = getResources().getStringArray(R.array.language);
        final SharedPreferences sharedPreferences = getSharedPreferences(getPackageName()+"_settings", Context.MODE_PRIVATE);
        int x = 0;
        int current = sharedPreferences.getInt("language", 0);
        for(String language : languages){
            //Each item needs a SwitchSetting with a value
            final int finalX = x;
            settings.add(new IconSetting( (current==x)?getDrawable(R.drawable.check):getDrawable(R.drawable.circle_icon), language, null, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharedPreferences.edit().putInt("language", finalX).apply();
                    LanguageActivity.this.finish();
                }
            }, null));
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
