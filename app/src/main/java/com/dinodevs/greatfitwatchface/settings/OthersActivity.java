package com.dinodevs.greatfitwatchface.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dinodevs.greatfitwatchface.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OthersActivity extends FragmentActivity {
    public final String[] BUILD_VERGE_MODELS = {"qogir", "qogirUS"};
    public boolean isVerge(){
        return Arrays.asList(BUILD_VERGE_MODELS).contains(Build.PRODUCT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView root = new RecyclerView(this);

        //Add header to a list of settings
        List<BaseSetting> settings = new ArrayList<>();
        settings.add(new HeaderSetting(getString(R.string.other_features)));

        //Setup items
        final SharedPreferences sharedPreferences = getSharedPreferences(getPackageName()+"_settings", Context.MODE_PRIVATE);

        final boolean better_resolution_when_raising_hand = sharedPreferences.getBoolean( "better_resolution_when_raising_hand", getResources().getBoolean(R.bool.better_resolution_when_raising_hand));
        settings.add(new SwitchSetting(null, "Better resolution", "in slpt mode when raising hand", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "better_resolution_when_raising_hand", b).apply();
            }
        }, better_resolution_when_raising_hand));

        final boolean white_bg = sharedPreferences.getBoolean( "white_bg", getResources().getBoolean(R.bool.white_bg));
        settings.add(new SwitchSetting(null, "White background", "Theme with white bg - black text/icons", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "white_bg", b).apply();
            }
        }, white_bg));

        final boolean analog_clock = sharedPreferences.getBoolean( "analog_clock", getResources().getBoolean(R.bool.analog_clock));
        settings.add(new SwitchSetting(null, "Analog clock", "Show clock time hands", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "analog_clock", b).apply();
            }
        }, analog_clock));

        final boolean digital_clock = sharedPreferences.getBoolean( "digital_clock", getResources().getBoolean(R.bool.digital_clock));
        settings.add(new SwitchSetting(null, "Digital clock", "Show digital clock", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "digital_clock", b).apply();
            }
        }, digital_clock));

        final boolean clock_only_slpt = sharedPreferences.getBoolean( "clock_only_slpt", isVerge() || getResources().getBoolean(R.bool.clock_only_slpt));
        settings.add(new SwitchSetting(null, "SLPT clock only", "Show only clock when screen is off", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "clock_only_slpt", b).apply();
            }
        }, clock_only_slpt));

        final boolean flashing_indicator = sharedPreferences.getBoolean( "flashing_indicator", getResources().getBoolean(R.bool.flashing_indicator));
        settings.add(new SwitchSetting(null, "Flashing \":\"", "Make time's \":\" flashing", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "flashing_indicator", b).apply();
            }
        }, flashing_indicator));

        final boolean month_as_text = sharedPreferences.getBoolean( "month_as_text", getResources().getBoolean(R.bool.month_as_text));
        settings.add(new SwitchSetting(null, "Month as text", "Show month as text", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "month_as_text", b).apply();
            }
        }, month_as_text));

        final boolean three_letters_month_if_text = sharedPreferences.getBoolean( "three_letters_month_if_text", getResources().getBoolean(R.bool.three_letters_month_if_text));
        settings.add(new SwitchSetting(null, "3 letters month", "If text (ex.APR)", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "three_letters_month_if_text", b).apply();
            }
        }, three_letters_month_if_text));

        final boolean three_letters_day_if_text = sharedPreferences.getBoolean( "three_letters_day_if_text", getResources().getBoolean(R.bool.three_letters_day_if_text));
        settings.add(new SwitchSetting(null, "3 letters day", "(ex.MON)", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "three_letters_day_if_text", b).apply();
            }
        }, three_letters_day_if_text));

        final boolean no_0_on_hour_first_digit = sharedPreferences.getBoolean( "no_0_on_hour_first_digit", getResources().getBoolean(R.bool.no_0_on_hour_first_digit));
        settings.add(new SwitchSetting(null, "No 0 in hours/months", "Remove 0 if first digit", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "no_0_on_hour_first_digit", b).apply();
            }
        }, no_0_on_hour_first_digit));

        final boolean wind_direction_as_arrows = sharedPreferences.getBoolean( "wind_direction_as_arrows", getResources().getBoolean(R.bool.wind_direction_as_arrows));
        settings.add(new SwitchSetting(null, "Wind as arrows", "Wind direction as arrows", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "wind_direction_as_arrows", b).apply();
            }
        }, wind_direction_as_arrows));

        final boolean status_bar = sharedPreferences.getBoolean( "status_bar", getResources().getBoolean(R.bool.status_bar));
        settings.add(new SwitchSetting(null, "Show status bar", "Status bar (charging icon etc)", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "status_bar", b).apply();
            }
        }, status_bar));

        final boolean flashing_heart_rate_icon = sharedPreferences.getBoolean( "flashing_heart_rate_icon", getResources().getBoolean(R.bool.flashing_heart_rate_icon));
        settings.add(new SwitchSetting(null, "Animate heart rate", "Flashing heart rate icon", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "flashing_heart_rate_icon", b).apply();
            }
        }, flashing_heart_rate_icon));

        final boolean am_pm_always = sharedPreferences.getBoolean( "am_pm_always", getResources().getBoolean(R.bool.am_pm_always));
        settings.add(new SwitchSetting(null, "Always am/pm", "Show am/pm on 24h format", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "am_pm_always", b).apply();
            }
        }, am_pm_always));

        final int target_calories = sharedPreferences.getInt( "target_calories", 1000);
        settings.add(
                new IncrementalSetting(null, "Target calories", "Current: "+target_calories,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int new_value = sharedPreferences.getInt( "target_calories", 1000)-50;
                                if(new_value>=100) {
                                    sharedPreferences.edit().putInt("target_calories", new_value).apply();
                                    View parent = (View) view.getParent();
                                    TextView value = (TextView) parent.findViewById(R.id.value);
                                    value.setText(new_value+"");
                                }
                            }
                        },new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int new_value = sharedPreferences.getInt( "target_calories", 1000)+50;
                        if(new_value<=10000) {
                            sharedPreferences.edit().putInt("target_calories", new_value).apply();
                            View parent = (View) view.getParent();
                            TextView value = (TextView) parent.findViewById(R.id.value);
                            value.setText(new_value+"");
                        }
                    }
                }, target_calories+""
                )
        );

        /*
        final int custom_refresh_rate = sharedPreferences.getInt( "custom_refresh_rate", getResources().getInteger(R.integer.custom_refresh_rate)*1000);
        settings.add(new SeekbarSetting(null, "Air pressure refresh sec", null, new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sharedPreferences.edit().putInt( "custom_refresh_rate", progress).apply();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(seekBar.getContext(), "Refresh rate: "+seekBar.getProgress()+" sec", Toast.LENGTH_SHORT).show();
            }
        }, custom_refresh_rate, 60));
         */

        final int custom_refresh_rate = sharedPreferences.getInt( "custom_refresh_rate", getResources().getInteger(R.integer.custom_refresh_rate)*1000);
        settings.add(
                new IncrementalSetting(null, "Custom refresh", "Pressure/Walked distance: "+((custom_refresh_rate/1000<120)?Math.round(custom_refresh_rate/1000)+" sec":Math.round(custom_refresh_rate/1000)/60+" min"),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int new_value = Math.round(sharedPreferences.getInt( "custom_refresh_rate", getResources().getInteger(R.integer.custom_refresh_rate)*1000)/1000-5);
                                if(new_value>115)
                                    new_value = new_value - 55;
                                if(new_value>=0) {
                                    sharedPreferences.edit().putInt("custom_refresh_rate", new_value*1000).apply();
                                    View parent = (View) view.getParent();
                                    TextView value = (TextView) parent.findViewById(R.id.value);
                                    value.setText((new_value<120)?new_value+" sec":new_value/60+" min");
                                }
                            }
                        },new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int new_value = Math.round(sharedPreferences.getInt( "custom_refresh_rate", getResources().getInteger(R.integer.custom_refresh_rate)*1000)/1000+5);
                        if(new_value>120)
                            new_value = new_value + 55;
                        sharedPreferences.edit().putInt("custom_refresh_rate", new_value*1000).apply();
                        View parent = (View) view.getParent();
                        TextView value = (TextView) parent.findViewById(R.id.value);
                        value.setText((new_value<120)?new_value+" sec":new_value/60+" min");
                    }
                }, (custom_refresh_rate/1000<120)?Math.round(custom_refresh_rate/1000)+" sec":Math.round(custom_refresh_rate/1000)/60+" min")
        );

        final boolean pressure_to_mmhg = sharedPreferences.getBoolean( "pressure_to_mmhg", getResources().getBoolean(R.bool.pressure_to_mmhg));
        settings.add(new SwitchSetting(null, "Pressure units", "(off: hPa, on: mmHg)", new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean( "pressure_to_mmhg", b).apply();
            }
        }, pressure_to_mmhg));

        final float world_time_zone = sharedPreferences.getFloat( "world_time_zone", 0f);
        settings.add(
                new IncrementalSetting(null, "World-time zone", "Current: GMT "+((world_time_zone > 0) ? "+" + world_time_zone : world_time_zone),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                float new_value = sharedPreferences.getFloat( "world_time_zone", 0)-0.5f;
                                if(new_value>=-12) {
                                    sharedPreferences.edit().putFloat("world_time_zone", new_value).apply();
                                    View parent = (View) view.getParent();
                                    TextView value = (TextView) parent.findViewById(R.id.value);
                                    value.setText("GMT " + ((new_value > 0) ? "+" + new_value : new_value));
                                }
                            }
                        },new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        float new_value = sharedPreferences.getFloat( "world_time_zone", 0)+0.5f;
                        if(new_value<=12) {
                            sharedPreferences.edit().putFloat("world_time_zone", new_value).apply();
                            View parent = (View) view.getParent();
                            TextView value = (TextView) parent.findViewById(R.id.value);
                            value.setText("GMT " + ((new_value > 0) ? "+" + new_value : new_value));
                        }
                    }
                }, "GMT " + ((world_time_zone > 0) ? "+" + world_time_zone : world_time_zone))
        );

        final int height = sharedPreferences.getInt( "height", 175);
        settings.add(
                new IncrementalSetting(null, "Set height", "Current: "+height+" cm",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int new_value = sharedPreferences.getInt( "height", 175)-1;
                                if(new_value>=100) {
                                    sharedPreferences.edit().putInt("height", new_value).apply();
                                    View parent = (View) view.getParent();
                                    TextView value = (TextView) parent.findViewById(R.id.value);
                                    value.setText(new_value+" cm");
                                }
                            }
                        },new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int new_value = sharedPreferences.getInt( "height", 175)+1;
                        if(new_value<=250) {
                            sharedPreferences.edit().putInt("height", new_value).apply();
                            View parent = (View) view.getParent();
                            TextView value = (TextView) parent.findViewById(R.id.value);
                            value.setText(new_value + " cm");
                        }
                    }
                }, height+" cm")
        );

        //Setup layout
        root.setBackgroundResource(R.drawable.settings_background);
        root.setLayoutManager(new LinearLayoutManager(this));
        root.setAdapter(new Adapter(this, settings));
        root.setPadding((int) getResources().getDimension(R.dimen.padding_round_small), 0, (int) getResources().getDimension(R.dimen.padding_round_small), (int) getResources().getDimension(R.dimen.padding_round_large));
        root.setClipToPadding(false);
        setContentView(root);
    }
}
