package com.dinodevs.greatfitwatchface.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Xdrip {
    public String JSONstr;

    public String sgv, delta, timeago, sgv_graph, strike, color, phonebattery;
    public Long timestamp;
    public Boolean badvalue, ishigh, islow, isstale, firstdata;

    public Xdrip(String parmStr1) {
        this.JSONstr = parmStr1;

        // Default values
        this.sgv = "--";
        this.delta = "--";
        this.timeago = "--";
        this.sgv_graph = "false";
        this.strike = "";
        this.color = "WHITE";
        this.phonebattery = "--";
        this.timestamp = Long.valueOf(1);
        this.badvalue = false;
        this.ishigh = false;
        this.islow = false;
        this.isstale = false;
        this.firstdata = false;

        if(parmStr1!=null && !parmStr1.equals("")){
            try {
                // Extract data from JSON
                JSONObject json_data = new JSONObject(parmStr1);

                // Based on com.klaus3d3.xDripwatchface.data.Xdrip.java
                // https://github.com/Klaus3d3/xDrip-Watchface

                if (json_data.has("sgv"))
                    sgv = json_data.getString("sgv");

                if (json_data.has("delta"))
                    delta = json_data.getString("delta");

                if (json_data.has("date")) {
                    timestamp = Long.valueOf(json_data.getString("date"));
                    //timeago = TimeAgo.using(Long.valueOf(json_data.getString("date")));
                }

                if (json_data.has("WFGraph"))
                    sgv_graph = json_data.getString("WFGraph");

                if (json_data.has("phone_battery"))
                    phonebattery = json_data.getString("phone_battery");

                if (json_data.has("ishigh"))
                    ishigh = Boolean.valueOf(json_data.getString("ishigh"));

                if (json_data.has("islow"))
                    islow = Boolean.valueOf(json_data.getString("islow"));

                if (json_data.has("isstale"))
                    isstale = Boolean.valueOf(json_data.getString("isstale"));

                if (!isstale && !islow && !ishigh)
                    color="BLACK";
                else
                    color="WHITE";

                if( System.currentTimeMillis() > timestamp+10*60*1000 )
                    strike = new String(new char[sgv.length()]).replace("\0", "─");
                else
                    strike = "";

            }catch (JSONException e) {
                // Nothing
            }
        }
    }

    public String toString() {
        // Default onDataUpdate value
        return String.format("[Xdrip data info] Sting 1:%s", this.JSONstr);
    }
}