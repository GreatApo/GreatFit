package com.dinodevs.greatfitwatchface.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Xdrip {
    public String JSONstr;

    public String sgv;
    public String delta;
    public String timeago;
    public String sgv_graph;


    public Xdrip(String parmStr1) {
        this.JSONstr = parmStr1;

        this.sgv = "--";
        this.delta = "--";
        this.timeago = "--";
        this.sgv_graph = "";

        if(parmStr1!=null && !parmStr1.equals("")){
            try {
                // Extract data from JSON
                JSONObject json_data = new JSONObject(parmStr1);
                this.sgv = json_data.getString("sgv");
                this.delta = json_data.getString("delta");
                this.timeago = json_data.getString("updatetime");
                this.sgv_graph=json_data.getString("sgv_graph");
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