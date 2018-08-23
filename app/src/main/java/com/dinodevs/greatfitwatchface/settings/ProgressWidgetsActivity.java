package com.dinodevs.greatfitwatchface.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dinodevs.greatfitwatchface.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgressWidgetsActivity extends FragmentActivity {

    /*
        Activity to provide a settings list for choosing days
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView root = new RecyclerView(this);
        //Add header to a list of settings
        List<BaseSetting> settings = new ArrayList<>();
        settings.add(new HeaderSetting(getString(R.string.set_progress_bar)));

        //Setup items for each day
        String[] elements = getResources().getStringArray(R.array.supported_progress_elements);
        final SharedPreferences sharedPreferences = getSharedPreferences(getPackageName()+"_settings", Context.MODE_PRIVATE);

        // Get Widgets
        String widgetsAsText = sharedPreferences.getString("progress_bars", null);
        String text = widgetsAsText.replaceAll("(\\[|\\]| )",""); // Replace "[", "]" and "spaces"
        List <String> widgets_list = Arrays.asList(text.split(","));

        int current = sharedPreferences.getInt("temp_progress_bars", 0);
        String currentAsText = widgets_list.get(current);
        for(String element : elements){
            // Go to next if already a widget
            if(widgets_list.indexOf(element)>=0 && !element.equals(currentAsText)){
                continue;
            }
            // Create the setting
            widgets_list.set(current,element);
            final String finalWidgets = widgets_list.toString();
            settings.add(new IconSetting( (element.equals(currentAsText))?getDrawable(R.drawable.check):getDrawable(R.drawable.circle_icon), prepareTitle(element), prepareSubtitle(element), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharedPreferences.edit().putString("progress_bars", finalWidgets).apply();
                    ProgressWidgetsActivity.this.finish();
                }
            }, null));
        }
        //Setup layout
        root.setBackgroundResource(R.drawable.settings_background);
        root.setLayoutManager(new LinearLayoutManager(this));
        root.setAdapter(new Adapter(this, settings));
        root.setPadding((int) getResources().getDimension(R.dimen.padding_round_small), 0, (int) getResources().getDimension(R.dimen.padding_round_small), (int) getResources().getDimension(R.dimen.padding_round_large));
        root.setClipToPadding(false);
        setContentView(root);
    }

    private String prepareTitle(String title){
        // Replace _ with spaces
        title = title.replaceAll("_"," ");
        // Capitalize first letter
        title = title.substring(0, 1).toUpperCase() + title.substring(1);
        return title;
    }

    private String prepareSubtitle(String element){
        String subtitle = null;

        switch (element) {
            case "air_pressure":
                subtitle = getString(R.string.nextWatchAlarm);
                break;
        }

        return subtitle;
    }
}
