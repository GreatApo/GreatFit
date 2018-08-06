package com.dinodevs.greatfitwatchface.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;

import com.dinodevs.greatfitwatchface.R;

import java.util.ArrayList;
import java.util.List;

public class ColorActivity extends FragmentActivity {

    /*
        Activity to provide a settings list for choosing days
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView root = new RecyclerView(this);
        //Add header to a list of settings
        List<BaseSetting> settings = new ArrayList<>();
        settings.add(new HeaderSetting(getString(R.string.set_color)));
        //Setup items for each day
        String[] colors = getResources().getStringArray(R.array.colors);
        final SharedPreferences sharedPreferences = getSharedPreferences(getPackageName()+"_settings", Context.MODE_PRIVATE);
        int x = 1;
        int current = sharedPreferences.getInt("sltp_circle_color", 1);
        String[] colorCodes = {"#ff0000", "#00ffff", "#00ff00", "#ff00ff", "#ffffff", "#ffff00", "#111111"};
        //<!-- 1=red, 2=light blue, 3=green, 4=purple, 5=white, 6=yellow, 7=black, 8=grey -->
        for(String color : colors){
            //Each item needs a SwitchSetting with a value
            final int finalX = x;
            settings.add(new IconSetting( (current==x)?getDrawable(R.drawable.check):getDrawable(R.drawable.circle_icon), color, null, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharedPreferences.edit().putInt("sltp_circle_color", finalX).apply();
                    ColorActivity.this.finish();
                }
            }, Color.parseColor(colorCodes[x-1])));
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
