package com.allvaa.untld.commands;

import com.allvaa.untld.Untld;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PingCommand extends Command {
    private final Untld untld;

    public PingCommand(Untld untld) {
        this.untld = untld;
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