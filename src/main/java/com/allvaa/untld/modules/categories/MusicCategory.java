package com.allvaa.untld.modules.categories;

import com.allvaa.untld.Untld;
import com.allvaa.untld.handler.Category;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

import static com.allvaa.untld.handler.CommandHandler.cmdClient;

public class MusicCategory extends Category {
    public MusicCategory(Untld untld) {
        super(untld);
        this.category = new Category("Music");

        new Reflections("com.allvaa.untld.modules.commands")
                .getSubTypesOf(this.getClass())
                .forEach(c -> {
                    try {
                        MusicCategory cmd = c.getConstructor(untld.getClass()).newInstance(untld);
                        commands.add(cmd);
                        cmdClient.addCommand(cmd);
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    protected void execute(CommandEvent event) {}
}