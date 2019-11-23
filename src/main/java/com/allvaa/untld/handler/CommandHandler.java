package com.allvaa.untld.handler;

import com.allvaa.untld.Config;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.reflections.Reflections;

public class CommandHandler {
    public CommandHandler(JDA client) {
        CommandClientBuilder cmdClient = new CommandClientBuilder()
                .setOwnerId(Config.ownerID)
                .setPrefix(Config.prefix)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.playing("In Development!"));
        new Reflections("com.allvaa.untld.commands")
                .getSubTypesOf(Command.class)
                .forEach(c -> {
                    try {
                        cmdClient.addCommand(c.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        client.addEventListener(cmdClient.build());
    }
}
