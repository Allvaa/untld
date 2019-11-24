package com.allvaa.untld;

import kong.unirest.json.JSONObject;

import java.util.HashMap;

public class Untld {
    private final Config config;
    private final HashMap<String, JSONObject> afkData = new HashMap<>();

    public Untld(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return this.config;
    }

    public HashMap getAfkData() {
        return afkData;
    }
}
