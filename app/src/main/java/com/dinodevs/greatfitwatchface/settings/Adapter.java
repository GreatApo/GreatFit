package com.dinodevs.greatfitwatchface.settings;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.dinodevs.greatfitwatchface.R;

import java.util.List;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final Context context;
    private final List<BaseSetting> settings;

    public Adapter(Context context, List<BaseSetting> settings){
        this.context = context;
        this.settings = settings;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Three layouts possible - Header, icon and switch
        if (viewType == 0) {
            //Header
            return new ViewHolder(layoutInflater.inflate(R.layout.item_header, parent, false));
        } else if (viewType == 1) {
            //Icon Item
            return new ViewHolder(layoutInflater.inflate(R.layout.item_preference_icon, parent, false));
        } else if (viewType == 2) {
            //Switch Item
            return new ViewHolder(layoutInflater.inflate(R.layout.item_preference_switch, parent, false));
        } else if (viewType == 3) {
            //Button Item
            return new ViewHolder(layoutInflater.inflate(R.layout.item_preference_button, parent, false));
        } else if (viewType == 4) {
            //Seekbar Item
            return new ViewHolder(layoutInflater.inflate(R.layout.item_preference_seekbar, parent, false));
        } else if (viewType == 5) {
            //Incremental Item
            return new ViewHolder(layoutInflater.inflate(R.layout.item_preference_increment, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        //Return the type of a given item
        BaseSetting setting = settings.get(position);
        if (setting instanceof HeaderSetting) return 0;
        if (setting instanceof IconSetting) return 1;
        if (setting instanceof SwitchSetting) return 2;
        if (setting instanceof ButtonSetting) return 3;
        if (setting instanceof SeekbarSetting) return 4;
        else return 5;
    }


    @Override
    public void onBindViewHolder(final Adapter.ViewHolder holder, int position) {
        //Get base setting for position
        BaseSetting setting = settings.get(position);
        if (setting instanceof HeaderSetting) {
            //Header, just set text
            holder.title.setText(((HeaderSetting) setting).title);
        } else if (setting instanceof SwitchSetting) {
            //Switch, setup the change listener and click listener for the root view
            SwitchSetting switchSetting = (SwitchSetting) setting;
            holder.sw.setOnCheckedChangeListener(switchSetting.changeListener);
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.sw.toggle();
                }
            });
            //Set default check
            holder.sw.setChecked(switchSetting.isChecked);
            //Setup title
            holder.title.setText(switchSetting.title);
            //Setup subtitle if required
            if (switchSetting.subtitle != null) {
                holder.subtitle.setText(switchSetting.subtitle);
                holder.subtitle.setVisibility(View.VISIBLE);
            } else {
                holder.subtitle.setText("");
                holder.subtitle.setVisibility(View.GONE);
            }
        } else if (setting instanceof ButtonSetting) {
            //Icon, setup icon, click listener and title
            ButtonSetting buttonSetting = (ButtonSetting) setting;
            holder.root.setOnClickListener(buttonSetting.onClickListener);
            holder.title.setText(buttonSetting.title);
            holder.title.setBackground(buttonSetting.bg);
        } else if (setting instanceof SeekbarSetting) {
            //Icon, setup icon, click listener and title
            SeekbarSetting seekbarSetting = (SeekbarSetting) setting;
            //holder.icon.setImageDrawable(seekbarSetting.icon);
            holder.sb.setMax(seekbarSetting.max);
            holder.sb.setProgress(seekbarSetting.current);
            holder.sb.setOnSeekBarChangeListener(seekbarSetting.onChangeListener);
            holder.title.setText(seekbarSetting.title);
            holder.subtitle.setText(seekbarSetting.subtitle);
            holder.subtitle.setVisibility(View.VISIBLE);
        } else if (setting instanceof IncrementalSetting) {
            //Icon, setup icon, click listeners, title, value
            IncrementalSetting IncrementalSetting = (IncrementalSetting) setting;
            holder.minus.setOnClickListener(IncrementalSetting.onClickLessListener);
            holder.plus.setOnClickListener(IncrementalSetting.onClickMoreListener);
            holder.title.setText(IncrementalSetting.title);
            holder.subtitle.setText(IncrementalSetting.subtitle);
            holder.value.setText(IncrementalSetting.value);
        }  else {
            //Icon, setup icon, click listener and title
            IconSetting iconSetting = (IconSetting) setting;
            holder.icon.setImageDrawable(iconSetting.icon);
            holder.root.setOnClickListener(iconSetting.onClickListener);
            holder.title.setText(iconSetting.title);
            holder.icon.setImageTintList(ColorStateList.valueOf(iconSetting.color));
            //Setup subtitle if required
            if (iconSetting.subtitle != null) {
                holder.subtitle.setText(iconSetting.subtitle);
                holder.subtitle.setVisibility(View.VISIBLE);
            } else {
                holder.subtitle.setText("");
                holder.subtitle.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View root;
        TextView title, subtitle, minus, plus, value;
        ImageView icon;
        Switch sw;
        SeekBar sb;

        public ViewHolder(View itemView) {
            super(itemView);
            //Set views
            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            sw = (Switch) itemView.findViewById(R.id.sw);
            sb = (SeekBar) itemView.findViewById(R.id.seekBar);
            minus = (TextView) itemView.findViewById(R.id.decrease);
            plus = (TextView) itemView.findViewById(R.id.increase);
            value = (TextView) itemView.findViewById(R.id.value);
            root = itemView;
        }
    }
}