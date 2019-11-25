package com.allvaa.untld.modules.categories;

import com.allvaa.untld.Untld;
import static com.allvaa.untld.handler.CommandHandler.cmdClient;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class UtilityCategory extends Command {
    public final Untld untld;
    public final CommandClient commandClient;

    public UtilityCategory(Untld untld) {
        this.commandClient = cmdClient;
        this.untld = untld;
        this.category = new Category("general");

        new Reflections("com.allvaa.untld.modules.commands")
                .getSubTypesOf(this.getClass())
                .forEach(c -> {
                    try {
                        cmdClient.addCommand(c.getConstructor(untld.getClass()).newInstance(untld));
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    protected void execute(CommandEvent event) {}
}