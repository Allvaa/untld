package com.allvaa.untld.listeners;

import com.allvaa.untld.Untld;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class MessageReceivedListener extends ListenerAdapter {
    private final Untld untld;
    private final HashMap afkData;

    public MessageReceivedListener(Untld untld) {
        this.untld = untld;
        this.afkData = this.untld.getAfkData();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (this.afkData.get(event.getAuthor().getId()) != null) {
            this.untld.getAfkData().remove(event.getAuthor().getId());
            event.getChannel().sendMessage("You removed from AFK!").queue();
        }
        if (!event.getMessage().getMentionedUsers().isEmpty() && this.afkData.get(event.getMessage().getMentionedUsers().get(0).getId()) != null) {
            User user = event.getMessage().getMentionedUsers().get(0);
            JSONObject data = (JSONObject) this.afkData.get(event.getMessage().getMentionedUsers().get(0).getId());
            event.getChannel().sendMessage("***" + user.getAsTag() + "*** was AFK because **" + data.get("reason") + "**").queue();
        }
    }
}
