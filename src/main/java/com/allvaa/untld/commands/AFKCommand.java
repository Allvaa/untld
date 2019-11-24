package com.allvaa.untld.commands;

import com.allvaa.untld.Untld;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public class AFKCommand extends Command {
    private final Untld untld;

    public AFKCommand(Untld untld) {
        this.untld = untld;
        this.name = "afk";
        this.aliases = new String[]{"awayfromkeyboard"};
        this.help = "nothing.";
    }

    @Override
    protected void execute(CommandEvent event) {
        String reason;
        Number now = System.currentTimeMillis();
        if (event.getArgs().length() == 0) {
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