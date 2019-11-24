package com.allvaa.untld.handler;

import com.allvaa.untld.Untld;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class CommandHandler {
    public CommandHandler(JDA client, Untld untld) {
        CommandClient cmdClient = new CommandClientBuilder()
                .setOwnerId(untld.getConfig().getOwnerID())
                .setCoOwnerIds(untld.getConfig().getCoOwnersID())
                .setPrefix(untld.getConfig().getPrefix())
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.playing("In Development!"))
                .build();
        new Reflections("com.allvaa.untld.commands")
                .getSubTypesOf(Command.class)
                .forEach(c -> {
                    try {
                        cmdClient.addCommand(c.getConstructor(untld.getClass()).newInstance(untld));
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
        client.addEventListener(cmdClient);
    }
}
