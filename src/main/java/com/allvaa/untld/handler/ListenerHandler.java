package com.allvaa.untld.handler;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class ListenerHandler {
    public ListenerHandler(JDA client) {
        new Reflections("com.allvaa.untld.listeners")
                .getSubTypesOf(ListenerAdapter.class)
                .forEach(c -> {
                    try {
                        client.addEventListener(c.getConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }
}
