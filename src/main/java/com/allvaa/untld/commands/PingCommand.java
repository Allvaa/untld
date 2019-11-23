package com.allvaa.untld.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PingCommand extends Command {
    public PingCommand() {
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