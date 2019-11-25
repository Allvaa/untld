package com.allvaa.untld.modules.commands.general;

import com.allvaa.untld.Untld;
import com.allvaa.untld.modules.categories.GeneralCategory;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PingCommand extends GeneralCategory {
    public PingCommand(Untld untld) {
        super(untld);
        this.name = "ping";
        this.aliases = new String[]{"p"};
        this.help = "the bot latency";
    }

    @Override
    protected void execute(CommandEvent event) {
        String ping = String.valueOf(event.getJDA().getGatewayPing());
        event.reply(ping + "ms");
    }
}
