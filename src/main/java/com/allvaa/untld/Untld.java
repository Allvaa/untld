package com.allvaa.untld;

import com.allvaa.untld.handler.music.QueueConstruct;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;

public class Untld {
    private final Config config;
    private final HashMap<String, JSONObject> afkData = new HashMap<>();
    private final HashMap<String, QueueConstruct> queue = new HashMap<>();

    public Untld(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return config;
    }

    public HashMap<String, JSONObject> getAfkData() {
        return afkData;
    }

    public HashMap<String, QueueConstruct> getQueue() {
        return queue;
    }

    public QueueConstruct getGuildQueue(Guild guild) {
        return queue.get(guild.getId());
    }

    public QueueConstruct setGuildQueue(Guild guild, QueueConstruct obj) {
        return queue.put(guild.getId(), obj);
    }

    public QueueConstruct removeGuildQueue(Guild guild) {
        guild.getAudioManager().closeAudioConnection();
        return queue.remove(guild.getId());
    }
}
