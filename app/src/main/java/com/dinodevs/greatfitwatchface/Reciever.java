package com.dinodevs.greatfitwatchface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Reciever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent serviceIntent = new Intent(context, CustomDataUpdater.class);
            context.startService(serviceIntent);
        }
}
