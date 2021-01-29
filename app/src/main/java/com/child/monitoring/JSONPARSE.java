package com.child.monitoring;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nevon Dell on 4/3/2017.
 */

class JSONPARSE {

    public String parse(JSONObject json){
        String name = " ";
        try {
            name = json.getString("Value");
        } catch (JSONException e) {
//            e.printStackTrace();
            name =e.getMessage();

        }
        return name;
    }

}
