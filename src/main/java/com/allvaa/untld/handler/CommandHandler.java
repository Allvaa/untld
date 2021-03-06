package com.allvaa.untld.handler;

import com.allvaa.untld.Untld;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class CommandHandler {
    public static CommandClientBuilder cmdClient;
    public static EventWaiter waiter;
    public static ArrayList<Category> categories;

    public CommandHandler(JDA client, Untld untld) {
        categories = new ArrayList<>();
        waiter = new EventWaiter();
        cmdClient = new CommandClientBuilder()
                .setOwnerId(untld.getConfig().getOwnerID())
                .setCoOwnerIds(untld.getConfig().getCoOwnersID())
                .setPrefix(untld.getConfig().getPrefix())
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.playing("In Development!"))
                .useHelpBuilder(false);

        new Reflections("com.allvaa.untld.modules.categories")
                .getSubTypesOf(Category.class)
                .forEach(c -> {
                    try {
                        categories.add(c.getConstructor(untld.getClass()).newInstance(untld));
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });
        client.addEventListener(waiter);
        client.addEventListener(cmdClient.build());
    }
}
