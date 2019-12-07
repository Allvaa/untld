package com.allvaa.untld.handler;

import com.allvaa.untld.Untld;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;

import java.util.ArrayList;

import static com.allvaa.untld.handler.CommandHandler.cmdClient;

public abstract class Category extends Command {
    public final Untld untld;
    public final CommandClient commandClient;
    public final ArrayList<com.allvaa.untld.handler.Category> commands;

    public Category(Untld untld) {
        this.commandClient = cmdClient.build();
        this.untld = untld;
        this.commands = new ArrayList<>();
    }
}
