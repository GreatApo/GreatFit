package com.dinodevs.greatfitwatchface.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dinodevs.greatfitwatchface.GreatFit;
import com.dinodevs.greatfitwatchface.R;
import com.dinodevs.greatfitwatchface.widget.GreatWidget;

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

        // Load settings
        LoadSettings watchface_settings = new LoadSettings(getApplicationContext());
        if(sharedPreferences.getString("widgets", null)==null) {
            sharedPreferences.edit().putString("widgets", watchface_settings.widgets_list.toString()).apply();
        }
        if(sharedPreferences.getString("progress_bars", null)==null) {
            sharedPreferences.edit().putString("progress_bars", watchface_settings.circle_bars_list.toString()).apply();
        }

        //Add header to a list of settings
        List<BaseSetting> settings = new ArrayList<>();

        // Add IconSettings for each sub-setting. They contain an icon, title and subtitle, as well as a click action to launch the sub-setting's activity
        settings.add(new HeaderSetting(getString(R.string.settings)));

        // Add color selection
        settings.add(new IconSetting(getDrawable(R.drawable.palette), getString(R.string.main_color), getString(R.string.main_color_c), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, ColorActivity.class));
            }
        }, null));

        // Add font selection
        settings.add(new IconSetting(getDrawable(R.drawable.font), getString(R.string.font), getString(R.string.font_c), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, FontActivity.class));
            }
        }, null));

        // Add other features
        settings.add(new IconSetting(getDrawable(R.drawable.gear), getString(R.string.other_features), getString(R.string.other_features_c), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, OthersActivity.class));
            }
        }, null));

        // One for each widget
        for (int i=0; i<watchface_settings.widgets_list.size(); i++) {
            final int j = i;
            settings.add(new IconSetting(getDrawable(R.drawable.widgets), getString(R.string.widget)+" "+(i+1), getString(R.string.widget_c), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharedPreferences.edit().putInt( "temp_widget", j).apply();
                    startActivity(new Intent(Settings.this, WidgetsActivity.class));
                }
            }, null));
        }

        // One for each progress widget
        for (int i=0; i<watchface_settings.circle_bars_list.size(); i++) {
            final int j = i;

            settings.add(new IconSetting(getDrawable(R.drawable.progress), getString(R.string.progress_widget)+" "+(i+1), getString(R.string.progress_widget_c), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharedPreferences.edit().putInt( "temp_progress_bars", j).apply();
                    startActivity(new Intent(Settings.this, ProgressWidgetsActivity.class));
                }
            }, null));
        }

        // Add language
        settings.add(new IconSetting(getDrawable(R.drawable.language), getString(R.string.language), getString(R.string.language_c), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, LanguageActivity.class));
            }
        }, null));

        // Add about
        settings.add(new IconSetting(getDrawable(R.drawable.info), getString(R.string.about), getString(R.string.about_c), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get pkg info
                String version = "n/a";
                try {
                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    version = pInfo.versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                // Please do not change the following line
                Toast.makeText(getApplicationContext(), "GreatFit Project\nVersion: "+ version +"\nAuthor: GreatApo\nStyle: "+getResources().getString(R.string.author), Toast.LENGTH_LONG).show();
            }
        }, null));

        //Add save button
        settings.add(new ButtonSetting(getString(R.string.save), getDrawable(R.drawable.green_button), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.quit(Settings.this);

                /*
                // CODE BASED ON STOCK WAY
                // Restart watchface
                Settings.this.sendBroadcast(new Intent("com.huami.intent.action.WATCHFACE_CONFIG_CHANGED"));
                // Slpt some times doesn't run
                startService(new Intent(getApplicationContext(), GreatFitSlpt.class));
                // Kill this
                Settings.this.setResult(-1);
                Settings.this.finish();
                */
            }
        }));

        //Add reset button
        settings.add(new ButtonSetting(getString(R.string.reset), getDrawable(R.drawable.grey_button), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().clear().apply();
                Toast.makeText(view.getContext(), "Settings reset", Toast.LENGTH_SHORT).show();

                Settings.quit(Settings.this);

                /*
                // CODE BASED ON STOCK WAY
                // Restart watchface
                Settings.this.sendBroadcast(new Intent("com.huami.intent.action.WATCHFACE_CONFIG_CHANGED"));
                // Slpt some times doesn't run
                startService(new Intent(getApplicationContext(), GreatFitSlpt.class));
                // Kill this
                Settings.this.setResult(-1);
                Settings.this.finish();
                 */
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

    private static void quit(final Settings settings) {
        GreatWidget greatWidget = GreatFit.getGreatWidget();
        if (greatWidget != null) {
            greatWidget.refreshSlpt("Apply settings", true);
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                settings.sendBroadcast(new Intent("com.huami.intent.action.WATCHFACE_CONFIG_CHANGED"));
                // Kill this
                settings.setResult(-1);
                settings.finish();
            }
        }, 5);
    }
}
