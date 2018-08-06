package com.dinodevs.greatfitwatchface.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dinodevs.greatfitwatchface.widget.MainClock;
import com.dinodevs.greatfitwatchface.R;

// BY GREATAPO

public class SettingsOld extends FragmentActivity {

    public static String[] color = {"#ff0000", "#00ffff","#00ff00","#ff00ff","#ffffff","#ffff00"};
    public int currentColor = 3;
    public int currentLanguage = 0;
    private com.dinodevs.greatfitwatchface.settings.APsettings settings;
    // Languages
    public static String[] codes = {
            "English", "中文", "Czech", "Français", "Deutsch", "Ελληνικά", "עברית", "Magyar", "Italiano", "日本語", "Polski", "Português", "Русский", "Slovenčina", "Español"//, "Türkçe",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setup activity
        setContentView(R.layout.settings);

        // Load settings
        this.settings = new APsettings(MainClock.class.getName(), this.getApplicationContext());

        // Save button
        TextView about_button = (TextView) findViewById(R.id.about);
        about_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Made by GreatApo", Toast.LENGTH_SHORT).show();
            }
        });

        // Save button
        TextView close_settings_button = (TextView) findViewById(R.id.close_settings);
        close_settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Made by GreatApo", Toast.LENGTH_SHORT).show();
                change_watchface();
            }
        });

        // Change Color Buttons
        View.OnClickListener onColorChange = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsOld.this.onColorChangeBtn(v);
            }
        };
        this.findViewById(R.id.color1).setOnClickListener(onColorChange);
        this.findViewById(R.id.color2).setOnClickListener(onColorChange);
        this.findViewById(R.id.color3).setOnClickListener(onColorChange);
        this.findViewById(R.id.color4).setOnClickListener(onColorChange);
        this.findViewById(R.id.color5).setOnClickListener(onColorChange);
        this.findViewById(R.id.color6).setOnClickListener(onColorChange);

        this.currentColor = this.settings.getInt("color",this.currentColor);
        //"#ff0000", "#00ffff","#00ff00","#ff00ff","#ffffff","#ffff00"
        switch (color[this.currentColor]) {
            case "#ff0000":
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color1);
                break;
            case "#00ffff":
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color2);
                break;
            case "#00ff00":
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color3);
                break;
            case "#ff00ff":
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color4);
                break;
            case "#ffffff":
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color5);
                break;
            case "#ffff00":
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color6);
                break;
            default:
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color4);
                break;
        }

        // Language button
        TextView language_button = (TextView) findViewById(R.id.language);
        language_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateLanguage(v);
            }
        });
        this.currentLanguage = this.settings.get("lang", this.currentLanguage) % this.codes.length;
        // Show ui in new language
        ((TextView) this.findViewById(R.id.language)).setText(this.codes[this.currentLanguage]);
    }

    private void onColorChangeBtn(View v) {
        // Find caller and change to the appropriate color
        switch (v.getId()) {
            case R.id.color1:
                this.currentColor = 0;
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color1);
                break;
            case R.id.color2:
                this.currentColor = 1;
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color2);
                break;
            case R.id.color3:
                this.currentColor = 2;
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color3);
                break;
            case R.id.color4:
                this.currentColor = 3;
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color4);
                break;
            case R.id.color5:
                this.currentColor = 4;
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color5);
                break;
            case R.id.color6:
                this.currentColor = 5;
                this.findViewById(R.id.close_settings).setBackgroundResource(R.drawable.color6);
                break;
        }
        this.settings.setInt("color",this.currentColor);
        //Toast.makeText(v.getContext(), "Color: "+this.currentColor, Toast.LENGTH_SHORT).show();
    }

    // Change to the next language
    private void rotateLanguage(View v) {
        this.currentLanguage = (this.currentLanguage + 1) % this.codes.length;
        // Show ui in new language
        ((TextView) v.findViewById(R.id.language)).setText(this.codes[this.currentLanguage]);
        // Save lang
        this.settings.set("lang", this.currentLanguage);
    }

    // Change watchface
    private void change_watchface() {
        this.sendBroadcast(new Intent("com.huami.intent.action.WATCHFACE_CONFIG_CHANGED"));

        this.setResult(-1);
        this.finish();
    }
}