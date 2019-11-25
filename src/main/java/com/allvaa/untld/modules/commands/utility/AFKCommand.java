package com.allvaa.untld.modules.commands.utility;

import com.allvaa.untld.Untld;
import com.allvaa.untld.modules.categories.UtilityCategory;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;

public class AFKCommand extends UtilityCategory {
    public AFKCommand(Untld untld) {
        super(untld);
        this.name = "afk";
        this.aliases = new String[]{"awayfromkeyboard"};
        this.help = "nothing.";
    }

    @Override
    protected void execute(CommandEvent event) {
        String reason;
        Number now = System.currentTimeMillis();
        if (event.getArgs().isEmpty()) {
            reason = "Not set.";
        } else {
            reason = event.getArgs();
        }
        JSONObject data = new JSONObject()
                .put("reason", reason)
                .put("timestamp", now);
        this.untld.getAfkData().put(event.getAuthor().getId(), data);
        event.reply("You're AFK because **" + reason + "**");
    }
}